#!/bin/bash

mkdir Build
javac -d ./build *.java
cd build
jar cvf FishBot.jar *
