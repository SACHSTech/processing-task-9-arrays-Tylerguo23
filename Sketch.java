import processing.core.PApplet;

/**
* An interactive simulation of snow falling and the player dodging/clicking the snow
* @author: T. Guo
*
*/

public class Sketch extends PApplet {
  
  // variable declarations and initializations
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


  public void settings() {
    size(400, 400);
  }


  public void setup() {
    background(100, 100, 200);

    // puts the player's position in the middle of the bottom of the screen.
    playerX = width / 2;
    playerY = height  * 3 / 4;

    // randomize the snowflake positions to somewhere above the top half of the screen. Set all isSnowExist[] elements to true by default.
    for (int i = 0; i < snowY.length; i++){
      snowY[i] = random(height / 2 * -1, height / 2);
      snowX[i] = random(width);
      isSnowExist[i] = true;
    }
  }


  public void draw() {
    
    if(!isGameOver && !isGameWin){
      background(100, 100, 200);
      
      // reset snow fallign speed if no arrow keys are held
      fltSnowSpeed = 1;

      // Adjust the speed that snow falls while the UP or DOWN arrow keys are being held
      if (keyPressed){
        if (keyCode == UP){
          fltSnowSpeed = 0.5f;
        }
        else if (keyCode == DOWN){
          fltSnowSpeed = 1.5f;
        }
      }

      // For every element in the snow arrays, change the Y position and then draw the snowflake.
      for (int i = 0; i < snowY.length; i++) {
        fill(255);
        if(isSnowExist[i]){
          snowY[i] += fltSnowSpeed;
          ellipse(snowX[i], snowY[i], width / 20, height / 20);

          // Check for collision for each snow element with player. If a collision occurs, flashes the screen red, removes that snowflake, and subtracts a life.
          if (dist(playerX, playerY, snowX[i], snowY[i]) < width / 20 && isSnowExist[i]){
            isSnowExist[i] = false;
            background(100, 0, 0);
            intPlayerLives--;
            
            // if the player runes out of lives, set isGameOver to true
            if (intPlayerLives == 0){
              isGameOver = true;
            }
          }

          // Resets snow back to a random position at the top of the screen once it reaches the bottom
          if (snowY[i] > height) {
            snowY[i] = 0;
            snowX[i] = random(width);
          }
      }
      }


      // Moves the player according to movement booleans
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

      // draws the player
      fill(0, 0, 255);
      ellipse(playerX, playerY, width/20, height/20);

      // draws the player lives in top right
      for (int i = 0; i < intPlayerLives; i++){
        fill(255, 0, 0);
        rect(width * 4 / 5 + i * 20, height / 10, 10, 10);
      }

      // displays a score counter in the top left
      fill(150, 255, 150);
      textSize(20);
      text(intScore + "/50", width / 20, height / 10);
    }
   
    // change background to white if isGameOver
    else if (isGameOver){
      background(255);
    }

    // change background to green if isGameWin
    else if (isGameWin){
      background(150, 255, 150);
    }
  }


  /**
  * Set corresponding movement booleans to true when wasd keys are pressed
  */
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


  /**
  * Reset movement booleans when wasd keys are released
  */
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


  /**  
  * When the mouse is pressed, check if the mouse is on a snowflake. 
  * If so, set that snowflake to false in isSnowExist[] and increase score.
  * If score reaches 50, set isGameWin to true.
  */
  public void mousePressed(){

    for(int i = 0; i < isSnowExist.length; i++){
      if(dist(mouseX, mouseY, snowX[i], snowY[i]) < width / 40 && isSnowExist[i]){
        isSnowExist[i] = false;
        intScore++;

        if(intScore >= 50){
          isGameWin = true;
        }
      }
    }
  }
}