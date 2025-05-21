# Student Course Planner with Gemini AI

A Spring Boot application that uses Google's Gemini AI to provide personalized course recommendations.

## Prerequisites

- Java 17 or higher
- Maven
- Google Gemini API key

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
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

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`
