# 📚 API RESTful - Training Courses

[![Docker Hub](https://img.shields.io/badge/Docker%20Hub-training--course--api-blue?logo=docker)](https://hub.docker.com/)
[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.12-green?logo=spring)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/)

> API RESTful completa para gestión de cursos de capacitación desarrollada con Spring Boot, dockerizada y lista para producción.

---

## 🚀 Inicio Rápido (Con Docker)

**Requisito:** Tener [Docker Desktop](https://www.docker.com/products/docker-desktop) instalado.

Ejecuta estos 3 comandos:

```bash
# 1. Crear red Docker
docker network create training-course-net

# 2. Levantar MySQL
docker run -d --name mysql-training-course --network training-course-net \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=training_course_db \
  -p 3306:3306 mysql:8.0

# 3. Construir y ejecutar la API
cd "c:\APLICACIONES DISTRIBUIDAS\Ortiz_TrainingCourse"
docker build -t training-course-api:1.0 .
docker run -d --name training-course-api --network training-course-net \
  -p 8081:8081 -e SPRING_PROFILES_ACTIVE=docker \
  training-course-api:1.0
```

**Probar:** Abre en navegador → http://localhost:8081/api/training-courses

---

## 📋 Características

- ✅ **CRUD Completo**: Crear, Listar, Buscar, Actualizar, Eliminar cursos
- ✅ **Validaciones**: Bean Validation con mensajes personalizados en español
- ✅ **RESTful**: Códigos HTTP apropiados (200, 201, 204, 400, 404)
- ✅ **Dockerizado**: Imagen multi-stage optimizada (~350MB)
- ✅ **Base de Datos**: MySQL 8.0 containerizado
- ✅ **Documentado**: JavaDoc completo + Colección Postman

---

## 🛠️ Stack Tecnológico

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 17 | Lenguaje |
| Spring Boot | 3.4.12 | Framework |
| Spring Data JPA | 3.4.12 | Persistencia |
| MySQL | 8.0 | Base de datos |
| Lombok | Latest | Reducción de código |
| Bean Validation | Latest | Validaciones |
| Docker | Latest | Contenedorización |
| Maven | 3.9+ | Build tool |

---

## 📡 Endpoints de la API

| Método | Endpoint | Descripción | Código |
|--------|----------|-------------|--------|
| **GET** | `/api/training-courses` | Listar todos los cursos | 200 |
| **GET** | `/api/training-courses/{id}` | Buscar curso por ID | 200 / 404 |
| **POST** | `/api/training-courses` | Crear nuevo curso | 201 / 400 |
| **PUT** | `/api/training-courses/{id}` | Actualizar curso | 200 / 404 / 400 |
| **DELETE** | `/api/training-courses/{id}` | Eliminar curso | 204 / 404 |

### Modelo de Datos: Training Course

```json
{
  "id": 1,
  "courseName": "Spring Boot Fundamentals",
  "instructor": "John Doe",
  "durationHours": 40,
  "category": "Programming",
  "status": "Active"
}
```

**Validaciones:**
- `courseName`: Obligatorio, 1-200 caracteres
- `instructor`: Obligatorio, 1-100 caracteres
- `durationHours`: Obligatorio, mínimo 1 hora
- `category`: Obligatorio, 1-100 caracteres
- `status`: Obligatorio, 1-50 caracteres

---

## 🐳 Uso con Docker

### Opción 1: Construcción Local (Recomendado)

```bash
# 1. Clonar repositorio
git clone https://github.com/BryanAndresO/Ortiz_TrainingCourse.git
cd Ortiz_TrainingCourse

# 2. Construir imagen
docker build -t training-course-api:1.0 .

# 3. Crear red Docker
docker network create training-course-net

# 4. Levantar MySQL
docker run -d --name mysql-training-course --network training-course-net \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=training_course_db \
  -p 3306:3306 mysql:8.0

# 5. Ejecutar API
docker run -d --name training-course-api --network training-course-net \
  -p 8081:8081 -e SPRING_PROFILES_ACTIVE=docker \
  training-course-api:1.0
```

### Opción 2: Desde Docker Hub

```bash
# 1. Crear red y MySQL (pasos 3 y 4 de arriba)

# 2. Ejecutar API desde Docker Hub
docker run -d --name training-course-api --network training-course-net \
  -p 8081:8081 -e SPRING_PROFILES_ACTIVE=docker \
  TU_USUARIO_DOCKERHUB/training-course-api:latest
```

### Comandos Útiles

```bash
# Ver contenedores corriendo
docker ps

# Ver logs de la API
docker logs training-course-api

# Ver logs de MySQL
docker logs mysql-training-course

# Reiniciar API
docker restart training-course-api

# Detener todo
docker stop training-course-api mysql-training-course

# Eliminar contenedores
docker rm training-course-api mysql-training-course

# Eliminar red
docker network rm training-course-net
```

**Ver más comandos:** [DOCKER_COMMANDS.md](docs/DOCKER_COMMANDS.md)

---

## 💻 Ejecución Local (Sin Docker)

### Requisitos

- Java 17
- Maven 3.6+
- MySQL 8.0

### Pasos

```bash
# 1. Crear base de datos
mysql -u root -p
CREATE DATABASE training_course_db;
EXIT;

# 2. Compilar y ejecutar
mvn clean install
mvn spring-boot:run
```

La API estará en: http://localhost:8081

---

## 🧪 Pruebas con Postman

### Importar colección

1. Abrir Postman
2. Import → Seleccionar `TrainingCourse-API-Collection.postman_collection.json`
3. Ejecutar las 12 pruebas

### Pruebas incluidas

- ✅ Crear curso (exitoso y con errores de validación)
- ✅ Crear con duración inválida (negativa o cero)
- ✅ Listar todos los cursos
- ✅ Buscar por ID (exitoso y 404)
- ✅ Actualizar (exitoso, 404, validación)
- ✅ Eliminar (exitoso y 404)
- ✅ Verificar eliminación

**Total: 12 pruebas con 30+ assertions**

---

## 📁 Estructura del Proyecto

```
src/main/java/com/espe/test/
├── Controllers/
│   └── TrainingCourseController.java     # Endpoints REST
├── models/entities/
│   └── TrainingCourse.java               # Entidad JPA + Validaciones
├── repositories/
│   └── TrainingCourseRepository.java     # Repository JPA
├── services/
│   ├── TrainingCourseService.java        # Interface Service
│   └── TrainingCourseServiceImpl.java    # Implementación Service
└── TestApplication.java                   # Clase principal
```

### Arquitectura en Capas

```
Controller → Service → Repository → Database
    ↓          ↓           ↓           ↓
   REST      Lógica    Acceso      MySQL
                                (training_course tabla)
```

---

## 🔧 Configuración

### Variables de Entorno

| Variable | Default | Descripción |
|----------|---------|-------------|
| `SERVER_PORT` | 8081 | Puerto del servidor |
| `DB_URL` | localhost:3306/training_course_db | URL de MySQL |
| `DB_USERNAME` | root | Usuario MySQL |
| `DB_PASSWORD` | root | Contraseña MySQL |

### Perfiles

- **default**: Ejecución local
- **docker**: Ejecución en contenedor

---



## 🐳 Publicar en Docker Hub

```bash
# 1. Login
docker login

# 2. Etiquetar imagen
docker tag training-course-api:1.0 TU_USUARIO/training-course-api:latest
docker tag training-course-api:1.0 TU_USUARIO/training-course-api:1.0

# 3. Publicar
docker push TU_USUARIO/training-course-api:latest
docker push TU_USUARIO/training-course-api:1.0


---

## 🗄️ Base de Datos

### Tabla Creada Automáticamente

La tabla `training_course` se crea automáticamente por Hibernate con esta estructura:

```sql
CREATE TABLE training_course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(200) NOT NULL,
    instructor VARCHAR(100) NOT NULL,
    duration_hours INT NOT NULL,
    category VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL
);
```

### Conectarse a MySQL (Docker)

```bash
docker exec -it mysql-training-course mysql -u root -p
# Contraseña: root

USE training_course_db;
SHOW TABLES;
DESCRIBE training_course;
SELECT * FROM training_course;
```

---

##  Verificación del Sistema

### Verificar que todo funciona

```bash
# 1. Verificar contenedores
docker ps

# 2. Verificar API responde
curl http://localhost:8081/api/training-courses

# 3. Crear un curso de prueba
curl -X POST http://localhost:8081/api/training-courses \
  -H "Content-Type: application/json" \
  -d '{
    "courseName": "Docker y Kubernetes",
    "instructor": "María López",
    "durationHours": 50,
    "category": "DevOps",
    "status": "Active"
  }'

# 4. Verificar en la base de datos
docker exec -it mysql-training-course mysql -u root -p -e "SELECT * FROM training_course_db.training_course;"
```

