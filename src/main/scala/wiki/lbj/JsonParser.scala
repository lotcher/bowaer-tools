package wiki.lbj

import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.{ClassTagExtensions, DefaultScalaModule}

import scala.annotation.tailrec

object JsonParser {
    val mapper = new ObjectMapper() with ClassTagExtensions
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    def parse(json: String): JsonNode = mapper.readTree(json)

    @tailrec
    def find(jsonNode: JsonNode, pathFields: List[String], funcName: String = PATH): JsonNode = {
        if (pathFields.isEmpty) jsonNode
        else find(
            if (funcName == GET) jsonNode.get(pathFields.head) else jsonNode.path(pathFields.head),
            pathFields.tail
        )
    }

    implicit class JsonNodeToScala(jsonNode: JsonNode) {

        import scala.jdk.CollectionConverters._
        import scala.reflect.{ClassTag, classTag}

        def toSeq[T: ClassTag]: Seq[Option[T]] = toIterator.toSeq

        def toList[T: ClassTag]: List[Option[T]] = toIterator.toList

        def toArray[T: ClassTag]: Array[Option[T]] = toIterator.toArray

        def toMap[T: ClassTag]: Map[String, Option[T]] = jsonNode.fields.asScala.map(field => (field.getKey, field.getValue.toScala[T])).toMap

        def toIterator[T: ClassTag]: Iterator[Option[T]] = jsonNode.elements.asScala.map(_.toScala[T])

        def toScala[T: ClassTag]: Option[T] = (this.jsonNode match {
            case v if classTag.runtimeClass == classOf[JsonNode] => v
            case v if classTag.runtimeClass == classOf[String] => v.asText
            case v if v.isBoolean => v.asBoolean
            case v if v.isNumber =>
                (v.asDouble, classTag.runtimeClass) match {
                    case (v, c) if c == classOf[Int] => v.toInt
                    case (v, c) if c == classOf[Long] => v.toLong
                    case (v, _) => v: Any //Scala2中不可省略Any，https://stackoverflow.com/questions/68555400/invalid-when-i-convert-a-data-type-in-a-generic-function-toint-is-called-but-t/68557273#68557273
                }
            case jsonNode => jsonNode
        }) match {
            case v: T => Some(v)
            case _ => None
        }
    }

    final val GET = "get"
    final val PATH = "path"
}