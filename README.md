# 🛒 Spring Boot E-Commerce Application

A comprehensive e-commerce REST API built with Spring Boot 3.5.3, featuring JWT authentication, role-based access control, and complete shopping cart functionality.

## 📋 Table of Contents

- [Features](#-features)
- [Technologies](#-technologies)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Configuration](#️-configuration)
- [API Documentation](#-api-documentation)
- [Security](#-security)
- [Database Schema](#-database-schema)
- [Running the Application](#-running-the-application)
- [Docker Support](#-docker-support)

## ✨ Features

### User Management
- User registration and authentication
- JWT-based authentication
- Role-based access control (USER, ADMIN)
- User profile management
- Account activation/deactivation

### Product Management
- Product CRUD operations
- Product categorization
- Product search and filtering
- Inventory management

### Shopping Cart
- Add/remove items from cart
- Update item quantities
- View cart contents
- Cart persistence for logged-in users

### Order Management
- Place orders from cart
- Order history tracking
- Order status management
- Order details retrieval

### Categories
- Category CRUD operations
- Product categorization
- Category-based product browsing

### Performance & Security
- Rate limiting with Bucket4j
- Caching with Caffeine
- Input validation
- CORS configuration
- SQL injection protection

## 🛠 Technologies

### Core Framework
- **Spring Boot** 3.5.3
- **Java** 17
- **Maven** - Dependency management

### Spring Modules
- **Spring Data JPA** - Database persistence
- **Spring Security** - Authentication & authorization
- **Spring Web** - REST API development
- **Spring Validation** - Input validation

### Database
- **PostgreSQL** - Primary database
- **Hibernate** - ORM framework

### Security
- **JWT (jjwt)** 0.11.5 - Token-based authentication
- **Spring Security** - Security framework

### Documentation
- **SpringDoc OpenAPI** 2.8.9 - API documentation (Swagger UI)

### Performance
- **Caffeine** 3.1.8 - In-memory caching
- **Bucket4j** 7.6.0 - Rate limiting

### Development Tools
- **Lombok** - Boilerplate code reduction
- **Spring DevTools** - Hot reload

## 📁 Project Structure

```
spring-boot-ecommerce/
├── src/
│   ├── main/
│   │   ├── java/com/project/hammer/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── constants/       # Application constants
│   │   │   ├── controller/      # REST controllers
│   │   │   │   ├── CartController.java
│   │   │   │   ├── CategoryController.java
│   │   │   │   ├── OrdersController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── SecurityController.java
│   │   │   ├── entity/          # JPA entities
│   │   │   │   ├── Cart.java
│   │   │   │   ├── CartItems.java
│   │   │   │   ├── Category.java
│   │   │   │   ├── Orders.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Role.java
│   │   │   │   └── Users.java
│   │   │   ├── exceptions/      # Custom exceptions
│   │   │   ├── model/           # DTOs and request/response models
│   │   │   ├── repository/      # JPA repositories
│   │   │   ├── service/         # Service interfaces
│   │   │   ├── serviceimpl/     # Service implementations
│   │   │   └── HammerApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application-dev.yml
│   └── test/                    # Unit and integration tests
├── .env                         # Environment variables
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- **JDK 17** or higher
- **Maven 3.6+**
- **PostgreSQL 12+**
- **Git**

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd spring-boot-ecommerce
   ```

2. **Set up PostgreSQL database**
   ```sql
   CREATE DATABASE ecommerce;
   CREATE USER your_username WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE ecommerce TO your_username;
   ```

3. **Configure environment variables**
   
   Create or update the `.env` file in the project root:
   ```properties
   DATASOURCE_URL=jdbc:postgresql://localhost:5432/ecommerce
   DATASOURCE_USERNAME=your_username
   DATASOURCE_PASSWORD=your_password
   ```

4. **Install dependencies**
   ```bash
   mvn clean install
   ```

## ⚙️ Configuration

### Application Configuration

The application uses profile-based configuration:

- **application.yml** - Base configuration (activates `dev` profile)
- **application-dev.yml** - Development environment settings

### Key Configuration Properties

```yaml
server:
  port: 8080

spring:
  datasource:
    url: ${DATASOURCE_URL}
    username: ${DATASOURCE_USERNAME}
    password: ${DATASOURCE_PASSWORD}
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
```

### Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `DATASOURCE_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/ecommerce` |
| `DATASOURCE_USERNAME` | Database username | `postgres` |
| `DATASOURCE_PASSWORD` | Database password | `password` |

## 📚 API Documentation

### Swagger UI

Once the application is running, access the interactive API documentation at:

```
http://localhost:8080/swagger-ui.html
```

### API Endpoints Overview

#### Authentication (`/api/auth`)
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh JWT token

#### Products (`/api/products`)
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create product (ADMIN)
- `PUT /api/products/{id}` - Update product (ADMIN)
- `DELETE /api/products/{id}` - Delete product (ADMIN)

#### Categories (`/api/categories`)
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create category (ADMIN)
- `PUT /api/categories/{id}` - Update category (ADMIN)
- `DELETE /api/categories/{id}` - Delete category (ADMIN)

#### Cart (`/api/cart`)
- `GET /api/cart` - Get user's cart
- `POST /api/cart/items` - Add item to cart
- `PUT /api/cart/items/{id}` - Update cart item quantity
- `DELETE /api/cart/items/{id}` - Remove item from cart

#### Orders (`/api/orders`)
- `GET /api/orders` - Get user's order history
- `GET /api/orders/{id}` - Get order details
- `POST /api/orders` - Place new order
- `PUT /api/orders/{id}/status` - Update order status (ADMIN)

## 🔒 Security

### JWT Authentication

The application uses JWT (JSON Web Tokens) for stateless authentication:

1. User logs in with credentials
2. Server validates and returns JWT token
3. Client includes token in `Authorization` header for subsequent requests
4. Format: `Authorization: Bearer <token>`

### Role-Based Access Control

Two primary roles:
- **USER** - Standard customer access
- **ADMIN** - Administrative privileges

### Security Features

- Password encryption (BCrypt)
- CSRF protection
- CORS configuration
- Rate limiting
- Input validation
- SQL injection prevention

## 🗄 Database Schema

### Core Entities

- **Users** - User accounts with authentication details
- **Role** - User roles for authorization
- **Product** - Product catalog
- **Category** - Product categories
- **Cart** - Shopping carts for users
- **CartItems** - Individual items in cart
- **Orders** - Order information and history

### Relationships

- User ↔ Role (Many-to-Many)
- User ↔ Cart (One-to-One)
- Cart ↔ CartItems (One-to-Many)
- Product ↔ Category (Many-to-One)
- User ↔ Orders (One-to-Many)
- Orders ↔ Product (Many-to-Many through OrderItems)

## 🏃 Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using Java

```bash
mvn clean package
java -jar target/hammer-0.0.1-SNAPSHOT.jar
```

### Accessing the Application

- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console** (if configured): http://localhost:8080/h2-console

## 🐳 Docker Support

### Build Docker Image

```bash
docker build -t spring-boot-ecommerce .
```

### Run with Docker Compose

```bash
docker-compose up -d
```

The `docker-compose.yml` file includes the application and PostgreSQL database configuration.

## 🧪 Testing

### Run All Tests

```bash
mvn test
```

### Run Specific Test

```bash
mvn test -Dtest=TestClassName
```

## 📝 Development Guidelines

### Code Style
- Follow Java naming conventions
- Use Lombok annotations to reduce boilerplate
- Document public APIs with JavaDoc
- Write meaningful commit messages

### REST API Best Practices
- Use proper HTTP methods (GET, POST, PUT, DELETE)
- Return appropriate HTTP status codes
- Use DTOs for request/response bodies
- Implement pagination for list endpoints
- Version your APIs

### Security Best Practices
- Never commit `.env` file
- Use environment variables for sensitive data
- Validate all user inputs
- Implement rate limiting for public endpoints
- Keep dependencies updated

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Manikandan**

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- PostgreSQL community
- All contributors and supporters

---

**Note**: This is a learning/portfolio project demonstrating Spring Boot best practices and modern Java development techniques.
