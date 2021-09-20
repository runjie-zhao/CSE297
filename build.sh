#!/bin/bash
rm -rf build
mkdir build
mkdir build/code
cp *.java build/code
cat *.java >> build/allcode.java
cp run build
cp recompile build
cd build
zip -r ../anno.zip .
cd ..
rm -rf build