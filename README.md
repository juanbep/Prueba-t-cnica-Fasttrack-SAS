# Sistema de Gestión de Estudiantes - Solución Prueba Técnica Fasttrack

## 📚 Descripción

Aplicación básica para una Institución Educativa, en la cual se pueda almacenar y gestionar a los estudiantes, materias, etc. Desarrollado como solución a la prueba técnica de Fasttrack, ofrece una variedad de servicios web SOAP con operaciones CRUD y una arquitectura de software Modelo-Vista-Controlador.

## 🛠️ Tecnologías Utilizadas

Backend:
Project: Maven
Framework: Spring Boot 3.4.5
Lenguaje: Java 21
Gestor de base de datos: MySQL 

Frontend:
Framework/Librerias: React + Vite + Tailwind CSS + Axios + Node.js
Lenguaje: JavaScript

## 🏛️ Arquitectura del Sistema MVC

## 🏗️ Estructura del Proyecto

    ```bash
    .
    ├───src/
    │   ├───controllers/       # 🎮 Controladores de endpoints (SOAP)
    │   ├───models/            # 🏛️ Entidades de base de datos
    │   ├───config/            # ⚙️ Configuración (DB, Web Services)
    │   ├───services/          # 🧠 Lógica de negocio principal
    │   ├───repository/        # 💾 Capa de acceso a datos (Repositorios)
    │   └───utils/             # 🛠️ Utilidades/Helpers
    │
    ├───resources/             # 📦 Recursos estáticos
    │   ├───xsd/               # 📜 Esquemas XSD para servicios SOAP
    │   └───application.properties # 🔧 Configuración del entorno
    │
    └───tests/                 # 🧪 Pruebas automatizadas
        ├───unit/              # 🔬 Pruebas unitarias
        └───integration/       # 🧩 Pruebas de integración

## 🚀 Cómo correr el proyecto

1. Crear la base de datos MySQL con el nombre de fasttrack
2. Ejecutar el "scriptBD" disponible en el repositorio para crear las tablas 
3. Insertar datos de prueba con el script "InsertDatosDePrueba" disponible en el repositorio
4. Ejecutar la aplicación spring boot como Maven Project en el IDE de preferencia
5. Validar intalación de todas las dependencias en el pom.xml
6. Configurar el archivo "application.properties" en la ruta src/main/resources
   
      ### server.port=8081 ---> Puerto de nuestra preferenci
      ### spring.datasource.url=jdbc:mysql://localhost:3306/fasttrack  --> Nombre de la base de datos 
      ### credenciales para conectarnos a MySQL (según nuestra configuración)
          spring.datasource.username=root 
          spring.datasource.password=
   
8. Configura el archivo "DatabaseConfig" en la ruta com.fasttrack.application/config con la misma configuracion que hicimos en "application.properties"
9. Levantar la aplicación
10. Abrir la carpeta web aplication en nuestro editor de preferencia
11. Tener instalado Node e instalar las dependencias con npm install
12. Configurar el puerto del localhost en el archivo vite.config.js asegurandonos que sea el mismo puerto que usamos en el back 
13. Ejecutar la aplicacion con npm run dev 

## 🗄️ Estructura de Base de Datos

🧑‍🎓 Tabla estudiante

| Campo             | Tipo           | Restricciones                   |
| ----------------- | -------------- | ------------------------------- |
| `id_estudiante`   | `INT UNSIGNED` | `PRIMARY KEY`, `AUTO_INCREMENT` |
| `primer_nombre`   | `VARCHAR(30)`  | `NOT NULL`                      |
| `primer_apellido` | `VARCHAR(30)`  | `NOT NULL`                      |
| `pais`            | `CHAR(2)`      | `NOT NULL`                      |
| `correo`          | `VARCHAR(100)` | `NOT NULL`, `UNIQUE`            |

📘 Tabla materia

| Campo        | Tipo           | Restricciones                   |
| ------------ | -------------- | ------------------------------- |
| `id_materia` | `INT UNSIGNED` | `PRIMARY KEY`, `AUTO_INCREMENT` |
| `nombre`     | `VARCHAR(100)` | `NOT NULL`                      |
| `codigo`     | `INT(11)`      | `NOT NULL`, `UNIQUE`            |

🔗 Tabla estudiante_materia

| Campo           | Tipo           | Restricciones                            |
| --------------- | -------------- | ---------------------------------------- |
| `id`            | `INT UNSIGNED` | `PRIMARY KEY`, `AUTO_INCREMENT`          |
| `id_estudiante` | `INT UNSIGNED` | `NOT NULL`, `FOREIGN KEY` (`estudiante`) |
| `id_materia`    | `INT UNSIGNED` | `NOT NULL`, `FOREIGN KEY` (`materia`)    |


## 📡 Web Services Implementados

🧑‍🎓 Estudiante

| Servicio               | Descripción                                    | 
| ---------------------- | ---------------------------------------------- |
| `RegistrarEstudiante`  | Registra un nuevo estudiante                   | 
| `ListarEstudiantes`    | Retorna la lista de todos los estudiantes      | 
| `EliminarEstudiante`   | Elimina un estudiante por su ID                | 
| `ActualizarEstudiante` | Actualiza los datos de un estudiante existente | 

📚 Materia

| Servicio               | Descripción                                    | 
| ---------------------- | ---------------------------------------------- |
| `RegistrarMateria`     | Registra una nueva materia                     | 
| `ListarMaterias`       | Retorna la lista de todos las materias         | 
| `EliminarMateria`      | Elimina una materia por su ID                  | 
| `ActualizarMateria`    | Actualiza los datos de una materia             | 


🔗estudiante_materia

| Servicio                       |  Descripción                                    | 
| ------------------------------ | ----------------------------------------------  |
| `AsignarMateria`               | Asigna una materia a un estudiante              | 
| `DesasignarMateria`            | Desasigna una materia a un estudiante           | 
| `FiltrarMateriasEstudiante`    | Filtra las materias asignadas a un estudiante   | 
| `FiltrarEstudiantesMateria`    | Filtra los estudiantes asignados a una materia  | 

## ✨ Notas Adicionales



