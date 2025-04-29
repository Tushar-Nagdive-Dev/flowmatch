#!/bin/bash

echo "WARNING: This will drop and re-create your entire schema."
read -p "Are you sure? (yes/no): " confirm

if [ "$confirm" == "yes" ]; then
  ./mvnw flyway:clean flyway:migrate
else
  echo "Cancelled."
fi
