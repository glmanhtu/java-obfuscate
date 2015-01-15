@echo off
rem ===========================================================================
rem Note: A 1.5 or later VM is required to run DashO PRO
rem ===========================================================================

setlocal
set DASHO_HOME=%~dp0
set memoryStart=192m
set memoryMax=192m
if exist "%DASHO_HOME%\dashorc.bat" call "%DASHO_HOME%\dashorc.bat"
if "%HOME%"=="" goto checkHomeDriveRC
if exist "%HOME%\dashorc.bat" call "%HOME%\dashorc.bat"
:checkHomeDriveRC
if "%HOMEDRIVE%%HOMEPATH%"=="" goto checkUserProfRC
if "%HOMEDRIVE%%HOMEPATH%"=="%HOME%" goto checkUserProfRC
if exist "%HOMEDRIVE%%HOMEPATH%\dashorc.bat" call "%HOMEDRIVE%%HOMEPATH%\dashorc.bat"
:checkUserProfRC
if "%USERPROFILE%"=="" goto rcCheckDone
if "%USERPROFILE%"=="%HOME%" goto rcCheckDone
if "%USERPROFILE%"=="%HOMEDRIVE%%HOMEPATH%" goto rcCheckDone
if exist "%USERPROFILE%\dashorc.bat" call "%USERPROFILE%\dashorc.bat"
:rcCheckDone
set DASHO_CMD_LINE_ARGS=%1 %options%
if ""%1""=="""" goto start
shift
:args
if ""%1""=="""" goto start
set DASHO_CMD_LINE_ARGS=%DASHO_CMD_LINE_ARGS% %1
shift
goto args
:start
java -Xms%memoryStart% -Xmx%memoryMax% %javaOptions% -cp "%DASHO_HOME%DashOPro.jar" DashOPro %DASHO_CMD_LINE_ARGS%
endlocal
