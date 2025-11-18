Registration Form System (Java + MySQL)

A desktop registration system built using Java Swing and MySQL. It provides a simple interface for capturing user details, storing them in a database, and displaying saved records inside a table.

Overview
What This Project Does

The Registration Form System is a desktop Java application that shows how to:

Build graphical interfaces with Java Swing

Connect Java to MySQL using JDBC (MySQL Connector/J)

Insert and fetch data from a relational database

Organize and structure Java projects with external libraries

Display database results dynamically using JTable

Why This App Exists

This project is ideal when you need:

A small user-registration tool

A local database for saving form entries

A working Java–MySQL integration example

A learning resource for understanding Swing + JDBC

A simple CRUD-style application without frameworks

Where It Can Be Used

Systems like this commonly appear in:

Event attendance registration

Simple school or college admission systems

Club or membership data capture

HR onboarding forms

Desktop data-entry tools in small offices

How the System Works
User Fills Form (Swing UI)
            ↓
Validation (check required fields + terms)
            ↓
Java Program (RegistrationForm.java)
            ↓
JDBC Driver (mysql-connector-j.jar)
            ↓
MySQL Database (registration_db)
            ↓
Data retrieved and displayed in JTable

Main Features

Interactive Registration Form
Includes fields for:

Full name

Contact number

Gender (Male/Female)

Date of Birth (Drop-down)

Address (Text area)

Terms acceptance checkbox

Database Integration
All user data is saved inside a MySQL database.

Live Table Updates
Newly added records appear immediately in the JTable on the interface.

Form Reset Button
Clears fields for the next entry.

Validation
Users must accept Terms & Conditions before submitting.

Project Structure
MyJavaProject/
├── lib/
│   └── mysql-connector-j-9.5.0.jar   
│
├── src/
│   ├── Main.java                     
│   └── RegistrationForm.java         
│
└── README.md

Requirements

JDK 8 or later

MySQL Server (XAMPP or standalone installation)

MySQL Connector/J

Operating System: Windows, macOS, or Linux
