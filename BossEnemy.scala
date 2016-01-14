package cs1321.game

import java.awt.image.BufferedImage
import cs1321.util.Vec2
import javax.swing._
import scala.swing.Swing

class BossEnemy(val bossPic:BufferedImage, val bossInitPos:Vec2, val bossBulletPic:BufferedImage, var hits:Int) extends Enemy(bossPic, bossInitPos, bossBulletPic) {

  val moveLeftVec:Vec2 = new Vec2(-10, 0)
  val moveRightVec:Vec2 = new Vec2(10, 0)
  val moveDownVec:Vec2 = new Vec2(0, 10)
  val moveUpVec:Vec2 = new Vec2(0, -10)
  
  override def move(direction:Vec2) {
    if(this.pos.x + direction.x > (1200 - this.img.getWidth) || this.pos.x + direction.x < 0){
      if(this.pos.y + direction.y  > (600 - this.img.getHeight) || this.pos.y + direction.y < 0) {
  	    } 
      else {
  	    this.pos += new Vec2(0, direction.y)
      }
    }
    else if(this.pos.y + direction.y > (600 - this.img.getHeight) || this.pos.y + direction.y < 0) {
  	  this.pos += new Vec2(direction.x, 0)
  	}
  	
  	else {
  	  this.pos += direction
  	}
  	      
    
  }
  
  def moveLeft() {
    move(moveLeftVec)
  }
    
  def moveRight() {
    move(moveRightVec)
  }
  
  def moveUp() {
    move(moveUpVec)
  }
  
  def moveDown() {
    move(moveDownVec)
  }
  
  var left = false
  var right = false
  var up = false
  var down = false
  
  
  var isPresent:Boolean = false
  
  override def shoot():Bullet = {
    new Bullet(bossBulletPic, new Vec2(pos.x+(bossPic.getWidth/3), pos.y+pic.getHeight), new Vec2(0,15))
  }
  
  
  
}