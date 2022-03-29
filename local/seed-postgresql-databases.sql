INSERT INTO holder (id, name, cpf) VALUES ('fc0a369d-615a-4119-aa75-b3d35119aa4c', 'Holder Name', '53767283310');
INSERT INTO account (id, balance, number, branch, holder_id, status, withdrawal_limit)
    VALUES ('fc0a369d-615a-4119-aa75-b3d35119aa4d', 100, '45215', '1452', 'fc0a369d-615a-4119-aa75-b3d35119aa4c', 'ACTIVATED', 200000);
INSERT INTO transaction (id, amount, type, created_date, account_id)
    VALUES ('fc0a369d-615a-4119-aa75-b3d35119aa4e', 50, 'CREDIT', '2022-03-01', 'fc0a369d-615a-4119-aa75-b3d35119aa4d' );



