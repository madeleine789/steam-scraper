import scraper.{OriginScraper, Scraper, SteamScraper}

object App extends App {
  override def main(args: Array[String]): Unit = {
    var scraper : Scraper = new SteamScraper
    scraper.getInfo("counter-strike").foreach {println}
    scraper = new OriginScraper
    println()
    scraper.getInfo("star wars").foreach {println}
  }
}