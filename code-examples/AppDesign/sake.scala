// code-examples/AppDesign/sake.scala
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

environment.classpath :::= (files(libDir + "*.jar") -- files(libDir + "*src.jar"))
environment.classpath ::= buildDir
environment.classpath ::= "../Traits/" + buildDir
environment.classpath ::= "../BasicOOP/" + buildDir

target('all -> List('clean, 'compile, 'spec, 'scripts))

target('spec) {
    specs(
        'classpath -> environment.classpath,
        'path -> "**/*.scala",
        'pattern -> ".*Spec.*"
    )
}

target('scripts) { 
  // Omits version 2.8 scripts.
  (files("**/*-script.scala") -- 
   files("**/*-v28-script.scala")).foreach { script =>
    scala(
        'classpath -> environment.classpath,
        'opts -> script 
    )
  }
}

target('compile -> List('clean, 'build_dir)) {
    sh("javac -Xlint:unchecked -d " + buildDir + " -cp " + environment.classpath + " annotations/Pre.java")
    scalac(
        // Exclude this build script, demonstration scripts, some test scripts, and
        // files that intentionally don't compile.
        'files     -> (files("**/*.scala") -- files("sake.scala") --
                       files("**/*-script.scala") -- files("**/*-wont-compile.scala")),
        'classpath -> environment.classpath,
        'd         -> buildDir,
        'opts      -> "-unchecked -deprecation"
    )
    sh("javac -Xlint:unchecked -d " + buildDir + " -cp ../lib/scala/scala-library.jar:" + environment.classpath + " annotations/FilePrinterMain.java")
}

target('clean) {
    deleteRecursively(buildDir)
}

target('build_dir) {
    mkdir(buildDir)
}
