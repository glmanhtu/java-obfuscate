@ECHO OFF

set rel_path_outfolder=..\test-output\allatori
set abs_path_outfolder=

rem	// Save current directory and change to target directory
pushd %rel_path_outfolder%
rem	// Save value of CD variable (current directory)
set abs_path_outfolder=%CD%
rem // Restore original directory
popd

set current_file_test=%~1

rem //Remove all file in output folder
cd %abs_path_outfolder%


