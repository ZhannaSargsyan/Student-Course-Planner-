# Student Course Planner with Gemini AI

A SpringBoot + Flask application that uses Google's Gemini AI to provide personalized course recommendations.

## Prerequisites

- Docker
- Docker Compose
- Google Gemini API key

## Setup

#### 1. Clone the repository:
```bash
git clone https://github.com/ZhannaSargsyan/Student-Course-Planner-.git
cd Student-Course-Planner-
```

#### 2. Set environment variables:
##### For macOS/Linux
```bash
export GENAI_API_KEY=your_gemini_api_key
export JWT_SECRET=your_jwt_secret_at_least_32_chars_long
```
##### For Windows
```bash
set GENAI_API_KEY=your_gemini_api_key
set JWT_SECRET=your_jwt_secret_at_least_32_chars_long
```
####  3. Run with Docker:
```bash
docker-compose up --build
```
The application will start on `http://localhost:5001/`

---

#### Note: 
This app binds: 
- Flask frontend to port 5001 
- Spring Boot backend to port 8080
- PostgreSQL to port 5432

Please make sure these ports are available before running the application with Docker.
