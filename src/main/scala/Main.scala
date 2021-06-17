import cats.effect.*
import cats.effect.std.Console
import cats.implicits.*
import scala.concurrent.duration.*

object Main extends IOApp:
  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- app[IO]()
      _ <- IO.println("App ended. We'll wait 2 seconds")
      _ <- IO.sleep(2.second)
      _ <- IO.println("starting http4s app")
      _ <- http4sApp[IO]()
    } yield ExitCode.Success

  def app[F[_]: Async: Console](): F[Unit] =
    for {
      _ <- Console[F].println("Hellooo ;)")
      _ <- Async[F].start {
        Async[F].sleep(1.second) *> Console[F].println("I'm async")
      }
    } yield ()

  def http4sApp[F[_]: Async: Console](): F[Unit] =
    Async[F].unit

def msg = "I was compiled by Scala 3. :)"
