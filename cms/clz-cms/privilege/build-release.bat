cd ../intapi
call build-release.bat
cd ../privilege
call mvn  dependency:copy-dependencies -DoutputDirectory=deploy/release/lib --settings d:\settings.xml
call mvn  package -Prelease -DskipTests --settings d:\settings.xml
xcopy /Y target\privilege.jar deploy\release\lib
xcopy /Y deploy\*.sh deploy\release\