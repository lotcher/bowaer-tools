import org.junit.Test
import org.junit.Assert.assertEquals
import wiki.lbj.Implicits._


class ImplicitsTest {
    @Test def testMap(): Unit = {
        val map = Map(1 -> 1, 2 -> 2, 3 -> 3)
        assertEquals(map.gets(Seq(1, 2, 3, 4)), Seq(1, 2, 3))
        assertEquals(map.gets(Seq(1, 2), v => v.toString), Seq("1", "2"))

        assertEquals(map.sub(Seq(1, 2, 3, 4)), Map(1 -> 1, 2 -> 2, 3 -> 3))
        assertEquals(map.sub(Seq(1, 2), v => v + 1), Map(1 -> 2, 2 -> 3))
    }

    @Test def testDouble(): Unit = {
        val double = math.Pi
        assertEquals(double.precision, 3, 1e-5)
        assertEquals(double.precision(2), 3.14, 1e-5)
    }

}