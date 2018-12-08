# tConfig
Small, simple library to convert configuration files to and from Map objects

## Features
 - Convert config files to and from HashMap objects
 - Edit, add, and delete config files' values and fields directly

## Code Snippets
To parse a config file into a HashMap:
```java
// HashMap to put the config file's contents into
HashMap<String, String> configContent = null;

// Parse the config
// Arg 1 - the file to parse
// Arg 2 - the separating String between fields and values, e.g. ":" for "field: value"
// Arg 3 - the comment String to ignore, e.g. "#" for "# This is a comment!"
configContent = tConfig.parseConfig(new File("myconfig.ini"), ":", "#")
```

To convert a HashMap to a config file:
```java
// HashMap to convert to a config file
HashMap<String, String> myMap = new HashMap<String, String>();
myMap.put("field", "value");

// Convert the HashMap
tConfig.createConfig(new File("myconfig.ini"), myMap, ":");
```