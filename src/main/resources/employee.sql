
create database pallabi_db; -- Creates the new database -- Creates the user
grant all on pallabi_db.* to 'pallabi'@'%'; -- Gives all privileges to the new user on the newly created database


DROP TABLE Customer;
CREATE TABLE Customer (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       orderID INT UNIQUE,
                       name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       postCode VARCHAR(10) NOT NULL,
                       fullAddress VARCHAR(100) NOT NULL
);