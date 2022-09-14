package jaackotorus

import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router
import sttp.tapir.server.http4s.ztapir.ZHttp4sServerInterpreter
import zio.interop.catz.*
import zio.{Scope, Task, ZIO, ZIOAppArgs, ZIOAppDefault}
import scala.io.StdIn

object Main extends ZIOAppDefault:

  override def run: ZIO[Any & ZIOAppArgs & Scope, Any, Any] =
    val routes = ZHttp4sServerInterpreter().from(Endpoints.all).toRoutes

    val port = sys.env.get("http.port").map(_.toInt).getOrElse(8080)

    ZIO.executor.flatMap { executor =>
      BlazeServerBuilder[Task]
        .withExecutionContext(executor.asExecutionContext)
        .bindHttp(port, "localhost")
        .withHttpApp(Router("/" -> routes).orNotFound)
        .resource
        .use { server =>
          ZIO.succeedBlocking {
            println (s"Go to http://localhost:${server.address.getPort}/docs to open SwaggerUI. Press ENTER key to exit.")
            StdIn.readLine ()
          }
        }
        .unit
    }
