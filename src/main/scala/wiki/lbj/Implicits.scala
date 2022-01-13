package wiki.lbj

object Implicits {
    implicit class DoubleMixin(double: Double) {
        def precision(n: Int = 2): Double = double.formatted(s"%.${n}f").toDouble
    }

    implicit class MapMixinOperation[K, V](map: Map[K, V]) {
        def gets[B](fields: Iterable[K]): Map[K, V] =
            fields.map(v => v -> map.get(v)).filter(_._2.nonEmpty).map { case (k, v) => k -> v.get }.toMap

        def gets[B](fields: Iterable[K], transfer: V => B): Map[K, B] =
            gets(fields).map { case (k, v) => k -> transfer(v) }
    }
}
