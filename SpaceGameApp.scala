package cs1321.game

import scala.swing._

/** main object that initiates the execution of the game, including construction of the window
 */
object SpaceGameApp {
  
	
  
  
	def main(args:Array[String]) { 
	   
	  val frame = new MainFrame
	  val panel = new SpaceGamePanel {
	    preferredSize = new Dimension(width, height)
	  }
	  frame.contents = { 
      panel
    } 
	 
	  frame.visible = true
	  frame.title = "Matt Hibbs: Mole-Rat Detective"
	  
	  
	  panel.startGame 
	  panel.requestFocus
	}
}