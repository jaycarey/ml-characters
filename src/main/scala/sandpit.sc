import breeze.io.CSVReader
import breeze.linalg.Matrix
import breeze.numerics.exp
import java.io.InputStreamReader
import breeze.linalg._
import breeze.linalg.operators._
import breeze.numerics._

val a = DenseMatrix((1.0, 2.0), (3.0, 4.0))
val b = DenseMatrix((5.0, 6.0), (7.0, 8.0))

val c = DenseMatrix.horzcat(a, b)

//(10.0 / exp(a) + 10.0)

//1.0 / (1.0 + -map)

