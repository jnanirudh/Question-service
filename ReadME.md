# Question Service
Microservice responsible for managing the question bank and evaluating student responses.

## Tech Stack
* Java 17 / Spring Boot
* Spring Data JPA (MySQL)
* Lombok

## Endpoints
### Question Management (Admin)
* `GET /questions` - Retrieve all questions.
* `POST /questions/add` - Add a new question (Auto-generates ID).
* `DELETE /questions/delete/{id}` - Remove a question via Path Variable.

### Microservice Internal API (Feign)
* `GET /questions/generate?subject={s}&numQ={n}` - Returns a list of random IDs.
* `POST /questions/getQuestionsFromId` - Returns `QuestionWrapper` list (no answers).
* `POST /questions/getScore` - Accepts `List<Response>`, returns `Integer` score.

## Database Configuration
* **Database Name:** `question_db`
* **Table:** `question`