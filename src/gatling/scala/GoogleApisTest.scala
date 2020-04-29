import java.util.Properties

import io.gatling.core.Predef._
import io.gatling.core.structure._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class GoogleApisTest extends Simulation {

  val httpConfig: HttpProtocolBuilder = http
    .baseUrl("https://www.googleapis.com/blogger/v3/")
    .header(HttpHeaderNames.Accept, HttpHeaderValues.ApplicationJson)

  val props = new Properties()
  props.load(this.getClass.getResourceAsStream("gradle.properties"))

  val API_KEY: String = props.getProperty("API_KEY")

  val scn: ScenarioBuilder = scenario("Retrieving A Blog")
    .exec(getBlogById(API_KEY, "2399953"))

  def getBlogById(apiKey: String, blogId: String): ChainBuilder = {
    exec(http("Get Blog By Id")
      .get(s"blogs/$blogId")
      .queryParam("key", apiKey).check(status.is(200)))
  }

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConfig))

}
