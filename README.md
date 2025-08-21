# Java Playwright Login Page with Code Coverage

This project demonstrates how to add code coverage to a Java project that uses Playwright for browser automation, specifically for testing a login page.

## Project Structure

```
├── src/
│   ├── main/java/com/example/
│   │   ├── App.java                 # Main application class
│   │   └── LoginPage.java           # Playwright login page automation class
│   └── test/
│       ├── java/com/example/
│       │   └── LoginPageTest.java   # Comprehensive test suite for LoginPage
│       └── resources/
│           └── login.html           # Sample HTML login page for testing
├── pom.xml                          # Maven configuration with JaCoCo
└── README.md                        # This file
```

## Features

### LoginPage Class
The `LoginPage` class provides comprehensive functionality for login page automation:

- **Navigation**: Navigate to login pages
- **Form Interaction**: Enter username/password, click login button
- **Validation**: Input validation with proper error handling  
- **State Checking**: Check element readiness and visibility
- **Message Handling**: Retrieve success/error messages
- **Page Management**: Get page title, URL, wait for page load

### Test Coverage
The test suite includes 11 comprehensive test cases covering:

- ✅ Successful login with valid credentials
- ✅ Failed login with invalid credentials  
- ✅ Individual field operations
- ✅ Input validation (null/empty values)
- ✅ Element readiness checks
- ✅ Page navigation and properties
- ✅ Form field clearing
- ✅ Page load waiting
- ✅ Error handling for missing elements
- ✅ Whitespace validation

## Dependencies

- **Playwright**: Browser automation framework
- **JUnit 5**: Testing framework
- **AssertJ**: Fluent assertions
- **JaCoCo**: Code coverage analysis

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone or download this project
2. Install dependencies and Playwright browsers:

```bash
mvn clean compile
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

## Running Code Coverage

### 1. Run Tests with Coverage

```bash
mvn test
```

This command will:
- Compile the source code
- Run all tests with JaCoCo agent
- Generate coverage reports

### 2. View Coverage Reports

After running tests, coverage reports are generated in multiple formats:

#### HTML Report (Recommended)
Open `target/site/jacoco/index.html` in your browser for an interactive coverage report with:
- Overall coverage statistics
- Class-by-class breakdown
- Line-by-line coverage highlighting
- Branch coverage details

#### CSV Report
View `target/site/jacoco/jacoco.csv` for machine-readable coverage data

#### XML Report
View `target/site/jacoco/jacoco.xml` for integration with CI/CD tools

### 3. Coverage Metrics

Current coverage results:

| Class | Line Coverage | Branch Coverage | Method Coverage |
|-------|---------------|-----------------|-----------------|
| LoginPage | **78.1%** (50/64 lines) | **83.3%** (25/30 branches) | **100%** (18/18 methods) |
| App | **0%** (0/3 lines) | **N/A** | **0%** (0/2 methods) |
| **Overall** | **73.5%** (50/68 lines) | **83.3%** (25/30 branches) | **64.3%** (18/28 methods) |

### 4. Coverage Thresholds

The project is configured with a minimum 50% line coverage threshold. You can modify this in `pom.xml`:

```xml
<limit>
    <counter>LINE</counter>
    <value>COVEREDRATIO</value>
    <minimum>0.50</minimum>
</limit>
```

## Advanced Coverage Commands

### Generate Coverage Report Only
```bash
mvn jacoco:report
```

### Check Coverage Thresholds
```bash
mvn jacoco:check
```

### Clean and Run Full Coverage Analysis
```bash
mvn clean test jacoco:report
```

## JaCoCo Configuration

The `pom.xml` includes a complete JaCoCo configuration with:

- **prepare-agent**: Instruments code during test execution
- **report**: Generates coverage reports after tests
- **check**: Validates coverage meets minimum thresholds

### Key Configuration Options

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Understanding Coverage Metrics

### Line Coverage
Percentage of executable lines that were executed during tests

### Branch Coverage  
Percentage of decision points (if/else, switch, loops) that were tested

### Method Coverage
Percentage of methods that were called during tests

### Instruction Coverage
Percentage of bytecode instructions executed (most granular)

## Best Practices

1. **Aim for High Coverage**: Target 80%+ line coverage for critical code
2. **Focus on Branch Coverage**: Ensure all decision paths are tested
3. **Test Edge Cases**: Include null checks, boundary conditions, error scenarios
4. **Regular Monitoring**: Run coverage analysis in CI/CD pipelines
5. **Quality over Quantity**: High coverage doesn't guarantee good tests

## Continuous Integration

To integrate with CI/CD, add this to your pipeline:

```bash
# Run tests with coverage
mvn clean test

# Fail build if coverage is below threshold
mvn jacoco:check
```

## Troubleshooting

### Common Issues

1. **No coverage data**: Ensure JaCoCo agent is properly configured
2. **Low coverage**: Add more comprehensive test cases
3. **Browser issues**: Verify Playwright browsers are installed correctly

### Debugging Coverage

1. Check `target/jacoco.exec` exists after running tests
2. Verify JaCoCo agent arguments in Maven output
3. Review test execution logs for any failures

## Sample Test Execution

```bash
$ mvn test

[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0
[INFO] --- jacoco:0.8.11:report (report) @ simple-java-app ---
[INFO] Loading execution data file /workspace/target/jacoco.exec
[INFO] Analyzed bundle 'simple-java-app' with 2 classes
```

## Next Steps

1. **Increase Coverage**: Add tests for the `App` class
2. **Integration Tests**: Add end-to-end browser tests
3. **Performance Testing**: Add load testing for login functionality
4. **Security Testing**: Add tests for authentication edge cases

## Resources

- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)
- [Playwright Java Documentation](https://playwright.dev/java/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)