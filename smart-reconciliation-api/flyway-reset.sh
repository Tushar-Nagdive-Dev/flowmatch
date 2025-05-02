#!/bin/bash

echo "WARNING: This will DROP ALL TABLES and RE-CREATE schema using Flyway."
read -p "Are you sure you want to continue? (yes/no): " confirm

if [ "$confirm" == "yes" ]; then
  echo "Cleaning database (flyway clean)..."
  ./mvnw flyway:clean

  if [ $? -eq 0 ]; then
    echo "Database cleaned successfully."
    echo "Migrating database (flyway migrate)..."
    ./mvnw flyway:migrate

    if [ $? -eq 0 ]; then
      echo "Database migrated successfully. DONE."
    else
      echo "ERROR: Migration failed."
    fi
  else
    echo "ERROR: Clean failed."
  fi
else
  echo "Cancelled by user."
fi
