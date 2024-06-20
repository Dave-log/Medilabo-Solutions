CREATE DATABASE IF NOT EXISTS patients;
USE patients;

CREATE TABLE IF NOT EXISTS patient (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    lastname VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    birthdate DATE NOT NULL,
    gender ENUM('M', 'F', 'OTHER') NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(15)
);