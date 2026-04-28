# 📌 Integración Kafka con Spring Boot

## 🧱 Arquitectura del proyecto

Este proyecto consta de dos aplicaciones Spring Boot que se comunican a través de Apache Kafka:

- **products-api**: Actúa como **Producer** (publicador de mensajes). Expone una API REST para crear productos y envía comandos a Kafka.
- **products-command**: Actúa como **Consumer** (consumidor de mensajes). Escucha los comandos enviados por el producer y los procesa.

Ambas aplicaciones usan Spring Cloud Stream para integrar con Kafka de manera sencilla.

## 🔁 Flujo de datos

El flujo completo del sistema es el siguiente:

1. **Cliente hace petición HTTP**: Un cliente envía una petición POST a `/products` en `products-api` con los datos del producto (nombre y precio).
2. **products-api recibe la petición**: El controlador `ProductController` valida los datos y llama al servicio `ProductCommandService`.
3. **Se construye un mensaje**: El servicio crea un objeto `Command` que contiene el tipo de comando ("CREATE"), un ID (null en este caso) y el cuerpo con el `ProductDto`.
4. **Se envía a Kafka (Producer)**: Usando `StreamBridge`, se envía el mensaje al topic de Kafka.
5. **Kafka lo almacena en el topic**: Kafka recibe el mensaje y lo guarda en el topic `products.commands`.
6. **products-command lo consume**: El consumer `ProductCommandConsumer` escucha el topic y recibe el mensaje.
7. **Se procesa el mensaje**: El consumer registra en el log que recibió el comando (en un escenario real, aquí se guardaría en base de datos, etc.).

## 🧠 Conceptos de Kafka aplicados en el código

### Producer
- **Dónde se encuentra**: En la clase `ProductCommandServiceImpl` dentro de `products-api`.
- **Cómo funciona**: El método `sendCreate(ProductDto dto)` construye un `Command` y lo envía usando `StreamBridge.send("commands-out-0", cmd)`.
- **Relación con HTTP**: Se activa cuando llega una petición POST al endpoint `/products` en `ProductController`.

### Topic
- **Nombre usado**: `products.commands`
- **Dónde se configura**: En `application.properties` de ambas aplicaciones, bajo `spring.cloud.stream.bindings.*.destination=products.commands`.
- **Explicación**: Es el canal donde se publican y consumen los mensajes. Actúa como una cola persistente.

### Consumer
- **Dónde se encuentra**: En la clase `ProductCommandConsumer` dentro de `products-command`.
- **Cómo funciona**: Define un bean `Consumer<Command<ProductDto>>` que se ejecuta automáticamente cuando llega un mensaje al topic. Actualmente solo registra el mensaje en el log.

### Offset
- **Explicación conceptual**: Es la posición del mensaje en el topic. Kafka lo gestiona automáticamente para asegurar que los mensajes se procesen en orden y sin perderse.
- **En el código**: No se ve directamente, ya que Spring Cloud Stream lo maneja internamente. El consumer procesa mensajes de manera secuencial.

### Brokers
- **Explicación**: Los brokers son los servidores de Kafka que manejan el almacenamiento y distribución de mensajes.
- **En el proyecto**: Se configura en `application.properties` como `spring.cloud.stream.kafka.binder.brokers=localhost:9092`, indicando que Kafka corre localmente en Docker.

## 📂 Ubicación en el código

### products-api
- **Controlador**: `src/main/java/com/springcloud/kafka/api/controllers/ProductController.java`
- **Servicio**: `src/main/java/com/springcloud/kafka/api/services/ProductCommandServiceImpl.java`
- **Modelos**: 
  - `Command`: `src/main/java/com/springcloud/kafka/api/models/Command.java`
  - `ProductDto`: `src/main/java/com/springcloud/kafka/api/models/dto/ProductDto.java`
- **Configuración**: `src/main/resources/application.properties`

### products-command
- **Consumer**: `src/main/java/com/springcloud/kafka/command/handlers/ProductCommandConsumer.java`
- **Modelos**: 
  - `Command`: `src/main/java/com/springcloud/kafka/command/models/Command.java`
  - `ProductDto`: `src/main/java/com/springcloud/kafka/command/models/dto/ProductDto.java`
- **Configuración**: `src/main/resources/application.properties`

## 🚀 Ejemplo de ejecución

1. Asegúrate de que Kafka esté corriendo en Docker (puerto 9092).
2. Ejecuta `products-api` en puerto 8080.
3. Ejecuta `products-command` en puerto 8081.
4. Envía una petición POST a `http://localhost:8080/products` con JSON: `{"name": "Producto Ejemplo", "price": 100.0}`.
5. Observa en los logs de `products-command` el mensaje recibido.

## 🧩 Reglas importantes
- Explicación en lenguaje simple: Este documento está escrito para principiantes, explicando cada paso sin asumir conocimientos avanzados.
- Ejemplos del código real: Todas las referencias son a clases y métodos existentes en el proyecto.
- Relación teoría-implementación: Se conecta cada concepto de Kafka con su uso práctico en el código.
- Claridad y pedagogía: El flujo se describe paso a paso, como un tutorial.