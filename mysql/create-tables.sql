USE userdb;

CREATE TABLE IF NOT EXISTS users (
   id INT AUTO_INCREMENT PRIMARY KEY,
   email VARCHAR(255) NOT NULL UNIQUE
);

USE patients;

CREATE TABLE IF NOT EXISTS patient (
   id INT AUTO_INCREMENT PRIMARY KEY,
   firstname VARCHAR(255) NOT NULL,
   lastname VARCHAR(255) NOT NULL,
   birthdate DATE NOT NULL,
   gender ENUM('M', 'F', 'OTHER'),
   address VARCHAR(255),
   phone_number VARCHAR(20)
);