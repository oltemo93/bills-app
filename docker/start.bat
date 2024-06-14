@ECHO OFF

echo Running Maven clean install...
call mvn clean package -f ../pom.xml && ^

echo Spin up all the services
docker compose up --build bill-app

echo Build and deployment completed successfully!
pause