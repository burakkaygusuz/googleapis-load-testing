# Google APIs Load Testing

The sample load test application for API of Google Blogger v3.

## Requirements

 - This project requires [Java 8 JDK](https://adoptopenjdk.net/).
 - Install and configure [Gradle](https://gradle.org/install/) build tool.
 - Install [Scala](https://www.scala-lang.org/download/) library.

**Note:** Create **config.properties** file into **resources** directory and type ***"API_KEY '"*** and its value.

### Steps to clone execute the tests

 ```sh
 $ git clone https://github.com/burakkaygusuz/googleapis-load-testing.git
 $ cd googleapis-load-testing
 $ gradle gatlingRun
 ```



