import org.junit.Test
import org.junit.Assert.assertEquals
import wiki.lbj.JsonParser._


class JsonParserTest {
    @Test def testToMap(): Unit = {
        val json = """{"a":1, "b":true, "c":"2", "d":null, "e":[1,2,3,4], "f":2.1}"""
        val node = parse(json)
        val stringMap = node.toMap[String].filter(_._2.nonEmpty).map(v => v._1 -> v._2.get)
        println(stringMap)
        assertEquals(stringMap.keySet, Set("a", "b", "c", "d", "e", "f"))
        assertEquals(stringMap.get("a"), Some("1"))
        assertEquals(stringMap.get("b"), Some("true"))
        assertEquals(stringMap.get("c"), Some("2"))
        assertEquals(stringMap.get("e"), Some(""))

        val IntMap = node.toMap[Int].filter(_._2.nonEmpty).map(v => v._1 -> v._2.get)
        println(IntMap)
        assertEquals(IntMap.keySet, Set("a", "f"))
        assertEquals(IntMap.get("a"), Some(1))
        assertEquals(IntMap.get("f"), Some(2))

        val DoubleMap = node.toMap[Double].filter(_._2.nonEmpty).map(v => v._1 -> v._2.get)
        println(DoubleMap)
        assertEquals(DoubleMap.keySet, Set("a", "f"))
        assertEquals(DoubleMap.get("a"), Some(1.0))
        assertEquals(DoubleMap.get("f"), Some(2.1))
    }

    @Test def testToSeq(): Unit = {
        val json = """[1, 2.1, "3", null]"""
        val node = parse(json)
        val IntSeq = node.toSeq[Int]
        println(IntSeq)
        assertEquals(IntSeq, Seq(Some(1), Some(2), None, None))

        val DoubleSeq = node.toSeq[Double]
        println(DoubleSeq)
        assertEquals(DoubleSeq, Seq(Some(1.0), Some(2.1), None, None))
    }

    @Test def testFind(): Unit = {
        val json = """{"a":{"b":{"c":[1,2,3,4]}}}"""
        val node = find(parse(json), List("a", "b", "c"))
        println(node)
        assertEquals(node.toSeq[Int], Seq(Some(1), Some(2), Some(3), Some(4)))
    }
}