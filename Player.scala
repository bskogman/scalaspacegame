package cs1321.game

import cs1321.util.Vec2
import java.awt.image.BufferedImage

/** The player representation for a simple game based on sprites. Handles all information regarding the
 *  player's positions, movements, and abilities.
 *  
 *  @param avatar the image representing the player
 *  @param initPos the initial position of the '''center''' of the player
 *  @param bulletPic the image of the bullets fired by this player
 */
class Player(avatar:BufferedImage, initPos:Vec2, bulletPic:BufferedImage, var hits:Int) extends Sprite(avatar, initPos) with ShootsBullets {

  
  def moveLeft() {
    move(new Vec2(-10, 0))
  }
    
  def moveRight() {
    move(new Vec2(10,0))
  }
  
  def moveUp() {
    move(new Vec2(0, -10))
  }
  
  def moveDown() {
    move(new Vec2(0, 10))
  }
  
  var left = false
  var right = false
  var up = false
  var down = false
  
  
  
  def shoot():Bullet = {
    val speed = new Vec2(0,-10)
    new Bullet(bulletPic, new Vec2(pos.x+(avatar.getWidth/3), pos.y), speed)
  }
  
}