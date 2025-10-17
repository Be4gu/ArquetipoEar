# Plantilla Spring Boot para WebSphere

Esta es una plantilla de proyecto Spring Boot multi-módulo diseñada para crear aplicaciones que se despliegan en IBM WebSphere Application Server. El proyecto genera un archivo EAR (Enterprise Archive) listo para despliegue.

## 📋 Estructura del Proyecto

```
mi-aplicacion-completa/
├── pom.xml                    # POM padre
├── demo-properties/           # Configuraciones externas
│   └── demo.properties       # Archivo de propiedades principal
├── demo-service/             # Módulo de servicios (JAR)
│   ├── pom.xml
│   └── src/main/java/com/example/demo/service/
├── demo-web/                 # Módulo web (WAR)
│   ├── pom.xml
│   └── src/main/java/com/example/demo/
└── demo-ear/                 # Módulo EAR para WebSphere
    ├── pom.xml
    └── target/
        └── demo-ear-1.0.ear  # Archivo desplegable
```

## 🚀 Configuración Inicial

### 1. Cambios Obligatorios

Antes de usar esta plantilla, **DEBES** cambiar las siguientes configuraciones:

#### a) Identificación del Proyecto (pom.xml padre)

```xml
<groupId>com.tuempresa</groupId>
<artifactId>tu-aplicacion-nombre</artifactId>
<version>1.0</version>
```

#### b) Contexto de la Aplicación (demo-ear/pom.xml)

```xml
<modules>
    <webModule>
        <groupId>com.tuempresa</groupId>
        <artifactId>tu-aplicacion-web</artifactId>
        <contextRoot>/tu-aplicacion</contextRoot>  <!-- CAMBIAR AQUÍ -->
    </webModule>
</modules>
```

#### c) Paquetes Java

- Cambiar `com.example` por tu dominio: `com.tuempresa.tuaplicacion`
- Actualizar todos los archivos Java en:
  - `demo-service/src/main/java/`
  - `demo-web/src/main/java/`

#### d) Nombres de los Módulos

- Renombrar carpetas: `demo-service` → `tu-aplicacion-service`
- Renombrar carpetas: `demo-web` → `tu-aplicacion-web`
- Renombrar carpetas: `demo-ear` → `tu-aplicacion-ear`
- Actualizar `<artifactId>` en cada pom.xml de módulo

#### e) Archivo de Properties

- Renombrar `demo-properties/demo.properties` a `tu-aplicacion-properties/tu-aplicacion.properties`
- Actualizar el contenido:

```properties
spring.application.name=tu-aplicacion
example.property=Valor personalizado para tu aplicación
```

#### f) Configuración del PropertySource

- Actualizar la anotación en `AppConfig.java`:

```java
@PropertySource("classpath:tu-aplicacion.properties")
```

⚠️ **IMPORTANTE**: El archivo de properties debe estar ubicado en una carpeta que esté en el **classpath del servidor WebSphere**. Normalmente se configura en el directorio de configuración del servidor.

### 2. Configuración del Entorno

#### Java y Maven

- **Java 8** (requerido para compatibilidad con WebSphere)
- **Maven 3.6+**
- **Spring Boot 2.7.5** (compatible con Java 8)

#### Variables de Entorno (Opcional)

```bash
JAVA_HOME=C:\Program Files\Java\jdk1.8.0_XXX
MAVEN_HOME=C:\apache-maven-3.x.x
```

## 🔧 Construcción del Proyecto

### Comando Principal

```bash
mvn clean install -DskipTests
```

⚠️ **IMPORTANTE**:

- Ejecutar desde la **raíz del proyecto** (donde está el pom.xml padre)
- El parámetro `-DskipTests` es **obligatorio** para evitar errores en la generación del EAR
- Este comando genera todos los artefactos necesarios incluyendo el EAR

### Estructura de Build

1. **demo-service** → Genera `demo-service-1.0.jar`
2. **demo-web** → Genera `demo-web-1.0.war`
3. **demo-ear** → Genera `demo-ear-1.0.ear` (contiene WAR + JAR + dependencias)

### Ubicación del EAR

El archivo desplegable se encuentra en:

```
demo-ear/target/demo-ear-1.0.ear
```

## 🌐 Endpoints Disponibles

Una vez desplegado, la aplicación expone estos endpoints:

| Endpoint                          | Descripción                      |
| --------------------------------- | -------------------------------- |
| `GET /tu-aplicacion/api/test`     | Endpoint de prueba básico        |
| `GET /tu-aplicacion/api/ear`      | Confirmación del módulo EAR      |
| `GET /tu-aplicacion/api/property` | Muestra propiedades configuradas |
| `GET /tu-aplicacion/api/service`  | Prueba del servicio interno      |

> **Nota**: Reemplaza `/tu-aplicacion` con el `contextRoot` que configuraste.

## 📦 Despliegue en WebSphere

### 1. Preparación del EAR

```bash
# Construir el proyecto
mvn clean install -DskipTests

# El EAR estará en: demo-ear/target/demo-ear-1.0.ear
```

### 2. Despliegue en WebSphere

1. Acceder a la **Consola Administrativa de WebSphere**
2. Navegar a: **Applications** → **New Application** → **New Enterprise Application**
3. Seleccionar el archivo `demo-ear-1.0.ear`
4. Seguir el asistente de instalación
5. Configurar el **Context Root** si es necesario
6. **Start** la aplicación

### 3. Verificación

- URL base: `http://servidor:puerto/tu-aplicacion/api/test`
- Debe retornar: `"¡Endpoint de pruebas funcionando correctamente! Spring Boot 2.7.18 con Java 8"`

## 🔧 Configuraciones para WebSphere

### Class Loading

La aplicación está configurada con:

- **Parent Last** class loading (recomendado para Spring Boot)
- **Shared libraries** habilitadas
- **WAR file** con `<scope>provided</scope>` para Tomcat

### Dependencias Incluidas

El EAR incluye automáticamente:

- Spring Boot starter y dependencias
- Logback para logging
- JAR del módulo service
- Todas las dependencias transitivas en `lib/`

### Logging

- Configurado con `logback-spring.xml`
- Logs a **consola** por defecto
- Compatible con el sistema de logs de WebSphere

## 🔄 Desarrollo Local

### Ejecución en modo desarrollo

```bash
# Solo para pruebas locales (no para WebSphere)
cd demo-web
mvn spring-boot:run
```

### Profile de desarrollo

Crear `application-dev.properties` en `demo-web/src/main/resources/`:

```properties
server.port=8080
logging.level.com.tuempresa=DEBUG
```

## 📝 Personalización Avanzada

### Añadir Nuevos Módulos

1. Crear nuevo módulo Maven
2. Añadir al `<modules>` del POM padre
3. Incluir como dependencia en `demo-ear/pom.xml`

### Configuración de Base de Datos

Añadir dependencias JDBC en `demo-service/pom.xml`:

```xml
<dependency>
    <groupId>com.ibm.db2</groupId>
    <artifactId>db2jcc4</artifactId>
    <version>11.5.x</version>
</dependency>
```

### Seguridad

Para añadir Spring Security:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## ❗ Troubleshooting

### Error: "Tests failing during EAR generation"

**Solución**: Usar siempre `mvn clean install -DskipTests`

### Error: "Context root conflicts"

**Solución**: Cambiar `<contextRoot>` en `demo-ear/pom.xml`

### Error: "ClassNotFoundException in WebSphere"

**Solución**: Verificar class loading policy y shared libraries

### Error: "Port conflicts in development"

**Solución**: Cambiar puerto en `application.properties` o usar profiles

---

**Versión**: 1.0  
**Compatibilidad**: Java 8, Spring Boot 2.7.5, WebSphere 8.5+  
**Última actualización**: Octubre 2025
