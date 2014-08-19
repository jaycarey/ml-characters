import breeze.linalg.{DenseVector, DenseMatrix}
import breeze.numerics.{sin, cos}
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

class Excercise3Spec extends SpecificationWithJUnit {

  "Excercise3" should {

    "predict correctly using the coursera unit test" in new Scope {
      val X = DenseMatrix.create(2, 16, Array(1.0, 1.0, -1.0, -2.0, -2.0, -1.0, 2.0, 2.0, -1.0, -1.0, -1.0, 2.0, 2.0, 1.0, -2.0, -2.0, -1.0, -1.0, 1.0, 2.0, -2.0, -1.0, -2.0, -2.0, 1.0, -1.0, 1.0, -2.0, -2.0, 1.0, -2.0, 2)).t
      val Theta1 = cos(DenseMatrix.create(4, 3, Array(1.0, 3.0, 5.0, 7.0, 9.0, 11.0, 13.0, 15.0, 17.0, 19.0, 21.0, 23)))
      val Theta2 = sin(DenseMatrix.create(4, 5, Array(1.0, 3.0, 5.0, 7.0, 9.0, 11.0, 13.0, 15.0, 17.0, 19.0, 21.0, 23.0, 25.0, 27.0, 29.0, 31.0, 33.0, 35.0, 37.0, 39.0)))
      val prediction = Excercise3.predict(Theta1, Theta2, X)
      prediction mustEqual DenseVector[Double](1, 1, 1, 2, 1, 4, 2, 1, 1, 1, 1, 1, 1, 1, 4, 4)
    }

    "produce correct values for the sigmoid function" in new Scope {
      val expectedResult = Excercise3.parse(
        "[0.7310585786300049,0.8807970779778823,0.9525741268224334;0.9820137900379085,0.9933071490757153,0.9975273768433653]"
      )

      val actualResult = Excercise3.sigmoid(Excercise3.parse("[1,2,3;4,5,6]"))

      actualResult mustEqual expectedResult
    }

    "correctly parse octave style matrix definitions" in new Scope {
      val expectedMatrix = DenseMatrix.create(3, 2, Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)).t

      val actualMatrix = Excercise3.parse("[1,2,3;4,5,6]")

      expectedMatrix mustEqual actualMatrix
      actualMatrix.toString mustEqual "1.0  2.0  3.0  \n4.0  5.0  6.0  "
    }
  }
}