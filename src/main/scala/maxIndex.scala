
import breeze.generic.UFunc
import breeze.macros.expand
import breeze.linalg.support.{CanTransformValues, CanMapValues, CanTraverseValues}
import breeze.linalg.support.CanTraverseValues.ValuesVisitor

object maxIndex extends UFunc {
  @expand
  implicit def reduce[T]
  (implicit iter: CanTraverseValues[T, Double]): Impl[T, Double] = new Impl[T, Double] {

    def apply(v: T): Double = {
      class SumVisitor extends ValuesVisitor[Double] {
        var max = Double.NegativeInfinity
        var index = -1

        def visit(a: Double): Unit = {
          throw new RuntimeException()
        }

        def zeros(numZero: Int, zeroValue: Double): Unit = {
          throw new RuntimeException()
        }

        override def visitArray(arr: Array[Double], offset: Int, length: Int, stride: Int): Unit = {
          var i = 0
          var off = offset
          while(i < length) {
            if (arr(off) > max) {
              max = arr(off)
              index = i
            }
            i += 1
            off += stride
          }
        }
      }

      val visit = new SumVisitor

      iter.traverse(v, visit)
      if(visit.index == -1) throw new IllegalArgumentException(s"No values in $v!")

      visit.index
    }

  }

  /**
   * Method for computing the max of the first length elements of an array. Arrays
   * of size 0 give Double.NegativeInfinity
   * @param arr
   * @param length
   * @return
   */
  def array(arr: Array[Double], length: Int) = {
    var accum = Double.NegativeInfinity
    var i = 0
    while(i < length) {
      accum = scala.math.max(arr(i), accum)
      i += 1
    }
    accum
  }
}