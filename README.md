<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <h1>Cosmetic Management</h1>
    <h2>Description</h2>
    <p>
        Cosmetic Management is a web application built using Spring Boot, Bootstrap, Thymeleaf, JPA, and MySQL. It serves as an online platform for a cosmetics shop, allowing users to browse, search, and purchase cosmetic products.
    </p>    
    <h2>Features</h2>
    <ul>
         <li>User authentication and authorization with Spring Security</li>
        <li>Login with google or facebook</li>
        <li>Forget password (send an email link to reset)</li>
        <li>Product browsing and searching (pagination, filtering and sorting)</li>
        <li>Shopping cart functionality (for both non-logged in and logged in users)</li>
        <li>Checkout and payment processing (online payment with VNPay)</li>
        <li>User profile management</li>
        <li>Admin panel for managing categories, products, users</li>
    </ul>
    <h2>Installation</h2>
    <h3>Prerequisites</h3>
    <ul>
        <li><strong>JDK 11</strong> or higher</li>
        <li><strong>Maven</strong></li>
        <li><strong>MySQL</strong></li>
    </ul>    
    <h3>Steps</h3>
    <ol>
        <li><strong>Clone the repository</strong>:
            <pre><code>git clone https://github.com/PhuongTay1109/spring-cosmetic-shop</code></pre>
        </li>
        <li><strong>Navigate to the project directory</strong>:
            <pre><code>cd spring-cosmetic-shop</code></pre>
        </li>
        <li><strong>Configure the database</strong>:
            <p>Create a database in MySQL. Update the <code>application.properties</code> file in the <code>src/main/resources</code> directory with your MySQL database configuration:</p>
            <pre><code>spring.datasource.url=jdbc:mysql://localhost:3306/cosmetic
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true</code></pre>
        </li>
        <li><strong>Build the project</strong>:
            <pre><code>mvn clean install</code></pre>
        </li>
        <li><strong>Run the application</strong>:
            <pre><code>mvn spring-boot:run</code></pre>
        </li>
    </ol>
    <h2>Usage</h2>
    <ol>
        <li>Open your web browser and navigate to <code>http://localhost:8080</code>.</li>
        <li>Register a new account or log in with existing credentials.</li>
        <li>Browse the product catalog and add items to your shopping cart.</li>
        <li>Proceed to checkout to place an order.</li>
        <li>Manage your profile from the user profile section.</li>
        <li>Use the admin panel to manage categories, products, and users (accessible for admin users only).</li>
    </ol>
    <h2>Project Structure</h2>
    <ul>
        <li><code>src/main/java</code>: Contains the Java source code for the project.</li>
        <li><code>src/main/resources</code>: Contains the configuration files and static resources.</li>
        <li><code>src/main/webapp</code>: Contains the Thymeleaf templates for the web pages.</li>
        <li><code>pom.xml</code>: Maven configuration file.</li>
    </ul>
</body>
</html>
