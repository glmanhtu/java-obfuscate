@echo off
setlocal
if ""%1""=="""" goto autoset
if ""%1""==""show"" goto showconfig
java -jar ant-dasho.jar %1
if ERRORLEVEL 1 goto done
goto setconfig
:autoset
java -jar ant-dasho.jar "%~dp0"
if ERRORLEVEL 1 goto done
:setconfig
jar -uf ant-dasho.jar dasho.ant.runtime.properties
goto done
:showconfig
java -jar ant-dasho.jar
:done
endlocal
