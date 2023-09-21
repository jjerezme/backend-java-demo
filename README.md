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
- Hay un endpoint de pruebas llamado "test" que permite probar los diferentes metodos

*Crear un registro*
```
curl --location 'localhost:8080/api/test' \
--header 'Content-Type: application/json' \
--data '{
    "value": "Hello World"
}'
```

*Listar todos los registos*
```
curl --location 'localhost:8080/api/test'
```

*Obtiene un registro según el ID*
```
curl --location 'localhost:8080/api/test/1'
```

*Actualiza un registro*
```
curl --location --request PUT 'localhost:8080/api/test/1' \
--header 'Content-Type: application/json' \
--data '{
    "value": "Update"
}'
```

*Actualiza algunas propieadades de un registro*
```
curl --location --request PATCH 'localhost:8080/api/test/1' \
--header 'Content-Type: application/json' \
--data '{
    "value": "Update by patch",
    "foo": "bar"
}'
```

*Elimina el registro*
```
curl --location --request DELETE 'localhost:8080/api/test/1'
```