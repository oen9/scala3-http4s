import cats.effect.Sync
import cats.implicits._
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

class MyClass[F[_]](logger: Logger[F]):
  def foo(): F[Unit] = logger.info("hello")

object MyClass:
  def apply[F[_]: Sync](): F[MyClass[F]] =
    for {
      logger <- Slf4jLogger.fromName("com.github.oen9.MyClass")
    } yield new MyClass(logger)
