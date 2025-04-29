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
