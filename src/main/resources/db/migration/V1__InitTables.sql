CREATE TABLE users (
	id INT UNSIGNED auto_increment NOT NULL,
	username VARCHAR(80) NOT NULL,
	firstname VARCHAR(160) NOT NULL,
	lastname VARCHAR(160) NOT NULL,
	birthday DATE NOT NULL,
	email TEXT NOT NULL,
	CONSTRAINT users_PK PRIMARY KEY (id),
	UNIQUE(username)
)