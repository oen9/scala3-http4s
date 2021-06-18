import cats.effect.kernel.Async
import cats.effect.kernel.Sync
import org.typelevel.log4cats.SelfAwareStructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jLogger

trait Logging {
  given [F[_]: Async]: SelfAwareStructuredLogger[F] = Slf4jLogger.getLoggerFromClass(getClass)
}
