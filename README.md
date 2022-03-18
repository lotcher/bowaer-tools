# bowaer-tools

A collection of common Scala tools, refer to unit test for usage. 
[Scala Docs](https://lotcher.github.io/bowaer-tools/)

## Installation

### Maven

```shell
<dependency>
  <groupId>wiki.lbj</groupId>
  <artifactId>bowaer-tools</artifactId>
  <version>1.2.1</version>
</dependency>
```

### SBT

```shell
libraryDependencies += "wiki.lbj" % "bowaer-tools" % "1.2.1"
```



## Intro

### Common

Contains common tool functions, such as exception handling(triable), block execution time(timeIt), etc

### JsonParser

Provide more convenient operation for the JsonNode returned by Jackson

### Implicits

Add common properties and methods to standard library objects, such as Map, Double, etc

### Logger

Output the log directly on any object, which is convenient to print intermediate variables in chain call. You can control the printing behavior by customizing the Marker