#!/bin/bash
# http://blog.zacholauson.io/database-management-in-clojure

MIGRATIONS_DIR=./migrations
MIGRATION_NAME=$1
TIMESTAMP=`date +%Y%m%d%H%M%S`
UNDERSCORE="_"
UP_MIGRATION_FILE="$MIGRATIONS_DIR/$TIMESTAMP$UNDERSCORE$MIGRATION_NAME.up.sql"
DOWN_MIGRATION_FILE="$MIGRATIONS_DIR/$TIMESTAMP$UNDERSCORE$MIGRATION_NAME.down.sql"

touch $UP_MIGRATION_FILE
touch $DOWN_MIGRATION_FILE

echo "created $UP_MIGRATION_FILE"
echo "created $DOWN_MIGRATION_FILE"
