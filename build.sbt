lazy val root = (project in file(".")).
	settings(
		name := "steam-scraper",
		version := "1.0",
		scalaVersion := "2.11.7",
		libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "1.0.0"
	)
