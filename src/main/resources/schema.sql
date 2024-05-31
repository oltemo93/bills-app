CREATE TABLE electricity_bill (
  id          INTEGER PRIMARY KEY,
  customer_id INTEGER,
  period_start DATE,
  period_end DATE,
  cost_per_khw DOUBLE,
  total_khw_consumed INTEGER,
  total_amount NUMERIC,
  balance   NUMERIC);

CREATE TABLE customer (
    id INTEGER PRIMARY KEY,
    first_name VARCHAR,
    last_name VARCHAR
);