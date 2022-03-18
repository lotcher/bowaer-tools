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

    implicit class SeqMixIn[T](seq: Seq[T]) {
        def get(i: Int, other: T): T = seq.applyOrElse(i, (_: Int) => other)

        def gets(indices: Seq[Int], other: T): Seq[T] = indices.map(i => get(i, other))

        def maxTrueRange(transfer: T => Boolean): (Int, Int) = {
            var ranges = (0, 0)
            var (maxCount, currentCount, start) = (0, 0, 0)
            seq.zipWithIndex.foreach {
                case (v, i) =>
                    if (transfer(v)) currentCount += 1
                    else {
                        if (currentCount > maxCount) {
                            maxCount = currentCount
                            ranges = (start, i - 1)
                        }
                        currentCount = 0
                        start = i + 1
                    }
            }
            if (currentCount > maxCount) ranges = (start, seq.length - 1)
            ranges
        }

        def filter(isFilters: Seq[Boolean]): Seq[T] = seq.zip(isFilters).filter(_._2).map(_._1)
    }

    implicit class DoubleSeqMixin(seq: Seq[Double]) {
        def mean: Double = seq.sum / seq.length

        def std: Double = {
            val m = mean
            math.sqrt(seq.map(x => math.pow(x - m, 2)).sum / seq.length)
        }

        // 差分
        def difference: Seq[Double] = seq.sliding(2).map(s => s.last - s.head).toSeq

        //欧氏距离
        def distance(other: Seq[Double]): Double = math.sqrt(seq.zip(other).map(x => math.pow(x._1 - x._2, 2)).sum)

        def quantiles(qs: Double*): Seq[Double] = {
            val sortedValues = seq.toArray.sorted
            qs.map(q => {
                val i = (seq.size - 1) * q
                val (index, deviation) = (i.toInt, i % 1)
                sortedValues(index) + (sortedValues(index + 1) - sortedValues(index)) * deviation
            })
        }
    }
}
