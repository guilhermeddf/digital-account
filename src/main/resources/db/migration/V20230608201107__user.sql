CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE role as ENUM ('ADMIN', 'USER');
CREATE CAST (CHARACTER VARYING as role) WITH INOUT AS IMPLICIT;

CREATE TABLE "user"(
    id UUID not null primary key default public.uuid_generate_v4(),
    username varchar(30) not null ,
    password varchar(255),
    role role not null
);

INSERT INTO "user"(id, username, password, role) values ('fc0a369d-615a-4119-aa75-b3d35119aa4c', 'guilhermeddf', '1234', 'ADMIN')