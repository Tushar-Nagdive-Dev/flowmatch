# 🚀 FlowMatch - Smart Invoice and Payment Reconciliation Platform

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)]()
[![Technologies](https://img.shields.io/badge/Tech-Stack-blue.svg)]()

---

## 📚 Project Overview

**FlowMatch** is a smart, event-driven platform to automate the matching of invoices and incoming payments.  
Built using **Spring Boot**, **Apache Kafka**, **Apache Camel**, and **Angular**, it offers:
- Automated reconciliation
- Real-time alerts on unmatched entries
- Dynamic dashboards
- Monthly reconciliation reports (Excel/PDF)
- Secure JWT-based user management

---

## 🛠️ Tech Stack

| Backend | Frontend | Event Streaming | Integration | Database |
|:---|:---|:---|:---|:---|
| Java 21 | Angular 17+ | Apache Kafka | Apache Camel | PostgreSQL |
| Spring Boot 3+ | Angular Material | | | |
| Spring Security + JWT | ngx-charts (for graphs) | | | |

---

## 📦 Project Structure

/flowmatch
  ├── backend/        # Spring Boot Project
  ├── frontend/       # Angular Project
  ├── docs/           # Documentation, Diagrams, Architecture
  └── README.md

flowmatch/
├── project-structure.txt              ⬅️ Optional doc (good for notes)
├── README.md                          ⬅️ Ready to update with project info

├── smart-reconciliation-api/         ⬅️ ✅ Spring Boot Backend (Maven)
│   ├── mvnw*, pom.xml                ⬅️ Maven wrapper & config
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/inn/smart_reconciliation_api/
│   │   │   │   └── SmartReconciliationApiApplication.java
│   │   │   ├── resources/
│   │   │   │   ├── application.properties
│   │   │   │   ├── db/migration/     ⬅️ ✅ Flyway migrations here
│   │   │   │   ├── static/
│   │   │   │   └── templates/
│   │   └── test/
│   │       └── java/.../SmartReconciliationApiApplicationTests.java

├── smart-reconciliation-ui/          ⬅️ ✅ Angular 17 Frontend (SCSS + Material)
│   ├── angular.json
│   ├── package.json
│   ├── server.ts                     ⬅️ SSR setup (optional if not using SSR now)
│   ├── src/
│   │   ├── app/
│   │   │   ├── app.component.*       ⬅️ Root component
│   │   │   ├── app.config.ts
│   │   │   └── app.routes.ts
│   │   ├── assets/
│   │   ├── favicon.ico
│   │   ├── index.html
│   │   └── styles.scss
│   ├── tsconfig*.json                ⬅️ TypeScript configs

└── docs/                             ⬅️ (Create this folder next if missing)
    ├── diagrams/
    └── workflow.svg (later)



Great move, Tushar! ✅  
Creating a dedicated **PostgreSQL user and database** for the FlowMatch application is essential for security, maintainability, and isolation.

Let’s do this in clean, **step-by-step commands**.

---

# 🛠️ PostgreSQL Setup for FlowMatch App

### 🎯 Goal:
- Create a **PostgreSQL user**: `flowmatch_user`
- Create a **PostgreSQL database**: `flowmatch_db`
- Grant all necessary privileges to `flowmatch_user` on `flowmatch_db`

---

## ✅ Step 1: Login to PostgreSQL as Superuser

```bash
psql -U postgres
```

(If you have a different admin user, replace `postgres`)

---

## ✅ Step 2: Create Application Database User

```sql
CREATE USER flowmatch_user WITH PASSWORD 'flowmatch_secure_password';
```

> 🔒 Choose a strong password in a real environment.

---

## ✅ Step 3: Create the Database

```sql
CREATE DATABASE flowmatch_db;
```

---

## ✅ Step 4: Grant Access to the New User

```sql
GRANT ALL PRIVILEGES ON DATABASE flowmatch_db TO flowmatch_user;
```

---

## ✅ Step 5 (Optional): Verify Access

Exit the current session:

```sql
\q
```

Login using the new user:

```bash
psql -U flowmatch_user -d flowmatch_db
```

You should now be inside the `flowmatch_db` using `flowmatch_user`.

---

# 🔧 Spring Boot `application.properties` Example

Here’s how you’ll update your `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/flowmatch_db
spring.datasource.username=flowmatch_user
spring.datasource.password=flowmatch_secure_password

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
```

---

# ✅ You’re Ready for Backend Migration + Auth Coding

| Item | Status |
|:---|:---|
| PostgreSQL User | ✅ flowmatch_user |
| PostgreSQL Database | ✅ flowmatch_db |
| Privileges | ✅ Full |
| Spring Boot config | ✅ Ready to point to DB |

---

# 📣 Shall We Now Proceed?

👉 If you're ready to move into **Step 2: JWT Authentication Setup**,  
just say:

**"Maya, begin Step 2: JWT Authentication."** 🔐

I’ll guide you step-by-step (starting with `AuthController`, `UserDetailsService`, etc.).  
No rush — clean and clear. 🌟