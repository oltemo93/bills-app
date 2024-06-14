CREATE TABLE electricity_bill (
  id INTEGER NOT NULL AUTO_INCREMENT,
  customer_id INTEGER,
  period_start DATE,
  period_end DATE,
  cost_per_khw DOUBLE,
  total_khw_consumed INTEGER,
  total_amount NUMERIC,
  balance   NUMERIC,
  service_address VARCHAR(50),
  PRIMARY KEY (id)
);

CREATE TABLE customer (
    id INTEGER NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    PRIMARY KEY (id)
);