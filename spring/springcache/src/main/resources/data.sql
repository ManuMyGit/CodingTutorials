delete from payment;
delete from person;
delete from account;
delete from address;

insert into address (id, street, city, state, zip_code, country) values
   (100, '47 W 13th St', 'New York City', 'NY', '10011', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (101, '20 Cooper Square', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (102, '1 E 2nd St', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (103, '75 3rd Ave', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (104, 'Metrotech Center', 'Brooklyn', 'NY', '11201', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (105, '721 Broadway', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (106, '40 E 7th St', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (107, '334 E 25th St', 'New York City', 'NY', '10010', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (108, '120 E 14th St', 'New York City', 'NY', '10003', 'US');
insert into address (id, street, city, state, zip_code, country) values
   (109, '120 E 12th St', 'New York City', 'NY', '10003', 'US');

insert into account (id, routing_number, account_number) values
    (1, '021000021', '200000001');
insert into account (id, routing_number, account_number) values
    (2, '021000021', '200000002');
insert into account (id, routing_number, account_number) values
    (3, '021000021', '200000003');
insert into account (id, routing_number, account_number) values
    (4, '021000021', '200000004');
insert into account (id, routing_number, account_number) values
    (5, '121000248', '300000001');
insert into account (id, routing_number, account_number) values
    (6, '121000248', '300000002');
insert into account (id, routing_number, account_number) values
    (7, '121000248', '300000003');
insert into account (id, routing_number, account_number) values
    (8, '121000248', '300000004');

insert into person(id, first_name, last_name, address) values
    (1, 'Dior', 'Eluchil', 100);
insert into person(id, first_name, last_name, address) values
    (2, 'Finrod', 'Felagund', 101);
insert into person(id, first_name, last_name, address) values
    (3, 'Tar', 'Miniatur', 102);
insert into person(id, first_name, last_name, address) values
    (4, 'Tar', 'Palantir', 103);

insert into payment(id, funding_account, payee_account, payer_info, payee_info, amount) values
    (1, 1, 5, 1, 4, 100);
insert into payment(id, funding_account, payee_account, payer_info, payee_info, amount) values
    (2, 2, 6, 2, 3, 99.5);
insert into payment(id, funding_account, payee_account, payer_info, payee_info, amount) values
    (3, 3, 7, 1, 2, 33.89);
insert into payment(id, funding_account, payee_account, payer_info, payee_info, amount) values
    (4, 4, 8, 3, 4, 1500);
insert into payment(id, funding_account, payee_account, payer_info, payee_info, amount) values
    (5, 1, 5, 4, 2, 45.74);
insert into payment(id, funding_account, payee_account, payer_info, payee_info, amount) values
    (6, 2, 6, 3, 1, 564);