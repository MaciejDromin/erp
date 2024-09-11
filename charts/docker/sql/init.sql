-- Finances

CREATE USER finances WITH PASSWORD 'finances';
CREATE DATABASE finances;
GRANT ALL PRIVILEGES ON DATABASE finances to finances;
GRANT ALL ON SCHEMA public TO finances;
ALTER DATABASE finances OWNER TO finances;

-- Reports

CREATE USER reports WITH PASSWORD 'reports';
CREATE DATABASE reports;
GRANT ALL PRIVILEGES ON DATABASE reports to reports;
GRANT ALL ON SCHEMA public TO reports;
ALTER DATABASE reports OWNER TO reports;
