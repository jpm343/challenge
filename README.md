# Challenge
Desafío para MobDev

## Ambientes y prerrequisitos

### Prerrequisitos

* **Java JDK 11**. Sugerencia: AdoptOpenJDK (https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot)

Es necesario tener acceso a internet para que se puedan descargar las dependencias.

## Construcción

### Maven

Para generar el artefacto y ejecutar las pruebas unitarias/integración, basta con ejecutar:

```text
./mvn clean package
```

Para ejecutar la aplicación en modo local, se debe ejecutar:

```text
./mvn spring-boot:run
```

El proyecto está configurado con swagger-ui para testear la API. Se puede acceder a esta en la ruta raíz del proyecto:

```text
localhost:8080
```

