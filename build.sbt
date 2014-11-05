import AssemblyKeys._ // put this at the top of the file

assemblySettings

name		:= "smartu"

version		:= "0.1.0"

organization 	:= "com.prismtech.vortex.demo"

homepage :=  Some(new java.net.URL("http://prismtech.com"))

scalaVersion 	:= "2.11.4"

resolvers += "nuvo.io maven repo" at "http://nuvo-io.github.com/mvn-repo/snapshots"

resolvers += "Vortex Snapshot Repo" at "ADD REPO HERE"

libraryDependencies += "io.nuvo" % "moliere_2.11" % "0.5.1-SNAPSHOT"

libraryDependencies += "com.prismtech.cafe" % "cafe" % "2.1.0p1-SNAPSHOT"

libraryDependencies += "com.espertech" % "esper" % "4.11.0"

autoCompilerPlugins := true

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

scalacOptions += "-optimise"

scalacOptions += "-feature"

scalacOptions += "-language:postfixOps"

proguardSettings

ProguardKeys.options in Proguard += """
-dontnote
-dontwarn
-ignorewarnings
-dontobfuscate
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keeppackagenames **
-optimizationpasses 3
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable
-keep public class com.prismtech.cafe.core.ServiceEnvironmentImpl
-keep public class org.slf4j.ILoggerFactor {
      *;
}
-keep  class vortex.demo.smartu.gen.*Helper {  
       *; 
}
"""

// ProguardKeys.options in Proguard += ProguardOptions.keepMain(vortex.demo.smartu.Meter")
