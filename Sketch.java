import processing.core.PApplet;

/**
* An interactive simulation of snow falling and the player dodging the snow
* @author: T. Guo
*
*/

public class Sketch extends PApplet {

  public void settings() {
    size(400, 400);
    
  }

  float[] snowY = new float[80];
  float[] snowX = new float[80];
  boolean[] isSnowExist = new boolean[80];
  float fltSnowSpeed = 1;

  float playerX;
  float playerY;
  int intPlayerLives = 3;
  int intScore = 0;

  boolean upPressed = false;
  boolean downPressed = false;
  boolean leftPressed = false;
  boolean rightPressed = false;

  boolean isGameOver = false;
  boolean isGameWin = false;

  public void setup() {
    background(100, 100, 200);
    playerX = width / 2;
    playerY = height  * 3 / 4;
    for (int i = 0; i < snowY.length; i++){
      snowY[i] = random(height / 2 * -1, height / 2);
      snowX[i] = random(width);
      isSnowExist[i] = true;
    }
  }


  public void draw() {
    if(!isGameOver && !isGameWin){
      background(100, 100, 200);
      
      fltSnowSpeed = 1;
      if (keyPressed){
        if (keyCode == UP){
          fltSnowSpeed = 0.5f;
        }
        else if (keyCode == DOWN){
          fltSnowSpeed = 1.5f;
        }
      }

      for (int i = 0; i < snowY.length; i++) {
        fill(255);
        if(isSnowExist[i]){
          ellipse(snowX[i], snowY[i], width / 20, height / 20);
    
          snowY[i] += fltSnowSpeed;
          if (dist(playerX, playerY, snowX[i], snowY[i]) < width / 20 && isSnowExist[i]){
            isSnowExist[i] = false;
            background(100, 0, 0);
            intPlayerLives--;
            
            if (intPlayerLives == 0){
              isGameOver = true;
            }
          }

          if (snowY[i] > height) {
            snowY[i] = 0;
            snowX[i] = random(width);
          }
      }
      }

      if (mousePressed){
      
      }

      if (upPressed && playerY > 0) {
        playerY -= 2;
      }
      if (downPressed && playerY < height) {
        playerY += 2;
      }
      if (leftPressed && playerX > 0) {
        playerX -= 2;
      }
      if (rightPressed && playerX < width) {
        playerX += 2;
      }


      fill(0, 0, 255);
      ellipse(playerX, playerY, width/20, height/20);

      for (int i = 0; i < intPlayerLives; i++){
        fill(255, 0, 0);
        rect(width * 4 / 5 + i * 20, height / 10, 10, 10);
      }

      fill(150, 255, 150);
      textSize(20);
      text(intScore + "/50", width / 20, height / 10);
 
    }
   
    else if (isGameOver){
      background(255);
    }
    else if (isGameWin){
      background(150, 255, 150);
    }
  }

  // Set booleans when wasd keys are pressed
  public void keyPressed() {
    if (key == 'w') {
      upPressed = true;
    }
    else if (key == 's') {
      downPressed = true;
    }
    else if (key == 'a') {
      leftPressed = true;
    }
    else if (key == 'd') {
      rightPressed = true;
    }
  }

  // Set booleans when wasd keys are released
  public void keyReleased() {
    if (key == 'w') {
      upPressed = false;
    }
    else if (key == 's') {
      downPressed = false;
    }
    else if (key == 'a') {
      leftPressed = false;
    }
    else if (key == 'd') {
      rightPressed = false;
    }
  }

  public void mouseClicked(){
    for(int i = 0; i < isSnowExist.length; i++){
      if(dist(mouseX, mouseY, snowX[i], snowY[i]) < width / 40 && isSnowExist[i]){
        isSnowExist[i] = false;
        intScore++;
        if(intScore > 50){
          isGameWin = true;
        }
      }
    }
  }
}