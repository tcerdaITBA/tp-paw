-- considering that only the videoId of youtube is saved in the database instead of all the URL
CREATE DOMAIN IF NOT EXISTS EMAIL AS VARCHAR(11)
    CHECK ( value ~ '[a-z_A-Z0-9\-]{11}' );
