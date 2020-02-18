INSERT INTO client (id, first_name,last_name) VALUES (1, 'Jovana', 'Grabez');
INSERT INTO client (id, first_name,last_name) VALUES (2, 'Andjela', 'Trninic');

INSERT INTO account (id, amount, client_id, merchant_id, merchant_password) VALUES (1, 5000, 1, '1', '123');
INSERT INTO account (id, amount, client_id, merchant_id, merchant_password) VALUES (2, 100, 2, '2', '123');
INSERT INTO account (id, amount, client_id, merchant_id, merchant_password) VALUES (3, 200, 2, '3', '123');
INSERT INTO account (id, amount, client_id, merchant_id, merchant_password) VALUES (4, 0, 2, '4', '123');

INSERT INTO card(id,cardholder_name, pan, security_code,valid_to,account_id) values (1,'Jovana Grabez','kPa8ifq8VelvLDpIgS8u5w==','1V/HEhSFe8gsmlW5LOBqjQ==','2019-03-02 01:00:00',1);
INSERT INTO card(id,cardholder_name, pan, security_code,valid_to,account_id) values (2,'Jovana Grabez','joedWlmIpX9Dij902Z9fXA==','1V/HEhSFe8gsmlW5LOBqjQ==','2019-03-02 01:00:00',2);
INSERT INTO card(id,cardholder_name, pan, security_code,valid_to,account_id) values (3,'Jovana Grabez','3ZO6OlJQ2f+88DzGvj6Z4w==','1V/HEhSFe8gsmlW5LOBqjQ==','2019-03-02 01:00:00',3);
INSERT INTO card(id,cardholder_name, pan, security_code,valid_to,account_id) values (4,'Jovana Grabez','Q+fU4NjtWe3jfe/8aQS18Q==','1V/HEhSFe8gsmlW5LOBqjQ==','2019-03-02 01:00:00',4);