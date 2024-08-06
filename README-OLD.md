Start the application: From the root directory, run:

docker-compose up --build -d This will build and start all the necessary containers (database, backend, and frontend). The initial setup might take 2-3 minutes.

Shut down the application: To stop all running containers, use:

docker-compose down

Restart the application: When you want to start the application again, run: docker-compose up

or, if you want to continue using the terminal: docker-compose up -d

The -d flag runs the containers in detached mode.

Development Workflow: (From root dir (where docker-compose are) After making any changes, rebuild and restart the containers using: docker-compose down (stop all) docker-compose up --build -d (start all) docker-compose build db (build only database) docker-compose build db backend (builds backend and database, because backend cannot be built without an active database) docker-compose build frontend (build only frontend)

Additional Information You can also build specific parts, lets say you are working on the Backend, okay. build the database only. (Because it needs to be running) Or lets say you are building on the frontend, okay, build both backend and database but run frontend with ng -serve. If you need help with this. Joar will help you. Bonus: A working Kubernetes setup is also available, but it complicates development and will be implemented later.

Previous Setup (For Reference) Previously, you needed to install and configure the following manually:

Docker Node Angular PostgreSQL Postman Java Springboot Maven Various VSCode extensions Old commands for reference:

cd backend mvn clean mvn install mvn compile mvn spring-boot:run

Alternatively all together mvn clean install compile spring-boot:run

cd frontend npm install ng serve -o

all the commands docker ps docker exec -it postgres /bin/bash psql -U dollarstore \l \c table \q for quit

remove volumes with: docker system prune -a --volumes

docker-compose down docker-compose build --no-cache docker-compose up

GOOGLE_CLOUD_PROJECTNAME = Removed because its my perosnal.

docker build -t gcr.io/{GOOGLE_CLOUD_PROJECTNAME}/frontend:latest ./frontend docker build -t gcr.io/{GOOGLE_CLOUD_PROJECTNAME}/backend:latest ./backend docker build -t gcr.io/{GOOGLE_CLOUD_PROJECTNAME}/db:latest ./db

docker push gcr.io/{GOOGLE_CLOUD_PROJECTNAME}/frontend:latest docker push gcr.io/{GOOGLE_CLOUD_PROJECTNAME}/backend:latest docker push gcr.io/{GOOGLE_CLOUD_PROJECTNAME}/db:latest

kubectl set image deployment/backend backend=gcr.io/{GOOGLE_CLOUD_PROJECTNAME}/backend:latest kubectl set image deployment/frontend frontend=gcr.io/{GOOGLE_CLOUD_PROJECTNAME}/frontend:latest

kubectl apply -f backend-deployment.yaml kubectl apply -f backend-service.yaml kubectl apply -f db-deployment.yaml kubectl apply -f db-service.yaml kubectl apply -f db-persistentvolumeclaim.yaml kubectl apply -f frontend-deployment.yaml kubectl apply -f frontend-service.yaml

kubectl get pods kubectl get svc kubectl get statefulsets

kubectl delete pod podnamnet kubectl logs podnamnet

Delete and redepl

kubectl delete deployment frontend kubectl delete deployment backend kubectl apply -f frontend-deployment.yaml kubectl apply -f backend-deployment.yaml kubectl apply -f db-headless-service.yaml kubectl apply -f db-statefulset.yaml

kubectl apply -f k8s/

kubectl get svc db -o yaml kubectl get endpoints db -o yaml

Access db on k8s just like we do docker kubectl exec -it postgres-0 -- bash psql -U dollarstore -d customer

Scale Down kubectl scale deployment backend --replicas=0 kubectl scale deployment frontend --replicas=0 kubectl scale statefulset postgres --replicas=0

Verify Scaling Down kubectl get pods

Scale Up kubectl apply -f k8s/ kubectl scale deployment backend --replicas=1 kubectl scale deployment frontend --replicas=1 kubectl scale statefulset postgres --replicas=1

Verify Scaling Up kubectl get pods
