# Global City Weather Stats

## Overview

The **Global City Weather Stats** is a Java application developed as part of the CSC 143 (Programming 2) course. It is designed to manage and analyze weather data across multiple cities globally, showcasing the use of Java for handling complex data structures, file I/O operations, and statistical computations such as linear regression analysis.

## Reminder: 
The city Temerpatures file must be unzipped for this code to work. Since the file has 3M lines of data, it's too big to upload it on its own

## Features

- **City List Statistics:** Manages statistical data for city lists, helping in quick data retrieval and analysis.
- **Weather Data Management:** Reads, processes, and manages detailed weather data from structured CSV files.
- **Data Analysis Tools:** Implements linear regression to analyze long-term temperature trends across different geographical locations.
- **Interface-Driven Design:** Employs interfaces to ensure robust and flexible code structure, facilitating easy maintenance and scalability.
- **Comprehensive Testing:** Includes detailed unit tests to verify the system's accuracy and reliability.
  


## Installation and Setup

### Prerequisites

- Java JDK 11 or later.
- JUnit 5 for executing unit tests.
### Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/GlobalCityWeatherStats.git
   cd GlobalCityWeatherStats
### Compile the Java Classes:
javac src/*.java

### Run the Application:
java src/Main
### Execute Unit Tests:
java -cp .:lib/junit-platform-console-standalone.jar org.junit.platform.console.ConsoleLauncher --scan-class-path

### Usage
Initialize the Weather Manager: Start by creating an instance of GlobalWeatherManager using a path to your CSV data file.
Query Weather Data: Use the manager to extract and analyze data, applying filters such as date and location.
Statistical Analysis: Perform linear regression on temperature data to predict future trends and display these analytics.
### Contributing
Contributors are welcome to further enhance the functionalities of the Global City Weather Stats project. To contribute:
  Fork the repository.
  Create a feature branch (git checkout -b feature/AmazingFeature).
  Commit your changes (git commit -m 'Add some AmazingFeature').
  Push to the branch (git push origin feature/AmazingFeature).
  Open a pull request.

