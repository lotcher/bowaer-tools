import org.junit.Test
import org.junit.Assert.{assertEquals, assertTrue}
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

    @Test def testSeqMixin(): Unit = {
        val seq = Seq(1, 2, 3, 4, 5, 6)

        assertEquals(seq.gets(Seq(3, 5)), Seq(4, 6))
        assertEquals(seq.gets(Seq(3, 6), 0), Seq(4, 0))

        assertEquals(seq.maxTrueRange(_ > 3), (3, 5))
    }

    @Test def testDoubleSeqMixin(): Unit = {
        val seq = Seq(1, 2, 3, 4, 5).map(_.toDouble)
        val eps = 0.01

        assertTrue((seq.mean - 3) <= eps)
        assertTrue((seq.std - 1.414) <= eps)

        assertEquals(seq.difference, Seq(1, 1, 1, 1))
        assertEquals(seq.quantiles(0.5, 0.1), Seq(3, 1.4))

        assertTrue((seq.distance(seq) - 0) <= eps)
    }


    @Test def testCast(): Unit = {
        assertEquals(true.toInt, 1)
        assertEquals(false.toInt, 0)

        case class A(v: Int)
        assertEquals(A(0).toJson, """{"v":0}""")
    }

}