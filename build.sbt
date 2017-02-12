import sbt.Keys._

scalaVersion := "2.11.8"

lazy val versions = new {
	val finatra = "2.8.0"
	val slf4j = "1.7.22"
}

lazy val commonSettings = Seq(
	libraryDependencies ++= Seq(
		"com.twitter" %% "finatra-http" % versions.finatra
		, "org.slf4j" % "slf4j-simple" % versions.slf4j
	)
	, scalaVersion := "2.11.8"
)

lazy val common = project.in(file("common"))
	.settings(commonSettings: _*)

lazy val talk_service = project.in(file("talk_service"))
	.settings(commonSettings: _*)
	.dependsOn(common)






