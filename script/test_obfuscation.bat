@ECHO OFF
set rel_path_testfolder=..\test-file
set abs_path_testfolder=
set current_test_file=

rem	// Save current directory and change to target directory
pushd %rel_path_testfolder%
rem	// Save value of CD variable (current directory)
set abs_path_testfolder=%CD%
rem // Restore original directory
popd


for /f "usebackq delims=|" %%f in (`dir /b %rel_path_testfolder%`) do (
	call allatori_test.bat "%abs_path_testfolder%\%%f"
)
PAUSE