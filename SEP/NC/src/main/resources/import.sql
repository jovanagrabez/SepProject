insert into user (id, email, enabled, last_name, name, password, country, city) VALUES (1, 'user@gmail.com', true, 'Peric', 'Pera', '$argon2i$v=19$m=65536,t=2,p=1$zhnA1rxWP8VawiICutflhQ$evRAwV+uGTn8hjnfref5cVEoql0l4Gld6a+EzTRXd1E', 'Serbia', 'Novi Sad');
insert into user (id, email, enabled, last_name, name, password, country, city) VALUES (2, 'user@yahoo.com', true, 'Peric', 'Pera', '$argon2i$v=19$m=65536,t=2,p=1$zhnA1rxWP8VawiICutflhQ$evRAwV+uGTn8hjnfref5cVEoql0l4Gld6a+EzTRXd1E', 'Serbia', 'Novi Sad');
insert into user (id, email, enabled, last_name, name, password, country, city) VALUES (3, 'user1@yahoo.com', true, 'Peric', 'Pera', '$argon2i$v=19$m=65536,t=2,p=1$zhnA1rxWP8VawiICutflhQ$evRAwV+uGTn8hjnfref5cVEoql0l4Gld6a+EzTRXd1E', 'Serbia', 'Novi Sad');
insert into user (id, email, enabled, last_name, name, password, country, city) VALUES (4, 'editor@yahoo.com', true, 'Peric', 'Pera', '$argon2i$v=19$m=65536,t=2,p=1$zhnA1rxWP8VawiICutflhQ$evRAwV+uGTn8hjnfref5cVEoql0l4Gld6a+EzTRXd1E', 'Serbia', 'Novi Sad');
insert into user (id, email, enabled, last_name, name, password, country, city) VALUES (5, 'editor1@yahoo.com', true, 'Peric', 'Pera', '$argon2i$v=19$m=65536,t=2,p=1$zhnA1rxWP8VawiICutflhQ$evRAwV+uGTn8hjnfref5cVEoql0l4Gld6a+EzTRXd1E', 'Serbia', 'Novi Sad');
insert into user (id, email, enabled, last_name, name, password, country, city) VALUES (6, 'editor2@yahoo.com', true, 'Peric', 'Pera', '$argon2i$v=19$m=65536,t=2,p=1$zhnA1rxWP8VawiICutflhQ$evRAwV+uGTn8hjnfref5cVEoql0l4Gld6a+EzTRXd1E', 'Serbia', 'Novi Sad');
insert into user (id, email, enabled, last_name, name, password, country, city) VALUES (7, 'editor3@yahoo.com', true, 'Peric', 'Pera', '$argon2i$v=19$m=65536,t=2,p=1$zhnA1rxWP8VawiICutflhQ$evRAwV+uGTn8hjnfref5cVEoql0l4Gld6a+EzTRXd1E', 'Serbia', 'Novi Sad');




insert into role(id, name) values (1, 'USER');
insert into role(id, name) values (2, 'AUTHOR');
insert into role(id, name) values (3, 'REVIEWER');
insert into role(id, name) values (4, 'EDITOR');

insert into user_role (user_id, role_id) VALUES (1, 1);
insert into user_role (user_id, role_id) VALUES (2, 1);
insert into user_role (user_id, role_id) VALUES (3, 1);
insert into user_role (user_id, role_id) VALUES (4, 4);
insert into user_role (user_id, role_id) VALUES (5, 4);
insert into user_role (user_id, role_id) VALUES (6, 4);
insert into user_role (user_id, role_id) VALUES (7, 4);


insert into magazine (id, issnnumber, name, payment_type, main_editor_id, price) values (1, '7342-8843', 'Brava casa', 0, 3, 100);
insert into magazine (id, issnnumber, name, payment_type, main_editor_id, price) values (2, '1729-6482', 'Cosmopolitan', 1, 4, 150);
insert into magazine (id, issnnumber, name, payment_type, main_editor_id, price) values (3, '3110-0072', 'Auto magazine', 0, 5, 125);

insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (1, 3);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (1, 5);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (1, 14);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (1, 17);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (2, 1);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (2, 4);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (2, 19);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (3, 7);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (3, 18);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (3, 20);
insert into magazine_scientific_areas(magazine_id, scientific_areas) VALUES (3, 21);


insert into scientific_work (id, title, scientific_area, apstract, accepted) values (1, 'Naucni rad 1', 11, 'aps', true);
insert into scientific_work (id, title, scientific_area, apstract, accepted) values (2, 'Naucni rad 2', 2, 'aps', true);
insert into scientific_work (id, title, scientific_area, apstract, accepted) values (3, 'Naucni rad 3', 21, 'aps', false);
insert into scientific_work (id, title, scientific_area, apstract, accepted) values (4, 'Naucni rad 4', 8, 'aps', true);
insert into scientific_work (id, title, scientific_area, apstract, accepted) values (5, 'Naucni rad 5', 15, 'aps', true);
insert into scientific_work (id, title, scientific_area, apstract, accepted) values (6, 'Naucni rad 6', 12, 'aps', true);
insert into scientific_work (id, title, scientific_area, apstract, accepted) values (7, 'Naucni rad 7', 18, 'aps', true);
insert into scientific_work (id, title, scientific_area, apstract, accepted) values (8, 'Naucni rad 8', 7, 'aps', true);









