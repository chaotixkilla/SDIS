@echo off
 
SET /p servers=server count:
SET /a max=servers 

cd ..

cd bin

FOR /L %%a IN (1,1,%max%) DO ( start java interfaces.Server 1.0 %%a %%a 239.255.255.251 2000 239.255.255.252 3000 239.255.255.253 4000)