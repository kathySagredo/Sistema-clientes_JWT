# Sistema de Clientes JWT

Sistema de Clientes JWT es una API REST desarrollada con **Java**, **Spring Boot** y **PostgreSQL**, que implementa autenticación y autorización mediante **JSON Web Token (JWT)** utilizando **Spring Security**.

El proyecto fue desarrollado con una arquitectura en capas, separando la lógica de negocio, el acceso a datos y la seguridad, siguiendo buenas prácticas de desarrollo backend.

---

# 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Neon PostgreSQL
- Maven
- Lombok

---

# 📁 Estructura del proyecto

```
src
├── main
│   ├── java
│   │   └── cl
│   │       └── ...
│   │           ├── config
|   |           ├── auth
│   │           ├── controller
│   │           ├── dto
│   │           ├── entity
│   │           ├── repository
│   │           ├── security
│   │           ├── service
│   │           ├── exception
│   │           └── SistemaClientesApplication.java
│   │
│   └── resources
│       └── application.yml
│
└── test
```

---

# 📌 Arquitectura

El proyecto sigue una arquitectura en capas:

| Capa | Responsabilidad |
|-------|-----------------|
| Controller | Expone los endpoints REST |
| Service | Contiene la lógica de negocio |
| Repository | Acceso a la base de datos mediante Spring Data JPA |
| Entity | Representación de las tablas de la base de datos |
| DTO | Objetos para solicitudes y respuestas |
| Security | Configuración de Spring Security y JWT |
| Config | Configuraciones generales |
| Exception | Manejo centralizado de excepciones |

---

# ✨ Funcionalidades

- Inicio de sesión
- Generación de JWT
- Validación automática del token
- Protección de rutas mediante Spring Security
- CRUD de clientes
- Persistencia de datos en PostgreSQL
- API REST Stateless

---

# 🔐 Flujo de autenticación JWT

```
Usuario
   │
   │ Login
   ▼
AuthenticationController
   │
   ▼
AuthenticationManager
   │
   ▼
UserDetailsService
   │
   ▼
Base de datos
   │
Usuario válido
   │
   ▼
JwtService
Genera Token
   │
   ▼
Cliente almacena el JWT
   │
   ▼
Solicitudes posteriores
Authorization: Bearer <token>
   │
   ▼
JwtAuthFilter
   │
Valida Token
   │
Carga usuario autenticado
   │
   ▼
Spring Security
   │
   ▼
Acceso permitido
```

---

# 📡 Endpoints principales

## Autenticación

| Método | Endpoint | Descripción |
|---------|----------|-------------|
| POST | `/auth/login` | Iniciar sesión |

---

## Clientes

| Método | Endpoint | Descripción |
|---------|----------|-------------|
| GET | `/clientes` | Obtener todos los clientes |
| GET | `/clientes/{id}` | Obtener un cliente por ID |
| POST | `/clientes` | Crear cliente |
| PUT | `/clientes/{id}` | Actualizar cliente |
| DELETE | `/clientes/{id}` | Eliminar cliente |

> Todos los endpoints del módulo de clientes requieren un token JWT válido.

---

# 📦 Configuración

Configurar el archivo `application.yml`.

Ejemplo utilizando variables de entorno:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: update

jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXPIRATION}
```

---

# ▶️ Ejecución del proyecto

## Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/Sistema-clientes_JWT.git
```

## Entrar al proyecto

```bash
cd Sistema-clientes_JWT
```

## Ejecutar

Con Maven Wrapper:

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

O ejecutar directamente la clase:

```
SistemaClientesApplication.java
```

---

# 🔑 Ejemplo de autenticación

## Login

**POST**

```
/auth/login
```

Body:

```json
{
    "email": "admin@correo.cl",
    "password": "123456"
}
```

Respuesta:

```json
{
    "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

---

## Consumir una ruta protegida

Agregar el header:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

Ejemplo:

```
GET /clientes
```

---

# 🛠 Herramientas utilizadas para pruebas

- Bruno
- Postman
- Insomnia
  
---

# 👩‍💻 Autora

**Kathy Sagredo**

Proyecto desarrollado con fines de aprendizaje para implementar autenticación y autorización mediante JWT utilizando Spring Boot, Spring Security y PostgreSQL, aplicando buenas prácticas de desarrollo backend y arquitectura en capas.
