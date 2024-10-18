# VCIT DEV PROBLEM

This project is a REST API for managing client records. It allows you to create, update, search, and delete clients,
while also validating key information like South African ID numbers and ensuring no duplicates exist. The data is stored
in-memory for simplicity.

## Features

* **Create clients:** Add new client records with fields for First Name, Last Name, Mobile Number, ID Number, and
  Physical Address.
* **Update clients:** Modify existing client records using their ID number.
* **Search clients:**  Find clients by First Name, ID Number, or Mobile Number.
* **Delete clients:** Remove client records by ID number.
* **Data validation:** Ensures valid South African ID numbers and prevents duplicate ID numbers or mobile numbers.

## Requirements

* Java 17
* Spring Boot 3.3.4 or higher
* Gradle

## Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/king-pep/vcit-dev-problem.git
   cd vcit-dev-problem
   ```

2. **Build the project:**

   ```bash
   ./gradlew build
   ```

3. **Run the application:**

   ```bash
   ./gradlew bootRun
   ```

4. **Run with the generated JAR:**

   ```bash
   java -jar build/libs/vcit-dev-problem-0.0.1-SNAPSHOT.jar
   ```

## API Endpoints

### 1. Create Client

* **URL:** `POST /api/v1/clients/create`
* **Request Body:**

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "mobileNumber": "0712345678",
  "idNumber": "9001015800083",
  "physicalAddress": "123 Elm Street"
}
```

* **Success Response:**
    * **Status:** `200 OK`
    * **Body:**

```json
{
  "resultCode": 0,
  "resultMessageCode": "api-fm-012",
  "resultMessage": "Client created successfully.",
  "friendlyCustomerMessage": "Your client has been created.",
  "payload": {
    "firstName": "John",
    "lastName": "Doe",
    "mobileNumber": "0712345678",
    "idNumber": "9001015800083",
    "physicalAddress": "123 Elm Street"
  }
}
```

### 2. Update Client

* **URL:** `PUT /api/v1/clients/update/{idNumber}`
* **Request Body:**

```json
{
  "firstName": "John",
  "lastName": "Smith",
  "mobileNumber": "0712345679",
  "idNumber": "9001015800083",
  "physicalAddress": "456 Oak Street"
}
```

* **Success Response:**
    * **Status:** `200 OK`
    * **Body:**

```json
{
  "resultCode": 0,
  "resultMessageCode": "api-fm-013",
  "resultMessage": "Client updated successfully.",
  "friendlyCustomerMessage": "Your client has been updated.",
  "payload": {
    "firstName": "John",
    "lastName": "Smith",
    "mobileNumber": "0712345679",
    "idNumber": "9001015800083",
    "physicalAddress": "456 Oak Street"
  }
}
```

### 3. Search Client

* **URL:** `GET /api/v1/clients/search`
* **Query Params:** `firstName`, `idNumber`, `phoneNumber`
* **Example:** `GET /api/v1/clients/search?idNumber=9001015800083`
* **Success Response:**
    * **Status:** `200 OK`
    * **Body:**

```json
{
  "resultCode": 0,
  "resultMessageCode": "api-fm-014",
  "resultMessage": "Client found successfully.",
  "friendlyCustomerMessage": "Client found.",
  "payload": {
    "firstName": "John",
    "lastName": "Doe",
    "mobileNumber": "0712345678",
    "idNumber": "9001015800083",
    "physicalAddress": "123 Elm Street"
  }
}
```

### 4. Delete Client

* **URL:** `DELETE /api/v1/clients/delete/{idNumber}`
* **Example:** `DELETE /v1/clients/delete/9001015800083`
* **Success Response:**
    * **Status:** `200 OK`
    * **Body:**

```json
{
  "resultCode": 0,
  "resultMessageCode": "api-fm-015",
  "resultMessage": "Client deleted successfully.",
  "friendlyCustomerMessage": "Your client has been deleted.",
  "payload": "9001015800083"
}
```

## Running Tests

```bash
./gradlew test
```

The tests cover client creation, updating, searching, deletion, and data validation.



