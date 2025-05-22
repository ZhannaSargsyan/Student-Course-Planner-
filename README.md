# Student Course Planner with Gemini AI

A SpringBoot + Flask application that uses Google's Gemini AI to provide personalized course recommendations.

---

##  Overview

This application allows students to receive personalized course plans based on:
- Degree program
- Academic interests
- Workload preferences
- Weekly availability

---

## Tech Stack

- **Backend**: Java + Spring Boot
- **Frontend**: Python + Flask
- **Database**: PostgreSQL
- **AI Integration**: Gemini API by Google
- **DevOps**: Docker + Docker Compose

---

## Project Structure

```
.
├── frontend-python/                # Flask frontend
│   ├── app.py                      # Entry point
│   ├── templates/                  # HTML templates
│   ├── static/                     # CSS
│   └── requirements.txt
├── src/
│   └── main/java/com/planner/
│       ├── api/                    # REST Controllers
│       ├── business/               # Services, DTOs, PromptBuilder
│       ├── data/                   # Entities
│       ├── persistence/            # Repositories
│       └── config/                 # Security config
├── application.yml
├── Data.sql                        # Seed data for DB
├── docker-compose.yml
└── README.md
```

## Prerequisites

- Docker, Docker Compose
  - Java 17+
  - Python 3.9+
  - PostgreSQL
- Google Gemini API key

---

## Local Setup

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

The app will start on:

- Frontend (Flask): http://localhost:5001 
- Backend (Spring Boot): http://localhost:8080  
- Database (PostgreSQL): http://localhost:5432

Make sure these ports are available.

## Running Without Docker (Alternative)

#### Backend (Java + Spring Boot)
```bash
cd Student-Course-Planner-
export GENAI_API_KEY=...
export JWT_SECRET=...
mvn spring-boot:run
```

#### Frontend (Python Flask)
```bash
cd frontend-python
python -m venv venv
source venv/bin/activate
pip install -r requirements.txt
python app.py
```

If using Linux, you should use `python3` instead of `python`

---

## API Endpoints

| Method | Endpoint               | Description                                  |
|--------|------------------------|----------------------------------------------|
| POST   | `/api/plan/preview`    | Submit student preferences, get AI prompt    |
| POST   | `/api/session`         | Start Gemini chat session                    |
| GET    | `/api/session`         | Retrieve chat history                        |
| DELETE | `/api/session`         | End Gemini session                           |

## How Gemini AI Works

- Backend gathers student data and eligible courses
- Builds a detailed prompt using PromptBuilder
- Sends prompt to Gemini AI
- Receives natural-language course plan and sends it to frontend

## License

This project is for **educational/demo** purposes.

---

## Authors

- [Zhanna Sargsyan](https://github.com/ZhannaSargsyan)
- [Ruzanna Ghazaryan](https://github.com/ruzannaghazaryan)
- [Tigran Ohanyan](https://github.com/Audman)
- [Syuzanna Harutyunyan](https://github.com/syuzannaharutyunyan7)
- [Nane Andreasyan](https://github.com/nane-andreasyan)
- [Anahit Apresyan](https://github.com/AnApresyan)
