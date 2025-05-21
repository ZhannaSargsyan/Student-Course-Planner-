# Student Course Planner with Gemini AI

A Spring Boot application that uses Google's Gemini AI to provide personalized course recommendations.

## Prerequisites

- Java 17 or higher
- Maven
- Google Gemini API key
- Docker

## Setup

1. Clone the repository:
```bash
git clone https://github.com/ZhannaSargsyan/Student-Course-Planner-.git
cd student-course-planner
```

2. Set up environment variables:
```bash
# For macOS/Linux
export GENAI_API_KEY=your_gemini_api_key
export JWT_SECRET=your_jwt_secret_at_least_32_chars_long

# For Windows
set GENAI_API_KEY=your_gemini_api_key
set JWT_SECRET=your_jwt_secret_at_least_32_chars_long
```

3. Option 1: Build and run the application using Maven:
```bash
mvn clean install
mvn spring-boot:run
```

4. Option 2: Build and run the application using Docker:
```bash
GENAI_API_KEY=your_gemini_api_key JWT_SECRET=your_jwt_secret_at_least_32_chars_long docker-compose up --build
```

The application will start on `http://localhost:8080`
