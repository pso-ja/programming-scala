// code-examples/sake.scala
import sake.Project._

// Define some convenient variables.
val buildDir = "build/"
val distDir = "../../dist/"

// If true, don't actually run any commands.
environment.dryRun = false

// If true, show stack traces when a failure happens (doesn't affect some "errors").
showStackTracesOnFailures = false

// Logging level: Info, Notice, Warn, Error, Failure
log.threshold = Level.Info

// Build chapters in book order. In some cases, a chapter depends
// on code in a previous chapter.
val code_chapters = List(
    "IntroducingScala",
    "TypeLessDoMore",
    "Rounding", 
    "Traits",
    "BasicOOP",
    "AdvOOP",
    "ObjectSystem",
    "FP",
    "Concurrency",
    "XML",
    "DSLs",
    "TypeSystem",
    "AppDesign",
    "ToolsLibs"
)

List('all, 'clean).foreach { sym => 
    echo("Defining target " + sym)
    target(sym) {
        code_chapters.foreach { chapter =>
            echo("Building " + sym + " for chapter \""+chapter+"\"")
            sakecmd('directory -> chapter, 'targets -> sym)
        }
    }
}
