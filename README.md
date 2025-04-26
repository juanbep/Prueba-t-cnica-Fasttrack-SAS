# Sistema de Gestión de Estudiantes - FastTrack

## 📚 Descripción

Aplicación de gestión de estudiantes y materias desarrollada como parte de la prueba técnica de FastTrack. Permite registrar estudiantes, asignar materias, desasignarlas y consultar información mediante servicios SOAP.

## 🛠️ Tecnologías Utilizadas

- Backend: Java (Spring Boot)
- Frontend: React + Vite
- Base de Datos: MySQL
- Contenedores: Docker
- Servicios: SOAP (JAX-WS)

## 🏛️ Arquitectura del Sistema

- **Backend SOAP**: Exposición de servicios de gestión.
- **Frontend React**: Interfaces de usuario para registro y consultas.
- **MySQL**: Persistencia de datos.
- **Docker**: Contenedorización de la aplicación para fácil despliegue.

## 🚀 Cómo correr el proyecto

1. Clonar el repositorio:
    ```bash
    git clone https://github.com/tu-usuario/fasttrack-project.git
    cd fasttrack-project
    ```

2. Correr backend:
    ```bash
    cd backend
    ./mvnw spring-boot:run
    ```

3. Correr frontend:
    ```bash
    cd frontend
    npm install
    npm run dev
    ```

4. (Opcional) Correr todo con Docker:
    ```bash
    docker-compose up --build
    ```

## 🗄️ Estructura de Base de Datos

- Entidades principales:
  - `estudiante`
  - `materia`
  - `estudiante_materia`
- Script de creación disponible en `/database/fasttrack.sql`

## 📡 Web Services Implementados

- `consultarMateriasPorEstudiante(id_estudiante)`
- `consultarEstudiantesPorMateria(id_materia)`
- `asignarMateriaAEstudiante(id_estudiante, id_materia)`
- `desasignarMateriaDeEstudiante(id_estudiante, id_materia)`

## ✨ Notas Adicionales

- El correo electrónico del estudiante se genera automáticamente según reglas de negocio.
- Las materias no pueden ser asignadas dos veces al mismo estudiante.
- Se maneja la validación de integridad de datos en todas las operaciones.

