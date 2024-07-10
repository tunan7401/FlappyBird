
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MainMenuPanel extends JPanel {

    private BufferedImage birdImage;
    private BufferedImage pipeImage;
    private BufferedImage cloudImage;
    private BufferedImage groundImage;

    public MainMenuPanel() {
        setLayout(null);

        try {
            birdImage = ImageIO.read(new File("Image/bg1.png"));
            pipeImage = ImageIO.read(new File("Image/bg2.png"));
            cloudImage = ImageIO.read(new File("Image/bg3.png"));
            groundImage = ImageIO.read(new File("Image/bg4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel titleLabel = new JLabel("Flappy Bird");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(125, 50, 300, 100);

        JButton playButton = new JButton("PLAY");
        playButton.setFont(new Font("Arial", Font.PLAIN, 15));
        playButton.setBounds(100, 200, 200, 50);

        JButton chooseBirdButton = new JButton("CHOOSE BIRD");
        chooseBirdButton.setFont(new Font("Arial", Font.PLAIN, 15));
        chooseBirdButton.setBounds(100, 300, 200, 50);

        JButton watchRankButton = new JButton("WATCH RANK");
        watchRankButton.setFont(new Font("Arial", Font.PLAIN, 15));
        watchRankButton.setBounds(100, 400, 200, 50);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PLAY button pressed");
            }
        });

        chooseBirdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProcessBuilder pb = new ProcessBuilder("java", "-cp", ".", "BirdChooser");
                    pb.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        watchRankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("WATCH RANK button pressed");
            }
        });

        add(titleLabel);
        add(playButton);
        add(chooseBirdButton);
        add(watchRankButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(144, 202, 249));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (cloudImage != null) {
            g2d.drawImage(cloudImage, 0, getHeight() - 300, getWidth(), 150, this);
        }

        if (groundImage != null) {
            g2d.drawImage(groundImage, 0, getHeight() - 50, getWidth(), 50, this);
        }

        if (pipeImage != null) {
            g2d.drawImage(pipeImage, 250, getHeight() - 250, pipeImage.getWidth(), pipeImage.getHeight(), this);
        }

        if (birdImage != null) {
            g2d.drawImage(birdImage, 50, getHeight() - 200, birdImage.getWidth(), birdImage.getHeight(), this);
        }
    }
}
