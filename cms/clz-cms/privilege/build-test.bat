cd ../intapi
call build-test.bat
cd ../privilege
call mvn  dependency:copy-dependencies -DoutputDirectory=deploy/test/lib --settings d:\settings.xml
call mvn  package -Ptest -DskipTests --settings d:\settings.xml
xcopy /Y target\privilege.jar deploy\test\lib
xcopy /Y deploy\*.sh deploy\test\