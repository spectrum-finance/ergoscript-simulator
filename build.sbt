version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.17"

lazy val NexusReleases  = "Sonatype Releases" at "https://s01.oss.sonatype.org/content/repositories/releases"
lazy val NexusSnapshots = "Sonatype Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file("."))
  .settings(
    name := "ergoscript-simulator",
    resolvers ++=
      Resolver.sonatypeOssRepos("public") ++
      Resolver.sonatypeOssRepos("snapshots") ++
      Seq(
        NexusReleases,
        NexusSnapshots
      )
  )

scalacOptions ++= List(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-feature",
  "-unchecked",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ypartial-unification"
)

libraryDependencies ++= Seq(
  ("org.scorexfoundation"         %% "sigma-state"     % "4.0.7").exclude("org.typelevel", "cats-kernel_2.12"),
  "org.typelevel"                 %% "cats-core"       % "2.6.1",
  "com.softwaremill.sttp.client3" %% "circe"           % "3.3.18",
  "com.softwaremill.sttp.client3" %% "okhttp-backend"  % "3.3.18",
  "tf.tofu"                       %% "tofu"            % "0.10.8",
  "tf.tofu"                       %% "derevo-circe"    % "0.13.0",
  "org.scalactic"                 %% "scalactic"       % "3.2.9"   % Test,
  "org.scalatest"                 %% "scalatest"       % "3.2.9"   % Test,
  "org.scalatestplus"             %% "scalacheck-1-15" % "3.2.9.0" % Test,
  "org.scalacheck"                %% "scalacheck"      % "1.15.4"  % Test,
  compilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.2" cross CrossVersion.full),
  compilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1")
)
