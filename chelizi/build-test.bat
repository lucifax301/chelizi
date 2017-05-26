@echo off
set mydir=%~dp0
set "p=%~dp0"
for /f "delims=" %%i in ("%p:~0,-1%") do (set project=%%~ni)
cd /d %mydir%/

cmd /c mvn -X clean package -Ptest -DskipTests 

cd /d %mydir%
pause