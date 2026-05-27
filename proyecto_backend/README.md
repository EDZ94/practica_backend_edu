# Instrucciones

## Cómo lanzar el proyecto

### Requisitos previos

- **Java 17** o superior
- **Maven 3.6+**
- **MySQL 8.0+** (o servidor MySQL compatible)

### Configuración de variables de entorno

El proyecto requiere las siguientes variables de entorno para conectar con la base de datos MySQL:

| Variable              | Descripción                                      | Ejemplo                                         |
|-----------------------|--------------------------------------------------|-------------------------------------------------|
| `MYSQL_DATABASE_URL`  | URL de conexión JDBC a la base de datos          | `jdbc:mysql://localhost:3306/usuarios_db`       |
| `MYSQL_DATABASE_USER` | Usuario de la base de datos                      | `root`                                          |
| `MYSQL_DATABASE_PWD`  | Contraseña del usuario de la base de datos       | `mi_password`                                   |

**Windows (PowerShell):**
```powershell
$env:MYSQL_DATABASE_URL="jdbc:mysql://localhost:3306/usuarios_db"
$env:MYSQL_DATABASE_USER="root"
$env:MYSQL_DATABASE_PWD="tu_password"
```

**Windows (CMD):**
```cmd
set MYSQL_DATABASE_URL=jdbc:mysql://localhost:3306/usuarios_db
set MYSQL_DATABASE_USER=root
set MYSQL_DATABASE_PWD=tu_password
```

**Linux/Mac:**
```bash
export MYSQL_DATABASE_URL="jdbc:mysql://localhost:3306/usuarios_db"
export MYSQL_DATABASE_USER="root"
export MYSQL_DATABASE_PWD="tu_password"
```

**VS Code:**

Puedes configurar las variables de entorno de varias formas en VS Code:

1. **Archivo `.env` en la raíz del proyecto:**

   Crea un archivo `.env` en la raíz del proyecto:
   ```env
   MYSQL_DATABASE_URL=jdbc:mysql://localhost:3306/usuarios_db
   MYSQL_DATABASE_USER=root
   MYSQL_DATABASE_PWD=tu_password
   ```

2. **Configuración en `launch.json`:**

   Crea o edita el archivo `.vscode/launch.json`:
   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "type": "java",
         "name": "UsuarioApplication",
         "request": "launch",
         "mainClass": "es.ediae.master.programacion.gestionusuario.UsuarioApplication",
         "env": {
           "MYSQL_DATABASE_URL": "jdbc:mysql://localhost:3306/usuarios_db",
           "MYSQL_DATABASE_USER": "root",
           "MYSQL_DATABASE_PWD": "tu_password"
         }
       }
     ]
   }
   ```

3. **Usar `envFile` en `launch.json`:**

   Referencia el archivo `.env` desde `launch.json`:
   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "type": "java",
         "name": "UsuarioApplication",
         "request": "launch",
         "mainClass": "es.ediae.master.programacion.gestionusuario.UsuarioApplication",
         "envFile": "${workspaceFolder}/.env"
       }
     ]
   }
   ```

4. **Configuración en `settings.json` del workspace:**

   Edita `.vscode/settings.json` para configurar el terminal integrado:
   ```json
   {
     "terminal.integrated.env.windows": {
       "MYSQL_DATABASE_URL": "jdbc:mysql://localhost:3306/usuarios_db",
       "MYSQL_DATABASE_USER": "root",
       "MYSQL_DATABASE_PWD": "tu_password"
     }
   }
   ```

> **Nota:** Añade `.env` a tu `.gitignore` para no subir credenciales al repositorio.

### Compilar el proyecto

```bash
mvn clean install
```

### Ejecutar la aplicación

**Opción 1: Con Maven**
```bash
mvn spring-boot:run
```

**Opción 2: Con el JAR generado**
```bash
java -jar target/usuarios-0.0.1-SNAPSHOT.jar
```

### Acceso a la aplicación

Una vez iniciada la aplicación:

- **API REST:** `http://localhost:8080`
- **Documentación Swagger UI:** `http://localhost:8080/misproductos.html`

### Migraciones de base de datos

El proyecto utiliza **Flyway** para gestionar las migraciones de base de datos. Las migraciones se ejecutan automáticamente al iniciar la aplicación. Los scripts se encuentran en:

```
src/main/resources/db/migration/
```

---

## Cómo añadir nuevos servicios

El proyecto sigue la arquitectura en capas típica de Spring Boot. Para añadir un nuevo servicio, sigue estos pasos:

### Estructura del proyecto

```
src/main/java/es/ediae/master/programacion/gestionusuario/
├── entity/          # Entidades JPA
├── repository/      # Repositorios (acceso a datos)
├── service/         # Interfaces de servicios
│   └── impl/        # Implementaciones de servicios
├── controller/      # Controladores REST
├── exception/       # Excepciones personalizadas
└── constant/        # Constantes
```

### Paso 1: Crear la Entidad

Crea una nueva clase en el paquete `entity`:

```java
package es.ediae.master.programacion.gestionusuario.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mi_entidad")
public class MiEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String nombre;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
```

### Paso 2: Crear el Repositorio

Crea una interfaz en el paquete `repository` que extienda `JpaRepository`:

```java
package es.ediae.master.programacion.gestionusuario.repository;

import es.ediae.master.programacion.gestionusuario.entity.MiEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiEntidadRepository extends JpaRepository<MiEntidad, Integer> {
    // Métodos de consulta personalizados (opcional)
    // List<MiEntidad> findByNombre(String nombre);
}
```

### Paso 3: Crear la Interfaz del Servicio

Crea una interfaz en el paquete `service`:

```java
package es.ediae.master.programacion.gestionusuario.service;

import es.ediae.master.programacion.gestionusuario.entity.MiEntidad;
import java.util.List;

public interface IMiEntidadService {
    List<MiEntidad> obtenerTodos();
    MiEntidad obtenerPorId(Integer id);
    MiEntidad guardar(MiEntidad entidad);
    void eliminar(Integer id);
}
```

### Paso 4: Implementar el Servicio

Crea la implementación en el paquete `service.impl`:

```java
package es.ediae.master.programacion.gestionusuario.service.impl;

import es.ediae.master.programacion.gestionusuario.entity.MiEntidad;
import es.ediae.master.programacion.gestionusuario.repository.MiEntidadRepository;
import es.ediae.master.programacion.gestionusuario.service.IMiEntidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiEntidadServiceImpl implements IMiEntidadService {

    @Autowired
    private MiEntidadRepository miEntidadRepository;

    @Override
    public List<MiEntidad> obtenerTodos() {
        return miEntidadRepository.findAll();
    }

    @Override
    public MiEntidad obtenerPorId(Integer id) {
        return miEntidadRepository.findById(id).orElse(null);
    }

    @Override
    public MiEntidad guardar(MiEntidad entidad) {
        return miEntidadRepository.save(entidad);
    }

    @Override
    public void eliminar(Integer id) {
        miEntidadRepository.deleteById(id);
    }
}
```

### Paso 5: Crear el Controlador

Crea el controlador REST en el paquete `controller`:

```java
package es.ediae.master.programacion.gestionusuario.controller;

import es.ediae.master.programacion.gestionusuario.entity.MiEntidad;
import es.ediae.master.programacion.gestionusuario.service.IMiEntidadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mi-entidad")
public class MiEntidadController {

    private final IMiEntidadService miEntidadService;

    public MiEntidadController(IMiEntidadService miEntidadService) {
        this.miEntidadService = miEntidadService;
    }

    @GetMapping
    public List<MiEntidad> obtenerTodos() {
        return miEntidadService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public MiEntidad obtenerPorId(@PathVariable Integer id) {
        return miEntidadService.obtenerPorId(id);
    }

    @PostMapping
    public MiEntidad crear(@RequestBody MiEntidad entidad) {
        return miEntidadService.guardar(entidad);
    }

    @PutMapping("/{id}")
    public MiEntidad actualizar(@PathVariable Integer id, @RequestBody MiEntidad entidad) {
        entidad.setId(id);
        return miEntidadService.guardar(entidad);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        miEntidadService.eliminar(id);
    }
}
```

### Paso 6: Crear la migración de base de datos

Añade un nuevo script SQL en `src/main/resources/db/migration/` siguiendo la nomenclatura de Flyway:

```sql
-- V3__crear_tabla_mi_entidad.sql
CREATE TABLE mi_entidad (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255)
);
```

> **Nota:** El nombre del archivo debe seguir el patrón `V{numero}__{descripcion}.sql` (dos guiones bajos).

### Resumen de archivos a crear

| Archivo | Ubicación |
|---------|-----------|
| `MiEntidad.java` | `entity/` |
| `MiEntidadRepository.java` | `repository/` |
| `IMiEntidadService.java` | `service/` |
| `MiEntidadServiceImpl.java` | `service/impl/` |
| `MiEntidadController.java` | `controller/` |
| `V{n}__crear_tabla_mi_entidad.sql` | `resources/db/migration/` |

---
