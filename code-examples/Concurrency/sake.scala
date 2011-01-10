// code-examples/Concurrency/sake.scala
import sake.Project._

// Define some convenient variables.
val buildDir = "build/"
val libDir   = "../lib/"

// If true, don't actually run any commands.
environment.dryRun = false

// If true, show stack traces when a failure happens (doesn't affect some "errors").
showStackTracesOnFailures = false

// Logging level: Info, Notice, Warn, Error, Failure
log.threshold = Level.Info

environment.classpath :::= files(libDir + "*.jar")
environment.classpath ::= buildDir

target('all -> List('clean, 'compile, 'spec, 'scripts))

target('spec) {
    specs(
        'classpath -> environment.classpath,
        'path -> "**/*.scala", 
        'pattern -> ".*Spec.*"
    )
}

target('scripts) {
  // Omits the following:
  //   actor-mailbox-script and pattern-match-actor-script: Weird Scala 2.7.X bug
  //   causes them to fail if run using "scala *-script.scala", but they can be
  //   loaded just fine! That is, you can use the following commands:
  //     echo ':load actor-mailbox-script.scala' | scala
  //     echo ':load pattern-match-actor-script.scala' | scala
  (files("**/*-script.scala") -- 
   files("**/actor-mailbox-script.scala")  -- 
   files("**/pattern-match-actor-script.scala")).foreach { script =>
    scala(
        'classpath -> environment.classpath,
        'opts -> ("-nocompdaemon " + script) 
    )
  }
}

target('compile -> List('clean, 'build_dir)) {
    scalac(
        // Exclude this build script, demonstration scripts, and 
        // files that intentionally don't compile. Also exclude "smptd/...", which
        // are built by the "ant" target instead.
        'files     -> (files("**/*.scala") -- files("sake.scala") -- 
                       files("**/*-script.scala") -- files("**/*-wont-compile.scala") --
                       files("**/programmingscala") -- files("smtpd/**")),
        'classpath -> environment.classpath,
        'd         -> buildDir,
        'opts      -> "-unchecked -deprecation"
    )
}

target('clean) {
    deleteRecursively(buildDir)
}

target('build_dir) {
    mkdir(buildDir)
}
