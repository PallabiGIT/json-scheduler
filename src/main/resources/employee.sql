
DROP TABLE db.customer;
CREATE TABLE db.customer (
                       id INT PRIMARY KEY,
                       order_id INT UNIQUE,
                       name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       post_code VARCHAR(10) NOT NULL,
                       full_address VARCHAR(100) NOT NULL,
                       insertion_time DATETIME DEFAULT CURRENT_TIMESTAMP
);


DROP TABLE db.back_json;
CREATE TABLE db.back_json (
                             id INT PRIMARY KEY,
                             backup_json BLOB NOT NULL,
                             insertion_time DATETIME DEFAULT CURRENT_TIMESTAMP
)

