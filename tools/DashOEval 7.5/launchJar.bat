@echo off
echo.
echo Unable to find javaw.exe on the system path.
echo Attempting to launch DashO as an executable jar file...
echo WARNING: Memory settings and other options will not be used.
echo.
echo Please add Java to the system path.
echo.
setlocal
set DASHO_HOME=%~dp0
"%DASHO_HOME%DashOPro.jar"
endlocal
