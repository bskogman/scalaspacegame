package cs1321.game

import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import scala.collection.mutable.Buffer
import scala.swing._
import javax.swing.Timer
import cs1321.util.Vec2


class Explosion(var place:Vec2) {
  var exCount = 0
  
  val explodeSheet = new File("src/cs1321/game/explosion_64x64.png")
  val expImg = ImageIO.read(explodeSheet)
  val explodeBuff = Buffer[BufferedImage]()
  for(row <- 0 to 320 by 64) {
    for(column <- 0 to 448 by 64) {
      explodeBuff += expImg.getSubimage(column, row, 64, 64)       
    }
  }
  
  var timeToRemove:Boolean = false
    

  def display(g:Graphics2D) {
    if(exCount == explodeBuff.length - 1) {
      timeToRemove = true
    }
    else {exCount += 1}
    if(exCount > 0) {
      g.drawImage(explodeBuff(exCount), place.x.toInt, place.y.toInt, null)
    }
    
  }

}