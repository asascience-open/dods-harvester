#!/bin/bash
mv target/dods-harvester*.jar target/dependency
cd target/dependency
tar -czf dods-harvester.tar.gz ./*
scp dods-harvester.tar.gz maracoos-tds-harvest:/opt/dods-harvester/
rm -f dods-harvester.tar.gz
