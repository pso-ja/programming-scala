# Programming Scala, 1st Edition#
## README for the Code Examples ##

Dean Wampler and Alex Payne  
September 20, 2009

This archive contains all the code examples found in [Programming Scala](http://programming-scala.labs.oreilly.com/), with the exception of several code snippets in the text. In the book, all the examples for which there are corresponding files here begin with a path comment like this:

    // code-examples/chapter/../filename
   
Use these comments to find the corresponding file in this distribution. In addition, this archive contains Scala *Specs* to test the code, most of which are not reproduced in the text of the book. *Specs* is a Behavior-Driven Development - BDD - tool like RSpec for Ruby. It is discussed in the _Tools and Libraries_ chapter of the book.

## Naming Conventions

The examples include scripts that are run with the `scala` command, source files that are compiled with `scalac`, and source files that deliberately fail to compile. To keep then straight and to support the build process, the following naming conventions are used for the files.

- `sake.scala` - build scripts for the *sake* build tool (described below).
- `*-script.scala` - Scripts that you can run using, for example, `scala foo-script.scala`. These script are executed as part of the normal compile and test process, but currently their outputs aren't checked automatically for correctness. (If they fail outright, they cause the build to fail.)
- `*-wont-compile.scala` - Scala files that are used in the book, but deliberately don't compile. They are also excluded from the build.
- `*-v28-*.scala` - Scala version 2.8 only. These files demonstrate new 2.8 features (see note below).
- All other `*.scala` files - Compilable Scala files that are built and tested by the *sake* build process.

> NOTE: While we have tried to ensure that all files work with Scala version 2.8, that release has not matured as fast as we expected while writing the book. For example, some libraries we use do not yet support v2.8. Therefore, the build process will not work, as is, with the v2.8 tool chain.

## Building the Code Examples

To build and run the examples, the following tools must be installed with the appropriate environment variables set (e.g., `PATH`), so that the tools can be used from the command line.

- Scala version 2.7.5 or later (excluding version 2.8).
- Java 1.5 or later.

To build the examples and run the tests, change to the `code-examples` directory and use the following shell command

    bin/sake

There will be a *lot* of output. (The *sake* build tool is described below.) You will find both a *bash* shell and a Windows command shell version of this script in `bin`. However, the Windows version does not support the options supported by the *bash* version at this time.

This `sake` script reads the build targets and rules from the `sake.scala` file in the current directory (analogous to a *make* file). The top-level build file simple iterates through all the chapter directories and builds the `all` target in each of them.

The `all` target in the `saka.scala` file for each chapter begins by cleaning previous build artifacts. The source files (i.e., non-scripts, as described above) are compiled and the tests are run, most of which are written using *Specs*. Finally, most of the "scripts" are executed, although their output is not automatically checked for the correct results, at this time.

In some directories, some compilable files and scripts are excluded for various reasons. These exceptions are documented in the corresponding `sake.scala` build files. 
  
Each directory under `code-examples` corresponds to a chapter in the book. If you want to build in those individual directories, run the following command:

    ../bin/sake [target]

The targets supported include the following:

    all             Default target
    clean           Remove build artifacts
    compile         Compile the non-script Scala files
    spec            Run the tests (Specs) files
    scripts         Run the script files
    
Some chapters have only script files. For them, the `clean`, `compile`, and `spec` targets do nothing.

Many of the script files depend on other code compiled for the same chapter (if any). All compiled class files are written to the `build` directory corresponding to each chapter. In some cases, scripts rely on code in other chapters or on some of the jars in the top-level `lib` directory. Hence, to run an individual script file, *e.g.,* `foo-script.scala`, try the following command first.

    scala foo-script.scala
    
If it requires code in the build directory, run the `../bin/sake` command first to build the code, then try the following:

    scala -classpath build foo-script.scala
    
Finally, if the the script appears to require one or more jars from `lib`, add those jars to the `classpath` argument, *e.g.,*
 
    scala -classpath build:../lib/junit4-5.jar foo-script.scala

### Optional Build Targets ###

There are a few examples in the book that use *Ant* with *Ivy*, *AspectJ*, or *Spring* (but never at the same time ;). These are not built by default, so we don't assume you have those tools installed. If you want to build some or all of them, you'll need one or more of the following tools installed.

    - Ant 1.7+ and Ivy 2 
    - AspectJ 1.6 or later
    - Spring 2.5 or later 

*Ant* and *Ivy* are required to build the SMTPD example in the `Concurrency` chapter. If you want to build it, go to the `Concurrency/smtpd` directory and run the command `ant`.

*AspectJ* and *Spring* are only required if you build the examples in the `ToolsLibs` directory that demonstrate integration between these tools and Scala. In the `ToolsLibs` directory, run either or both of the following commands:

    ../bin/sake spring
    ../bin/sake aspectj

## About Sake ##

As an exercise, Dean built a build tool, named *sake* (as in the Japanese alcoholic beverage) that we used for the code examples. It's inspired by Ruby *rake* and Unix *make*. It's quite incomplete in terms of features and it has a number of warts. Nevertheless, it provides a nontrivial example of implementing an *internal* DSL in Scala. (There is a chapter on DSLs in the book.)

The `sake` driver script for *nix-like systems is in the `bin` directory. There is also a `sake.bat` for Windows systems (but it is largely untested and it supports fewer invocation options). The corresponding jar file and a jar of the source code are in the `lib` directory. Sake is maintained at [http://github.com/deanwampler/SakeScalaBuildTool](http://github.com/deanwampler/SakeScalaBuildTool).

The *Scala Tools, Libraries, and IDE Support* chapter in the book discuss build tool options. If you are looking for "production quality" alternatives to *ant* and *maven*, we recommend that you consider the *Simple Build Tool* (sbt) or *Buildr*.

## Feedback ##

We welcome feedback on the examples. For the code that actually appears in the book, you can post comments, corrections, etc. at the public "labs" site, [http://programming-scala.labs.oreilly.com/](http://programming-scala.labs.oreilly.com/). Or, you can post feedback on the [O'Reilly forum](http://forums.oreilly.com/). We always appreciate your willingness to provide feedback.
