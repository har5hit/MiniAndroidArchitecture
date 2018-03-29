# MiniAndroidArchitecture
A starter kit with basic use of Architecture components. It displays Github users from api

Build Steps:

* Clone this repository 
* Open it using android Studio 3.1

# Specifications

* Architecture - MVVM(Model-View-ViewModel) with Dagger2 and Android Architecture Components
* Language - Kotlin / JAVA

## Screens

### Login Screen

The first screen which shows user to Login via github to access the app with his OAuth token.

### List Screen

* The list of users are retrieved from the database otherwise fetched from server using githu's users api. 

* User can perform search or filter by name,

* If the user exists in the db, it will be shown instantly, 
  else it will be fetched from the server and shown to the user.

## Authors

* **Harshith Shetty**
