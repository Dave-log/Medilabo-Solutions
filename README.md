![](public/medilaboBanner.jpeg)

# <div align="center">Medilabo Solutions - Diabetes Report Application</div>

The Diabetes Report Application by Medilabo Solutions is a comprehensive tool designed to assist healthcare providers in managing patient data, notes, and assessing diabetes risk. This application leverages modern microservices architecture to deliver a secure, scalable, and user-friendly interface.

## Features

- **Patient Management**: Efficiently manage patient records including creation, update, deletion and retrieval.
- **Patient Notes Management**: Maintain detailed notes for each patient, ensuring that all relevant information is recorded and easily accessible.
- **Diabetes Risk Assessor**: Automatically assess the risk of diabetes for patients based on their medical history and other factors.
- **Secured Application**: Secure access to the application with Google account sign-in and OAuth2 integration.
- **Simple and Practical Interface**: User-friendly interface designed for ease of use by healthcare professionals.

## Installation

To get started with the Diabetes Report Application, follow these steps:

1. Download the **docker-compose.yml** in the [public folder](https://gitlab.com/DaveLog/medilabo-solutions/-/blob/main/public/docker-compose.yml?ref_type=heads).
2. Download the **two database initialization files** in the public folder as well : [init.sql](https://gitlab.com/DaveLog/medilabo-solutions/-/blob/main/public/init.sql?ref_type=heads) and [mongo-init.js](https://gitlab.com/DaveLog/medilabo-solutions/-/blob/main/public/mongo-init.js?ref_type=heads). **Ensure you put them in the same folder of the docker-compose file**.
3. Ensure you have **Docker Desktop installed** on your machine. You can download it from the official [Docker's official website](https://www.docker.com/products/docker-desktop).
4. Run the following command in a terminal: 
```bash
docker compose up
```
This command will pull the necessary Docker images from the container registry and start the services. It may take a minute.

## Usage

Once the microservices are running:

1. Open your browser and visit http://localhost:3000
2. Sign in using your Google account
3. You can now access and use the Patient table to manage patient information and generate diabetes risk reports.

## API Documentation

For detailed API documentation, you can visit the Swagger UI at http://localhost:8080/swagger-ui.html when the application is running.
