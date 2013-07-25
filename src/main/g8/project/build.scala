import sbt._
import Keys._

object Build extends sbt.Build {

  lazy val root =
    project(id = "$name;format="norm"$",
            base = file(".")) aggregate(core)

  lazy val core =
    project(id = "$name;format="norm"$-core",
            base = file("$name$-core")
           )
            
  def project(id: String, base: File, settings: Seq[Project.Setting[_]] = Nil) =
    Project(id = id,
            base = base,
            settings = Project.defaultSettings ++ Shared.settings ++ settings ++ Seq(
              libraryDependencies ++= Shared.testDeps
            ))
}

object Shared {
    
  val testDeps = Seq(
    "org.specs2" %% "specs2" % "1.12.3" % "test",
    "org.mockito" % "mockito-all" % "1.9.0" % "test",
    "org.scalacheck" %% "scalacheck" % "1.9" % "test",
    "junit" % "junit" % "4.7" % "test"
  )

  val settings = Seq(
    organization := "$organization$",
    version := "$version$",
    scalaVersion := "2.9.2",
    crossScalaVersions := Seq("2.9.1"),
    scalacOptions := Seq("-deprecation", "-unchecked"),
    javacOptions ++= Seq("-source", "1.5", "-target", "1.5"),
    testOptions in Test += Tests.Argument("console", "junitxml"),
    resolvers ++= Seq("Novus Nexus Public" at "https://nexus.novus.com:65443/nexus/content/groups/public/"),
    initialCommands := "import $organization$.$name;format="lower,word"$._",
    shellPrompt := ShellPrompt.buildShellPrompt,
    publishTo <<= (version) { version: String =>
      val sfx =
        if (version.trim.endsWith("SNAPSHOT")) "snapshots"
        else "releases"
      val nexus = "https://nexus.novus.local:65443/nexus/content/repositories/"
      Some("Novus " + sfx at nexus + sfx + "/")
    },
    credentials += Credentials(Path.userHome / ".ivy2" / ".novus_nexus")
  ) ++ net.virtualvoid.sbt.graph.Plugin.graphSettings ++ Format.settings
  
}

// Shell prompt which show the current project, git branch and build version
object ShellPrompt {
  object devnull extends ProcessLogger {
    def info (s: => String) {}
    def error (s: => String) { }
    def buffer[T] (f: => T): T = f
  }
  def currBranch = (
    ("git status -sb" lines_! devnull headOption)
      getOrElse "-" stripPrefix "## "
    )

  val buildShellPrompt = {
    (state: State) => {
      val currProject = Project.extract (state).currentProject.id
      "[%s](%s)\$ ".format (
        currProject, currBranch /*, BuildSettings.buildVersion*/
      )
    }
  }
}

object Format {

  import com.typesafe.sbtscalariform.ScalariformPlugin
  import ScalariformPlugin._

  lazy val settings = scalariformSettings ++ Seq(
    ScalariformKeys.preferences := formattingPreferences
  )

  lazy val formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences().
      setPreference(AlignParameters, true).
      setPreference(AlignSingleLineCaseStatements, true).
      setPreference(CompactControlReadability, true).
      setPreference(CompactStringConcatenation, false).
      setPreference(DoubleIndentClassDeclaration, true).
      setPreference(FormatXml, true).
      setPreference(IndentLocalDefs, true).
      setPreference(IndentPackageBlocks, true).
      setPreference(IndentSpaces, 2).
      setPreference(MultilineScaladocCommentsStartOnFirstLine, true).
      setPreference(PreserveSpaceBeforeArguments, false).
      setPreference(PreserveDanglingCloseParenthesis, false).
      setPreference(RewriteArrowSymbols, false).
      setPreference(SpaceBeforeColon, false).
      setPreference(SpaceInsideBrackets, false).
      setPreference(SpacesWithinPatternBinders, true)
  }
}
