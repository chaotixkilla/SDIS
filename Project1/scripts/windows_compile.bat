@echo off

cd ..

mkdir bin

javac -d bin -sourcepath src src/interfaces/Server.java src/interfaces/TestApp.java