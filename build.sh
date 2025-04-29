#!/bin/bash

# ┌─────────────────────────────────────────────┐
# │ FlowMatch Frontend Build & Deploy Script    │
# └─────────────────────────────────────────────┘

APP_NAME="flowmatch-ui"
ANGULAR_PROJECT_NAME="smart-reconciliation-ui"
SPRING_BOOT_JAR="target/smart-reconciliation-api-0.0.1.jar"
ANGULAR_DIST_PATH="dist/browser"
STATIC_RES_PATH="src/main/resources/static"

echo "╭──────────────────────────────────────────────╮"
echo "│ FlowMatch Master Build & Run Script          │"
echo "╰──────────────────────────────────────────────╯"

select mode in "Run Dev (Angular)" "Run SSR" "Build Spring Boot with Angular" "Exit"; do
  case $mode in

    "Run Dev (Angular)")
      echo "👉 Starting Angular Dev Server..."
      cd smart-reconciliation-ui || exit
      npm start
      break
      ;;

    "Run SSR")
      echo "👉 Building & Running Angular SSR..."
      cd smart-reconciliation-ui || exit
      rm -rf dist/
      npx ng cache clean
      npm run build:ssr
      npm run serve:ssr
      break
      ;;

    "Build Spring Boot with Angular")
      echo "👉 Building Angular for Production..."
      cd smart-reconciliation-ui || exit
      rm -rf dist/
      ng build --configuration production

      if [ ! -d "$ANGULAR_DIST_PATH" ]; then
        echo "❌ Angular build failed. No dist/browser directory found."
        exit 1
      fi

      echo "👉 Copying Angular dist to Spring Boot static folder..."
      rm -rf ../smart-reconciliation-api/${STATIC_RES_PATH}/*
      cp -r dist/browser/* ../smart-reconciliation-api/${STATIC_RES_PATH}/

      echo "👉 Packaging Spring Boot JAR..."
      cd ../smart-reconciliation-api || exit
      ./mvnw clean package

      if [ ! -f "$SPRING_BOOT_JAR" ]; then
        echo "❌ Spring Boot JAR not found. Build may have failed."
        exit 1
      fi

      echo "✅ Spring Boot JAR built: ${SPRING_BOOT_JAR}"
      echo "👉 Running Spring Boot App..."
      java -jar ${SPRING_BOOT_JAR}
      break
      ;;

    "Exit")
      echo "👋 Exiting script..."
      break
      ;;

    *)
      echo "❗ Invalid option"
      ;;
  esac
done
