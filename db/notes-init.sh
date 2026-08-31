#!/bin/bash
mongoimport --db "$MONGO_INITDB_DATABASE" --collection notes \
  --authenticationDatabase admin \
  --username "$MONGO_INITDB_ROOT_USERNAME" --password "$MONGO_INITDB_ROOT_PASSWORD" \
  --jsonArray --file /docker-entrypoint-initdb.d/notes-data.json
