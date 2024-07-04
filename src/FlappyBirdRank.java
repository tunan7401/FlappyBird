import javax.swing.*;
import java.awt.*;

public class FlappyBirdRank extends JFrame {

    private ImageIcon groundIcon;
    private ImageIcon backgroundIcon;
    private ImageIcon birdIcon;
    private ImageIcon pipeIcon;

    public FlappyBirdRank() {
        setTitle("Flappy Bird Rank");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        groundIcon = new ImageIcon(getClass().getResource("Image/bg4.png"));
        backgroundIcon = new ImageIcon(getClass().getResource("Image/bg3.png"));
        birdIcon = new ImageIcon(getClass().getResource("Image/bg1.png"));
        pipeIcon = new ImageIcon(getClass().getResource("Image/bg2.png"));

        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                
                g.setColor(new Color(144, 202, 249));
                g.fillRect(0, 0, getWidth(), getHeight());

                
                g.drawImage(backgroundIcon.getImage(), 0, getHeight() - 300, getWidth(), 150, this);

                
                g.drawImage(groundIcon.getImage(), 0, getHeight() - 50, getWidth(), 50, this);

                
                g.drawImage(birdIcon.getImage(), 50, getHeight() - 200, birdIcon.getIconWidth(), birdIcon.getIconHeight(), this);

               
                g.drawImage(pipeIcon.getImage(), 250, getHeight() - 250, pipeIcon.getIconWidth(), pipeIcon.getIconHeight(), this);
            }
        };
        mainPanel.setLayout(null);

        
        JLabel rankTitle = new JLabel("Rank", SwingConstants.CENTER);
        rankTitle.setFont(new Font("Arial", Font.BOLD, 48));
        rankTitle.setForeground(Color.WHITE);
        rankTitle.setBounds(0, 20, getWidth(), 50);
        mainPanel.add(rankTitle);

        
        JPanel scoresPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(192, 192, 192));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        scoresPanel.setBounds(50, 100, 300, 150);
        scoresPanel.setLayout(new GridLayout(4, 1));

        JLabel topLabel = new JLabel("Top", SwingConstants.CENTER);
        topLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        scoresPanel.add(topLabel);

        String[] scores = {
            "1: High Score : 9999     Dưa",
            "2: High Score : 7777     Sếp Củng",
            "3: High Score : 6666     Dưa"
        };

        for (String score : scores) {
            JLabel scoreLabel = new JLabel(score, SwingConstants.CENTER);
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            scoresPanel.add(scoreLabel);
        }

        mainPanel.add(scoresPanel);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlappyBirdRank frame = new FlappyBirdRank();
            frame.setVisible(true);
        });
    }
}
