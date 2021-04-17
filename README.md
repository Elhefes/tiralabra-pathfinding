# tiralabra-pathfinding

## Pathfinding algorithms comparison

This repository contains a project made for a course in University of Helsinki, [Data structures and algorithms project course](https://tiralabra.github.io/2021_p4/en/). The goal is to implement a program that you can use to compare various different path finding algorithms. It is written in Java as that is the preferred language in this course.

## Documentation
* [Specifications](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/specifications.md)
* [Implementation](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/implementation.md)
* [Testing](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/testing.md)
* [Manual](https://github.com/Elhefes/tiralabra-pathfinding/blob/main/documentation/user_manual.md)

## Weekly progress reports
* [Week 4](documentation/week_report.md#week-4)
* [Week 3](documentation/week_report.md#week-3)
* [Week 2](documentation/week_report.md#week-2)
* [Week 1](documentation/week_report.md#week-1)

## Gradle
The project uses Gradle for easy running, testing and building. You should run gradle commands from the tiralabra-pathfinding/tiralabra-pathfinding folder.

### Run the program
```
./gradlew run
```

### Generate checkstyle report
```
./gradlew check
./gradlew checkstyleMain
```
The checkstyle report will be generated to *tiralabra-pathfinding/build/reports/checkstyle/main.html*

### Unit tests
You run the unit tests with command

```
./gradlew test
```
A report of the unit tests will be generated to *tiralabra-pathfinding/build/reports/tests/test/index.html*

### Jacoco test coverage
You can generate jacoco test coverage report with

```
./gradlew jacocoTestReport
```
The report will be generated to *tiralabra-pathfinding/build/reports/jacoco/test/html/index.html*

### Build project
```
./gradlew build
```
The build of the program will be created to *tiralabra-pathfinding/build/distributions*
