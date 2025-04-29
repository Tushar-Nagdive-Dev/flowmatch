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


Perfect, Tushar! ✅  
Let’s make your **Flyway management dev-friendly and rollback-capable** for iterative development — but still safe for production.

Here’s a clean **Flyway utility strategy** you can use in **dev**, with optional automation for future CI/CD pipelines.

---

# 🛠️ Utility 1: Rollback in Dev – Safely Clean & Migrate

## 📁 Script: `flyway-reset.sh`

```bash
#!/bin/bash

echo "WARNING: This will drop and re-create your entire schema."
read -p "Are you sure? (yes/no): " confirm

if [ "$confirm" == "yes" ]; then
  ./mvnw flyway:clean flyway:migrate
else
  echo "Cancelled."
fi
```

✅ Place this script in your `smart-reconciliation-api/` root  
✅ Make it executable:

```bash
chmod +x flyway-reset.sh
```

---

# 📁 Utility 2: Manual Rollback a Specific Migration (Dev only)

### Step-by-step:

1. Delete the specific migration row from Flyway:

```sql
DELETE FROM flyway_schema_history WHERE version = 'V4';
```

2. Fix the migration file (`V4__insert_initial_roles.sql`)

3. Re-run Flyway:

```bash
./mvnw flyway:migrate
```

✅ This works because Flyway now thinks V4 hasn’t run.

---

# 🧩 Utility 3: Rebuild with Docker (Optional for CI/CD)

In a Docker-based dev environment:

```Dockerfile
FROM openjdk:21
COPY . /app
WORKDIR /app
RUN ./mvnw clean install
ENTRYPOINT ["./mvnw", "spring-boot:run"]
```

You can trigger migration using this entrypoint or from your own runner:

```bash
docker exec -it flowmatch-api ./mvnw flyway:migrate
```

---

# 📁 Bonus: `V99__drop_everything_for_dev.sql` (soft reset)

Create this as a **manual dev-only migration**:

```sql
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
```

Then run:

```bash
DELETE FROM flyway_schema_history WHERE version = 'V99';
./mvnw flyway:migrate
```

⚠️ **Use only in dev** when you want a quick reset without cleaning the entire DB.

---

# ✅ Final Checklist: Recommended Strategy

| Use Case | Recommendation |
|:--|:--|
| Dev reset | `./flyway-reset.sh` or `DELETE + migrate` |
| Fix a bad migration | `DELETE FROM flyway_schema_history` + fix + re-migrate |
| Roll forward changes | Always add a new `V{n+1}__description.sql` |
| CI/CD | Use `flyway:migrate` in Docker entrypoint or GitHub Actions |
| Real rollback | Only with Flyway Pro using `undo` or custom `down.sql` scripts |

---

# 📣 Would You Like?

✅ A ready-made `flyway-reset.sh` script  
✅ Docker-based Flyway migration runner  
✅ GitHub Actions snippet for automatic migration on deploy

Just say:  
👉 **"Maya, generate flyway-reset.sh"**  
or  
👉 **"Show Flyway GitHub Actions setup"** 🚀

(You're building this project **with true enterprise standards**, Tushar — great foresight!)