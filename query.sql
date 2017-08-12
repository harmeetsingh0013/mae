SELECT CONCAT((SELECT UPPER(code) FROM companies WHERE id = 1), 
LPAD(Auto_increment,8,'0')) FROM information_schema.tables 
WHERE table_name='orders' AND table_schema='mae_app';

SELECT id, LPAD(id,7,'0') FROM orders WHERE id = (
SELECT Auto_increment FROM information_schema.tables 
WHERE table_name='orders' AND table_schema='mae_app');