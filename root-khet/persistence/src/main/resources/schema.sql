CREATE TABLE IF NOT EXISTS users (
    userId SERIAL PRIMARY KEY,
    userName   VARCHAR(64) NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    productId SERIAL PRIMARY KEY,
    productName    VARCHAR(64) NOT NULL,
    shortDescription VARCHAR(140) NOT NULL,
    description TEXT NOT NULL,
    logo BYTEA NOT NULL,
    uploadDate TIMESTAMP NOT NULL,
    userId INTEGER REFERENCES users(userId) NOT NULL
);

CREATE TABLE IF NOT EXISTS productImages (
	productImageId INTEGER NOT NULL,
	productId INTEGER REFERENCES products(productId) ON DELETE CASCADE NOT NULL,
	data BYTEA NOT NULL,
	PRIMARY KEY (productImageId, productId)
);

CREATE TABLE IF NOT EXISTS videos (
	videoId CHAR(11) NOT NULL,
	productId INTEGER REFERENCES products(productId) ON DELETE CASCADE NOT NULL,
	PRIMARY KEY(videoId, productId)
);