ALTER TABLE user add column role VARCHAR(64) not null;

CREATE INDEX idx_role ON user(role);