CREATE TABLE IF NOT EXISTS users (
    userId SERIAL PRIMARY KEY,
    userName   VARCHAR(64) NOT NULL,
    mailAddr VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    productId SERIAL PRIMARY KEY,
    productName    VARCHAR(64) NOT NULL,
    shortDescription VARCHAR(140) NOT NULL,
    description TEXT NOT NULL,
    logo BYTEA NOT NULL,
    uploadDate DATE NOT NULL,
    userId INTEGER REFERENCES users(userId) NOT NULL
);

CREATE TABLE IF NOT EXISTS productImages (
	productImageId INTEGER NOT NULL,
	productId INTEGER REFERENCES products(productId) NOT NULL,
	data BYTEA NOT NULL,
	PRIMARY KEY (productImageId, productId)
)
