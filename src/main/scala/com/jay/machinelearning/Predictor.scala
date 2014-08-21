package com.jay.machinelearning

import breeze.linalg.{*, DenseVector, DenseMatrix}
import breeze.io.CSVReader
import java.io.InputStreamReader
import breeze.numerics

/**
 * @author jaycarey
 */
object Predictor {

  var Theta1 = load("/Theta1.csv")
  var Theta2 = load("/Theta2.csv")

  def load(s: String): DenseMatrix[Double] = {
    val map = loadCsv(s)
    new DenseMatrix(map.head.size, map.flatten)
  }

  def loadCsv(s: String): Array[Array[Double]] = {
    val data = CSVReader.iterator(new InputStreamReader(getClass.getResourceAsStream(s))).toArray
    val map = data.map(_.map(java.lang.Double.parseDouble).toArray).transpose
    map
  }

  def exp(matrix: DenseMatrix[Double]): DenseMatrix[Double] =
    numerics.exp(matrix)

  def sigmoid(z: DenseMatrix[Double]): DenseMatrix[Double] = {
    val value: DenseMatrix[Double] = exp(-z) + 1.0
    value.map(1 / _)
  }

  def predict(X: DenseMatrix[Double]): DenseVector[Double] =
    predict(Theta1, Theta2, X)

  def predict(Theta1: DenseMatrix[Double], Theta2: DenseMatrix[Double], X: DenseMatrix[Double]): DenseVector[Double] = {
    val m = X.rows
    val XplusOnes = DenseMatrix.horzcat(DenseMatrix.ones[Double](m, 1), X)
    val A1: DenseMatrix[Double] = sigmoid(XplusOnes * Theta1.t)
    val A1plusOnes = DenseMatrix.horzcat(DenseMatrix.ones[Double](m, 1), A1)
    val A2: DenseMatrix[Double] = A1plusOnes * Theta2.t
    maxIndex(A2(*, ::))
  }
}
