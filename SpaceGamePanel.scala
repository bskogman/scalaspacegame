package cs1321.game

import scala.swing.Panel
import java.awt.Graphics2D
import scala.swing.event._
import java.awt.Color
import scala.io.Source
import cs1321.util.Vec2
import java.awt.image.BufferedImage
import javax.imageio._
import java.io.File
import scala.swing.Swing
import scala.util.Random
import scala.collection.mutable.Buffer
import javax.swing.Timer
import cs1321.particle.RainbowBackground
import scala.collection.mutable.Queue
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import java.io.BufferedInputStream
import java.io.FileInputStream

class SpaceGamePanel extends Panel {
  
  val backgroundMusic = new BufferedInputStream(new FileInputStream(new File("src/cs1321/game/background.wav")))
  var backgroundM = AudioSystem.getAudioInputStream(backgroundMusic)
  var backgroundClip = AudioSystem.getClip()
  backgroundClip.open(backgroundM)
  
  val laserScream = new BufferedInputStream(new FileInputStream(new File("src/cs1321/game/Laser.wav")))
  var laserS = AudioSystem.getAudioInputStream(laserScream)
  var laserClip = AudioSystem.getClip()
  laserClip.open(laserS)
	
  val blarghSound = new BufferedInputStream(new FileInputStream(new File("src/cs1321/game/BLAH.wav")))
  var blarghS = AudioSystem.getAudioInputStream(blarghSound)
  var blahClip = AudioSystem.getClip()
  blahClip.open(blarghS)
  
  val blarghSound2 = new BufferedInputStream(new FileInputStream(new File("src/cs1321/game/BLAH.wav")))
  var blarghS2 = AudioSystem.getAudioInputStream(blarghSound2)
  var blahClip2 = AudioSystem.getClip()
  blahClip2.open(blarghS2)
  
  val netSwooshSound = new BufferedInputStream(new FileInputStream(new File("src/cs1321/game/swoosh.wav")))
  var netSwooshS = AudioSystem.getAudioInputStream(netSwooshSound)
  var swooshClip = AudioSystem.getClip()
  swooshClip.open(netSwooshS)
  
  val tadaSound = new BufferedInputStream(new FileInputStream(new File("src/cs1321/game/tada.wav")))
  var tadaS = AudioSystem.getAudioInputStream(tadaSound)
  var tadaClip = AudioSystem.getClip()
  tadaClip.open(tadaS)
  
  val tadaSound2 = new BufferedInputStream(new FileInputStream(new File("src/cs1321/game/tada.wav")))
  var tadaS2 = AudioSystem.getAudioInputStream(tadaSound2)
  var tadaClip2 = AudioSystem.getClip()
  tadaClip2.open(tadaS2)
  
  val width = 1200
  val height = 600
  
  //Images and info for the first player 
  val shipImage = new File("src/cs1321/game/mhibbs.png")
  val shipImageLeft = new File("src/cs1321/game/shoophibbsleft.png")
  val shipImageRight = new File("src/cs1321/game/shoophibbsright.png")
  val pImg = ImageIO.read(shipImage)
  val pImgL = ImageIO.read(shipImageLeft)
  val pImgR = ImageIO.read(shipImageRight)
  val playLoc = new Vec2((width - pImg.getWidth)/2, 500)
  val rocketImage = new File("src/cs1321/game/net.png")
  val playBul = ImageIO.read(rocketImage)
  var ship = new Player(pImg, playLoc, playBul, 0)
  var playLife = 3
  var score = 0
  var waveCount = 1
  val hit1Img = ImageIO.read(new File("src/cs1321/game/mhibbs1hit.png"))
  val hit2Img = ImageIO.read(new File("src/cs1321/game/mhibbs2hit.png"))
  val hit3Img = ImageIO.read(new File("src/cs1321/game/mhibbs3hit.png"))
  
  
  //Images and info for player 2
  val ship2Image = ImageIO.read(new File("src/cs1321/game/mhibbs2.png"))
  val ship2ImageL = ImageIO.read(new File("src/cs1321/game/mhibbs2left.png"))
  val ship2ImageR = ImageIO.read(new File("src/cs1321/game/mhibbs2right.png"))
  val ship2hit1Img = ImageIO.read(new File("src/cs1321/game/mhibbs21hit.png"))
  val ship2hit2Img = ImageIO.read(new File("src/cs1321/game/mhibbs22hit.png"))
  val ship2hit3Img = ImageIO.read(new File("src/cs1321/game/mhibbs23hit.png"))
  var play2Life = 3
  var ship2:Player = null
  val ship2Loc = new Vec2(width/3, 3*(height/4))
  
  //Images and info for the laser
  val laserImg = ImageIO.read(new File("src/cs1321/game/laser.png"))
  var laser:MegaDeathLaser = null
  var timetoFire:Boolean = false
  var laserCount:Int = 0
  var laserTimeCount = 1000
  
  var laser2:MegaDeathLaser = null
  var laser2Count:Int = 0
  var laser2TimeCount = 1000
  
  def changePlayerImg() {
    if(ship.hits == 0) {
      ship.img = pImg
    }
    if(ship.hits == 1) {
      ship.img = hit1Img
    }
    if(ship.hits == 2) {
      ship.img = hit2Img
    }
    if(ship.hits == 3) {
      ship.img = hit3Img
    }
  }
  
  def changePlayer2Img() {
    if(ship2.hits == 0) {
      ship2.img = ship2Image
    }
    if(ship2.hits == 1) {
      ship2.img = ship2hit1Img
    }
    if(ship2.hits == 2) {
      ship2.img = ship2hit2Img
    }
    if(ship2.hits == 3) {
      ship2.img = ship2hit3Img
    }
  }
  
  //Info for the boss
  val bossImage = new File("src/cs1321/game/rufus_3000.png")
  val bImg = ImageIO.read(bossImage)
  val bossBulImage = new File("src/cs1321/game/tacos-clip-art.png")
  val bBulImg = ImageIO.read(bossBulImage)
  val bossLoc = new Vec2((width + 20) + bImg.getWidth, height/2)
  val boss = new BossEnemy(bImg, bossLoc, bBulImg, 0)
  val boss1hit = ImageIO.read(new File("src/cs1321/game/boss1hit.png"))
  val boss2hit = ImageIO.read(new File("src/cs1321/game/boss2hit.png"))
  val boss3hit = ImageIO.read(new File("src/cs1321/game/boss3hit.png"))
  val boss4hit = ImageIO.read(new File("src/cs1321/game/boss4hit.png"))
  val boss5hit = ImageIO.read(new File("src/cs1321/game/boss5hit.png"))
  
  def changeBossImg() {
    if(boss.hits == 0) {
      boss.img = bImg
    }
    if(boss.hits == 1) {
      boss.img = boss1hit
    }
    if(boss.hits == 2) {
      boss.img = boss2hit
    }
    if(boss.hits == 3) {
      boss.img = boss3hit
    }
    if(boss.hits == 4) {
      boss.img = boss4hit
    }
    if(boss.hits == 5) {
      boss.img = boss5hit
    }
  }
  
  var bossMoveQue = Queue[Vec2]()
  
  
  //Info for the swarm
  var swarm = new EnemySwarm(8, 2)
  val swwidth = swarm.nCols * (swarm.aImg.getWidth+45)
  for(en <- swarm.es) {
    en.move(new Vec2((width-swwidth)/3, 10))
  }
  
  //creates a new swarm
  def newSwarm {
    if(boss.isPresent == false) {
      swarmMoveTimer.stop
      val newswarm = new EnemySwarm(8, 2)
      val swwidth = newswarm.nCols * (newswarm.aImg.getWidth+45)
      for(en <- newswarm.es) {
        en.move(new Vec2((width-swwidth)/3, 10))
      }
      swarm = newswarm
      waveCount += 1
      
      moveCount = 0
      swarmMoveTimer.start
    }
  }
  
  var playbullets = Buffer[Bullet]()
  var play2bullets = Buffer[Bullet]()
  var swarmbullets = Buffer[Bullet]()
  var bossbullets = Buffer[Bullet]()
  var explosions = Buffer[Explosion]()
  var timeToShootQue = Queue[Boolean]()
  
  //removes bullets as they leave
	def bulletRemover {
    var bcount = 0
    while(bcount < playbullets.length) {
	    if(playbullets(bcount).pos.y < 0 || playbullets(bcount).pos.y > height) {
	      playbullets.remove(bcount)
	    }
      else bcount += 1
	  }
    bcount = 0
    while(bcount < swarmbullets.length) {
	    if(swarmbullets(bcount).pos.y < 0 || swarmbullets(bcount).pos.y > height) {
	      swarmbullets.remove(bcount)
	    }
      else bcount += 1
	  }
  }

  //removes things as they collide
  def spriteRemover {
    
    //removes enemies hit by player bullets
    var swarmRemoveCount = 0
    var playbulRemoveCount = 0
    while(swarmRemoveCount < swarm.es.length){
      playbulRemoveCount = 0
	      while(playbulRemoveCount < playbullets.length){ 
	        if(!swarm.es.isEmpty && swarmRemoveCount >= 0 && swarm.es(swarmRemoveCount).collide(playbullets(playbulRemoveCount)) == true){
	          playbullets.remove(playbulRemoveCount)
            swooshClip.start
	          explosions += new Explosion(swarm.es(swarmRemoveCount).pos)
	          swarm.es.remove(swarmRemoveCount)
	          score += 15
	          swarmRemoveCount -= 1
	        }
	        else{
	          playbulRemoveCount += 1
	        }
	      }
      swarmRemoveCount += 1
	    }
    swooshClip.setFramePosition(0)
    
    //removes enemies hit by player 2 bullets
    if(ship2 != null) {
    var swarmRemoveCount2 = 0
    var playbulRemoveCount2 = 0
    while(swarmRemoveCount2 < swarm.es.length){
      playbulRemoveCount2 = 0
        while(playbulRemoveCount2 < play2bullets.length){ 
          if(!swarm.es.isEmpty && swarmRemoveCount2 >= 0 && swarm.es(swarmRemoveCount2).collide(play2bullets(playbulRemoveCount2)) == true){
            play2bullets.remove(playbulRemoveCount2)
            swooshClip.start
            explosions += new Explosion(swarm.es(swarmRemoveCount2).pos)
            swarm.es.remove(swarmRemoveCount2)
            score += 15
            swarmRemoveCount2 -= 1
          }
          else{
            playbulRemoveCount2 += 1
          }
        }
      swarmRemoveCount2 += 1
      }
    }
    swooshClip.setFramePosition(0)
    
    //resets the player if hit by a bullet
    var enBulRemoveCount = 0
    while(enBulRemoveCount < swarmbullets.length) {
      if(ship.collide(swarmbullets(enBulRemoveCount)) == true) {
        if(ship.hits >= 3) {
          explosions += new Explosion(ship.pos)
          ship.moveTo(playLoc)
          playLife -= 1
          swarmbullets.remove(enBulRemoveCount)
          ship.hits = 0
          changePlayerImg
        }
        
        else {
          ship.hits += 1
          changePlayerImg
          swarmbullets.remove(enBulRemoveCount)
        }
      }
      else {
        enBulRemoveCount += 1
      }
    }
    
    //resets the player 2 if hit by a bullet
    if(ship2 != null) {
    var enBulRemoveCount2 = 0
    while(enBulRemoveCount2 < swarmbullets.length) {
      if(ship2.collide(swarmbullets(enBulRemoveCount2)) == true) {
        if(ship2.hits >= 3) {
          explosions += new Explosion(ship2.pos)
          ship2.moveTo(ship2Loc)
          play2Life -= 1
          swarmbullets.remove(enBulRemoveCount2)
          ship2.hits = 0
          changePlayerImg
        }
        
        else {
          ship2.hits += 1
          changePlayer2Img
          swarmbullets.remove(enBulRemoveCount2)
        }
      }
      else {
        enBulRemoveCount2 += 1
      }
    }
    }
    
    //removes bullets that hit each other
    var playbulInterCount = 0
    var enbulInterCount = 0
    while(playbulInterCount < playbullets.length) {
      enbulInterCount = 0
      while(enbulInterCount < swarmbullets.length) {
        if(playbulInterCount >=0 && playbullets(playbulInterCount).collide(swarmbullets(enbulInterCount)) == true) {
          playbullets.remove(playbulInterCount)
          swarmbullets.remove(enbulInterCount)
          playbulInterCount -= 1
        }
        else{
          enbulInterCount += 1
        }
      }
      playbulInterCount += 1
    }
    
    //removes bullets that hit each other
    if(ship2 != null) {
    var playbulInterCount2 = 0
    var enbulInterCount2 = 0
    while(playbulInterCount2 < play2bullets.length) {
      enbulInterCount2 = 0
      while(enbulInterCount2 < swarmbullets.length) {
        if(playbulInterCount2 >=0 && play2bullets(playbulInterCount2).collide(swarmbullets(enbulInterCount2)) == true) {
          play2bullets.remove(playbulInterCount2)
          swarmbullets.remove(enbulInterCount2)
          playbulInterCount2 -= 1
        }
        else{
          enbulInterCount2 += 1
        }
      }
      playbulInterCount2 += 1
    }
    }
    
    //removes boss and player bullets that hit each other
    var shipbulInterCount = 0
    var bossbulInterCount = 0
    while(shipbulInterCount < playbullets.length) {
      bossbulInterCount = 0
      while(bossbulInterCount < bossbullets.length) {
        if(shipbulInterCount >=0 && playbullets(shipbulInterCount).collide(bossbullets(bossbulInterCount)) == true) {
          playbullets.remove(shipbulInterCount)
          bossbullets.remove(bossbulInterCount)
          shipbulInterCount -= 1
        }
        else{
          bossbulInterCount += 1
        }
      }
      shipbulInterCount += 1
    }
    
    //removes boss and player2 bullets that hit each other
    if(ship2 != null) {
    var shipbulInterCount2 = 0
    var bossbulInterCount2 = 0
    while(shipbulInterCount2 < play2bullets.length) {
      bossbulInterCount2 = 0
      while(bossbulInterCount2 < bossbullets.length) {
        if(shipbulInterCount2 >=0 && play2bullets(shipbulInterCount2).collide(bossbullets(bossbulInterCount2)) == true) {
          play2bullets.remove(shipbulInterCount2)
          bossbullets.remove(bossbulInterCount2)
          shipbulInterCount2 -= 1
        }
        else{
          bossbulInterCount2 += 1
        }
      }
      shipbulInterCount2 += 1
    }
    }
    
    //resets the player if hits an enemy
    var enSpriteRemoveCount = 0
    while(enSpriteRemoveCount < swarm.es.length) {
      if(ship.collide(swarm.es(enSpriteRemoveCount)) == true) {
        if(ship.hits >= 3) {
          explosions += new Explosion(ship.pos)
          ship.moveTo(playLoc)
          playLife -= 1
          explosions += new Explosion(swarm.es(enSpriteRemoveCount).pos)
          swarm.es.remove(enSpriteRemoveCount)
          ship.hits = 0
          changePlayerImg
        }
        
        else {
          ship.hits += 1
          changePlayerImg
          swarm.es.remove(enSpriteRemoveCount)
        }
      }
      else {
        enSpriteRemoveCount += 1
      }
    }
    
    //resets the player 2 if hits an enemy
    if(ship2 != null) {
    var enSpriteRemoveCount2 = 0
    while(enSpriteRemoveCount2 < swarm.es.length) {
      if(ship2.collide(swarm.es(enSpriteRemoveCount2)) == true) {
        if(ship2.hits >= 3) {
          explosions += new Explosion(ship2.pos)
          ship2.moveTo(ship2Loc)
          play2Life -= 1
          explosions += new Explosion(swarm.es(enSpriteRemoveCount2).pos)
          swarm.es.remove(enSpriteRemoveCount2)
          ship2.hits = 0
          changePlayer2Img
        }
        
        else {
          ship2.hits += 1
          changePlayer2Img
          swarm.es.remove(enSpriteRemoveCount2)
        }
      }
      else {
        enSpriteRemoveCount2 += 1
      }
    }
    }
    
    
    
    //removes the boss once its lives deplete from getting hit by bullets
    var bossRemoveCount = 0
    while(bossRemoveCount < playbullets.length) {
      if(boss.collide(playbullets(bossRemoveCount))) {
        boss.hits += 1
        changeBossImg
        score += 5
        if(boss.hits > 5) {
          explosions += new Explosion(new Vec2(boss.pos.x + (boss.img.getWidth/3), boss.pos.y + (boss.img.getHeight/2)))
          boss.moveTo(bossLoc)
          boss.isPresent = false
          boss.hits = 0
          changeBossImg
          score += 50
        }
        playbullets.remove(bossRemoveCount)
      }
      else {
        bossRemoveCount += 1
      }
    }
    
    //removes the boss once its lives deplete from getting hit by bullets
    if(ship2 != null) {
    var bossRemoveCount2 = 0
    while(bossRemoveCount2 < play2bullets.length) {
      if(boss.collide(play2bullets(bossRemoveCount2))) {
        boss.hits += 1
        changeBossImg
        score += 5
        if(boss.hits > 5) {
          explosions += new Explosion(new Vec2(boss.pos.x + (boss.img.getWidth/3), boss.pos.y + (boss.img.getHeight/2)))
          boss.moveTo(bossLoc)
          boss.isPresent = false
          boss.hits = 0
          changeBossImg
          score += 50
        }
        play2bullets.remove(bossRemoveCount2)
      }
      else {
        bossRemoveCount2 += 1
      }
    }
    }
    
    //removes enemies that are under the boss when it spawns
    var bossEnemyInterCount = 0
    while(bossEnemyInterCount < swarm.es.length) {
      if(boss.collide(swarm.es(bossEnemyInterCount))) {
        swarm.es.remove(bossEnemyInterCount)
      }
      else{
        bossEnemyInterCount += 1
      }
    }
    
    //damages boss and player upon intersection
    if(boss.collide(ship)) {
      if(ship.hits < 3) {
        ship.hits += 1
        changePlayerImg
      }
      if(ship.hits >= 3) {
        explosions += new Explosion(ship.pos)
        ship.moveTo(playLoc)
        playLife -= 1
        ship.hits = 0
        changePlayerImg
      }
      boss.hits += 1
      changeBossImg
      score += 5
      if(boss.hits >= 5) {
          explosions += new Explosion(new Vec2(boss.pos.x + (boss.img.getWidth/3), boss.pos.y + (boss.img.getHeight/2)))
          boss.moveTo(bossLoc)
          boss.isPresent = false
          boss.hits = 0
          changeBossImg
          score += 50
        }
    }
    
    //damages boss and player 2 upon intersection
    if(ship2 != null) {
    if(boss.collide(ship2)) {
      if(ship2.hits < 3) {
        ship2.hits += 1
        changePlayer2Img
      }
      if(ship2.hits >= 3) {
        explosions += new Explosion(ship2.pos)
        ship2.moveTo(ship2Loc)
        play2Life -= 1
        ship2.hits = 0
        changePlayer2Img
      }
      boss.hits += 1
      changeBossImg
      score += 5
      if(boss.hits >= 5) {
          explosions += new Explosion(new Vec2(boss.pos.x + (boss.img.getWidth/3), boss.pos.y + (boss.img.getHeight/2)))
          boss.moveTo(bossLoc)
          boss.isPresent = false
          boss.hits = 0
          changeBossImg
          score += 50
        }
    }
    }
    
    //checks boss bullets hitting the player
    var bossBulCounter = 0
    while(bossBulCounter < bossbullets.length) {
      if(ship.collide(bossbullets(bossBulCounter)) == true) {
        if(ship.hits >= 3) {
          explosions += new Explosion(ship.pos)
          ship.moveTo(playLoc)
          playLife -= 1
          bossbullets.remove(bossBulCounter)
          ship.hits = 0
          changePlayerImg
        }
        
        else {
          ship.hits += 1
          changePlayerImg
          bossbullets.remove(bossBulCounter)
        }
      }
      else {
        bossBulCounter += 1
      }
    }
    
    //checks boss bullets hitting the player
    if(ship2 != null) {
    var bossBulCounter2 = 0
    while(bossBulCounter2 < bossbullets.length) {
      if(ship2.collide(bossbullets(bossBulCounter2)) == true) {
        if(ship2.hits >= 3) {
          explosions += new Explosion(ship2.pos)
          ship2.moveTo(ship2Loc)
          play2Life -= 1
          bossbullets.remove(bossBulCounter2)
          ship2.hits = 0
          changePlayer2Img
        }
        
        else {
          ship2.hits += 1
          changePlayer2Img
          bossbullets.remove(bossBulCounter2)
        }
      }
      else {
        bossBulCounter2 += 1
      }
    }
    }
    
    //does damage from laser to enemy swarm
    var enSwarmLasCount = 0
    while(enSwarmLasCount < swarm.es.length) {
      if(laser != null && laser.collide(swarm.es(enSwarmLasCount)) == true) {
        explosions += new Explosion(swarm.es(enSwarmLasCount).pos)
        swarm.es.remove(enSwarmLasCount)
        score += 15
      }
      else{
        enSwarmLasCount += 1
      }
    }
    
    //does damage from laser2 to enemy swarm
    if(ship2 != null) {
    var enSwarmLasCount2 = 0
    while(enSwarmLasCount2 < swarm.es.length) {
      if(laser2 != null && laser2.collide(swarm.es(enSwarmLasCount2)) == true) {
        explosions += new Explosion(swarm.es(enSwarmLasCount2).pos)
        swarm.es.remove(enSwarmLasCount2)
        score += 15
      }
      else{
        enSwarmLasCount2 += 1
      }
    }
    }
    
    //does damage from laser to boss enemy
    if(laser != null && laser.collide(boss) == true) {
      boss.hits += 1
      changeBossImg
      score += 10
      if(boss.hits > 5) {
        explosions += new Explosion(new Vec2(boss.pos.x + (boss.img.getWidth/3), boss.pos.y + (boss.img.getHeight/2)))
        boss.moveTo(bossLoc)
        boss.isPresent = false
        boss.hits = 0
        changeBossImg
        score += 50
      }
    }
    
    //does damage from laser to boss enemy
    if(ship2 != null) {
    if(laser2 != null && laser2.collide(boss) == true) {
      boss.hits += 1
      changeBossImg
      score += 10
      if(boss.hits > 5) {
        explosions += new Explosion(new Vec2(boss.pos.x + (boss.img.getWidth/3), boss.pos.y + (boss.img.getHeight/2)))
        boss.moveTo(bossLoc)
        boss.isPresent = false
        boss.hits = 0
        changeBossImg
        score += 50
      }
    }
    }
      
  }
  
  //moves the enemy swarm along its path
  var moveCount = 0
  def swarmMover {
    
    if(moveCount < 10){
      for(en <- swarm.es){
        en.move(new Vec2(0, 5))
      }
      moveCount += 1
    }
    if(moveCount >= 10 && moveCount < 20) {
      moveCount += 1
    }
    if(moveCount >= 20 && moveCount < 40) {
      for(en <- swarm.es) {
        en.move(new Vec2(-5, 0))
      }
      moveCount += 1
    }
    if(moveCount >= 40 && moveCount < 50) {
      for(en <- swarm.es) {
        en.move(new Vec2(0, -5))
      }
      moveCount += 1
    }
    if(moveCount >= 50 && moveCount < 70) {
      for(en <- swarm.es) {
        en.move(new Vec2(5, 0))
      }
      moveCount += 1
    }
    if(moveCount == 70){
      moveCount = 0
    }
  }
  
  var swarmMoveTimer = new Timer(50, Swing.ActionListener(e => {
    swarmMover
  }))
  swarmMoveTimer.start
  
  //Creates a really fun rainbow background
  var rbg = new RainbowBackground(width, height)
  
  var showMenuScreen:Boolean = true
  val menuTitleImage = new File("src/cs1321/game/title.png")
  val menuTitle = ImageIO.read(menuTitleImage)
  
  //Layers for background
  val topLayerImage = new File("src/cs1321/game/toplayer.png")
  val topLayImg = ImageIO.read(topLayerImage)
  val topLayStart = new Vec2(0, 0 - topLayImg.getHeight)
  val topLaySpeed = new Vec2(0, 25)
  val topLayer1 = new BackgroundImage(topLayImg, new Vec2(0, 0))
  val topLayer2 = new BackgroundImage(topLayImg, topLayStart)
  val midLayerImage = new File("src/cs1321/game/midlayer.png")
  val midLayImg = ImageIO.read(midLayerImage)
  val midLayStart = new Vec2(0, 0 - midLayImg.getHeight)
  val midLaySpeed = new Vec2(0, 10)
  val midLayer1 = new BackgroundImage(midLayImg, new Vec2(0, 0))
  val midLayer2 = new BackgroundImage(midLayImg, midLayStart)
  val botLayerImage = new File("src/cs1321/game/botlayer.png")
  val botLayImg = ImageIO.read(botLayerImage)
  val botLayStart = new Vec2(0, 0 - botLayImg.getHeight)
  val botLaySpeed = new Vec2(0, 3)
  val botLayer1 = new BackgroundImage(botLayImg, new Vec2(0, 0))
  val botLayer2 = new BackgroundImage(botLayImg, botLayStart)
  
  //counter to determine how often the player can shoot
  var playShootCounter = 5
  
  var playShootCountTimer = new Timer(20, Swing.ActionListener(e => {
    playShootCounter -= 1
  }))
  playShootCountTimer.start
  
  var play2ShootCounter:Int = 0
  if(ship2 != null) {play2ShootCounter = 5}
  
  var play2ShootCountTimer = new Timer(20, Swing.ActionListener(e => {
    if(ship2 != null) {
      play2ShootCounter -= 1
    }
  }))
  play2ShootCountTimer.start
  
  
	override def paint (g:Graphics2D) { 
	  g.setPaint(Color.BLACK)
	  g.fillRect(0, 0, width, height)
    //rbg(g)
	  if(score > 1000) rbg(g)
	  topLayer1.display(g)
	  topLayer2.display(g)
	  midLayer1.display(g)
	  midLayer2.display(g)
	  botLayer1.display(g)
	  botLayer2.display(g)
	  
	  
	  if(showMenuScreen == true) {
      
	    g.setPaint(Color.ORANGE)
	    //g.drawString("Press the spacebar to start", (width/2) - 92, height/2)
	    g.drawString("Press 'q' to quit", (width/2) - 65, (height/2) + 20)
	    g.drawString("Press 'esc' at any time to quit", (width/2) - 100, (height/2) + 40)
      g.drawImage(menuTitle, (width/4) + 50, height / 5, null)
      g.drawString("Press '1' for single-player or '2' for co-op", (width/2) - 127, (height/2))
      repaint
	    
	  }
    
	  else if(playLife < 0) {
      if(play2Life < 0 && ship2 == null) {
  	    g.setPaint(Color.BLACK)
  	    g.fillRect(0, 0, width, height)
  	    g.setPaint(Color.ORANGE)
  	    g.drawString("You both died from nacho overdose", (width/2) - 110, (height/2) - 20)
  	    g.drawString("GAME OVER", (width/2) - 50, height/2)
  	    g.drawString("Press 'n' to return to the menu", (width/2) - 101, (height/2) + 20)
  	    g.drawString("Press 'q' to quit", (width/2) - 60, (height/2) + 40)
  	    repaint
      }
      else if(ship2 == null) {
        g.setPaint(Color.BLACK)
        g.fillRect(0, 0, width, height)
        g.setPaint(Color.ORANGE)
        g.drawString("You died from nacho overdose", (width/2) - 103, (height/2) - 20)
        g.drawString("GAME OVER", (width/2) - 50, height/2)
        g.drawString("Press 'n' to return to the menu", (width/2) - 101, (height/2) + 20)
        g.drawString("Press 'q' to quit", (width/2) - 60, (height/2) + 40)
        repaint
      }
      else { 
      
      g.setPaint(Color.ORANGE)
	    if(playLife >= 0) g.drawString("P1 Lives: " + playLife, 10, height-10)
      g.drawString("Score: " + score, 10, height - 30)
	    g.drawString("Wave: " + waveCount, 10, height - 50)
      if(playLife >= 0) {
        g.drawRect(width - 130, height - 30, 100, 5)
        g.fillRect(width - 130, height - 30, 100 - (laserTimeCount/10), 5)
        if(laserTimeCount < 0) {
          g.drawString("TIME TO FIRE", width - 120, height - 35)
          tadaClip.start
        }
      }  
      if(ship2 != null) {
        g.setPaint(Color.RED)
        if(play2Life >= 0) g.drawString("P2 Lives: " + play2Life, 75, height-10)
        g.drawRect(width - 130, height - 60, 100, 5)
        g.fillRect(width - 130, height - 60, 100 - (laser2TimeCount/10), 5)
        if(laser2TimeCount < 0) {
          g.drawString("TIME TO FIRE", width - 120, height - 65)
          tadaClip2.start
        }
      }
      //g.drawString("Bullets: " + bullets.length, 10, height -50)
	            
      if(laser != null) {
        laser.display(g)
      }
      
      if(laser2 != null) {
        laser2.display(g)
      }
      
      ship.display(g)
      if(ship2 != null) { ship2.display(g) }
      swarm.display(g)
      for(b <- playbullets) {
        b.display(g)
      }
      if(ship2 != null) {
        for(b <- play2bullets) {
          b.display(g)
        }
      }
      for(b <- swarmbullets) {
        b.display(g)
      }
      for(b <- bossbullets) {
        b.display(g)
      }
      
      if(boss.isPresent == true) {
        boss.display(g)
      }
	    
	    for(ex <- explosions) {
	      ex.display(g)
	    }
	    	    
	  }
	  }
	  else { 
      
      g.setPaint(Color.ORANGE)
	    if(playLife >= 0) g.drawString("P1 Lives: " + playLife, 10, height-10)
      
	    g.drawString("Score: " + score, 10, height - 30)
	    g.drawString("Wave: " + waveCount, 10, height - 50)
      g.drawRect(width - 130, height - 30, 100, 5)
      g.fillRect(width - 130, height - 30, 100 - (laserTimeCount/10), 5)
      if(laserTimeCount < 0) {
        g.drawString("TIME TO FIRE", width - 120, height - 35)
        tadaClip.start
      }
      if(ship2 != null) {
        g.setPaint(Color.RED)
        if(play2Life >= 0) g.drawString("P2 Lives: " + play2Life, 75, height-10)
        g.drawRect(width - 130, height - 60, 100, 5)
        g.fillRect(width - 130, height - 60, 100 - (laser2TimeCount/10), 5)
        if(laser2TimeCount < 0) {
          g.drawString("TIME TO FIRE", width - 120, height - 65)
          tadaClip2.start
        }
      }
      //g.drawString("Bullets: " + bullets.length, 10, height -50)
	            
      if(laser != null) {
        laser.display(g)
      }
      
      if(laser2 != null) {
        laser2.display(g)
      }
      
      ship.display(g)
      if(ship2 != null) { ship2.display(g) }
      swarm.display(g)
      for(b <- playbullets) {
        b.display(g)
      }
      if(ship2 != null) {
        for(b <- play2bullets) {
          b.display(g)
        }
      }
      for(b <- swarmbullets) {
        b.display(g)
      }
      for(b <- bossbullets) {
        b.display(g)
      }
      
      if(boss.isPresent == true) {
        boss.display(g)
      }
	    
	    for(ex <- explosions) {
	      ex.display(g)
	    }
	    	    
	  }  
	  
	}
  
  //moves background layers down the screen
  def backgroundMover() {
    topLayer1.moveDownScreen(height, topLaySpeed)
    topLayer2.moveDownScreen(height, topLaySpeed)
    midLayer1.moveDownScreen(height, midLaySpeed)
    midLayer2.moveDownScreen(height, midLaySpeed)
    botLayer1.moveDownScreen(height, botLaySpeed)
    botLayer2.moveDownScreen(height, botLaySpeed)
  }   
  val backgroundTimer = new Timer(50, Swing.ActionListener(e => {
    backgroundMover
  }))
  backgroundTimer.start
	
	listenTo(keys, mouse.clicks)
	reactions += {
	  case e:KeyPressed =>
	    if(e.key == Key.Left) {
	      ship.img = pImgL
	      repaint
	      ship.left = true
	    }
	    if(e.key == Key.Right) {
	      ship.img = pImgR
	      repaint
	      ship.right = true
	    }
	    if(e.key == Key.Up) {
	      ship.up = true
	    }
	    if(e.key == Key.Down) {
	      ship.down = true
	    }
      if(ship2 != null && e.key == Key.A){
        ship2.img = ship2ImageL
        repaint
        ship2.left = true
      }
      if(ship2 != null && e.key == Key.D) {
        ship2.img = ship2ImageR
        repaint
        ship2.right = true
      }
      if(ship2 != null && e.key == Key.S) {
        ship2.down = true
      }
      if(ship2 != null && e.key == Key.W) {
        ship2.up = true
      }
	    if(e.key == Key.Space && playShootCounter <= 0) {
	      playbullets += ship.shoot
	      timeToShootQue.enqueue(true)
	      playShootCounter = 15
	    } else timeToShootQue.enqueue(false)
      
      if(ship2 != null && e.key == Key.X && play2ShootCounter <= 0) {
        play2bullets += ship2.shoot
        play2ShootCounter = 15
      }
	    
	    if(playLife < 0 && e.key == Key.N) {
	      showMenuScreen = true
        waveCount = 0
	      score = 0
	      swarmbullets = Buffer[Bullet]()
	      playbullets = Buffer[Bullet]()
        play2bullets = Buffer[Bullet]()
	      bossbullets = Buffer[Bullet]()
        explosions = Buffer[Explosion]()
	      newSwarm
	      playLife = 3
        play2Life = 3
        laserTimeCount = 1000
	    }
	    /*if(showMenuScreen == true && e.key == Key.Space) {
	      showMenuScreen = false
	    }*/
      if(showMenuScreen == true && e.key == Key.Key1) {
        showMenuScreen = false
      }
      if(showMenuScreen == true && e.key == Key.Key2) {
        ship2 = new Player(ship2Image, ship2Loc, playBul, 0)
        showMenuScreen = false
      }
	    if(showMenuScreen == true && e.key == Key.Q) {
	      sys.exit
	    }
	    if(playLife <= 0 && e.key == Key.Q) {
	      sys.exit
	    }
	    if(e.key == Key.Escape) {
	      sys.exit
	    }
	    if(e.key == Key.Enter && laser == null && laserTimeCount <= 0) {
	      blahClip.setFramePosition(0) 
        blahClip.start
        laser = new MegaDeathLaser(laserImg, ship.pos+(new Vec2(15, -750)))
        laserTimeCount = 1000
        tadaClip.setFramePosition(0)
	    }
      if(ship2 != null && laser2 == null && laser2TimeCount <= 0 && e.key == Key.Z) {
        blahClip2.setFramePosition(0)
        blahClip2.start
        laser2 = new MegaDeathLaser(laserImg, ship2.pos+(new Vec2(15, -750)))
        laser2TimeCount = 1000
        tadaClip2.setFramePosition(0)
      }
	    
	  case e:KeyReleased =>
	    if(e.key == Key.Left) {
	      if(ship.hits == 0) ship.img = pImg
	      if(ship.hits == 1) ship.img = hit1Img
	      if(ship.hits == 2) ship.img = hit2Img
	      if(ship.hits == 3) ship.img = hit3Img
	      ship.left = false
	      boss.left = false
	    }
	    if(e.key == Key.Right) {
	      if(ship.hits == 0) ship.img = pImg
	      if(ship.hits == 1) ship.img = hit1Img
	      if(ship.hits == 2) ship.img = hit2Img
	      if(ship.hits == 3) ship.img = hit3Img
	      ship.right = false
	      boss.right = false
	    }
	    if(e.key == Key.Up) {
        changePlayerImg
	      ship.up = false
	      boss.down = false
	    }
	    if(e.key == Key.Down) {
        changePlayerImg
	      ship.down = false
	      boss.up = false
	    }
      if(ship2 != null && e.key == Key.A) {
        changePlayer2Img
        ship2.left = false
      }
      if(ship2 != null && e.key == Key.D) {
        changePlayer2Img
        ship2.right = false
      }
      if(ship2 != null && e.key == Key.W) {
        changePlayer2Img
        ship2.up = false
      }
      if(ship2 != null && e.key == Key.S) {
        changePlayer2Img
        ship2.down = false
      }
      
	     
	}
	
  var explodeCount = 0
  def explosionCheck() {
    if(!explosions.isEmpty){
      while(explodeCount < explosions.length) {
        if(explosions(explodeCount).timeToRemove == true) {
          explosions.remove(explodeCount)
        }
        else explodeCount += 1
      }
    }
  }
	val explosionTimer = new Timer(150, Swing.ActionListener(e => {
	  explosionCheck
	}))
	explosionTimer.start
	
	  
  

	def startGame() {
    
	  backgroundClip.loop(Clip.LOOP_CONTINUOUSLY)
    
    
	  //creates bullets from the swarm
	  val swarmShooterTimer = new Timer(450, Swing.ActionListener(e => {
	    if(showMenuScreen == false && !swarm.es.isEmpty) {
	      swarmbullets += swarm.shoot
	      //bossbullets += boss.shoot
	    }
	  }))
	  
	  swarmShooterTimer.start
	  
	  //moves the player and the bullets. checks if the swarm is empty to create a new one
	  val moveTimer = new Timer(30, Swing.ActionListener(e => { 
	    if(showMenuScreen == false) {
        var bossMoveVec = new Vec2(0, 0)
      
        //moves the laser around
        if(laser != null && laser.pos != (ship.pos + (new Vec2(15, -750)))) {
          laser.moveTo(ship.pos + (new Vec2(15, -750)))
        }
        
        //moves the laser around
        if(ship2 != null  && laser2 != null && laser2.pos != (ship2.pos + (new Vec2(15, -750)))) {
          laser2.moveTo(ship2.pos + (new Vec2(15, -750)))
        }
        
        if(laserCount > 50) {
          laser = null
          laserCount = 0
        }
        
        if(laser2Count > 50) {
          laser2 = null
          laser2Count = 0
        }
        
	      if(ship.left == true && ship.pos.x > 0){
  	      ship.moveLeft
  	      bossMoveVec = bossMoveVec + boss.moveLeftVec
  	    }
  	    if(ship.right == true && ship.pos.x < width-ship.width) {
  	      ship.moveRight
  	      bossMoveVec = bossMoveVec + boss.moveRightVec
  	    }
  	    if(ship.up == true && ship.pos.y > 0) {
  	      ship.moveUp
  	      bossMoveVec = bossMoveVec + boss.moveDownVec
  	    }
  	    if(ship.down == true && ship.pos.y < height - ship.height) {
  	      ship.moveDown
  	      bossMoveVec = bossMoveVec + boss.moveUpVec
  	    }
        
        if(ship2 != null && ship2.left == true && ship2.pos.x > 0){
          ship2.moveLeft
        }
        if(ship2 != null && ship2.right == true && ship2.pos.x < width-ship2.width) {
          ship2.moveRight
        }
        if(ship2 != null && ship2.up == true && ship2.pos.y > 0) {
          ship2.moveUp
        }
        if(ship2 != null && ship2.down == true && ship2.pos.y < height - ship2.height) {
          ship2.moveDown
        }
        
        if(laser != null) {
          laserCount += 1
        }
        
        if(laser2 != null) {
          laser2Count += 1
        }
  	    
  	    bossMoveQue.enqueue(bossMoveVec)
  	    
  	    if(bossMoveQue.length > 20) {
  	      /*if(boss.pos.x + bossMoveQue.last.x > (width - boss.width) || boss.pos.x + bossMoveQue.last.x < 0){
  	        if(boss.pos.y + bossMoveQue.last.y  > (height - boss.height) || boss.pos.y + bossMoveQue.last.y < 0) {
  	        } else {
  	          boss.move(new Vec2(0, bossMoveQue.last.y))
  	        }
  	      }
  	      if(boss.pos.y + bossMoveQue.last.y > (height - boss.height) || boss.pos.y + bossMoveQue.last.y < 0) {
  	        boss.move(new Vec2(bossMoveQue.last.x, 0))
  	      }*/
  	      boss.move(bossMoveQue.dequeue)
  	    }
  	    
  	    if(timeToShootQue.length > 20) {
  	      if(timeToShootQue.last == true) {
  	        bossbullets += boss.shoot
  	      }
  	      timeToShootQue.dequeue
  	    }
  	     
  	    for(b <- playbullets) {
  	      b.timeStep
  	      }
        if(ship2 != null) {
          for(b <- play2bullets) {
            b.timeStep
          }
        }
  	    for(b <- swarmbullets) {
  	      b.timeStep
  	    }
  	    for(b <- bossbullets) {
  	      b.timeStep
  	    }
  	    repaint
  	    
  	    //removes bullets that are off the screen
  	    bulletRemover
  	    
  	    //removes intersecting sprites
  	    spriteRemover
  	    
        if(swarm.es.isEmpty == true && !boss.isPresent){
  	      newSwarm
  	      playLife += 1
          if(ship2 != null) play2Life += 1
  	    }
        
        if(laserTimeCount >= 0) {
          laserTimeCount -= 1
        }
        
        if(laser2TimeCount >= 0) {
          laser2TimeCount -= 1
        }
        if(ship2 != null && playLife < 0) {
          ship.moveTo(new Vec2(2 * width, 2 * height))
        }
        if(ship2 != null && play2Life < 0) {
          ship2 = null
        }
        
	     }	  
	   }))
	   moveTimer.start
	   
	  
	   
	   var bossCountTimer = new Timer(50, Swing.ActionListener(e => {
      if(showMenuScreen == false && boss.isPresent == false) {
	      var rand = Random.nextInt(1001)
	      if(rand == 36){
	        boss.moveTo(new Vec2((width/2) - boss.width, (height/2) - boss.height))
	        boss.isPresent = true
	      }
	    }
     }))
     bossCountTimer.start
	   
	}
  
}