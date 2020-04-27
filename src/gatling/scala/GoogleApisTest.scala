import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GoogleApisTest extends Simulation{

  val httpConfig = http
    .baseUrl("https://www.googleapis.com/blogger/v3/")
    .header(HttpHeaderNames.Accept, HttpHeaderValues.ApplicationJson)

  val scn = scenario("Retrieving A Blog")
    .exec(http("Get Blog By Id")
      .get("blogs/2399953")
      .queryParam("key", "AIzaSyBENR72f99cvvKiVGLHDjYD7kQk64-FJNk").check(status.is(200)))

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConfig))

}
