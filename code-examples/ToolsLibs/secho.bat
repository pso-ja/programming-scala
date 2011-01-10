::#!
@echo off
call scala -Xnojline %0 %*
goto :eof
::!#
print("You entered: ")
argv.toList foreach { s => printf("%s ", s) }
println
