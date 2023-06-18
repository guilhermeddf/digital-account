CREATE TYPE status as ENUM ('ACTIVATED', 'BLOCKED', 'DISABLED');

CREATE CAST (CHARACTER VARYING as status) WITH INOUT AS IMPLICIT;

CREATE TABLE account(
    id UUID not null primary key default public.uuid_generate_v4(),
    balance numeric(12,2) not null,
    number varchar(6) not null,
    branch varchar(4) not null,
    holder_id UUID not null references holder on delete cascade,
    status status not null,
    withdrawal_limit numeric(12,2) not null
);

INSERT INTO account (id, balance, number, branch, holder_id, status, withdrawal_limit)
    VALUES ('fc0a369d-615a-4119-aa75-b3d35119aa4d', 100, '45215', '1452', 'fc0a369d-615a-4119-aa75-b3d35119aa4c', 'ACTIVATED', 200000);