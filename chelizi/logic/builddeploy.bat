@echo off
set mydir=%~dp0
set "p=%~dp0"
for /f "delims=" %%i in ("%p:~0,-1%") do (set project=%%~ni)
cd /d %mydir%/../

cmd /c mvn -X package -DskipTests

if "%CATALINA_HOME%" == "" (
	echo 请设置tomcat的根目录到环境变量CATALINA_HOME,然后重试
        pause
	exit;
)
set tomcat=%CATALINA_HOME%/webapps
if not exist  %tomcat%  (
	echo tomcat路径%tomcat%，不存在，请检查后重试！
	pause
)
if not exist "%tomcat%\%project%" (
 mkdir "%tomcat%\%project%"
)

xcopy /f /Y /e /s  "%mydir%\target\%project%" "%tomcat%\%project%"

echo %mydir% has deploy to %tomcat% 
cd /d %mydir%
pause