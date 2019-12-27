insert into payment_method (id, name) values (1, 'Bank');
insert into payment_method (id, name) values (2, 'PayPal');
insert into payment_method (id, name) values (3, 'Bitcoin');

insert into seller (id, client, client_id, client_password, magazine, magazine_id, bitcoin_token) values (1, '1','1','123','magazin', 1, '9gLpd_BuuCxxzqp25ZCKMPfkkRSgXfXyYGzMLTL_');
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (1, 1);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (1, 3);