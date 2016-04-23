import scraper.{OriginScraper, Scraper, SteamScraper}

object App extends App {
  override def main(args: Array[String]): Unit = {
    var scraper : Scraper = new SteamScraper
    println(scraper.getInfo("gta").head)
    scraper = new OriginScraper
    println(scraper.getInfo("star wars").head)
  }
}