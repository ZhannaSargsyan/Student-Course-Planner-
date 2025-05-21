from flask import Flask, render_template, request, session
import requests
import bleach
import re
import os

app = Flask(__name__)
app.secret_key = "your_super_secret_key"

@app.route('/')
def welcome():
    return render_template('welcome.html')

@app.route('/terms')
def terms():
    return render_template('terms.html')

@app.route('/start', methods=['POST'])
def start():
    if request.form.get('agree') == 'on':
        return render_template('index.html')
    else:
        return render_template('welcome.html', error="You must agree to the terms to continue.")

@app.route('/followup', methods=['POST'])
def followup():
    original = request.form.get('original', '[No original response]')
    question = request.form.get('question', '')
    jwt_token = session.get('jwt_token')
    backend_url = os.environ.get('BACKEND_URL', 'http://localhost:8080')
    headers = {'Authorization': f'Bearer {jwt_token}'} if jwt_token else {}
    followup_payload = {
        "message": question
    }
    response = requests.post(
        f"{backend_url}/api/messages",
        json=followup_payload,
        headers=headers
    ) if jwt_token else None
    followup_response = response.text if response else f"Follow-up question: {question}\n\nOriginal response:\n{original}"

    return render_template('followup.html',
                           original=original,
                           question=question,
                           followup_response=followup_response)

@app.route('/plan', methods=['POST'])
def plan():
    fields = ['name', 'surname', 'student_id', 'degree', 'workload', 'interests', 'availability', 'credits', 'taken_courses']
    cleaned_data = {f: bleach.clean(request.form[f]) for f in fields}

    try:
        desired_credits = float(cleaned_data['credits'])
    except ValueError:
        desired_credits = 0

    taken_courses = [ code.strip().upper() for code in cleaned_data['taken_courses'].split(',') if code.strip() ]
    valid_course_codes = [code for code in taken_courses if re.match(r'^[A-Z]{1,3}[0-9]{3}$', code)]

   # Build payload to match Java backend DTO
    payload = {
        "firstName": cleaned_data['name'],
        "lastName": cleaned_data['surname'],
        "studentId": cleaned_data['student_id'],
        "degreeProgram": cleaned_data['degree'],
        "preferredWorkload": cleaned_data['workload'],
        "academicInterests": cleaned_data['interests'],
        "weeklyAvailability": cleaned_data['availability'],
        "desiredCredits": desired_credits,
        "takenCourses": valid_course_codes
    }


    backend_url = os.environ.get('BACKEND_URL', 'http://localhost:8080')
    session_response = requests.post(f"{backend_url}/api/session")
    jwt_token = session_response.text.strip().replace('"', '')
    session['jwt_token'] = jwt_token

    headers = {'Authorization': f'Bearer {jwt_token}'}
    response = requests.post(
        f"{backend_url}/api/generate-plan",
        json=payload,
        headers=headers
    )
    backend_response = response.text

    return render_template('result.html', response=backend_response)

def get_gemini_plan(prompt):
    api_key = 'GEMINI_API_KEY'
    endpoint = 'https://GEMINI_ENDPOINT'
    headers = {
        'Authorization': f'Bearer {api_key}',
        'Content-Type': 'application/json'
    }
    data = {
        "prompt": prompt,
        "model": "gemini-1.5-pro"
    }

    # response = requests.post(endpoint, headers=headers, json=data)
    # return response.json().get("text", "No response from Gemini")

    return prompt

if __name__ == '__main__':
    app.run(debug=True)

