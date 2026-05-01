# 🧠 Prompt para generar documentación de Kafka con Spring Boot

Quiero que analices un proyecto compuesto por **dos aplicaciones Spring Boot**:

- `products-api` → actúa como **Producer**
- `products-command` → actúa como **Consumer**

Ambos proyectos están integrados con **Apache Kafka**.

---

## 🎯 Objetivo

Generar un archivo de documentación en formato **Markdown (.md)** dentro de una carpeta llamada: /doc/kafka-flow.md



La documentación debe explicar de forma clara, sencilla y pedagógica el flujo completo del sistema, para que cualquier persona con conocimientos básicos de programación pueda entenderlo.

---

## 🔍 Qué debes analizar del código

Basado en la estructura del proyecto:

### 📦 products-api
- Controllers (`ProductController`)
- Services (`ProductCommandService`, `ProductCommandServiceImpl`)
- DTOs (`ProductDto`, `Command`)

### 📦 products-command
- Handlers (`ProductCommandConsumer`)
- DTOs (`ProductDto`, `Command`)

---

## 📚 Explicación requerida (IMPORTANTE)

Debes explicar **dónde se encuentra y cómo funciona cada concepto de Kafka en el código**:

### 🔑 Conceptos clave

1. **Producer (publica mensajes)**
   - Identificar en qué clase se envían mensajes a Kafka
   - Explicar qué método lo hace
   - Relacionarlo con el flujo HTTP (cuando se hace POST)

2. **Topic (canal de mensajes)**
   - Indicar el nombre del topic usado (ej: `products.commands`)
   - Explicar dónde se configura o se referencia

3. **Consumer (lee mensajes)**
   - Identificar la clase que consume mensajes (`ProductCommandConsumer`)
   - Explicar cómo escucha el topic

4. **Offset (posición del mensaje)**
   - Explicar conceptualmente qué es
   - Aclarar que Kafka lo gestiona automáticamente
   - Indicar si se ve o no directamente en el código

5. **Brokers**
   - Explicar que Kafka corre en Docker
   - Relacionarlo con `localhost:9092`

---

## 🔄 Flujo que debes documentar

Explicar paso a paso:

1. Cliente hace petición HTTP (POST `/products`)
2. `products-api` recibe la petición
3. Se construye un mensaje (DTO)
4. Se envía a Kafka (Producer)
5. Kafka lo almacena en el topic
6. `products-command` lo consume
7. Se procesa el mensaje

---

## 🧾 Estructura del archivo generado

El archivo debe tener esta estructura:

```md
# 📌 Integración Kafka con Spring Boot

## 🧱 Arquitectura del proyecto

## 🔁 Flujo de datos

## 🧠 Conceptos de Kafka aplicados en el código

### Producer
### Topic
### Consumer
### Offset
### Brokers

## 📂 Ubicación en el código

(Explicar clases y paquetes reales del proyecto)

## 🚀 Ejemplo de ejecución

🧩 Reglas importantes
Explicar en lenguaje simple (tipo tutorial)
No asumir conocimientos avanzados
Usar ejemplos del código real
Relacionar teoría con implementación
Ser claro y didáctico