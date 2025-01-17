# Ads Report App

## About the project

This is an application developed to facilitate the generation of Google Ads reports, designed on clean architecture.

The application allows you to retrieve data from your Google Ads accounts (manager and client), and return in csv or json format.

## Techs

### Architecture
- **Architecture**: Clean Architecture

### Back-end
- **Framework**: Spring Framework
- **Language**: Java 21
- **Tests**: JUnit and Mockito

### API Documentation
- **Tools**: Swagger API, Javadoc

## How to run

Follow the steps below to set up and run the project on your local machine.

## Prerequisites

- Git
- java 21 (JDK) e Maven
- Docker and Docker Compose

## Steps

**Make sure you have opened the ports 8080 (application) on your machine locally**

1. **Clone this repo**
   
2. **Configure the 'ads.properties' file on '/src/main/resources':**
   ```bash
    api.googleads.clientId=[your-client-id]
    api.googleads.clientSecret=[your-client-secret]
    api.googleads.refreshToken=[your-refersh-token]
    api.googleads.developerToken=[your-developer-token]
    loginCustomerId=[the-id-of-your-MCC]

3. **Run the app on your IDE**
