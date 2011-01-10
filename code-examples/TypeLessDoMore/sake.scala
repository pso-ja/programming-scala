// code-examples/TypeLessDoMore/sake.scala
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
  // Omits scripts with deliberate errors.
  (files("**/*-script.scala") -- 
   files("**/method-broad-inference-return-script.scala") --
   files("**/method-nested-return-script.scala") --
   files("**/method-overloaded-return-script.scala") --
   files("**/method-recursive-return-script.scala") --
   files("**/*-v28-script.scala")).foreach { script =>
    scala(
        'classpath -> environment.classpath,
        'opts -> ("-nocompdaemon " + script)
    )
  }
}

target('compile -> List('clean, 'build_dir)) {
    scalac(
        // Exclude this build script, demonstration scripts, some test scripts, and 
        // files that intentionally don't compile. 
        // Also, exclude "string-util.scala" from the build. It is a "first version"
        // of "string-util-v3.scala". They both define the same object, so a compilation
        // error would result if they were compiled together.
        'files     -> (files("**/*.scala") -- files("sake.scala") -- 
                       files("**/*-script.scala") -- files("**/*-wont-compile.scala") --
                       files("**/string-util-mutable.scala") -- files("**/string-util.scala")),
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
