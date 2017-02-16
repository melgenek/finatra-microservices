import sbt.Keys._
import sbtassembly.AssemblyPlugin.autoImport._

lazy val versions = new {
	val finatra = "2.8.0"
	val guice = "4.0"
	val logback = "1.1.7"
	val slf4j = "1.7.22"
	val slick = "3.2.0-M2"
	val h2 = "1.4.193"
	val flyway = "4.1.0"
	val hikaricp = "2.4.5"
}

lazy val commonSettings = Seq(
	libraryDependencies ++= Seq(
		"com.twitter" %% "finatra-http" % versions.finatra
		, "ch.qos.logback" % "logback-classic" % versions.logback

		, "com.typesafe.slick" %% "slick" % versions.slick
		, "com.h2database" % "h2" % versions.h2
		, "com.zaxxer" % "HikariCP" % versions.hikaricp
		, "org.flywaydb" % "flyway-core" % versions.flyway
	)
)

lazy val commonTestSettings = Seq(
	libraryDependencies ++= Seq(
		"com.twitter" %% "finatra-http" % versions.finatra % "test"
		, "com.twitter" %% "finatra-jackson" % versions.finatra % "test"
		, "com.twitter" %% "inject-server" % versions.finatra % "test"
		, "com.twitter" %% "inject-app" % versions.finatra % "test"
		, "com.twitter" %% "inject-core" % versions.finatra % "test"
		, "com.twitter" %% "inject-modules" % versions.finatra % "test"
		, "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test"

		, "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests"
		, "com.twitter" %% "finatra-jackson" % versions.finatra % "test" classifier "tests"
		, "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests"
		, "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests"
		, "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests"
		, "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests"

		, "org.mockito" % "mockito-core" % "1.9.5" % "test"
		, "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
		, "org.scalatest" %% "scalatest" % "3.0.0" % "test"
		, "org.specs2" %% "specs2-mock" % "2.4.17" % "test"
	)
)

lazy val scalaVersionSettings = Seq(
	scalaVersion := "2.11.8"
)

lazy val noPackageSettings = Seq(
	Keys.`package` := file(""),
	packageBin in Global := file(""),
	packagedArtifacts := Map()
)

lazy val dockerPackageSettings = Seq(
	sbt.Keys.`package` := {
		assembly.value
		docker.value
		file("")
	},
	dockerfile in docker := {
		val artifact: File = assembly.value
		val artifactTargetPath = s"/app/${artifact.name}"
		new Dockerfile {
			from("java")
			add(artifact, artifactTargetPath)
			entryPoint("java", "-jar", artifactTargetPath)
			expose(8888)
			expose(9990)
		}
	},
	imageNames in docker := Seq(
		ImageName(s"scalatalk/${name.value}:latest")
	),
	assembly := {
		(compile in Compile).value
		assembly.value
	},
	assemblyMergeStrategy in assembly := {
		case "BUILD" => MergeStrategy.discard
		case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.discard
		case other => MergeStrategy.defaultMergeStrategy(other)
	}
)

lazy val common = project.in(file("common"))
	.settings(scalaVersionSettings)
	.settings(commonSettings)
	.settings(commonTestSettings)
	.settings(noPackageSettings)

lazy val `talk-service` = project.in(file("talk-service"))
	.settings(scalaVersionSettings)
	.settings(dockerPackageSettings)
	.dependsOn(common % "compile->compile;test->test")
	.enablePlugins(DockerPlugin)

lazy val root = project.in(file("."))
	.settings(scalaVersionSettings)
	.settings(noPackageSettings)
	.aggregate(common, `talk-service`)

