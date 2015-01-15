@echo off
setlocal
if ""%1""=="""" goto nodir
setlocal enableextensions
if ERRORLEVEL 1 echo Unable to enable extension & goto done
set ANT_HOME=%~1
set ANT_LIB=%ANT_HOME%\lib
call :makedir "%ANT_HOME%" || goto done
call :makedir "%ANT_LIB%" || goto done
call :delfile "%ANT_LIB%\ant-dashopro.jar" || goto done
call :copyfile "ant-dasho.jar" "%ANT_LIB%" || goto done
call :copyfile "lib\doxfile.jar" "%ANT_LIB%" || goto done
call :copyfile "lib\jaxb-api.jar" "%ANT_LIB%" || goto done
call :copyfile "lib\jaxb-impl.jar" "%ANT_LIB%" || goto done
call :copyfile "lib\jaxb-libs.jar" "%ANT_LIB%" || goto done
call :copyfile "lib\relaxngDatatype.jar" "%ANT_LIB%" || goto done
call :copyfile "lib\xsdlib.jar" "%ANT_LIB%" || goto done
echo Installation complete
goto done
:copyfile
echo Copying %~1 to %~2
if exist %1 goto srcok
echo %~1 does not exist
exit /b 1
:srcok
if exist %2 goto dstok
echo %~2 does not exist
exit /b 1
:dstok
copy /Y %1 %2 1>NUL
if ERRORLEVEL 0 goto :EOF
echo COPY FAILED
exit /b 1
goto :EOF
:delfile
if exist %1 goto delok
goto :EOF
:delok
echo Deleting obsolete %~1
del /f %1 1>NUL
if ERRORLEVEL 0 goto :EOF
echo DELETE FAILED
exit /b 1
goto :EOF
:makedir
if exist %1 goto :EOF
echo Creating %~1
mkdir %1
if ERRORLEVEL 0 goto :EOF
echo MKDIR FAILED
exit /b 1
goto :EOF
:nodir
echo use: antinstall ANT_HOME
goto done
:done
endlocal
