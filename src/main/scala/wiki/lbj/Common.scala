package wiki.lbj

import wiki.lbj.Logger.logger

object Common {
    def triable[T](func: => T, throwable: Throwable = null, catchFunc: Throwable => Unit = _ => null,
                   finalFunc: => Unit = null): Option[T] = {
        try Some(func)
        catch {
            case e: Throwable => logger.error(e.toString)
                catchFunc(e)
                if (throwable != null) throw throwable else {
                    e.printStackTrace()
                    None
                }
        }
        finally finalFunc
    }

    def timeIt[T](func: => T, funcName: String = "", count: Int = 1): T = {
        val s = System.currentTimeMillis
        val r = (1 to count).map((_: Int) => func).head
        logger.info(s"${funcName}执行${count}次, 平均耗时:${(System.currentTimeMillis - s) / count}ms")
        r
    }
}
