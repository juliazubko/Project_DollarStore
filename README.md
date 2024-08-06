# DollarStore Customer Club


Customer Club for DollarStore is a personal online receipt management SaaS solution developed by [KTH](https://www.kth.se/en) students as a practical part of the Project Course in Software Development (HI1039 VT24) at KTH, in collaboration with [DollarStore](https://www.dollarstore.se/) as stakeholders.

Team: @gerlov, @CJPob, @elianrozgar, @juliazubko 
Original repo: https://github.com/gerlov/project_two  


https://github.com/user-attachments/assets/8394da1f-cd01-41de-974b-57e52baca642



## Our Product 

DollarStore's Customer Club offers a set of functionalities for online receipt management, including account creation, receipt uploading/downloading, sorting and zoom capabilities, all through a user-friendly interface.

Future plans for the Customer Club include developing a CRM application and a parsing service to extract and save all products from receipts, providing further insights into customer purchasing behavior.


## Tech Stack 

- Orchestration and Scaling: **Docker** and **Kubernetes**
- Database: **PostgreSQL**
- Backend: **Java** / **Spring Boot**
- Frontend: **Angular**
- Security: Client-side security via **Angular Route Guards** and backend protection through **Spring Security**
- Hosting: **Google Cloud** via **Google Kubernetes Engine (GKE)**

## Structure 


![image](https://github.com/user-attachments/assets/f417d699-f71a-47d0-9bf8-4b096569f754)  


- **Controller Layer**: Manages HTTP requests, directing them to the correct services.
- **Service Layer**: Implements business logic and manages data operations.
- **Repository Layer**: Interfaces with PostgreSQL for data storage and retrieval.

## API

Our APIs support comprehensive customer and receipt management functions, including user registration, login, and receipt handling, ensuring secure and efficient data interactions.

## Hosting and Deployment

- Google Kubernetes Engine (GKE): Manages the deployment and scaling of the application's containerized architecture.
- Docker: Packages the application in lightweight containers, ensuring consistency across environments.
- Kubernetes: Automates deployment, manages container lifecycles, and supports scaling and load balancing.

## Try Out the App 

- Open your terminal or command prompt.
- Navigate to the root directory of the project.
- Run the command below to build and start all necessary containers 
(database, backend, and frontend):

`docker-compose up --build -d`

This process might take 2-3 minutes for the initial setup.

- Once the containers are up and running, open a web browser and navigate to:

`http://localhost:4200`

This will direct you to the frontend of the app.






