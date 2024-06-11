CREATE TABLE electricity_bill (
  id BIGINT GENERATED ALWAYS AS IDENTITY,
  customer_id INTEGER,
  period_start DATE,
  period_end DATE,
  cost_per_khw DOUBLE,
  total_khw_consumed INTEGER,
  total_amount NUMERIC,
  balance   NUMERIC,
  service_address VARCHAR);

CREATE TABLE customer (
    id INTEGER PRIMARY KEY,
    first_name VARCHAR,
    last_name VARCHAR
);