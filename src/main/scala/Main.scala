import cats.effect.*
import cats.effect.implicits.*
import cats.effect.std.Console
import cats.effect.unsafe.IORuntime
import cats.implicits.*
import cats.syntax.show
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.SelfAwareStructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jLogger

import java.net.InetAddress
import java.util.Currency
import scala.concurrent.duration.*

object Main extends IOApp, Logging:
  //given [F[_]: Async]: SelfAwareStructuredLogger[F] = Slf4jLogger.getLogger[F]

  case class SrvInfo(hostname: String)

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- app[IO]()
      _ <- Logger[IO].info("App ended. We'll wait 2 seconds")
      _ <- IO.sleep(2.second)
      _ <- Logger[IO].info("starting http4s app")
      _ <- http4sApp[IO]()
    } yield ExitCode.Success

  def app[F[_]: Async](): F[Unit] =
    for {
      _ <- Logger[F].info("Hellooo ;)")
      _ <- Async[F].start {
        Async[F].sleep(1.second) *> Logger[F].info("I'm async")
      }
    } yield ()

  def http4sApp[F[_]: Async](): F[Unit] = {
    import org.http4s.*, org.http4s.dsl.Http4sDsl

    val dsl = Http4sDsl[F]
    import dsl._

    import io.circe.syntax.*
    import io.circe.generic.auto.*
    import org.http4s.circe.*
    val helloWorldService = HttpRoutes.of[F] {
      case GET -> Root =>
        for {
          hostname <- Sync[F].delay(InetAddress.getLocalHost.getHostName)
          srvInfo = SrvInfo(hostname = hostname)
          result <- Ok(srvInfo.asJson)
        } yield result

      case GET -> Root / "hello" / name =>
        Ok(s"Hello, $name.")
    }

    import org.http4s.server.Router
    import org.http4s.implicits.*
    val httpApp = Router("/" -> helloWorldService).orNotFound

    import org.http4s.blaze.server._
    for {
      ec  <- Async[F].executionContext
      cfg <- AppConfig.load()
      fiber <- BlazeServerBuilder[F](ec)
        .bindHttp(cfg.http.port, cfg.http.host)
        .withHttpApp(httpApp)
        .resource
        .use(_ => Async[F].never)
        .start
      _ <- fiber.join // hehe just to use fiber
    } yield ()
  }

def msg = "I was compiled by Scala 3. :)"
