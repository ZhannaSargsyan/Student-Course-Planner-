from flask import Flask, render_template, request
import requests
import bleach

app = Flask(__name__)

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

    # Placeholder: Echoing for now
    followup_prompt = f"Follow-up question: {question}\n\nOriginal response:\n{original}"
    # followup_response = get_gemini_plan(followup_prompt)
    followup_response = followup_prompt  # temporary echo

    return render_template('followup.html',
                           original=original,
                           question=question,
                           followup_response=followup_response)

@app.route('/plan', methods=['POST'])
def plan():
    fields = ['name', 'surname', 'student_id', 'degree', 'workload', 'interests', 'availability']
    cleaned_data = {f: bleach.clean(request.form[f]) for f in fields}

    degree = cleaned_data['degree']

    degree_map = {
        "BAB"    : "Bachelor of Arts in Business",
        "BAEC"   : "Bachelor of Arts in English and Communications",
        "BAPG"   : "Bachelor of Arts in Politics and Governance",
        "BSCS"   : "Bachelor of Science in Computer Science",
        "BSDS"   : "Bachelor of Science in Data Science",
        "BSES"   : "Bachelor of Science in Engineering Sciences",
        "BSN"    : "Bachelor of Science in Nursing",
        "BSESS"  : "Bachelor of Science in Environmental and Sustainability Sciences",
        "LL.M."  : "Master of Laws",
        "MAIRD"  : "Master of Arts in International Relations and Diplomacy",
        "MPA"    : "Master of Public Affairs",
        "MAHRSJ" : "Master of Arts in Human Rights and Social Justice",
        "MATEFL" : "Master of Arts in Teaching English as a Foreign Language",
        "MSE"    : "Master of Science in Economics",
        "MBA"    : "Master of Business Administration",
        "MSMA"   : "Master of Science in Management and Analytics",
        "MEIESM" : "Master of Engineering in Industrial Engineering and Systems Management",
        "MSCIS"  : "Master of Science in Computer and Information Science",
        "MPH"    : "Master of Public Health",
        "MAMJ"   : "Master of Arts in Multiplatform Journalism"
    }

    full_degree_name = degree_map.get(degree, degree)

    prompt = f"""
    Generate a semester plan for the student: {cleaned_data['name']} {cleaned_data['surname']}, ID: {cleaned_data['student_id']}

    Some additinal inforamation about the student:
    Degree Program: {full_degree_name}
    Preferred workload: {cleaned_data['workload']}
    Interests: {cleaned_data['interests']}
    Availability: {cleaned_data['availability']}
    """

    print(prompt)

    gemini_response = get_gemini_plan(prompt)
    return render_template('result.html', response=gemini_response)

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

