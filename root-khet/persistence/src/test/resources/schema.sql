CREATE TABLE IF NOT EXISTS users (
    userId INTEGER IDENTITY PRIMARY KEY,
    userName   VARCHAR(64) NOT NULL,
    mailAddr VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    productId INTEGER IDENTITY PRIMARY KEY,
    productName    VARCHAR(64) NOT NULL,
    shortDescription VARCHAR(140) NOT NULL,
    description VARCHAR(1024) NOT NULL,
    logo BLOB NOT NULL,
    uploadDate DATE,
    userId INTEGER REFERENCES users(userId) NOT NULL
);

CREATE TABLE IF NOT EXISTS productImages (
	productImageId INTEGER NOT NULL,
	productId INTEGER REFERENCES products(productId) NOT NULL,
	data BLOB NOT NULL,
	PRIMARY KEY (productImageId, productId)
);

CREATE TABLE IF NOT EXISTS videos (
	videoId CHAR(11),
	productId INTEGER REFERENCES products(productId),
	PRIMARY KEY(videoId, productId)
);