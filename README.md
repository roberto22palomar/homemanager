
# **HomeManager - Backend**

> **Estado**: En desarrollo

## **Descripción del Proyecto**

> **HomeManager** es una aplicación para gestionar las tareas, compras y más en una casa compartida. Proporciona un sistema de autenticación con JWT para asegurar las peticiones, y permite a los usuarios gestionar casas, tareas y listas de compras de manera colaborativa, también se dispone de un sistema de puntos para obtener recompensas dentro de la misma casa.

## **Tabla de Contenidos**
1. [Características](#características)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Requisitos Previos](#requisitos-previos)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Autenticación y Seguridad](#autenticación-y-seguridad)
6. [Pruebas](#pruebas)
7. [Swagger](#swagger)

## **Características**
- Autenticación basada en JWT.
- CRUD de usuarios, tareas, invitaciones, lista de la compra y casas.
- Asignación de tareas y compras a los miembros de una casa.
- Token de refresco para manejo de sesiones seguras.
- API documentada con Swagger.

## **Tecnologías Utilizadas**
- **Backend**: Java 17, Spring Boot, Spring Security.
- **Base de Datos**: MongoDB.
- **Autenticación**: JWT (JSON Web Token).
- **Documentación API**: Swagger + OpenAPI.
- **Manejo de Dependencias**: Maven.

## **Requisitos Previos**
Antes de configurar y ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

- **Java 17 o superior**: [Descargar Java](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **MongoDB**: [Descargar MongoDB](https://www.mongodb.com/try/download/community)

## **Estructura del Proyecto**

Una descripción de los principales paquetes y clases del proyecto.

\`\`\`
src/
├── main/
│   ├── java/
│   │   └── com/example/homemanager/
│   │       ├── auth/              # Módulo de autenticación y seguridad
│   │       ├── config/            # Configuración del proyecto (Seguridad, Base de datos, Swagger)
│   │       ├── domain/            # Modelos y Repositorios de MongoDB
│   │       ├── services/          # Lógica de negocio
│   │       ├── controllers/       # Controladores REST
│   └── resources/
│       └── application.properties # Configuración del proyecto
└── test/
    └── java/                      # Pruebas unitarias y de integración
\`\`\`

### **Descripción de Paquetes**
- \`auth/\`: Maneja el flujo de autenticación (login, registro, JWT).
- \`config/\`: Contiene configuraciones como seguridad, JWT y Swagger.
- \`domain/\`: Modelos de MongoDB y repositorios.
- \`services/\`: Servicios de negocio que implementan la lógica central de la aplicación.
- \`controllers/\`: Endpoints de las APIs.

## **Autenticación y Seguridad**
Este proyecto utiliza **JWT (JSON Web Token)** para autenticar y autorizar usuarios. A continuación, te explicamos cómo funciona el sistema de autenticación.

### **Registro y Login**

1. **Registro (\`/auth/register\`)**: Un usuario puede registrarse con un nombre de usuario, contraseña y correo electrónico.
2. **Login (\`/auth/login\`)**: Después de la autenticación, se emite un JWT que debe incluirse en cada solicitud en el header \`Authorization: Bearer <token>\`.

## **Pruebas**

Este proyecto incluye pruebas unitarias y de integración usando **JUnit** y **Mockito**.

### **Cobertura de Pruebas**
Explicar qué partes de la aplicación están cubiertas por pruebas, como el servicio de autenticación, controladores, etc.

## **Swagger**

La API está documentada con Swagger. Una vez que la aplicación esté corriendo, puedes acceder a la documentación interactiva en:

\`\`\`
http://localhost:8080/swagger-ui/index.html
\`\`\`

Puedes usar Swagger para probar los endpoints directamente desde el navegador.

