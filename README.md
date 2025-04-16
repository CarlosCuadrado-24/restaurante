# 🍽️ Restaurante API

Este proyecto es una **API RESTful** para la gestión de operaciones en un restaurante.  
Permite administrar **menús**, **platos**, **pedidos** y **clientes**, siguiendo los principios de diseño **REST**, el patrón **MVC**, y buenas prácticas de **Clean Code**.  
Incluye el uso de **DTOs**, **mappers**, documentación con **Swagger**, y patrones de diseño como **Chain of Responsibility**, **Mediator** y **Strategy**.

---

## 🚀 Características principales

- ✅ CRUD completo para:
  - Clientes
  - Menús
  - Platos
  - Pedidos
- 📚 Documentación interactiva con Swagger UI
- 🛡️ Uso de DTOs para exponer datos de forma segura
- 🧠 Separación de responsabilidades con el patrón MVC
- 🧩 Patrones de diseño aplicados para lógica compleja
- 🧼 Código limpio y mantenible
- 🔁 Manejadores de lógica encadenada con Chain of Responsibility
- 📊 Cálculo dinámico de totales mediante Strategy + Mediator
- 🔄 Conversores (Converters) para mapear entre entidades y DTOs

---

## 🧩 Patrones de Diseño Usados

- **Chain of Responsibility**  
  Para aplicar reglas sobre clientes frecuentes o platos populares en el proceso de pedido.

- **Strategy**  
  Para calcular el total de un pedido según el tipo de cliente.

- **Mediator**  
  Para centralizar y delegar el cálculo de totales a las estrategias adecuadas.

---

## 🧪 Endpoints Disponibles

Documentados automáticamente con Swagger.

🔗 Accede a la documentación en:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


## 🧼 Buenas prácticas aplicadas
✔️ Separación clara entre capa de presentación, lógica de negocio y persistencia

✔️ DTOs para evitar exponer directamente entidades

✔️ Patrón DTO + Mapper para desacoplar datos y lógica

✔️ Excepciones controladas y mensajes claros

✔️ Código limpio y legible

## ✍️ Autor
Carlos Cuadrado
💼 Proyecto académico

## 🗂️ Resumen de Estructura

```plaintext
com.resturante.logica
├── components
│   ├── chainOfResponsibility
│   ├── mediator
│   ├── strategy
│   ├── ClienteConverter
│   ├── DetalleConverter
│   ├── MenuConverter
│   ├── PedidoConverter
│   └── PlatoConverter
├── config
├── controllers
├── dto
├── models
├── repositories
├── services
├── LogicaApplication
└── documentacion


