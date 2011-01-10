// code-examples/TypeSystem/sake.scala
// Uses the "observer" code from "Advanced OOP".
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
environment.classpath ::= "../AdvOPP/" + buildDir

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
  //   func-script and func2-script:  Deliberate compilation errors
  //   mutable-type-variance-script:  Deliberate compilation errors
  (files("**/*-script.scala") -- 
   files("**/func-script.scala")  -- 
   files("**/func2-script.scala") -- 
   files("**/mutable-type-variance-script.scala")).foreach { script =>
    scala(
        'classpath -> environment.classpath,
        'opts -> script 
    )
  }
}

target('compile -> List('clean, 'build_dir, 'compile_scala, 'compile_java, 'compile_specs))

target('compile_scala) {
    scalac(
        // Exclude this build script, demonstration scripts, and 
        // files that intentionally don't compile. Also, exclude spec files for now.
        'files     -> (files("**/*.scala") -- files("**/*spec.scala") -- files("sake.scala") -- 
                       files("**/*-script.scala") -- files("**/*-wont-compile.scala")),
        'classpath -> environment.classpath,
        'd         -> buildDir,
        'opts      -> "-unchecked -deprecation"
    )
}
// Must compile the specs after the java files.
target('compile_specs) {
    scalac(
        // Exclude everything except for the specs.
        'files     -> files("**/*spec.scala"),
        'classpath -> environment.classpath,
        'd         -> buildDir,
        'opts      -> "-unchecked -deprecation"
    )
}

target('compile_java) {
    sh("javac -Xlint:unchecked -classpath " + environment.classpath + " -d " + buildDir + " " + 
      files("**/*.java").reduceLeft(_ + " " + _)) 
}

target('run_OptionExample) {
  sh("java -classpath " + environment.classpath + " variances.OptionExample")
}

target('clean) {
    deleteRecursively(buildDir)
}

target('build_dir) {
    mkdir(buildDir)
}
