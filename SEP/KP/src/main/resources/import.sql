insert into payment_method (id, name, create_transactionuri, check_statusuri, is_bank, image_associated) values (1, 'Bank', null, null, true, 'https://img.icons8.com/color/96/000000/bank-building.png');
insert into payment_method (id, name, create_transactionuri, check_statusuri, is_bank, image_associated) values (2, 'PayPal', 'https://localhost:8762/paypal_service/api/pay', 'https://localhost:8762/paypal_service/api/status', false, 'https://img.icons8.com/color/96/000000/paypal.png');
insert into payment_method (id, name, create_transactionuri, check_statusuri, is_bank, image_associated) values (3, 'Bitcoin', 'https://localhost:8762/bitcoin_service/api/order', 'https://localhost:8762/bitcoin_service/api/order/status', false, 'https://img.icons8.com/office/80/000000/bitcoin.png');

insert into seller (id, client_id, client_password, magazine, magazine_id, user_id) values (1, 1,'123','magazin', 1, 3);
insert into seller (id, client_id, client_password, magazine, magazine_id, user_id) values (2, 2,'123','magazin 1', 2, 4);
insert into seller (id, client_id, client_password, magazine, magazine_id, user_id) values (3, 3,'123','magazin 2', 3, 5);

insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (1, 1);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (1, 3);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (2, 1);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (2, 2);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (2, 3);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (3, 2);
insert into seller_payment_methods(seller_id, payment_methods_id) VALUES (3, 3);

insert into payment_data(id, name, value) values (1, 'merchantId', '12345');
insert into payment_data(id, name, value) values (2, 'merchantPassword', '123');
insert into payment_data(id, name, value) values (3, 'bankName', 'bank2');
insert into payment_data(id, name, value) values (4, 'bitcoinToken', 'LePn5ZVweTDw_3yquEPHP-BhfFuS15-D3U5ggYb8');

insert into payment_data(id, name, value) values (5, 'merchantId', '98765');
insert into payment_data(id, name, value) values (6, 'merchantPassword', '123');
insert into payment_data(id, name, value) values (7, 'bankName', 'bank1');
insert into payment_data(id, name, value) values (8, 'bitcoinToken', '9gLpd_BuuCxxzqp25ZCKMPfkkRSgXfXyYGzMLTL_');

insert into payment_data(id, name, value) values (9, 'bitcoinToken', 'LePn5ZVweTDw_3yquEPHP-BhfFuS15-D3U5ggYb8');

insert into seller_payments_data(seller_id, payments_data_id) VALUES (1, 1);
insert into seller_payments_data(seller_id, payments_data_id) VALUES (1, 2);
insert into seller_payments_data(seller_id, payments_data_id) VALUES (1, 3);
insert into seller_payments_data(seller_id, payments_data_id) VALUES (1, 4);

insert into seller_payments_data(seller_id, payments_data_id) VALUES (2, 5);
insert into seller_payments_data(seller_id, payments_data_id) VALUES (2, 6);
insert into seller_payments_data(seller_id, payments_data_id) VALUES (2, 7);
insert into seller_payments_data(seller_id, payments_data_id) VALUES (2, 8);

insert into seller_payments_data(seller_id, payments_data_id) VALUES (3, 9);

insert into form_data (id, name, code, type) values (1, 'Merchant id', 'merchantId', 0);
insert into form_data (id, name, code, type) values (2, 'Merchant password', 'merchantPassword', 0);
insert into form_data (id, name, code, type) values (3, 'Bank name', 'bankName', 0);
insert into form_data (id, name, code, type) values (4, 'Client id', 'clientId', 0);
insert into form_data (id, name, code, type) values (5, 'Client secret', 'clientSecret', 0);
insert into form_data (id, name, code, type) values (6, 'Bitcoin token', 'bitcoinToken', 0);

insert into payment_method_required_form_data (payment_method_id, required_form_data_id) values (1, 1);
insert into payment_method_required_form_data (payment_method_id, required_form_data_id) values (1, 2);
insert into payment_method_required_form_data (payment_method_id, required_form_data_id) values (1, 3);

insert into payment_method_required_form_data (payment_method_id, required_form_data_id) values (2, 4);
insert into payment_method_required_form_data (payment_method_id, required_form_data_id) values (2, 5);

insert into payment_method_required_form_data (payment_method_id, required_form_data_id) values (3, 6);