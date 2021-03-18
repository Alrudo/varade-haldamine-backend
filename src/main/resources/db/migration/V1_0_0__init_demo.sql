CREATE TABLE IF NOT EXISTS Person (
    id SERIAL PRIMARY KEY,
    azure_id VARCHAR(50) UNIQUE NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Possessor (
    id SERIAL PRIMARY KEY,
    institute SMALLINT,
    division SMALLINT,
    subdivision SMALLINT
);

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.modified_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TABLE IF NOT EXISTS Asset (
    id VARCHAR(20) PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    subclass VARCHAR(30) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    user_id INT DEFAULT NULL REFERENCES Person(id) ON DELETE SET NULL ON UPDATE CASCADE,
    possessor_id INT NOT NULL REFERENCES Possessor(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    expiration_date DATE,
    delicate_condition BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_at TIMESTAMP DEFAULT NOW()
);

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON Asset
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TABLE IF NOT EXISTS Worth (
    asset_id VARCHAR(20) PRIMARY KEY REFERENCES Asset(id) ON DELETE CASCADE ON UPDATE CASCADE,
    price NUMERIC(12, 2) NOT NULL,
    residual_price NUMERIC(12, 2) NOT NULL,
    purchase_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Kit_relation (
    component_asset_id VARCHAR(20) PRIMARY KEY REFERENCES Asset(id) ON DELETE CASCADE ON UPDATE CASCADE,
    major_asset_id VARCHAR(20) REFERENCES Asset(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Address (
    asset_id VARCHAR(20) PRIMARY KEY REFERENCES Asset(id) ON DELETE CASCADE ON UPDATE CASCADE,
    building_abbreviature VARCHAR(10) NOT NULL,
    room VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS Classification (
    subclass VARCHAR(20) NOT NULL,
    class VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Description (
    asset_id VARCHAR(20) PRIMARY KEY REFERENCES Asset(id) ON DELETE CASCADE ON UPDATE CASCADE,
    text VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS Comment (
    id SERIAL PRIMARY KEY,
    asset_id VARCHAR(20) PRIMARY KEY REFERENCES Asset(id) ON DELETE CASCADE ON UPDATE CASCADE,
    text VARCHAR(255) NOT NULL
);