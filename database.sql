CREATE DATABASE ebookmanagement;
USE ebookmanagement;

CREATE TABLE authors(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL unique
);

CREATE TABLE categories(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255) DEFAULT ""
);

CREATE TABLE painters(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL unique
);

CREATE TABLE roles(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name ENUM('admin', 'user', 'sys-admin') NOT NULL UNIQUE 
);

CREATE TABLE books(
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL UNIQUE,
    summary LONGTEXT,
    image LONGTEXT,
    type_of_book ENUM('composed', 'publish', 'private'),
    publishing_year INT DEFAULT 0,
    evaluate FLOAT DEFAULT 0,
    status ENUM('public', 'private', 'follow', 'pay'),
    thumbnail LONGTEXT NOT NULL,
    price FLOAT DEFAULT 0 CHECK (price >= 0)
);

CREATE TABLE users(
	id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(100) DEFAULT "",
    password VARCHAR(20) DEFAULT "",
    link_avatar LONGTEXT,
    phone_number VARCHAR(10) NOT NULL UNIQUE,
    gender TINYINT(1) DEFAULT 2,
    date_of_birth DATE,
    budget FLOAT DEFAULT 0,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0,
    is_active TINYINT(1) DEFAULT 1,
    create_at DATETIME,
    update_at DATETIME,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE tokens(
	id INT PRIMARY KEY AUTO_INCREMENT,
    token LONGTEXT NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE social_accounts(
	id INT PRIMARY KEY AUTO_INCREMENT,
    provider VARCHAR(20) NOT NULL,
    provider_id VARCHAR(50) NOT NULL,
    email VARCHAR(150) NOT NULL,
    name VARCHAR(150) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orders(
	id INT PRIMARY KEY AUTO_INCREMENT,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'wait-confirm', 'paid', 'cancelled'),
	total_money FLOAT NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE orders  ADD FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE order_details(
	id INT PRIMARY KEY AUTO_INCREMENT,
    price FLOAT NOT NULL,
    order_id INT,
    book_id INT,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

CREATE TABLE comment(
	id INT PRIMARY KEY AUTO_INCREMENT,
    comment LONGTEXT NOT NULL,
    create_at DATETIME,
    update_at DATETIME,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE follow(
	id INT PRIMARY KEY AUTO_INCREMENT,
    following INT NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE ratings(
	id INT PRIMARY KEY AUTO_INCREMENT,
    score TINYINT(2) NOT NULL CHECK (score >= 0),
    book_id INT,
    user_id INT,
    FOREIGN KEY (book_id) REFERENCES books(id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE user_book(
	id INT PRIMARY KEY AUTO_INCREMENT,
    status ENUM('owner', 'buyer', 'follower'),
    book_id INT,
    user_id INT,
    FOREIGN KEY (book_id) REFERENCES books(id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE author_book(
	id INT PRIMARY KEY AUTO_INCREMENT,
    author_id INT,
    book_id INT,
    FOREIGN KEY (book_id) REFERENCES books(id),
	FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE TABLE painter_book(
	id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT,
    painter_id INT,
    FOREIGN KEY (book_id) REFERENCES books(id),
	FOREIGN KEY (painter_id) REFERENCES painters(id)
);

CREATE TABLE category_book(
	id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT,
    book_id INT,
    FOREIGN KEY (book_id) REFERENCES books(id),
	FOREIGN KEY (category_id) REFERENCES categories(id)
);

