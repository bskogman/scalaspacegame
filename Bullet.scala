package cs1321.game

import cs1321.util.Vec2
import java.awt.image.BufferedImage
import java.awt.Color
import java.awt.Graphics2D
import scala.io.Source

/** Representation of a bullet/projective for a simple game based on sprites. Handles all information regarding
 *  a bullet's position, movements, and abilities.
 *  
 *  @param pic the image representing the bullet
 *  @param initPos the initial position of the '''center''' of the bullet
 *  @param vel the initial velocity of the bullet
 */
class Bullet(var pic:BufferedImage, var initPos:Vec2, var vel:Vec2) extends Sprite(pic, initPos) {
  def apply(g:Graphics2D){
    g.drawImage(pic, pic.getWidth, pic.getHeight, null)
  }
  /** advances the position of the Bullet over a single time step
   * 
   *  @return none/Unit
   */
	def timeStep() {
	  move(vel)
	}
  
}