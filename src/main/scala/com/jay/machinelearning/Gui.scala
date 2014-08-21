package com.jay.machinelearning

import scala.util.Random._
import breeze.linalg.DenseMatrix
import java.awt.{Font, Color, Dimension, Graphics}
import java.awt.event.KeyEvent._
import com.jay.machinelearning.GraphicsImplicits._
import java.awt.event.{InputEvent, MouseAdapter, MouseEvent}
import scala.collection.mutable
import scala.util.Random
import java.lang.Math.abs

/**
 * @author jaycarey
 */
object app extends App {
  Gui.init()
}

object Gui extends BaseFrame {
  val cellSize = 20
  val colCount = 20
  val rowCount = 20
  val cols = 0 until colCount
  val rows = 0 until rowCount
  //  val cells = rows.map(row => cols.map(n => nextDouble()).toArray).toArray
  val transpose = Predictor.loadCsv("/X.csv").transpose
  var current = nextInt(transpose.size)
  var cells = mutable.Buffer(mutable.Buffer[Double]())

  var guess = -1

  def init() = {
    setSize(colCount * cellSize, rowCount * cellSize)
    setVisible(true)
    setCurrent(Random.nextInt(transpose.size))
    guessCurrent()
  }

  def setCurrent(index: Int) = {
    current = (index + transpose.size) % transpose.size
    cells = transpose(current).grouped(colCount).map(_.toBuffer).toBuffer
    setTitle(s"Example $current")
    guessCurrent()
    repaint()
  }

  private def guessCurrent() = {
    val flatten = cells.flatten
    guess = (Predictor.predict(DenseMatrix.create(1, rowCount * colCount, flatten.toArray))(0) + 1).toInt % 10
    repaint()
  }

  override def render(g: Graphics)(implicit dimensions: Dimension) = if (cells.size > 1) {
    g.clear(Color.WHITE)
    rows.foreach(row => cols.foreach(renderCell(row, g)))
    g.color(Color.RED)
      .font(new Font("Menlo", Font.BOLD, 60))
      .text(guess.toString, 25, 100)
  }

  def renderCell(row: Int, graphics: Graphics)(cell: Int) = graphics
    .color(color(row, cell))
    .fillRect(row * cellSize, cell * cellSize, cellSize, cellSize)


  def color(row: Int, cell: Int) = {
    val value = 255 - Math.max(Math.min((cells(row)(cell) * 255).toInt, 255), 0)
    new Color(value, value, value)
  }

  override val keyHandlers: PartialFunction[Int, Unit] = {
    case VK_RIGHT => setCurrent(current + 1)
    case VK_LEFT => setCurrent(current - 1)
    case VK_R => setCurrent(nextInt(transpose.size))
    case VK_C => cells = rows.map(r => cols.map(c => 0.0).toBuffer).toBuffer
      guessCurrent()
      repaint()
  }

  addMouseMotionListener(new MouseAdapter {
    var mouseX = -1
    var mouseY = -1

    override def mouseDragged(e: MouseEvent): Unit = {
      val newMouseX = e.getX / colCount
      val newMouseY = e.getY / rowCount
      if (newMouseX != mouseX || newMouseY != mouseY) {
        mouseX = newMouseX
        mouseY = newMouseY
        val adjust = if ((e.getModifiers & InputEvent.CTRL_MASK) != 0) -0.01 else 0.01
        val n = 2
        (-n to n).filter(r => (mouseY + r) >= 0 && (mouseY + r) < rowCount).foreach(r =>
          (-n to n).filter(c => (mouseX + c) >= 0 && (mouseX + c) < colCount).foreach(c =>
          cells(mouseX + c)(mouseY + r) += Math.min(0.0001 * adjust * Math.pow(((n-abs(r)) * (n-abs(r)) + (n-abs(c)) * (n-abs(c))) * 100, n), 1)
        ))
        guessCurrent()
        repaint()
      }
    }
  })
}
