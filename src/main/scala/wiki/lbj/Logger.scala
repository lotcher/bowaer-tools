package wiki.lbj

import org.slf4j.{Logger, LoggerFactory}

object Logger {
    final val logger: Logger = LoggerFactory.getLogger("")
    implicit val defaultMarker: Marker = new DefaultMarker

    implicit class LogWithAny[T](obj: T) {
        def debug(msg: String = obj.toString)(implicit marker: Marker): T = _log(logger.debug, msg)(marker)

        def debug(rich: T => String)(implicit marker: Marker): T = _log(logger.debug, rich)(marker)

        def info(msg: String = obj.toString)(implicit marker: Marker): T = _log(logger.info, msg)(marker)

        def info(rich: T => String)(implicit marker: Marker): T = _log(logger.info, rich)(marker)

        def warn(msg: String = obj.toString)(implicit marker: Marker): T = _log(logger.warn, msg)(marker)

        def warn(rich: T => String)(implicit marker: Marker): T = _log(logger.warn, rich)(marker)

        def error(msg: String = obj.toString)(implicit marker: Marker): T = _log(logger.error, msg)(marker)

        def error(rich: T => String)(implicit marker: Marker): T = _log(logger.error, rich)(marker)

        private def _log(logFunc: String => Unit, msg: String)(implicit marker: Marker): T = {
            logFunc(msg)
            marker.mark(msg)
            obj
        }

        private def _log(logFunc: String => Unit, rich: T => String)(implicit marker: Marker): T = {
            val msg = rich(obj)
            _log(logFunc, msg)(marker)
            obj
        }
    }

    trait Marker {
        def mark(msg: String)
    }

    final class DefaultMarker extends Marker {
        override def mark(msg: String): Unit = ()
    }
}
