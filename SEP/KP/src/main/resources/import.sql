insert into payment_method (id, name, create_transactionuri, check_statusuri, is_bank) values (1, 'Bank', null, null, true);
insert into payment_method (id, name, create_transactionuri, check_statusuri, is_bank) values (2, 'PayPal', 'https://localhost:8762/paypal_service/api/pay', 'https://localhost:8762/paypal_service/api/status', false);
insert into payment_method (id, name, create_transactionuri, check_statusuri, is_bank) values (3, 'Bitcoin', 'https://localhost:8762/bitcoin_service/api/order', 'https://localhost:8762/bitcoin_service/api/order/status', false);

insert into seller (id, client_id, merchant_id, client_password, magazine, magazine_id, bitcoin_token, bank_name) values (1, 1,'12345','123','magazin', 1, 'LePn5ZVweTDw_3yquEPHP-BhfFuS15-D3U5ggYb8', 'bank2');
insert into seller (id, client_id, merchant_id, client_password, magazine, magazine_id, bitcoin_token, bank_name) values (2, 2,'98765','123','magazin 1', 2, '9gLpd_BuuCxxzqp25ZCKMPfkkRSgXfXyYGzMLTL_', 'bank1');
insert into seller (id, client_id, client_password, magazine, magazine_id, bitcoin_token, bank_name) values (3, 3,'123','magazin 2', 3, 'LePn5ZVweTDw_3yquEPHP-BhfFuS15-D3U5ggYb8', 'bank1');


insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (1, 1);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (1, 3);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (2, 1);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (2, 2);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (2, 3);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (3, 2);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (3, 3);