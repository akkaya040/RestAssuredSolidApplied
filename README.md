# PetStore API Test Automation Framework

Production-ready REST API test automation framework built with Java, JUnit 5, and RestAssured.

## 🏗️ Architecture

This framework follows industry best practices and SOLID principles:

```
restPetStoreBest/
├── src/main/java/com/petstore/framework/
│   ├── config/         # Configuration management (Singleton pattern)
│   ├── models/         # POJOs with Lombok (Builder pattern)
│   ├── services/       # API service layer (Inheritance)
│   └── utils/          # Logging filters
├── src/test/java/com/petstore/tests/
│   ├── base/           # BaseTest & TestDataFactory
│   ├── pet/            # Pet API tests
│   ├── store/          # Store API tests
│   └── user/           # User API tests
└── src/test/resources/
    ├── config/         # Environment configs (dev/stage/prod)
    ├── allure.properties
    ├── junit-platform.properties
    └── logback-test.xml
```

## 🎯 Features

- **SOLID Principles**: Clean architecture with separation of concerns
- **Design Patterns**: Singleton, Builder, Factory, Inheritance
- **Professional Logging**: SLF4J + Logback with file and console output
- **Allure Reporting**: Comprehensive test reports with request/response details
- **Parallel Execution**: JUnit 5 parallel test execution
- **Environment Management**: Easy switching between dev/stage/prod
- **Test Data Factory**: JavaFaker for realistic test data
- **Auto Cleanup**: Automatic resource cleanup after tests
- **Fluent Assertions**: AssertJ for readable assertions

## 🛠️ Tech Stack

- **Java**: 17
- **Build Tool**: Maven
- **Test Framework**: JUnit 5
- **API Client**: RestAssured 5.x
- **Logging**: SLF4J + Logback
- **Reporting**: Allure
- **Assertions**: AssertJ
- **Test Data**: JavaFaker
- **Code Generation**: Lombok

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Allure CLI (optional, for local report viewing)

## 🚀 Getting Started

### 1. Clone the repository
```bash
git clone https://github.com/akkaya040/RestAssuredSolidApplied
cd restPetStoreBest
```

### 2. Install dependencies
```bash
mvn clean install
```

### 3. Run tests
```bash
# Run all tests (default: dev environment)
mvn test

# Run with specific environment
mvn test -Denv=stage

# Run specific test class
mvn test -Dtest=PetCrudTest

# Run tests sequentially (disable parallel execution)
mvn test -Djunit.jupiter.execution.parallel.enabled=false
```

### 4. Generate Allure report
```bash

# Generate and open report in browser
mvn allure:serve

# Or generate report to target/site/allure-maven-plugin
mvn allure:report

# mvn allure plugin have to be installed. Otherwise It can be handled by allure.
# Only deploy a report in temp 
allure serve target/allure-report

# Generate report to the directory
allure generate target/allure-results -o  target/allure-report
allure open target/allure-report 

```
<img width="943" height="619" alt="allure-comment" src="https://github.com/user-attachments/assets/b76758c4-4176-4d4d-b268-d5f35a1b3c62" />


## 📊 Reports

### Allure Report
After running tests, generate the Allure report:
```bash
mvn allure:serve
```
<img width="1526" height="862" alt="allure-result" src="https://github.com/user-attachments/assets/01ba9528-54d9-4f92-934c-f97195dd9ad3" />


The report includes:
- Test execution summary
- Request/Response details
- Logs and attachments
- Test history and trends

### Logs
Logs are written to:
- **Console**: Real-time test execution logs
- **File**: `target/logs/test-execution.log`

## 🔧 Configuration

### Environment Configuration
Edit files in `src/test/resources/config/`:
- `dev.properties` - Development environment
- `stage.properties` - Staging environment
- `prod.properties` - Production environment

### Parallel Execution
Configure in `src/test/resources/junit-platform.properties`:
```properties
junit.jupiter.execution.parallel.config.fixed.parallelism = 3
```

### Logging
Configure in `src/test/resources/logback-test.xml`:
- Log levels
- Output format
- File appenders

## 🏛️ Design Principles Applied

### SOLID Principles
1. **Single Responsibility**: Each class has one reason to change
   - `PetService` only handles Pet API operations
   - `ConfigManager` only handles configuration

2. **Open/Closed**: Open for extension, closed for modification
   - `BaseService` can be extended by new service classes
   - No need to modify existing code to add new APIs

3. **Liskov Substitution**: Subtypes can replace base types
   - All services extend `BaseService` and can be used interchangeably

4. **Interface Segregation**: Clients don't depend on unused methods
   - Each service exposes only relevant methods

5. **Dependency Inversion**: Depend on abstractions
   - Tests depend on `BaseTest`, not concrete implementations

### Design Patterns
- **Singleton**: `ConfigManager` ensures single instance
- **Builder**: All POJOs use Lombok `@Builder`
- **Factory**: `TestDataFactory` creates test data
- **Template Method**: `BaseTest` defines test lifecycle
- **Filter**: `RequestResponseLoggingFilter` for cross-cutting concerns

## 📝 Writing Tests

### Example Test
```java
@Feature("Pet API")
@DisplayName("Pet CRUD Operations")
public class PetCrudTest extends BaseTest {
    
    @Test
    @DisplayName("Should create a new pet successfully")
    @Description("Verify that a new pet can be created")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldCreatePetSuccessfully() {
        // Arrange
        Pet newPet = TestDataFactory.createRandomPet();
        
        // Act
        Response response = petService.createPet(newPet);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(200);
        trackPetForCleanup(response.as(Pet.class).getId());
    }
}
```

## 🤝 Contributing

1. Follow existing code structure
2. Use meaningful test names with `@DisplayName`
3. Add `@Description` for Allure reports
4. Track created resources for cleanup
5. Use `TestDataFactory` for test data
6. Use AssertJ for assertions

## 📄 License

This project is licensed under the MIT License.


## 🙏 Acknowledgments

- Swagger PetStore API for testing
- RestAssured community
- Allure Framework team
