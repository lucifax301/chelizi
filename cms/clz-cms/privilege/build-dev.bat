cd ../intapi
call build-dev.bat
cd ../privilege
call mvn  dependency:copy-dependencies -DoutputDirectory=deploy/localdev/lib --settings d:\settings.xml
call mvn  package -Plocaldev -DskipTests --settings d:\settings.xml
xcopy /Y target\privilege.jar deploy\localdev\lib
xcopy /Y deploy\*.sh deploy\localdev\