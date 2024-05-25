CREATE USER finances WITH PASSWORD 'finances';
CREATE DATABASE finances;
GRANT ALL PRIVILEGES ON DATABASE finances to finances;
GRANT ALL ON SCHEMA public TO finances;
ALTER DATABASE finances OWNER TO finances;
