# Employee Management System

An **Employee Management System** built using **Java** and **MySQL**. This system allows you to perform CRUD (Create, Read, Update, Delete) operations on employee records, seamlessly integrating a Java application with a MySQL database.

## Features

- Add new employee records.
- View all employee details.
- Update existing employee information.
- Delete employee records with cascade effects.
- Manual control over unique IDs (no `AUTO_INCREMENT`).

## Prerequisites

Before running the project, ensure you have the following installed:

1. **Java Development Kit (JDK)** - Version 8 or higher.
2. **MySQL Server** - Version 5.7 or higher.
3. **MySQL Connector for Java** - To enable Java-MySQL connectivity.

## Table Structure

### Employee Table (MySQL Schema)
1. create database 
2. use database

```sql
CREATE TABLE employee (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    salary INT
);

```
3. Database configuration in java code
4. MYSQL-connector-jar (add to code editor)
5. Insert data optional
6. Compile and Run 

