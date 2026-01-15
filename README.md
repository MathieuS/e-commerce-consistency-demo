# E-Commerce Consistency Demo

## Overview

This project demonstrates design patterns used in a distributed microservices architecture to ensure **data consistency** and **transaction integrity** in an e-commerce context.

Technical stack: spring boot, mysql and rabbitmq 

## Architecture

The demonstration involves two main applications:

| Application       | Role                         | Description                                                                                                                 |
|:------------------|:-----------------------------|:----------------------------------------------------------------------------------------------------------------------------|
| `Customer-Portal` | Frontend Service / Initiator | Manages user interaction, initiates business processes                                                                      |
| `Backend-System`  | External Service             | Simulates external systems (like an ERP, a Logistics Provider that need to be updated as part of a distributed transaction. |

## Consistency Patterns Implemented

The demo focuses on common e-commerce scenarios, each implemented with a different consistency pattern:

---

### 1. New Account Creation (Asynchronous Consistency)

This scenario demonstrates creation of a user account in the `Customer-Portal`, which must then be registered in the `Backend-System`, then get an ERP account ID.

* **Business Process:** Create `Account` in Frontend service ->  Create corresponding record in backend service and enrich `Account` with an ERP account ID.
* **Pattern Used:** Transactional Outbox Pattern
* **Goal:** To ensure that the account creation and the event signaling the remote system update are treated as a single transaction.



## Getting Started

 **Setup and Run:**
    
```bash
    docker compose up 
```

Urls:
- frontend app : http://localhost:8080/swagger-ui/index.html
- rabbimq : http://localhost:15672 guest/guest
- mysql : mysql:localhost:3306

## Demo scenario

1. Start docker compose : `docker compose up `
2. Stop rabbit : `docker compose down rabbitmq`
3. Create account : 
    ```
   curl -X 'POST' \
    'http://localhost:8080/api/accounts' \
    -H 'accept: */*' \
    -H 'Content-Type: application/json' \
    -d '{
    "email": "jdoe@company.com",
    "firstName": "john",
    "lastName": "doe"
    }'
   ``` 
4. Check account is created :
    ```
   curl -X 'GET' \
    'http://localhost:8080/api/accounts?page=0&size=10&sortBy=id&sortDir=desc' \
    -H 'accept: */*'
   ```
5. Restart rabbit : `docker compose up rabbitmq`
6. Message has been published : http://localhost:15672/#/queues/%2F/portal_account_created