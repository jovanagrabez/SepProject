INSERT INTO client (id, first_name,last_name) VALUES (1, 'Jovana', 'Grabez');
INSERT INTO client (id, first_name,last_name) VALUES (2, 'Andjela', 'Trninic');

INSERT INTO account (id, amount, client_id, merchant_id, merchant_password) VALUES (1, 5000, 1, '12345', '123');
INSERT INTO account (id, amount, client_id, merchant_id, merchant_password) VALUES (2, 100, 2, '98765', '123');
INSERT INTO account (id, amount, client_id, merchant_id, merchant_password) VALUES (3, 200, 2, '3', '123');
INSERT INTO account (id, amount, client_id, merchant_id, merchant_password) VALUES (4, 0, 2, '4', '123');
INSERT INTO account (id, amount, client_id, merchant_id, merchant_password) VALUES (5, 2000, 1, '45612', '123');

INSERT INTO card(id,cardholder_name, pan, security_code,valid_to,account_id) values (1,'Jovana Grabez','d+Q9hBiw6bdFePUwFsX8pQ==','1V/HEhSFe8gsmlW5LOBqjQ==','2019-03-02 01:00:00',1);
INSERT INTO card(id,cardholder_name, pan, security_code,valid_to,account_id) values (2,'Jovana Grabez','2UWzyacY7IJrTH/IYBPjzw==','1V/HEhSFe8gsmlW5LOBqjQ==','2019-03-02 01:00:00',2);
INSERT INTO card(id,cardholder_name, pan, security_code,valid_to,account_id) values (3,'Jovana Grabez','bAMwyps24+WkoZhbW3ufPw==','1V/HEhSFe8gsmlW5LOBqjQ==','2019-03-02 01:00:00',3);
INSERT INTO card(id,cardholder_name, pan, security_code,valid_to,account_id) values (4,'Jovana Grabez','aEk1LjyGNkUbodgUPDyUUQ==','1V/HEhSFe8gsmlW5LOBqjQ==','2019-03-02 01:00:00',4);