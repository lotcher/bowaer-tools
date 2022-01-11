import org.junit.{Test, Assert}
import wiki.lbj.Common._


class CommonTest extends Assert {
    @Test def testTimeIt(): Unit = {
        timeIt(Thread.sleep(1000))
        timeIt(Thread.sleep(1000), count = 3)
        timeIt {
            (1 to 10000).map(_ + 1).sum
        }
    }

    @Test def testTriable(): Unit = {
        assert(triable(1 / 0).getOrElse(3) == 3)
        assert(triable(1 / 0).isEmpty)
        assert(triable(1 / 2).getOrElse(3) == 1 / 2)
        triable(print(s"first run 1/0: ${1 / 0}  "), finalFunc = println("final function run when error"))
        triable(print(s"first run 1/2: ${1 / 2}  "), finalFunc = println("final function run"))
    }

    @Test def testLog(): Unit = {
        log.debug("debug log")
        log.info("info log")
        log.warn("warn log")
        log.error("error log")
    }
}