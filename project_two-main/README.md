Start the application:
From the root directory, run:


docker-compose up --build -d
This will build and start all the necessary containers (database, backend, and frontend). The initial setup might take 2-3 minutes.

Shut down the application:
To stop all running containers, use:

docker-compose down

Restart the application:
When you want to start the application again, run:
docker-compose up

or, if you want to continue using the terminal:
docker-compose up -d

The -d flag runs the containers in detached mode.

Development Workflow: (From root dir (where docker-compose are)
  After making any changes, rebuild and restart the containers using:
  docker-compose down (stop all) 
  docker-compose up --build -d (start all)
  docker-compose build db (build only database)
  docker-compose build db backend (builds backend and database, because backend cannot be built without an active database)
  docker-compose build frontend (build only frontend)  

Additional Information
  You can also build specific parts, lets say you are working on the Backend, okay. build the database only. (Because it needs to be running)
  Or lets say you are building on the frontend, okay, build both backend and database but run frontend with ng -serve. 
  If you need help with this. Joar will help you.
  Bonus: A working Kubernetes setup is also available, but it complicates development and will be implemented later.
  



Previous Setup (For Reference)
Previously, you needed to install and configure the following manually:

Docker
Node
Angular
PostgreSQL
Postman
Java Springboot
Maven
Various VSCode extensions
Old commands for reference:

cd backend
mvn clean
mvn install
mvn compile
mvn spring-boot:run

Alternatively all together
mvn clean install compile spring-boot:run

cd frontend
npm install
ng serve -o
