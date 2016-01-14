package cs1321.game

import cs1321.util.Vec2
import java.awt.image.BufferedImage

/** An enemy representation for a simple game based on sprites. Handles all information regarding the
 *  enemy's position, movements, and abilities.
 *  
 *  @param pic the image representing the enemy
 *  @param initPos the initial position of the '''center''' of the enemy
 *  @param bulletPic the image of the bullets fired by this enemy
 */
class Enemy(val pic:BufferedImage, val initPos:Vec2, val bulletPic:BufferedImage) extends Sprite(pic, initPos) with ShootsBullets {

  /** creates a new Bullet instance beginning from this Enemy, with an appropriate velocity
   * 
   *  @return Bullet - the newly created Bullet object that was fired
   */
  def shoot():Bullet = { 
   new Bullet(bulletPic, new Vec2(pos.x+(pic.getWidth/3), pos.y+pic.getHeight), new Vec2(0,10))
  }
 
}