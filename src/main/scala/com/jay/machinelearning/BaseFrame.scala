package com.jay.machinelearning

import javax.swing.JFrame
import java.awt.event._
import java.awt.{Dimension, Color, Graphics}
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage._
import javax.imageio.ImageIO
import java.io.File
import com.jay.machinelearning.GraphicsImplicits._

abstract class BaseFrame() extends JFrame {

  var bufferImage: BufferedImage = null

  addKeyListener(new KeyAdapter {
    override def keyReleased(e: KeyEvent) = keyHandlers.applyOrElse(e.getKeyCode, Console.print)
  })

  addComponentListener(new ComponentAdapter {
    override def componentResized(e: ComponentEvent) = bufferImage = new BufferedImage(getWidth, getHeight, TYPE_INT_ARGB)
  })

  addMouseListener(new MouseAdapter {
    override def mouseClicked(e: MouseEvent) = print()
  })

  def print() {
    implicit val size = new Dimension(getWidth * 2, getHeight * 2)
    val image = new BufferedImage(size.width, size.height, TYPE_INT_ARGB)
    render(image.getGraphics.init().clear(Color.WHITE))
    ImageIO.write(image, "png", new File(getTitle + ".png"))
  }

  override def paint(g: Graphics): Unit = if (bufferImage != null) {
    implicit val dimensions = getSize
    val bufferGraphics = bufferImage.getGraphics.init().clear(Color.WHITE)
    render(bufferGraphics)
    g.drawImage(bufferImage, 0, 0, this)
  }

  def render(g: Graphics)(implicit dimensions: Dimension)

  val keyHandlers: PartialFunction[Int, Unit] = {
    case _ =>
  }
}
