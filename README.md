# Sistema de Gestión de Eventos y Boletos

Sistema REST para administrar eventos y venta de boletos.


## Requisitos Previos
- Java 17+
- MySQL 8+
- Maven

## Configuración
1. Crear base de datos MySQL:
```sql
CREATE DATABASE prueba_arkon;.
```

## Configurar application-local.properties:

```config
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_boletos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

```

Ejecución

```code
mvn spring-boot:run
```

Endpoints

### Eventos
- `POST /api/eventos` - Crear evento
- `PUT /api/eventos/{id}` - Actualizar evento
- `DELETE /api/eventos/{id}` - Eliminar evento  
- `GET /api/eventos/{id}/detalle` - Ver detalles

### Boletos

- `POST /api/eventos/{id}/boletos` - Vender boleto
- `POST /api/boletos/{codigo}/canjear` - Canjear boleto

### Ejemplos de Uso
Crear Evento

```json
{
    "nombre": "Conferencia Tech",
    "fechaInicio": "2025-02-01T14:30:00",
    "fechaFin": "2025-02-01T20:00:00",
    "totalBoletos": 100
}
```

Salida Ejemplo:

<img width="1052" alt="image" src="https://github.com/user-attachments/assets/2e7aed1a-0095-4baa-b138-8f4df052193a" />

### Base de Datos
Script de base de datos:

Ver script completo: [script.sql](/pruebatecnica/src/main/resources/Backup.sql)
