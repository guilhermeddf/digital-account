CREATE TYPE type as ENUM ('CREDIT', 'DEBIT');

CREATE CAST (CHARACTER VARYING as type) WITH INOUT AS IMPLICIT;

CREATE TABLE transaction(
    id UUID not null primary key default public.uuid_generate_v4(),
    amount numeric(12,2) not null,
    type type not null,
    created_date timestamp with time zone not null,
    account_id UUID not null references account
);


CREATE FUNCTION transaction_type_compare(type, text)
RETURNS boolean
AS '
select cast($1 as text) = $2;
'
LANGUAGE sql IMMUTABLE;

CREATE OPERATOR = (
    leftarg = type,
    rightarg = text,
    procedure = transaction_type_compare
);

INSERT INTO transaction (id, amount, type, created_date, account_id)
    VALUES ('fc0a369d-615a-4119-aa75-b3d35119aa4e', 50, 'CREDIT', '2022-03-01', 'fc0a369d-615a-4119-aa75-b3d35119aa4d' );