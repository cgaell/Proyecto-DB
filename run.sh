#!/bin/bash

# Run the Java application with MySQL JDBC driver in classpath
echo "Starting Sistema de Gestion Medica..."

java -cp ".:lib/mysql-connector-j-9.1.0.jar" src.Main

if [ $? -ne 0 ]; then
    echo "✗ Application failed to run!"
    exit 1
fi
