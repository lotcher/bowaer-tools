package wiki.lbj

object Implicits {
    implicit class DoubleMixin(double: Double) {
        def precision(n: Int = 0): Double = double.formatted(s"%.${n}f").toDouble

        def precision: Double = double.precision()
    }

    implicit class MapMixinOperation[K, V](map: Map[K, V]) {
        def gets[B](fields: Iterable[K]): Iterable[V] =
            fields.map(map.get).filter(_.nonEmpty).map(_.get)

        def gets[B](fields: Iterable[K], transfer: V => B): Iterable[B] =
            gets(fields).map(transfer)

        def sub[B](fields: Iterable[K]): Map[K, V] =
            fields.map(v => v -> map.get(v)).filter(_._2.nonEmpty).map { case (k, v) => k -> v.get }.toMap

        def sub[B](fields: Iterable[K], transfer: V => B): Map[K, B] =
            sub(fields).map { case (k, v) => k -> transfer(v) }
    }
}
