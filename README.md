## **Role-Based Access Control (RBAC) System Using Spring Boot**

This project demonstrates a Role-Based Access Control (RBAC) system implemented in Spring Boot. It includes user authentication, authorization, token-based security using JWT, Redis-backed token blacklisting, and a secure API design.

___

## **Features:**
  User Registration and Login: Secure registration and login endpoints.
  Role Management: Predefined roles (USER, ADMIN, MODERATOR) for different access levels.
  JWT Authentication: Stateless authentication using JSON Web Tokens (JWT).
  Redis Integration: Token blacklisting for logout functionality.
  Secure Endpoints: Access controlled by roles using Spring Security.
  RESTful API Design: Well-defined endpoints for managing users and resources.

  ___

## **Prerequisites**
  Java 17+
  Maven
  Spring Boot 3+
  Redis
  PostgreSQL/MySQL (or any supported database)
  Docker (Optional, for running Redis and the database)

  ___

## **Getting Started**

Clone the Repository
git clone https://github.com/sandeeperamalla/Role-Based-Access-Control-Project.git
cd Role-Based-Access-Control-Project

___

## **Configure the Application**

1) Update the application.properties file:
 # Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/Your_DB-Name
spring.datasource.username=your_User_Name
spring.datasource.password=Your_Password

___

**# Architecture**


1) Entities
   StudentDetails: Represents a student with attributes like stuNumber, fullName, fatherName, and branchName.
   StudentLoginDetails: Represents login credentials and associated role.
   Role: Enum with values USER, ADMIN, and MODERATOR.

2)  DTOs:
    LogoutRequest: Encapsulates the JWT token for logout.
    StudentLoginForm: Captures login details like userName and password.
    
3)  Security
    SecurityConfig: Configures Spring Security to secure endpoints and manage role-based access.
    JwtFilter: Validates JWT tokens for each request.
    jwtBlockListFilter: Ensures that blacklisted tokens (logged-out tokens) are not reused.

4)  Repositories
    StudentDetailsRepo: For CRUD operations on StudentDetails.
    StudentloginDetailsRepo: For managing StudentLoginDetails.

5) Services
   StudentDetailsService: Handles CRUD operations for StudentDetails.
   StudentlogindetailsService: Manages login, JWT generation, and logout operations.
6) Controller
   StudentController: REST endpoints for managing student details.
   Key Endpoints:
   POST /registerStudent - Register a new student.
   POST /loginStudent - Authenticate and receive a JWT token.
   POST /logoutStudent - Logout by blacklisting the JWT token.
   GET /getStudentById/{id} - Fetch details of a specific student.
   GET /getAllStudent - Fetch all students.
   
7) Custom Exceptions
   InvalidStudentLoginDetailsException
   StudentLoginDetailsCreationException
   JwtTokenExpired
   InvalidJwtToken

8) Redis Integration
   Used to store blacklisted JWT tokens during logout with a TTL matching the token's expiration

___

## **How To Use** 

1) Register a User:
   
   POST /registerStudent
{
  "userName": "john_doe",
  "password": "password123",
  "role": "USER" // Optional: If you don't specify a role, it will automatically assign you as a user.
}

2) Login

   POST /loginStudent
{
  "userName": "john_doe",
  "password": "password123"
}


3) Access Secure Endpoints Use the JWT token from the login response in the Authorization header:

   Authorization: Bearer <your-token>

4) Logout:

POST /logoutStudent
{
  "token": "<your-token>"
}

___

## **Role-Based API Access**

| Role       | Accessible Endpoints                     |
|------------|------------------------------------------|
| USER       | `/user/**`                               |
| MODERATOR  | `/moderator/**`, `/user/**`              |
| ADMIN      | `/admin/**`, `/moderator/**`, `/user/**` |

___

## **Tools and Libraries**

- **Spring Boot 3**: Framework for building Java-based microservices.
- **Spring Security**: For securing endpoints and implementing RBAC.
- **JWT**: Stateless authentication mechanism.
- **Redis**: Caching and token blacklisting.
- **Lombok**: Reduces boilerplate code for entities and DTOs.
- **PostgreSQL/MySQL**: Relational database for storing user and student details.

___

## **License**

This project does not have a license yet. Please check with the project owner for more details.




   





    


