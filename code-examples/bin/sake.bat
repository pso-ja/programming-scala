@echo off
rem sake for Windows need to add -Xnojline option on scala command.
@setlocal

if "%SAKE_HOME%" == "" (
  set SAKE_HOME=%~dp0..
)

set SAKE_CLASSPATH=
for %%f in ("%SAKE_HOME%\lib\*") do call :add_cpath "%%f"
set CLASSPATH=%SAKE_CLASSPATH%;%CLASSPATH%

set _INTERACTIVE=false
set _SAKE_FILE=sake.scala
set _TARGET=

:loop
if (%1) == () goto exec
if (%1) == (-h) call :show_help & goto :eof
if (%1) == (-i) set _INTERACTIVE=true & goto next
if (%1) == (-f) shift & set _SAKE_FILE=%1 & goto next
if (%1) == (-classpath) shift & call :add_cpath %1 & goto next

rem targets 
if "%_TARGET%"=="" (
 set _TARGET=%1
) else (
 set SAKE_CLASSPATH=%_TARGET% %1
)

:next
shift
goto loop

:exec

if (%_TARGET%)==() set _TARGET=all

if (%_INTERACTIVE%) == (true) (
  scala -i %_SAKE_FILE%
) else ( 
  %SAKE_HOME%\bin\sake_stdin.bat %_TARGET% |call scala -Xnojline -deprecation -unchecked
)

@endlocal
goto :eof


:add_cpath
  if "%SAKE_CLASSPATH%"=="" (
    set SAKE_CLASSPATH=%~1
  ) else (
    set SAKE_CLASSPATH=%SAKE_CLASSPATH%;%~1
  )
goto :eof

:show_help
rem echo usage: sake [-h] [-f sake.scala] [scala_opts] [targets]
 echo usage: sake [-h] [-f sake.scala] [targets]
 echo where:
 echo -h             Display this help and exit
 echo -i             Interactive mode. After loading the build file, it puts you 
 echo                at the scala command prompt.
 echo -f build_file  The build file name, where sake.scala is the default.
rem echo scala_opts     Any other options are passed to the scala command. 
 echo targets        What to build; defaults to all, unless in interactive
 echo                mode, in which case nothing is built by default. If 
 echo                targets are specified, they are built after loading the 
 echo                build file and before presenting the scala prompt.
goto :eof
