cd tomcat\webapps\PipeServer\WEB-INF\classes
del *.* /F /Q
cd ..\..\..\..\..\
xcopy Server\out\production\pipeGame_PTM tomcat\webapps\PipeServer\WEB-INF\classes /s /e
cd tomcat/bin
startup.bat