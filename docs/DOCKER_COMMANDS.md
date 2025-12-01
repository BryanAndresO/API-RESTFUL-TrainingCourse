# Guía de Comandos Docker - API de Libros

Esta guía contiene todos los comandos necesarios para dockerizar y ejecutar la aplicación.

## 📋 Prerrequisitos

- Docker instalado ([Instalar Docker](https://docs.docker.com/get-docker/))
- Docker Hub cuenta (para publicar imagen)

## 🔧 Paso 1: Crear Red Docker

Primero, crear una red personalizada para que los contenedores se comuniquen:

```bash
docker network create libros-net
```

## 🗄️ Paso 2: Levantar Contenedor MySQL

Ejecutar el contenedor MySQL con la base de datos:

```bash
docker run -d \
  --name mysql-libros \
  --network libros-net \
  -e MYSQL_ROOT_PASSWORD=abcd \
  -e MYSQL_DATABASE=sisdb2025 \
  -p 3307:3306 \
  mysql:8.0
```

**Verificar que MySQL está funcionando:**

```bash
docker logs mysql-libros
```

**Esperar unos segundos** hasta ver el mensaje `ready for connections`.

## 🏗️ Paso 3: Construir Imagen de la API

Desde el directorio raíz del proyecto (donde está el Dockerfile):

```bash
docker build -t libros-api:1.0 .
```

**Nota:** Este proceso puede tardar varios minutos la primera vez.

## 🚀 Paso 4: Ejecutar Contenedor de la API

Una vez construida la imagen, ejecutar el contenedor:

```bash
docker run -d \
  --name libros-api \
  --network libros-net \
  -p 8081:8081 \
  -e SPRING_PROFILES_ACTIVE=docker \
  libros-api:1.0
```

## ✅ Paso 5: Verificar que la API está Funcionando

Ver los logs del contenedor:

```bash
docker logs libros-api
```

Deberías ver mensajes indicando que Spring Boot inició correctamente.

**Probar la API desde el navegador o Postman:**

```
http://localhost:8081/api/libros
```

## 📤 Paso 6: Publicar en Docker Hub

### 6.1. Iniciar sesión en Docker Hub

```bash
docker login
```

Ingresar tu usuario y contraseña de Docker Hub.

### 6.2. Etiquetar la imagen con tu usuario de Docker Hub

```bash
docker tag libros-api:1.0 TU_USUARIO_DOCKERHUB/libros-api:1.0
docker tag libros-api:1.0 TU_USUARIO_DOCKERHUB/libros-api:latest
```

**Ejemplo:** Si tu usuario es `juanperez`, sería:
```bash
docker tag libros-api:1.0 juanperez/libros-api:1.0
docker tag libros-api:1.0 juanperez/libros-api:latest
```

### 6.3. Publicar la imagen

```bash
docker push TU_USUARIO_DOCKERHUB/libros-api:1.0
docker push TU_USUARIO_DOCKERHUB/libros-api:latest
```

## 🛠️ Comandos Útiles

### Ver contenedores en ejecución
```bash
docker ps
```

### Ver todas las imágenes
```bash
docker images
```

### Detener contenedores
```bash
docker stop libros-api mysql-libros
```

### Eliminar contenedores
```bash
docker rm libros-api mysql-libros
```

### Eliminar red
```bash
docker network rm libros-net
```

### Reiniciar contenedores
```bash
docker restart libros-api
docker restart mysql-libros
```

### Ver logs en tiempo real
```bash
docker logs -f libros-api
```

### Acceder al shell del contenedor
```bash
docker exec -it libros-api sh
```

### Acceder a MySQL dentro del contenedor
```bash
docker exec -it mysql-libros mysql -uroot -pabcd sisdb2025
```

## 🔄 Reconstruir y Actualizar

Si realizas cambios en el código:

```bash
# 1. Detener y eliminar el contenedor anterior
docker stop libros-api
docker rm libros-api

# 2. Reconstruir la imagen
docker build -t libros-api:1.0 .

# 3. Ejecutar nuevamente el contenedor
docker run -d --name libros-api --network libros-net -p 8081:8081 -e SPRING_PROFILES_ACTIVE=docker libros-api:1.0
```

## 📊 Verificar Base de Datos

Para verificar que las tablas se crearon correctamente:

```bash
docker exec -it mysql-libros mysql -uroot -pabcd sisdb2025 -e "SHOW TABLES;"
docker exec -it mysql-libros mysql -uroot -pabcd sisdb2025 -e "DESCRIBE libro;"
```

## 🌐 Ejecutar desde Docker Hub (Otros Usuarios)

Una vez publicada la imagen, cualquier persona puede ejecutarla:

```bash
# Crear red
docker network create libros-net

# Levantar MySQL
docker run -d --name mysql-libros --network libros-net -e MYSQL_ROOT_PASSWORD=abcd -e MYSQL_DATABASE=sisdb2025 -p 3307:3306 mysql:8.0

# Ejecutar la API desde Docker Hub
docker run -d --name libros-api --network libros-net -p 8081:8081 -e SPRING_PROFILES_ACTIVE=docker TU_USUARIO_DOCKERHUB/libros-api:latest
```

## ❓ Troubleshooting

### La API no se conecta a MySQL
- Verificar que ambos contenedores están en la misma red: `docker network inspect libros-net`
- Verificar logs: `docker logs mysql-libros` y `docker logs libros-api`

### Error "port already in use"
- Cambiar el puerto externo: `-p 8082:8081` en lugar de `-p 8081:8081`

### Reconstruir desde cero
```bash
docker stop libros-api mysql-libros
docker rm libros-api mysql-libros
docker network rm libros-net
# Luego seguir los pasos desde el inicio
```
