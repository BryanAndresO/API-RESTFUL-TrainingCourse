# 📚 API RESTful de Gestión de Libros

[![Docker Hub](https://img.shields.io/badge/Docker%20Hub-jaco224%2Flibros--api-blue?logo=docker)](https://hub.docker.com/r/jaco224/libros-api)
[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.12-green?logo=spring)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)](https://www.mysql.com/)

> API RESTful completa para gestión de libros desarrollada con Spring Boot, dockerizada y lista para producción.

---

## 🚀 Inicio Rápido (Con Docker)

**Requisito:** Tener [Docker Desktop](https://www.docker.com/products/docker-desktop) instalado.

Ejecuta estos 3 comandos:

```bash
# 1. Crear red Docker
docker network create libros-net

# 2. Levantar MySQL
docker run -d --name mysql-libros --network libros-net \
  -e MYSQL_ROOT_PASSWORD=abcd \
  -e MYSQL_DATABASE=sisdb2025 \
  -p 3307:3306 mysql:8.0

# 3. Ejecutar la API (desde Docker Hub)
docker run -d --name libros-api --network libros-net \
  -p 8081:8081 -e SPRING_PROFILES_ACTIVE=docker \
  -e DB_URL=jdbc:mysql://mysql-libros:3306/sisdb2025 \
  jaco224/libros-api:latest
```

**Probar:** Abre en navegador → http://localhost:8081/api/libros

---

## 📋 Características

- ✅ **CRUD Completo**: Crear, Listar, Buscar, Actualizar, Eliminar libros
- ✅ **Validaciones**: Bean Validation con mensajes personalizados
- ✅ **RESTful**: Códigos HTTP apropiados (200, 201, 204, 400, 404)
- ✅ **Dockerizado**: Imagen multi-stage optimizada (356MB)
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
| **GET** | `/api/libros` | Listar todos los libros | 200 |
| **GET** | `/api/libros/{id}` | Buscar libro por ID | 200 / 404 |
| **POST** | `/api/libros` | Crear nuevo libro | 201 / 400 |
| **PUT** | `/api/libros/{id}` | Actualizar libro | 200 / 404 / 400 |
| **DELETE** | `/api/libros/{id}` | Eliminar libro | 204 / 404 |

### Modelo de Datos: Libro

```json
{
  "id": 1,
  "titulo": "Cien Años de Soledad",
  "autor": "Gabriel García Márquez",
  "genero": "Realismo Mágico"
}
```

**Validaciones:**
- `titulo`: Obligatorio, 1-200 caracteres
- `autor`: Obligatorio, 1-100 caracteres
- `genero`: Obligatorio, 1-50 caracteres

---

## 🐳 Uso con Docker

### Opción 1: Descargar de Docker Hub (Recomendado)

Ya mostrado en [Inicio Rápido](#-inicio-rápido-con-docker) ↑

### Opción 2: Construir localmente

```bash
# 1. Clonar repositorio
git clone https://github.com/TU_USUARIO/libros-api.git
cd libros-api

# 2. Construir imagen
docker build -t libros-api:1.0 .

# 3. Crear red y MySQL
docker network create libros-net
docker run -d --name mysql-libros --network libros-net \
  -e MYSQL_ROOT_PASSWORD=abcd \
  -e MYSQL_DATABASE=sisdb2025 \
  -p 3307:3306 mysql:8.0

# 4. Ejecutar API
docker run -d --name libros-api --network libros-net \
  -p 8081:8081 -e SPRING_PROFILES_ACTIVE=docker \
  libros-api:1.0
```

### Comandos Útiles

```bash
# Ver contenedores corriendo
docker ps

# Ver logs de la API
docker logs libros-api

# Reiniciar API
docker restart libros-api

# Detener todo
docker stop libros-api mysql-libros

# Eliminar contenedores
docker rm libros-api mysql-libros
```

Ver más comandos en [DOCKER_COMMANDS.md](DOCKER_COMMANDS.md)

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
CREATE DATABASE sisdb2025;

# 2. Compilar y ejecutar
mvn clean install
mvn spring-boot:run
```

La API estará en: http://localhost:8081

---

## 🧪 Pruebas con Postman

### Importar colección

1. Abrir Postman
2. Import → Seleccionar `Libros-API-Collection.postman_collection.json`
3. Ejecutar las 11 pruebas

### Pruebas incluidas

- ✅ Crear libro (exitoso y con errores)
- ✅ Listar todos
- ✅ Buscar por ID (exitoso y 404)
- ✅ Actualizar (exitoso, 404, validación)
- ✅ Eliminar (exitoso y 404)

**Total: 11 pruebas con 25+ assertions**

---

## 📁 Estructura del Proyecto

```
src/main/java/com/espe/test/
├── Controllers/
│   └── LibroController.java      # Endpoints REST
├── models/entities/
│   └── Libro.java                 # Entidad JPA + Validaciones
├── repositories/
│   ├── LibroRepository.java       # Repository JPA
│   └── LibroServicesImpl.java     # Implementación Service
├── services/
│   └── LibroService.java          # Interface Service
└── TestApplication.java           # Clase principal
```

### Arquitectura en Capas

```
Controller → Service → Repository → Database
    ↓          ↓           ↓           ↓
   REST      Lógica    Acceso      MySQL
```

---

## 🔧 Configuración

### Variables de Entorno

| Variable | Default | Descripción |
|----------|---------|-------------|
| `SERVER_PORT` | 8081 | Puerto del servidor |
| `DB_URL` | localhost:3307 | URL de MySQL |
| `DB_USERNAME` | root | Usuario MySQL |
| `DB_PASSWORD` | abcd | Contraseña MySQL |

### Perfiles

- **default**: Ejecución local
- **docker**: Ejecución en contenedor (automático)

---

## 📖 Documentación Adicional

- [DOCKER_COMMANDS.md](DOCKER_COMMANDS.md) - Guía completa de Docker
- [docs/REPORTE_TECNICO.md](docs/REPORTE_TECNICO.md) - Reporte técnico completo
- [Colección Postman](Libros-API-Collection.postman_collection.json) - Pruebas API

---

## 🤝 Contribuir

1. Fork el proyecto
2. Crear rama: `git checkout -b feature/nueva-funcionalidad`
3. Commit: `git commit -m 'Agregar nueva funcionalidad'`
4. Push: `git push origin feature/nueva-funcionalidad`
5. Abrir Pull Request

---

## 📝 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.

---

## 👤 Autor

**Tu Nombre**
- GitHub: [@BryanAndresO](https://github.com/BryanAndresO/-API-RESTFUL-LIBRO.git)
- Docker Hub: [jaco224/libros-api](https://hub.docker.com/r/jaco224/libros-api)

---

## ⭐ Agradecimientos

Desarrollado para el curso de **Aplicaciones Distribuidas**.

---

**¿Problemas?** Abre un [issue](https://github.com/BryanAndresO/libros-api/issues)
