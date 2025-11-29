#!/bin/bash

# Compile the Java application with MySQL JDBC driver in classpath
echo "Compiling Java application..."

javac -cp ".:lib/mysql-connector-j-9.1.0.jar" src/Main.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
else
    echo "✗ Compilation failed!"
    exit 1
fi
