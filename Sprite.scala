package cs1321.game

import java.awt.image.BufferedImage
import cs1321.util.Vec2
import java.awt.Graphics2D
import scala.swing.Swing
import javax.imageio.ImageIO
import java.io.File
import scala.collection.mutable.Buffer
import javax.swing.Timer

/** A graphical sprite object used for gaming or other visual displays
 *  
 *  @constructor create a new sprite based on the given image and initial location
 *  @param img the image used to display this sprite
 *  @param pos the initial position of the '''center''' of the sprite in 2D space
 */
abstract class Sprite (var img:BufferedImage, var pos:Vec2) {

  
  val height = img.getHeight
  val width = img.getWidth
  val area = width * height
  
  
  
  /** moves the sprite a relative amount based on a specified vector
   *  
   *  @param direction - an offset that the position of the sprite should be moved by
   *  @return none/Unit
   */
  def move (direction:Vec2) {
    pos += direction
  }
  
  /** moves the sprite to a specific location specified by a vector (not a relative movement)
   *  
   *  @param location - the new location for the sprite's position
   *  @return none/Unit
   */
  def moveTo(location:Vec2) {
    pos = location
  }
  
  
  //tests if two Sprites have intersected areas
  def collide(other:Sprite):Boolean = {
    val thislowx = pos.x
    val thishighx = pos.x + width
    val thislowy = pos.y
    val thishighy = pos.y + height
    
    val otherlowx = other.pos.x
    val otherhighx = other.pos.x + other.width
    val otherlowy = other.pos.y
    val otherhighy = other.pos.y + other.height
    if((otherlowx < thishighx && otherhighx > thislowx) && (otherlowy < thishighy && otherhighy > thislowy)) {
      true
    }
    else false
  }
  
  /** Method to display the sprite at its current location in the specified Graphics2D context
   *  
   *  @param g - a Graphics2D object capable of drawing the sprite
   *  @return none/Unit
   */
  def display (g:Graphics2D) {
    g.drawImage(img, pos.x.toInt, pos.y.toInt, null)
  }
}