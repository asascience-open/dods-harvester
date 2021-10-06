#!/bin/bash
rm -rf $(ls | grep -vE '(\.sh|tar\.gz)')
tar -xf ./*.tar.gz
rm -f ./*.tar.gz
unzip dods-harvester*.jar
mkdir lib/
mv *.jar lib/

