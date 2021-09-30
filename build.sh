#!/bin/bash
rm -rf build
mkdir build
mkdir build/code
cp *.java build/code
cp Manifest.txt build/code
cat *.java >> build/allcode.java
cp scripts/run build
cp scripts/recompile build
cd build
bash recompile
zip -r ../doge.zip .
cd ..
rm -rf build