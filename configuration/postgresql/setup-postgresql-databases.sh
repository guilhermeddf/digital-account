#!/bin/bash
set -e
set -u

function create_user_and_database() {
    local dbname=$1;
    local username=$dbname;
    local applicationUser="$username-app";
    local migrationUser="$username-migration";
    local schema=$2;
    echo "  Creating database '$dbname' with schema '$schema' and user ['$applicationUser', '$migrationUser']"

    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        CREATE ROLE "$username";
        CREATE DATABASE "$dbname" WITH OWNER '$username';
        GRANT ALL PRIVILEGES ON DATABASE "$dbname" TO "$username";

        CREATE USER "$applicationUser" WITH PASSWORD '$applicationUser';
        GRANT CONNECT ON DATABASE "$dbname" TO "$applicationUser";
        GRANT "$username" TO "$applicationUser";

        CREATE USER "$migrationUser" WITH PASSWORD '$migrationUser';
        GRANT CONNECT ON DATABASE "$dbname" TO "$migrationUser";
        GRANT "$username" TO "$migrationUser";
EOSQL

    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -d "$dbname"<<-EOSQL
        CREATE SCHEMA "$schema" AUTHORIZATION "$username";
        CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;
        ALTER ROLE "$username" SET search_path TO "$schema", public;
        ALTER ROLE "$applicationUser" SET search_path TO "$schema", public;
        ALTER ROLE "$migrationUser" SET search_path TO "$schema", public;
EOSQL
}

if [ -n "$POSTGRES_DB" ]; then
		create_user_and_database "digital-account-service" "digital_account_service"
fi