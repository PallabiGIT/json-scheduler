
create database pallabi_db; -- Creates the new database -- Creates the user
grant all on pallabi_db.* to 'pallabi'@'%'; -- Gives all privileges to the new user on the newly created database


DROP TABLE Customer;
CREATE TABLE Customer (
                       id INT PRIMARY KEY,
                       order_id INT UNIQUE,
                       name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       post_code VARCHAR(10) NOT NULL,
                       full_address VARCHAR(100) NOT NULL,
                       backup_json BLOB NOT NULL,
                       insertion_time DATETIME DEFAULT CURRENT_TIMESTAMP
);