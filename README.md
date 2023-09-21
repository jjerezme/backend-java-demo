# Proyecto Backend JAVA

Este es un proyecto de backend con Spring Boot Framework.

## Requisitos
- Java 20
- PostgreSQL 11.18

## Instrucciones
- Crear una base de datos y [ejecutar el siguiente script.](script/database.sql)
- Actualizar archivo de propiedades con las credenciales de la BD
- Se puede utilizar otro motor de base de datos, sin embargo el script puede necesitar modificaciones
- Ejecutar la aplicación y probar los endpoints

## Endpoints
Hay un endpoint de pruebas llamado "test" que permite probar las diferentes operaciones

**Crear un registro**
```
curl --location 'localhost:8080/api/test' \
--header 'Content-Type: application/json' \
--data '{
    "value": "Hello World"
}'
```

**Listar todos los registos**
```
curl --location 'localhost:8080/api/test'
```

**Obtiene un registro según el ID**
```
curl --location 'localhost:8080/api/test/1'
```

**Actualiza un registro**
```
curl --location --request PUT 'localhost:8080/api/test/1' \
--header 'Content-Type: application/json' \
--data '{
    "value": "Update"
}'
```

**Actualiza algunas propiedades de un registro**
```
curl --location --request PATCH 'localhost:8080/api/test/1' \
--header 'Content-Type: application/json' \
--data '{
    "value": "Update by patch",
    "foo": "bar"
}'
```

**Elimina el registro**
```
curl --location --request DELETE 'localhost:8080/api/test/1'
```

### Clientes

**Endpoint base**
```
curl --location --request GET 'localhost:8080/api/clientes'
```

La estructura del objeto para crear un cliente es la siguiente:
```
{
    "nombre": "Juan Pablo",
    "dni": "999999",
    "genero": "M",
    "edad": 36,
    "direccion": "Mi Direccion",
    "telefono": "3159998877",
    "password": "12345",
    "estado": true
}
```

### Cuentas

**Endpoint base**
```
curl --location --request GET 'localhost:8080/api/cuentas'
```

La estructura del objeto para crear una cuenta es la siguiente:
```
{
    "dni": "999999",
    "cuenta": {
        "numero": "555000",
        "tipo": "Ahorros",
        "saldo": 1000
    }
}
```

Se debe asociar la cuenta con el DNI del cliente.

### Movimientos

**Endpoint base**
```
curl --location --request GET 'localhost:8080/api/movimientos'
```

La estructura del objeto para crear un movimientos es la siguiente:
```
{
    "cuenta": "555000",
    "valor": -100
}
```

Se debe asociar el movimiento con el número de la cuenta.

### Reportes

**Endpoint base**
```
curl --location --request GET 'localhost:8080/api/reportes'
```

Para el endpoint de reportes, se puede llamar sin parámetros para obtener todos los registros o se puede filtrar usando el número de cuenta y opcionalmente un rango de fechas. Las fechas deben ir en formato dd/MM/yyyy

```
curl --location 'localhost:8080/api/reportes/:num?fechaDesde=01%2F09%2F2023&fechaHasta=20%2F09%2F2023'
```