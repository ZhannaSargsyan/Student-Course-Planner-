from flask import Flask, render_template, request
import requests

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/plan', methods=['POST'])
def plan():
    name = request.form['name']
    surname = request.form['surname']
    student_id = request.form['student_id']
    degree = request.form['degree']
    workload = request.form['workload']
    interests = request.form['interests']
    availability = request.form['availability']

    prompt = f"""
    Generate a semester plan for:
    Student: {name} {surname}, ID: {student_id}
    Degree Program: {degree}
    Workload: {workload}
    Interests: {interests}
    Availability: {availability}
    """

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
    response = requests.post(endpoint, headers=headers, json=data)
    return response.json().get("text", "No response from Gemini")

if __name__ == '__main__':
    app.run(debug=True)
