# 🚀 DevHub - Dashboard for Developers

DevHub è una piattaforma completa per la gestione di progetti, task e comandi per sviluppatori. Permette di organizzare il lavoro, tenere traccia dei progressi e salvare comandi utili.

## 📋 Caratteristiche

- **Gestione Progetti**: Crea e monitora i tuoi progetti di sviluppo
- **Task Management**: Organizza le attività con priorità, scadenze e stati
- **Command Library**: Salva e recupera comandi utili
- **Autenticazione JWT**: Sistema sicuro di login/registrazione con refresh token
- **Integrazione GitHub OAuth**: (in sviluppo)
- **API RESTful**: Architettura completa con endpoints documentati

## 🛠️ Tecnologie

- **Framework**: Quarkus 3.21.3
- **Java**: 21
- **Database**: PostgreSQL
- **ORM**: Hibernate ORM Panache
- **Sicurezza**: JWT (JSON Web Tokens) con SmallRye JWT
- **Password Hashing**: BCrypt
- **Build Tool**: Maven
- **Testing**: JUnit 5, REST Assured

## 📦 Dipendenze Principali

- Quarkus REST (JAX-RS)
- Quarkus Hibernate ORM Panache
- Quarkus JDBC PostgreSQL
- Quarkus Security & SmallRye JWT
- JJWT (0.11.5)
- BCrypt (0.4)

## 🚀 Installazione e Avvio

### Prerequisiti

- Java 21 o superiore
- Maven 3.8+
- PostgreSQL

### Configurazione Database

1. Crea un database PostgreSQL:
```sql
CREATE DATABASE devhub;
```

2. Configura il file `src/main/resources/application.properties`:
```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=your_username
quarkus.datasource.password=your_password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/devhub
quarkus.hibernate-orm.database.generation=update
```

### Avvio Applicazione

#### Modalità Development
```bash
./mvnw quarkus:dev
```

#### Build e Run
```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

L'applicazione sarà disponibile su `http://localhost:8080`

## 📚 API Endpoints

### 🔐 Autenticazione (`/api/auth`)

#### Registrazione
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

**Risposta:**
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

#### Verifica Token
```http
POST /api/auth/verify
Content-Type: application/json

{
  "token": "eyJhbGc..."
}
```

---

### 📁 Progetti (`/api/project`)

> ⚠️ Tutti gli endpoint richiedono autenticazione tramite header:
> `Authorization: Bearer {accessToken}`

#### Ottieni tutti i progetti dell'utente
```http
GET /api/project
```

**Risposta:**
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

#### Ottieni progetto per ID
```http
GET /api/project/{id}
```

#### Crea nuovo progetto
```http
POST /api/project
Content-Type: application/json

{
  "name": "Nuovo Progetto",
  "description": "Descrizione del progetto",
  "status": "NOT_STARTED",
  "progress": 0,
  "technologies": ["Java", "React"],
  "notes": "Note varie",
  "folderColor": "BLUE"
}
```

#### Aggiorna progetto
```http
PUT /api/project/{id}
Content-Type: application/json

{
  "name": "Progetto Aggiornato",
  "progress": 50,
  "status": "IN_PROGRESS"
}
```

#### Elimina progetto
```http
DELETE /api/project/{id}
```

---

### ✅ Task (`/api/task`)

> ⚠️ Richiede autenticazione

#### Ottieni tutti i task
```http
GET /api/task
```

#### Ottieni task non completati
```http
GET /api/task/not-completed
```

#### Ottieni task completati
```http
GET /api/task/completed
```

#### Crea nuovo task
```http
POST /api/task
Content-Type: application/json

{
  "title": "Implementare autenticazione",
  "description": "Aggiungere JWT auth",
  "status": "NOT_STARTED",
  "priority": "HIGH",
  "dueDate": "2025-01-20"
}
```

**Priorità disponibili:** `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`  
**Stati disponibili:** `NOT_STARTED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

#### Aggiorna task
```http
PUT /api/task/{id}
Content-Type: application/json

{
  "title": "Task aggiornato",
  "status": "IN_PROGRESS"
}
```

#### Completa task
```http
PUT /api/task/{id}/complete
```

#### Elimina task
```http
DELETE /api/task/{id}
```

---

### 💻 Comandi (`/api/command`)

> ⚠️ Richiede autenticazione

#### Ottieni tutti i comandi
```http
GET /api/command
```

#### Ottieni comando random
```http
GET /api/command/random/{userId}
```

#### Crea nuovo comando
```http
POST /api/command
Content-Type: application/json

{
  "title": "Docker build",
  "commandText": "docker build -t myapp .",
  "description": "Build Docker image"
}
```

#### Aggiorna comando
```http
PUT /api/command/{id}
Content-Type: application/json

{
  "title": "Docker build aggiornato",
  "commandText": "docker build -t myapp:latest ."
}
```

#### Elimina comando
```http
DELETE /api/command/{id}
```

---

### 👤 Utenti (`/api/user`)

> ⚠️ Richiede autenticazione

#### Ottieni informazioni utente corrente
```http
GET /api/user/me
```

---

## 🗄️ Modelli Dati

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
- `user`: User (relazione Many-to-One)

### Task
- `id`: Long
- `title`: String (required)
- `description`: String
- `status`: Enum (NOT_STARTED, IN_PROGRESS, COMPLETED, CANCELLED)
- `priority`: Enum (LOW, MEDIUM, HIGH, CRITICAL)
- `dueDate`: LocalDate
- `createdAt`: LocalDateTime
- `updatedAt`: LocalDateTime
- `user`: User (relazione Many-to-One)

### Command
- `id`: Long
- `title`: String (required)
- `commandText`: String (required)
- `description`: String
- `createdAt`: LocalDateTime
- `user`: User (relazione Many-to-One)

### User
- `id`: Long
- `name`: String (required)
- `email`: String (required, unique)
- `password`: String (hashed with BCrypt)
- `role`: Enum (USER, ADMIN)
- `createdAt`: LocalDateTime

## 🔒 Sicurezza

- Le password sono hashate con BCrypt
- Autenticazione basata su JWT (Access Token + Refresh Token)
- Access Token: durata breve (es. 15 minuti)
- Refresh Token: durata estesa (es. 7 giorni)
- Chiavi RSA per firma JWT (private/public key)
- Protezione degli endpoint tramite `@Authenticated`

## 🧪 Testing

Esegui i test:
```bash
./mvnw test
```

## 📝 Note

- Il progetto utilizza Quarkus come framework, ottimizzato per il cloud e container
- Supporto per build native con GraalVM
- Configurazione disponibile in `application.properties`
- Chiavi JWT in `META-INF/privateKey.pem` e `META-INF/publicKey.pem`

## 🐛 Issues Noti

1. ~~**getRandomCommand**: Il metodo per cercare un comando random non funziona correttamente~~ (da verificare implementazione nel CommandService)
2. ~~**Project API**: I campi `createdAt` e `updatedAt` non vengono visualizzati nella risposta JSON~~ (verificare mapping DTO)

## 🚧 In Sviluppo

- [ ] Integrazione completa GitHub OAuth
- [ ] GitHub API Controller
- [ ] Miglioramenti UI/UX
- [ ] Sistema di notifiche
- [ ] Export/Import progetti

## 👨‍💻 Autore

Sviluppato da Luca

## 📄 Licenza

Questo progetto è privato e non ha una licenza open source.

---

**Versione**: 1.0-SNAPSHOT  
**Ultima modifica**: Ottobre 2025
