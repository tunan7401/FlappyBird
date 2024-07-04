import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import java.nio.channels.Pipe;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int boardWidth = 360;
    int boardHeight = 640;

    //image
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 70;
    int birdHeight = 70;
    //PipeBottom
    int PipeBottomX = 260;
    int PipeBottomY = 640-217;
    int PipeBottomWidth = 69;
    int PipeBottomHeight = 217;
    //PipeTop
    int PipeTopX = 260;
    int PipeTopY = 0;
    int PipeTopWidth = 69;
    int PipeTopHeight = 217;

    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img){
            this.img = img;
        }
    }

    class PipeBottom{
        int x = PipeBottomX;
        int y = PipeBottomY;
        int width = PipeBottomWidth;
        int height = PipeBottomHeight;
        Image img;

        PipeBottom(Image img){
            this.img = img;
        }
    }

    //pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }
    }

    //game logic 
    Bird bird;
    int velocityX = -4;
    PipeBottom pipeTop;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);


        //load images
        backgroundImg = new ImageIcon(getClass().getResource("./background.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./bird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./pipeUp.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./pipeDown.png")).getImage();

        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        //place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void placePipes(){
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4; 

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight +openingSpace;
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(birdImg, birdX, birdY, birdWidth, birdHeight, null);

        //pipes
        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
        //g.drawImage(bottomPipeImg, PipeBottomX, PipeBottomY, PipeBottomWidth, PipeBottomHeight, null);
        //g.drawImage(topPipeImg, PipeTopX, PipeTopY, PipeTopWidth, PipeTopHeight, null);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver){
            g.drawString("Game Over " + String.valueOf((int) score), 10, 35);
        }
        else{
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move(){
        //bird
        velocityY += gravity;
        birdY += velocityY;
        birdY = Math.max(birdY ,0);

        //pipes
        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5;
            }

            if (collision(bird, pipe)){
                gameOver = true;
            }
        }

        if (bird.y > boardHeight){
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b){
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            System.out.println("stop");
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
