# Indeed Scraper - Java and Selenium
This is a Java-based web scraping project that extracts job listing information from Indeed.ca using the Selenium framework. It allows you to scrape job listings from Indeed and gather data such as job titles, company names, locations, key points and job links. The data is stored in the form of .xlsx file. The number of pages the project will scrape depends on number of available pages. You can dig into the code and make required changes if necessary.

## Prerequisites
To use this framework, ensure you have the following installed:
- Java Development Kit (JDK) version 8 or higher
- Maven (for managing dependencies and building the project)
- Chrome, Firefox, or edge browser (depending on your test environment)
- Excel(to view the scrapped data)

## Getting Started

To get started, follow these steps:

1. Clone this repository to your local machine:

```bash
https://github.com/anoopezhap/Indeed-WebScrapping.git
```
2. Import the project into your preferred Java IDE (e.g., IntelliJ, Eclipse). You can either run it here or though terminal

3. Install the required dependencies by running the following command in the project root directory:

```bash
mvn clean install
```

4. Update the configuration file (`config.properties`) with your desired settings, such as browser type (chrome or firefox or edge), jobTitle and jobLocation.

5. After successfully installing the project, you can run all the tests using the following command:
```bash
mvn test
```
This will execute the project using data from config.properties file.

5. To pass on parameter from terminal use this command
```bash
mvn test -Dbrowser=chrome -DjobTitle=QA Engineer -DjobLocation=Oakville
```

## Result
After running the project you can find file named data.xlsx with the scrapped data.

## Contributing
Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.




