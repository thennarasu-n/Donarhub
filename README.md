# DonateHub

DonateHub tracks donation drives, donors, books and clothing, recipients, and distributions. Its Spring Boot API and static web interface use the same MySQL database.

## Requirements

- Java 17 or newer
- Maven
- MySQL Server

## Run locally

1. Start MySQL Server. The application connects to `jdbc:mysql://localhost:3306/donatehub` and creates the database and tables automatically.
2. The default local credentials are `root` / `root`. Set `DB_USERNAME` and `DB_PASSWORD` environment variables if your MySQL account differs.
3. From this project folder, run:

```powershell
mvn spring-boot:run
```

Open `http://localhost:8080`. The API documentation is at `http://localhost:8080/swagger-ui/index.html`.

In MySQL Workbench, refresh **Schemas**, select `donatehub`, and inspect `drives`, `donors`, `donated_items`, `recipients`, and `distributions`. To verify saved records, run:

```sql
USE donatehub;
SELECT * FROM drives;
SELECT * FROM donors;
SELECT * FROM donated_items;
SELECT * FROM recipients;
SELECT * FROM distributions;
```

Use the web interface to save each record. Donations reference an existing donor and drive; distributions reference an existing collected item and recipient.

## Verify

Run the automated tests with:

```powershell
mvn test
```
