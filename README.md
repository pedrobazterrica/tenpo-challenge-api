# tenpo-challenge-api

```
Los requerimientos son los siguientes:
1. Debes desarrollar una API REST en Spring Boot utilizando java 11 o superior, con las siguientes funcionalidades:
    a. Debe contener un servicio llamado por api-rest que reciba 2 números, los sume, y le aplique una suba de un porcentaje que debe ser adquirido de un servicio externo (por ejemplo, si el servicio recibe 5 y 5 como valores, y el porcentaje devuelto por el servicio externo es 10, entonces (5 + 5) + 10% = 11). Se deben tener en cuenta las siguientes consideraciones:
        i. El servicio externo puede ser un mock, tiene que devolver el % sumado.
        ii. Dado que ese % varía poco, podemos considerar que el valor que devuelve ese servicio no va cambiar por 30 minutos.
        iii. Si el servicio externo falla, se debe devolver el último valor retornado. Si no hay valor, debe retornar un error la api.
        iv. Si el servicio falla, se puede reintentar hasta 3 veces.
    b. Historial de todos los llamados a todos los endpoint junto con la respuesta en caso de haber sido exitoso. Responder en Json, con data paginada. El guardado del historial de llamadas no debe sumar tiempo al servicio invocado, y en caso de falla, no debe impactar el llamado al servicio principal.
    c. La api soporta recibir como máximo 3 rpm (request / minuto), en caso de superar ese umbral, debe retornar un error con el código http y mensaje adecuado.
    d. El historial se debe almacenar en una database PostgreSQL.
    e. Incluir errores http. Mensajes y descripciones para la serie 4XX.
2. Se deben incluir tests unitarios.
3. Esta API debe ser desplegada en un docker container. Este docker puede estar en un dockerhub público. La base de datos también debe correr en un contenedor docker. Recomendación usar docker compose
4. Debes agregar un Postman Collection o Swagger para que probemos tu API
5. Tu código debe estar disponible en un repositorio público, junto con las instrucciones de cómo desplegar el servicio y cómo utilizarlo.
6. Tener en cuenta que la aplicación funcionará de la forma de un sistema distribuido donde puede existir más de una réplica del servicio funcionando en paralelo.
```

## Java

- _Language:_ `Java <11>`
- _Framework:_ `Springboot <2.7.14>`

## Technologies

- `Spring retry`
- `Java Persistence API`
- `Redis/Redisson`
- `Bucket4j`
- `RestControllerAdvice`
- `Spring Web`
- `Docker`
- `Wiremock`

## Local Setup

To run the project you just need to have docker installed
Then, while standing on the root folder in cmd run 
```bash
$ docker-compose up
```

## Pruebas de los escenarios
- En la raiz del proyecto hay una collection de postman para probar.
- Para probar todos los escenarios recomiendo primero usar el llamado "set wiremock scenario server-error", luego si llamar al "Calculate numbers" que nos debería arrojar un error. Esto porque si no probamos el error del servicio de porcentaje no funcionando primero, ya tendriamos un valor de porcentaje almacenado y nunca encontraríamos esta situación.
- El llamado "set wiremock scenario fail then ok" lo que hará será que el mock devuelva primero error y luego un valor correcto. Asi probamos que los retries funcionan.
- Luego probar a gusto!

## Comentarios
Comentarios en español porque si.
El tiempo de vida del cache del porcentage está seteado en 5 segundos para que sea mas sencillo mostrar el funcionamiento del cache, etc.
Facilmente se podría setear en 30 min desde el archivo cache-config.yml
Cuando se guarda el ultimo porcentaje adquirido del servicio, este de guarda en memoria del ms, podríamos guardarlo en una bdd para que distintos pods del ms tuvieran acceso a este. Y al conseguir un valor nuevo del porcentaje sobreescribir el existente.
Cuando recibimos más de 3 rpm devolvemos un 429 y no guardamos el log de esta llamada porque eso nos quitaria recursos, no queremos que nuestra app pueda ser tirada por un ataque DoS.
