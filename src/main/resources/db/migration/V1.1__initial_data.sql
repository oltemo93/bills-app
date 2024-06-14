INSERT INTO customer (id, first_name, last_name) VALUES
 (1, 'JHON', 'DOE'),
 (2, 'FULATINO', 'DE TAL');

 INSERT INTO electricity_bill (customer_id, period_start, period_end, cost_per_khw, total_khw_consumed, total_amount, balance, service_address) VALUES
   ('1', '2024-04-01', '2024-04-30', 0.5, 1000.0, 500.0, 500.0, 'Calle 30 3519'),
   ('2', '2024-04-01', '2024-04-30', 0.5, 1400.0, 700.0, 700.0, 'Calle siempreviva 219');