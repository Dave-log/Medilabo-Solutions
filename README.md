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

1. **Download the docker-compose.yml** in the **public folder** (not the one at root folder)
2. Ensure you have **Docker Desktop installed** on your machine. You can download it from the official [Docker's official website](https://www.docker.com/products/docker-desktop).
3. Run the following command in a terminal: 
```bash
docker compose up
```
This command will pull the necessary Docker images from the container registry and start the services.

## Usage

Once the microservices are running:

1. Open your browser and visit http://localhost:3000
2. Sign in using your Google account
3. You can now access and use the Patient table to manage patient information and generate diabetes risk reports.

## API Documentation

For detailed API documentation, you can visit the Swagger UI at http://localhost:8080/swagger-ui.html when the application is running.
