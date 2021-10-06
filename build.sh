#!/bin/bash
mvn clean
mvn install dependency:copy-dependencies
mvn package
