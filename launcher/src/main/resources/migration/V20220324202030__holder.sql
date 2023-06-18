CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE holder(
    id UUID not null primary key default public.uuid_generate_v4(),
    cpf varchar(11) not null,
    name varchar(100) not null
);

INSERT INTO holder(id, name, cpf) VALUES ('fc0a369d-615a-4119-aa75-b3d35119aa4c', 'Holder Name', '53767283310');