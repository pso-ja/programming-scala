// code-examples/ObjectSystem/sake.scala
// This chapter contains only script files.

import sake.Project._

// Define some convenient variables.
val libDir   = "../lib/"

// If true, don't actually run any commands.
environment.dryRun = false

// If true, show stack traces when a failure happens (doesn't affect some "errors").
showStackTracesOnFailures = false

// Logging level: Info, Notice, Warn, Error, Failure
log.threshold = Level.Info

environment.classpath :::= (files(libDir + "*.jar") -- files(libDir + "*src.jar"))

target('all -> List('clean, 'scripts))

target('scripts) {
  files("**/*-script.scala").foreach { script =>
    scala(
        'classpath -> environment.classpath,
        'opts -> script 
    )
  }
}

target('clean, 'compile, 'spec) {}

