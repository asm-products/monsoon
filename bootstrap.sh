#!/bin/bash
set -e

fig run db createdb -h db -U postgres monsoon_development
fig run db psql -h db -U postgres -d monsoon_development -c "CREATE EXTENSION pgcrypto"
fig run db createdb -h db -U postgres monsoon_test
fig run db psql -h db -U postgres -d monsoon_test -c "CREATE EXTENSION pgcrypto"
fig run web lein clj-sql-up migrate
