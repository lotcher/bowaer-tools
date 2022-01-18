import org.junit.Assert.assertEquals
import org.junit.Test
import wiki.lbj.Logger.{LogWithAny, Marker}


class LoggerTest {
    @Test def testLog(): Unit = {
        val obj = 123
        assertEquals(obj.info(), obj)
        assertEquals(obj.info("test"), obj)
        assertEquals(obj.info(v => (v * 100).toString), obj)
    }

    @Test def testMarker(): Unit = {
        implicit val marker: MyMarker = new MyMarker()
        assertEquals("any".info(), "any")
    }


    class MyMarker extends Marker {
        override def mark(msg: String): Unit = println("MyMarker: you can see it")
    }
}