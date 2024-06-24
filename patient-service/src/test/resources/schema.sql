DROP TABLE IF EXISTS patient;

CREATE TABLE patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    lastname VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    birthdate DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(20)
);