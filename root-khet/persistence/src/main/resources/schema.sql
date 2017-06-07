CREATE TABLE IF NOT EXISTS users (
    userId SERIAL PRIMARY KEY,
    userName   VARCHAR(64) NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL,
    password CHAR(60) NOT NULL,
    profilePicture BYTEA NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    productId SERIAL PRIMARY KEY,
    productName    VARCHAR(64) NOT NULL,
    shortDescription VARCHAR(140) NOT NULL,
    website VARCHAR(1024),
    description TEXT NOT NULL,
    category VARCHAR(32) NOT NULL CHECK (category IN ('APP', 'ART', 'BOOK', 'FASHION', 'FILM', 'FOOD', 'GADGET', 'GAME', 'MUSIC', 'OTHER')),
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

CREATE TABLE IF NOT EXISTS comments (
    commentId SERIAL PRIMARY KEY NOT NULL,
    commentContent VARCHAR(1024) NOT NULL,
    commentDate TIMESTAMP NOT NULL,
    userId INTEGER REFERENCES users(userId) ON DELETE CASCADE NOT NULL,
    productId INTEGER REFERENCES products(productId) ON DELETE CASCADE NOT NULL,
    parentId INTEGER REFERENCES comments(commentId) ON DELETE CASCADE,
    UNIQUE(commentDate, userId, productId)
);

