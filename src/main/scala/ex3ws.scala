import breeze.io.CSVReader
import breeze.numerics
import java.io.InputStreamReader
import breeze.linalg._
import breeze.linalg.operators._
import java.util.Random


object Excercise3 extends App {
  def parse(octaveString: String): DenseMatrix[Double] = {
    val ignore = Seq('[', ']', ' ')
    val filtered = octaveString.filterNot(ignore.contains)
    val data = filtered.split(';').map(_.split(',').map(_.toDouble))
    DenseMatrix.create(data(0).size, data.size, data.flatten).t
  }

  val random = new Random

  //// Setup the parameters you will use for this exercise
  val input_layer_size = 400
  // 20x20 Input Images of Digits
  val hidden_layer_size = 25
  // 25 hidden units
  val num_labels = 10; // 10 labels, from 1 to 10
  // (note that we have mapped "0" to label 10)

  println("Loading and Visualizing Data ...")

  println(load("/test.csv"))

  def load(s: String): DenseMatrix[Double] = {
    val data = CSVReader.iterator(new InputStreamReader(getClass.getResourceAsStream(s))).toArray
    val map = data.map(_.map(java.lang.Double.parseDouble).toArray).transpose.flatten
    new DenseMatrix(data.size, map)
  }

  val X = load("/X.csv")

  val m = X.rows

  displayData((1 to 100).map(n => X(n, ::)): _*)

  println("Loading Saved Neural Network Parameters ...")

  // Load the weights into variables Theta1 and Theta2
  val Theta1 = load("/Theta1.csv")
  val Theta2 = load("/Theta2.csv")

  def exp(matrix: DenseMatrix[Double]): DenseMatrix[Double] = numerics.exp(matrix)

  def sigmoid(z: DenseMatrix[Double]): DenseMatrix[Double] = {
    val value: DenseMatrix[Double] = exp(-z) + 1.0
    value.map(1 / _)
  }

  def predict(Theta1: DenseMatrix[Double], Theta2: DenseMatrix[Double], X: DenseMatrix[Double]): DenseVector[Double] = {
    val m = X.rows
    val XplusOnes = DenseMatrix.horzcat(DenseMatrix.ones[Double](m, 1), X)
    val A1: DenseMatrix[Double] = sigmoid(XplusOnes * Theta1.t)
    val A1plusOnes = DenseMatrix.horzcat(DenseMatrix.ones[Double](m, 1), A1)

    val A2: DenseMatrix[Double] = A1plusOnes * Theta2.t
    maxIndex(A2(*, ::))
  }


  def size(A1: DenseMatrix[Double]) {
    println(s"${A1.rows} x ${A1.cols}")
  }

  val pred = predict(Theta1, Theta2, X)

  println(s"Prediction: $pred")

  //  println("\nTraining Set Accuracy: //f\n", mean(double(pred == y)) * 100)
  //  Randomly permute examples

  while (false) {
    val n = random.nextInt(m)

    // Display
    println("Displaying Example Image\n")
    displayData(X(n, ::))

    //    val pred = predict(Theta1, Theta2, X(n, ::).toDenseMatrix)
    //    println("\nNeural Network Prediction: //d (digit //d)\n", pred, mod(pred, 10))

    // Pause
    println("Program paused. Press enter to continue.\n")
    Console.readLine()
  }

  def displayData(d: Transpose[DenseVector[Double]]*) = {

  }
}