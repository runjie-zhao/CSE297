#!/bin/bash
rm -rf build
mkdir build
mkdir build/code
cp *.java build/code
cat *.java >> build/allcode.java
cp run build
cp recompile build
cd build
./recompile
zip -r ../doge.zip .
cd ..
rm -rf build