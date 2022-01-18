import org.junit.Test
import org.junit.Assert.{assertEquals, assertTrue}
import wiki.lbj.Common._


class CommonTest {
    @Test def testTimeIt(): Unit = {
        timeIt(Thread.sleep(1000))
        timeIt(Thread.sleep(1000), count = 3)
        timeIt {
            (1 to 10000).map(_ + 1).sum
        }
    }

    @Test def testTriable(): Unit = {
        assertEquals(triable(1 / 0).getOrElse(3), 3)
        assertTrue(triable(1 / 0).isEmpty)
        assertEquals(triable(1 / 2).getOrElse(3), 1 / 2)
        triable(print(s"first run 1/0: ${1 / 0}  "), finalFunc = println("final function run when error"))
        triable(print(s"first run 1/2: ${1 / 2}  "), finalFunc = println("final function run"))
    }
}