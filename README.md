
# **HomeManager - Backend**

## **Descripción del Proyecto**
Una breve descripción sobre el propósito del proyecto.

> **HomeManager** es una aplicación para gestionar las tareas, compras y más en una casa compartida. Proporciona un sistema de autenticación con JWT para asegurar las peticiones, y permite a los usuarios gestionar casas, tareas y listas de compras de manera colaborativa, también se dispone de un sistema de puntos para obtener recompensas dentro de la misma casa.

## **Tabla de Contenidos**
1. [Características](#características)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Requisitos Previos](#requisitos-previos)
4. [Configuración del Proyecto](#configuración-del-proyecto)
5. [Estructura del Proyecto](#estructura-del-proyecto)
6. [Autenticación y Seguridad](#autenticación-y-seguridad)
7. [Pruebas](#pruebas)
8. [Swagger](#swagger)
9. [Contribuciones](#contribuciones)
10. [Licencia](#licencia)

## **Características**
- Autenticación basada en JWT.
- Gestión de roles de usuarios (Admin, User).
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
- **Maven**: [Descargar Maven](https://maven.apache.org/download.cgi)
- **MongoDB**: [Descargar MongoDB](https://www.mongodb.com/try/download/community)

## **Configuración del Proyecto**

### **Clonar el Repositorio**

\`\`\`bash
git clone https://github.com/usuario/homemanager.git
cd homemanager
\`\`\`

### **Configuración del Entorno**

1. Configura las variables de entorno para la conexión con MongoDB y otros parámetros en un archivo \`application.properties\` o \`application.yml\` dentro de la carpeta \`src/main/resources/\`.

**application.properties**:

\`\`\`properties
# Configuración de MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/homemanager

# Configuración de JWT
jwt.secret-key=tu-secreto
jwt.expiration=3600000
jwt.refresh-expiration=86400000
\`\`\`

2. Asegúrate de tener MongoDB corriendo en el puerto correcto (por defecto \`27017\`).

### **Compilar y Ejecutar**

Compila y ejecuta la aplicación localmente:

\`\`\`bash
mvn clean install
mvn spring-boot:run
\`\`\`

La aplicación estará disponible en: \`http://localhost:8080\`

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

### **Protección de Rutas**
Las rutas están protegidas según el rol del usuario:

- **Usuarios normales** pueden acceder a las rutas bajo \`/dashboard/**\`.
- **Admin** tiene acceso a rutas específicas como \`/admin/**\`.

## **Pruebas**

Este proyecto incluye pruebas unitarias y de integración usando **JUnit** y **Mockito**.

### **Ejecutar Pruebas**

\`\`\`bash
mvn test
\`\`\`

### **Cobertura de Pruebas**
Explicar qué partes de la aplicación están cubiertas por pruebas, como el servicio de autenticación, controladores, etc.

## **Swagger**

La API está documentada con Swagger. Una vez que la aplicación esté corriendo, puedes acceder a la documentación interactiva en:

\`\`\`
http://localhost:8080/swagger-ui/index.html
\`\`\`

Puedes usar Swagger para probar los endpoints directamente desde el navegador.

## **Contribuciones**
Si deseas contribuir al proyecto:

1. Crea un fork del repositorio.
2. Crea una nueva rama con tu feature o fix (\`git checkout -b feature/nueva-feature\`).
3. Haz commit de tus cambios (\`git commit -m 'Agrega nueva feature'\`).
4. Haz push a la rama (\`git push origin feature/nueva-feature\`).
5. Crea un Pull Request.

## **Licencia**
Este proyecto está licenciado bajo la Licencia MIT - consulta el archivo [LICENSE](LICENSE) para más detalles.
