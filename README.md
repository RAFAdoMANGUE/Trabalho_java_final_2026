# Pizzeria Management System

Desktop application built with **Java Swing** to manage customers, pizza flavors, prices, orders, and order status in a pizzeria.

This project was developed as the final assignment for the Object-Oriented Programming course at the Federal University of Paraná. The goal was to build a complete Java application using object-oriented concepts, graphical interface development, validations, and a simple layered structure.

## About the project

The system simulates the basic workflow of a pizzeria. It allows the user to register customers, create pizza flavors, configure prices by category, place orders, calculate totals, and update the delivery status of each order.

One important part of the project is the price calculation. The pizza price is based on its flavor category and also on the area of the pizza, using different geometric shapes such as circle, square, and triangle.

## Features

* Customer registration and management
* Pizza flavor registration and management
* Price configuration by pizza category
* Order creation and editing
* Support for pizzas with one or two flavors
* Pizza price calculation based on shape and area
* Order total calculation
* Order status tracking
* Input validation
* Custom exceptions for invalid operations
* Graphical interface built with Java Swing

## Technologies used

* Java
* Java Swing
* Object-Oriented Programming
* Java Collections
* Custom Exceptions
* Layered Architecture

## Project structure

```text
src/
├── exception/    # Custom exceptions used for validations
├── model/        # Main domain classes
├── repository/   # In-memory data storage
├── service/      # Business rules and validations
└── view/         # Java Swing screens
```

## Main concepts applied

This project was mainly focused on practicing Object-Oriented Programming concepts in a real application structure.

Some of the concepts used were:

* Encapsulation
* Inheritance
* Polymorphism
* Abstraction
* Separation of responsibilities
* Business rule validation
* Layered organization

The geometric pizza shapes were modeled using inheritance and polymorphism. Each shape has its own area calculation, and this area is used as part of the final pizza price.

## How to run

1. Clone the repository:

```bash
git clone https://github.com/your-username/pizzeria-management-system.git
```

2. Open the project in a Java IDE, such as IntelliJ IDEA, Eclipse, or NetBeans.

3. Run the main class:

```text
view.TelaPrincipal
```

## Team

Developed by:

* Calebe Rodrigues Carozzi
* Dancleve Rafael Peres de Oliveira Nascimento
* Rafael Blaskowski Demeterko
* Rafael Maldonado Caetano

## Academic context

This project was created for academic purposes as part of the Object-Oriented Programming course at the Federal University of Paraná.

The main objective was to apply Java, Swing, object-oriented programming, validations, exception handling, and basic software architecture in a complete desktop application.
