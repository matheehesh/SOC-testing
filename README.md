# Lanka MicroJob

Microservices starter for a Sri Lankan microjob marketplace connecting workers, employers, brokers, and administrators.

## Stack

- Java Spring Boot services
- React/Vite frontend shell with the supplied HTML prototype in `frontend/public/index.html`
- Spring Cloud Gateway
- PostgreSQL
- Docker Compose
- Kubernetes manifests
- Jenkins pipeline

## Run

```bash
docker compose up --build
```

- Frontend: http://localhost:3000
- API Gateway: http://localhost:8080

## Services

- `user-service` — auth/register/login users.
- `job-service` — post and browse jobs.
- `matching-service` — score worker/job skill matches.
- `broker-service` — broker applications and offline workers.
- `notification-service` — notification/SMS-ready API.
- `api-gateway` — routes frontend API traffic.

