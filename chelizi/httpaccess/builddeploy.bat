@echo off
set mydir=%~dp0
set "p=%~dp0"
for /f "delims=" %%i in ("%p:~0,-1%") do (set project=%%~ni)
cd /d %mydir%/../

cmd /c mvn -X package -DskipTests

if "%CATALINA_HOME%" == "" (
	echo ������tomcat�ĸ�Ŀ¼����������CATALINA_HOME,Ȼ������
        pause
	exit;
)
set tomcat=%CATALINA_HOME%/webapps
if not exist  %tomcat%  (
	echo tomcat·��%tomcat%�������ڣ���������ԣ�
	pause
)
if not exist "%tomcat%\%project%" (
 mkdir "%tomcat%\%project%"
)

xcopy /f /Y /e /s  "%mydir%\target\%project%" "%tomcat%\%project%"

echo %mydir% has deploy to %tomcat% 
cd /d %mydir%
pause