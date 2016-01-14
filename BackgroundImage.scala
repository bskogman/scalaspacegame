package cs1321.game

import java.awt.image.BufferedImage
import cs1321.util.Vec2

class BackgroundImage(val backImage:BufferedImage, val backPos:Vec2) extends Sprite(backImage, backPos) {

  def moveDownScreen(height:Int, speed:Vec2) {
    if(this.pos.y < height) {
      this.move(speed)
    }
    else {
      this.moveTo(new Vec2(0, 0 - this.backImage.getHeight))
      moveDownScreen(height, speed)
    }
  }
  
}