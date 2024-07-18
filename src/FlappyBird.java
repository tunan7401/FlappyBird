import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Pipe;
import javax.sound.sampled.*; // Import thư viện âm thanh

public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int boardWidth = 360;
    int boardHeight = 640;
    double score;

    //image
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;


    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 60;
    int birdHeight = 60;


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

    //pipes
    int pipeX = boardWidth;
    int pipeWidth = 64;
    int pipeHeight = 512;
    int gapHeight = 150; // khoảng trống giữa cột trên và cột dưới

    class Pipe{
        int x;
        int y;
        int width;
        int height;
        Image img;
        boolean passed = false;

        Pipe(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    //game logic 
    Bird bird;
    int velocityX = -4;
    int velocityY = 0;
    int gravity = 1;

    ArrayList<Pipe> listTopPipes;
    ArrayList<Pipe> listBottomPipes;

    Random random = new Random();

    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    // double score = 0;

    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);


        //load images
        backgroundImg = new ImageIcon(getClass().getResource("./background1.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./chimxanh.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./pipeUp.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./pipeDown.png")).getImage();

        //bird
        bird = new Bird(birdImg);

        //pipes
        listTopPipes = new ArrayList<Pipe>();
        listBottomPipes = new ArrayList<Pipe>();

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
        //(0-1) * pipeHeight/2 -> (0-256)
        int randomPipeHeight = (int) ( Math.random() * ( (500-300) + 1));
        

        Pipe topPipe = new Pipe(pipeX, 0, pipeWidth, randomPipeHeight, topPipeImg);
        listTopPipes.add(topPipe);

        Pipe bottomPipe = new Pipe(pipeX, randomPipeHeight + gapHeight, pipeWidth, pipeHeight, bottomPipeImg);
        listBottomPipes.add(bottomPipe);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }


    public void draw(Graphics g){
        System.out.println(2);
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(birdImg, birdX, birdY, birdWidth, birdHeight, null);

        // toppipes
        for (int i = 0; i < listTopPipes.size(); i++){
            Pipe upPipe = listTopPipes.get(i);
            g.drawImage(upPipe.img, upPipe.x, upPipe.y, upPipe.width, upPipe.height, null);
        }
        // bottompipes
        for (int i = 0; i < listBottomPipes.size(); i++){
            Pipe lowPipe = listBottomPipes.get(i);
            g.drawImage(lowPipe.img, lowPipe.x, lowPipe.y, lowPipe.width, lowPipe.height, null);
        }

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

        // toppipes
        for (int i = 0; i < listTopPipes.size(); i++){
            Pipe pipeTren = listTopPipes.get(i);
            pipeTren.x += velocityX;

            if ((birdX + birdWidth > pipeTren.x 
            && birdX < pipeTren.x + pipeTren.width) 
            && (birdY < pipeTren.height)){
                gameOver = true;
                // playGameOverSound();
            }      
            else if (pipeTren.x + pipeTren.width < birdX && !pipeTren.passed) {
                score+=0.5;
                pipeTren.passed = true;
            }
        }

        // bottompipes
        for (int i = 0; i < listBottomPipes.size(); i++){
            Pipe pipeDuoi = listBottomPipes.get(i);
            pipeDuoi.x += velocityX;

            if ((birdX + birdWidth > pipeDuoi.x 
            && birdX < pipeDuoi.x + pipeDuoi.width) 
            && (birdY + birdHeight > pipeDuoi.y)){
                gameOver = true;
                // playGameOverSound();
            }
            else if (pipeDuoi.x + pipeDuoi.width < birdX && !pipeDuoi.passed) {
                score+=0.5;
                pipeDuoi.passed = true;
            }
        }

        if (birdY > 531){
            gameOver = true;
            // playGameOverSound();
        }
    }

    // Phương thức phát âm thanh khi trò chơi kết thúc
    public void playGameOverSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File file = new File("oof.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.start();
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
