import sbt._

object Dependencies {
  
  lazy val typesafeConfig =  "com.typesafe" % "config"       % "1.3.1"

  lazy val scalaTest = "org.scalatest"      %% "scalatest"   % "3.0.3"
  
  lazy val playJson = "com.typesafe.play"   %% "play-json"   % "2.6.8"
  
  lazy val httpScalaj = "org.scalaj"        %% "scalaj-http" % "2.3.0"
  
  lazy val hasher = "com.roundeights"       %% "hasher"      % "1.2.0"

}
