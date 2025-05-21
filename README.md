# Student Course Planner with Gemini AI

A Spring Boot + Flask application that uses Google's Gemini AI to provide personalized course recommendations.

## Prerequisites
- Docker
- Docker Compose
- Google Gemini API key

## Setup

1. Clone the repository:
```bash
git clone https://github.com/ZhannaSargsyan/Student-Course-Planner-.git
cd student-course-planner
```

2. Set environment variables and run with Docker:
```bash
export GENAI_API_KEY=your_gemini_api_key
export JWT_SECRET=your_jwt_secret_at_least_32_chars_long
docker-compose up --build
```
The application will start on `http://localhost:5001/`

Note: This app binds the Flask frontend to port 5001, the Spring Boot backend to 8080, and PostgreSQL to 5432 on your machine.
Please make sure these ports are available before running the application with Docker.
