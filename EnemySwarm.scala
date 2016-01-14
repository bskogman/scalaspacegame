package cs1321.game

import java.awt.Graphics2D
import scala.util.Random
import cs1321.util.Vec2
import java.io.File
import javax.imageio.ImageIO
import scala.collection.mutable.Buffer

/** contains the control and logic to present a coordinated set of Enemy objects. For now, this class
 *  generates a "grid" of enemy objects centered near the top of the screen.
 *  
 *  @param nRows - number of rows of enemy objects
 *  @param nCols - number of columns of enemy objects
 */
class EnemySwarm(val nRows:Int, val nCols:Int) extends ShootsBullets {
	
  val alienImage = new File("src/cs1321/game/rufus.png")
  val aImg = ImageIO.read(alienImage)
  val bulletImage = new File("src/cs1321/game/nachos.png")
  val enemyBul = ImageIO.read(bulletImage)
  var es = Buffer[Enemy]()
  for(i <- 0 until nRows) {
	  for(j <- 0 until nCols) {
	    es += new Enemy(aImg, new Vec2(i*(aImg.getWidth+25), j*(aImg.getHeight+25)), enemyBul)
	  }
	}
	/** method to display all Enemy objects contained within this EnemySwarm
	 * 
	 *  @param g - the Graphics2D context to draw into
	 *  @return none/Unit
	 */
	def display(g:Graphics2D) {
	  for(e <- es){
	    e.display(g)
	  }
	}
  
  /** overridden method of ShootsBullets. Creates a single, new bullet instance originating from a
   *  random enemy in the swarm. (Not a bullet from every object, just a single from a random enemy)
   *  
   *  @return Bullet - the newly created Bullet object fired from the swarm
   */
  def shoot():Bullet = {
    es(Random.nextInt(es.length)).shoot
  }
  
}