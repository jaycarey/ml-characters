package com.jay.machinelearning

import java.awt.BasicStroke.CAP_BUTT
import java.awt.BasicStroke.JOIN_MITER
import java.awt._
import java.awt.geom.GeneralPath
import java.awt.RenderingHints._

/**
 * @author jaycarey
 */
object GraphicsImplicits {

  implicit def graphics2WrappedGraphics(graphics: Graphics): WrappedGraphics =
    new WrappedGraphics(graphics.asInstanceOf[Graphics2D])

  implicit def wrappedGraphics2Graphics(wrappedGraphics: WrappedGraphics): Graphics =
    wrappedGraphics.graphics

  implicit def graphics2Graphics2D(graphics: Graphics): Graphics2D =
    graphics.asInstanceOf[Graphics2D]

  class WrappedGraphics(val graphics: Graphics2D) {

    object fluentSyntax {
      implicit def fluentSyntax1(void: Unit): Graphics2D = graphics

      implicit def fluentSyntax2(void: Unit): WrappedGraphics = WrappedGraphics.this
    }

    import fluentSyntax._

    def returnThis[T](what: T = this): T = what

    def heart(x: Int, y: Int, size: Int) = graphics
      .draw(heartPolyLine(x, y, size))


    def heartPolyLine(x: Int, y: Int, size: Int) = {
      val polyline = new GeneralPath
      polyline.moveTo(x, y)
      polyline.curveTo(x, y - size / 6 * 2, x + size, y - size / 6, x, y + size / 6 * 4)
      polyline.moveTo(x, y)
      polyline.curveTo(x, y - size / 6 * 2, x - size, y - size / 6, x, y + size / 6 * 4)
      polyline
    }

    def fontMetrics = graphics
      .getFontMetrics(graphics.getFont)

    def centeredText(text: String, x: Int, y: Int) = graphics
      .drawString(text, x - fontMetrics.stringWidth(text) / 2, y - fontMetrics.getHeight / 20)
      .returnThis(this)

    def text(text: String, x: Int, y: Int)(implicit size: Dimension) = graphics
      .drawString(text, x + 10, y - fontMetrics.getHeight / 20)
      .returnThis(this)

    def color(color: Color) = graphics
      .setColor(color)
      .returnThis(this)

    def font(font: Font) = graphics
      .setFont(font)
      .returnThis(this)

    def init()(implicit dimensions: Dimension) = graphics
      .setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
//      .translate(dimensions.width / 2, 0)
      .returnThis(this)

    def line(x1: Int, y1: Int, x2: Int, y2: Int) = graphics
      .drawLine(x1, y1, x2, y2)
      .returnThis(this)

    def clear(color: Color)(implicit dimensions: Dimension) = graphics
      .setColor(color)
      .fillRect(-dimensions.width / 2, 0, dimensions.width, dimensions.height)
      .returnThis(this)

    def stroke(width: Float) = graphics
      .setStroke(new BasicStroke(width))
      .returnThis(this)

    def dash(width: Float) = graphics
      .setStroke(new BasicStroke(width, CAP_BUTT, JOIN_MITER, 10.0f, Array[Float](width), 0.0f))
      .returnThis(this)
  }

  def pink(n: Int): Color = {
    new Color(255, 165 + n * 30, 165 + n * 30)
  }
}
