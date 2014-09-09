name := "characters"

scalaVersion := "2.10.4"

version := "1.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "org.specs2" % "specs2_2.10" % "2.3.13"

libraryDependencies  +="org.scalanlp" % "breeze_2.10" % "0.7"

libraryDependencies  +="org.scalanlp" % "breeze-natives_2.10" % "0.7"

resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
