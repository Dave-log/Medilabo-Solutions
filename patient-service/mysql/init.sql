CREATE DATABASE IF NOT EXISTS userdb;
CREATE DATABASE IF NOT EXISTS patients;

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

INSERT INTO patient (firstname, lastname, birthdate, gender, address, phone_number)
VALUES
    ('Test', 'TestNone', '1966-12-31', 'F', '1 Brookside St', '100-222-3333'),
    ('Test', 'TestBorderline', '1945-06-24', 'M', '2 High St', '200-333-4444'),
    ('Test', 'TestInDanger', '2004-06-18', 'M', '3 Club Road', '300-444-5555'),
    ('Test', 'TestEarlyOnset', '2002-06-28', 'F', '4 Valley Dr', '400-555-6666');


