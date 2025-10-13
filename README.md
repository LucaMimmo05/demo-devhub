# 🚀 DevHub - Dashboard for Developers

DevHub is a complete platform for managing projects, tasks, and commands for developers. It helps you organize your work, track progress, and save useful commands.

## 📋 Features

- **Project Management**: Create and monitor your development projects
- **Task Management**: Organize activities with priorities, deadlines, and statuses
- **Command Library**: Save and retrieve useful commands
- **JWT Authentication**: Secure login/registration system with refresh token
- **GitHub OAuth Integration**: (in development)
- **RESTful API**: Full architecture with documented endpoints

## 🛠️ Technologies

- **Framework**: Quarkus 3.21.3
- **Java**: 21
- **Database**: PostgreSQL
- **ORM**: Hibernate ORM Panache
- **Security**: JWT (JSON Web Tokens) with SmallRye JWT
- **Password Hashing**: BCrypt
- **Build Tool**: Maven
- **Testing**: JUnit 5, REST Assured

## 📦 Main Dependencies

- Quarkus REST (JAX-RS)
- Quarkus Hibernate ORM Panache
- Quarkus JDBC PostgreSQL
- Quarkus Security & SmallRye JWT
- JJWT (0.11.5)
- BCrypt (0.4)

## 🚀 Installation and Setup

### Prerequisites

- Java 21 or higher
- Maven 3.8+
- PostgreSQL

### Database Configuration

1. Create a PostgreSQL database:
```sql
CREATE DATABASE devhub;
```

2. Configure the `src/main/resources/application.properties` file:
```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=your_username
quarkus.datasource.password=your_password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/devhub
quarkus.hibernate-orm.database.generation=update
```

### Starting the Application

#### Development Mode
```bash
./mvnw quarkus:dev
```

#### Build and Run
```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

The application will be available at `http://localhost:8080`

## 📚 API Endpoints

### 🔐 Authentication (`/api/auth`)

#### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "Mario Rossi",
  "email": "mario@example.com",
  "password": "password123"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "mario@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "message": "Login successful"
}
```

#### Refresh Token
```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGc..."
}
```

#### Verify Token
```http
POST /api/auth/verify
Content-Type: application/json

{
  "token": "eyJhbGc..."
}
```

---

### 📁 Projects (`/api/project`)

> ⚠️ All endpoints require authentication via header:
> `Authorization: Bearer {accessToken}`

#### Get all user projects
```http
GET /api/project
```

**Response:**
```json
[
  {
    "id": 152,
    "name": "Devhub",
    "description": "Dashboard for Developers",
    "progress": 1,
    "status": "IN_PROGRESS",
    "technologies": "Java Python",
    "notes": "nothing",
    "folderColor": "RED",
    "createdAt": "2025-01-10T10:30:00",
    "updatedAt": "2025-01-13T15:20:00",
    "user": {
      "id": 101,
      "name": "luca",
      "email": "luca@gmail.com"
    }
  }
]
```

#### Get project by ID
```http
GET /api/project/{id}
```

#### Create new project
```http
POST /api/project
Content-Type: application/json

{
  "name": "New Project",
  "description": "Project description",
  "status": "NOT_STARTED",
  "progress": 0,
  "technologies": ["Java", "React"],
  "notes": "Various notes",
  "folderColor": "BLUE"
}
```

#### Update project
```http
PUT /api/project/{id}
Content-Type: application/json

{
  "name": "Updated Project",
  "progress": 50,
  "status": "IN_PROGRESS"
}
```

#### Delete project
```http
DELETE /api/project/{id}
```

---

### ✅ Tasks (`/api/task`)

> ⚠️ Requires authentication

#### Get all tasks
```http
GET /api/task
```

#### Get not completed tasks
```http
GET /api/task/not-completed
```

#### Get completed tasks
```http
GET /api/task/completed
```

#### Create new task
```http
POST /api/task
Content-Type: application/json

{
  "title": "Implement authentication",
  "description": "Add JWT auth",
  "status": "NOT_STARTED",
  "priority": "HIGH",
  "dueDate": "2025-01-20"
}
```

**Available priorities:** `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`  
**Available statuses:** `NOT_STARTED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

#### Update task
```http
PUT /api/task/{id}
Content-Type: application/json

{
  "title": "Updated task",
  "status": "IN_PROGRESS"
}
```

#### Complete task
```http
PUT /api/task/{id}/complete
```

#### Delete task
```http
DELETE /api/task/{id}
```

---

### 💻 Commands (`/api/command`)

> ⚠️ Requires authentication

#### Get all commands
```http
GET /api/command
```

#### Get random command
```http
GET /api/command/random/{userId}
```

#### Create new command
```http
POST /api/command
Content-Type: application/json

{
  "title": "Docker build",
  "commandText": "docker build -t myapp .",
  "description": "Build Docker image"
}
```

#### Update command
```http
PUT /api/command/{id}
Content-Type: application/json

{
  "title": "Updated Docker build",
  "commandText": "docker build -t myapp:latest ."
}
```

#### Delete command
```http
DELETE /api/command/{id}
```

---

### 👤 Users (`/api/user`)

> ⚠️ Requires authentication

#### Get current user info
```http
GET /api/user/me
```

---

## 🗄️ Data Models

### Project
- `id`: Long
- `name`: String (required)
- `description`: String
- `progress`: int (0-100)
- `status`: Enum (NOT_STARTED, IN_PROGRESS, COMPLETED, CANCELLED)
- `technologies`: List<String>
- `notes`: String
- `folderColor`: Enum (RED, BLUE, GREEN, YELLOW, PURPLE, etc.)
- `createdAt`: LocalDateTime
- `updatedAt`: LocalDateTime
- `user`: User (Many-to-One relationship)

### Task
- `id`: Long
- `title`: String (required)
- `description`: String
- `status`: Enum (NOT_STARTED, IN_PROGRESS, COMPLETED, CANCELLED)
- `priority`: Enum (LOW, MEDIUM, HIGH, CRITICAL)
- `dueDate`: LocalDate
- `createdAt`: LocalDateTime
- `updatedAt`: LocalDateTime
- `user`: User (Many-to-One relationship)

### Command
- `id`: Long
- `title`: String (required)
- `commandText`: String (required)
- `description`: String
- `createdAt`: LocalDateTime
- `user`: User (Many-to-One relationship)

### User
- `id`: Long
- `name`: String (required)
- `email`: String (required, unique)
- `password`: String (hashed with BCrypt)
- `role`: Enum (USER, ADMIN)
- `createdAt`: LocalDateTime

## 🔒 Security

- Passwords are hashed with BCrypt
- JWT-based authentication (Access Token + Refresh Token)
- Access Token: short duration (e.g., 15 minutes)
- Refresh Token: extended duration (e.g., 7 days)
- RSA keys for JWT signing (private/public key)
- Endpoint protection via `@Authenticated`

## 🧪 Testing

Run tests:
```bash
./mvnw test
```

## 📝 Notes

- The project uses Quarkus as the framework, optimized for cloud and containers
- Native build support with GraalVM
- Configuration available in `application.properties`
- JWT keys in `META-INF/privateKey.pem` and `META-INF/publicKey.pem`

## 🐛 Known Issues

1. ~~**getRandomCommand**: The method for searching a random command does not work correctly~~ (check implementation in CommandService)
2. ~~**Project API**: The fields `createdAt` and `updatedAt` are not shown in the JSON response~~ (verify DTO mapping)

## 🚧 In Development

- [ ] Full GitHub OAuth integration
- [ ] GitHub API Controller
- [ ] UI/UX improvements
- [ ] Notification system
- [ ] Project export/import

## 👨‍💻 Author

Developed by Luca

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**Version**: 1.0-SNAPSHOT  
**Last Updated**: January 2025
