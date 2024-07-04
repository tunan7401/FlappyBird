// package flappybird;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BirdChooser extends JFrame {
    public BirdChooser() {
        setTitle("Bird Chooser");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel birdPanel = new JPanel();
        birdPanel.setLayout(new GridLayout(2, 4, 10, 10));
        birdPanel.setBackground(new Color(0, 204, 204));

        ClassLoader classLoader = getClass().getClassLoader();
        for (int i = 1; i <= 8; i++) {
            String imagePath = "Image/" + i + ".png";
            java.net.URL imageURL = classLoader.getResource(imagePath);
            if (imageURL != null) {
                ImageIcon originalIcon = new ImageIcon(imageURL);
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon birdIcon = new ImageIcon(scaledImage);
                JLabel birdLabel = new JLabel(birdIcon);
                birdLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JOptionPane.showMessageDialog(null, "You clicked on bird: " + imagePath);
                    }
                });
                birdPanel.add(birdLabel);
            } else {
                System.out.println("Image not found: " + imagePath);
            }
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(new Color(0, 204, 204));

        JButton chooseButton = new JButton("CHOOSE BIRD");
        chooseButton.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(chooseButton);

        JButton backButton = new JButton("Back Home");
        backButton.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(backButton);

        add(birdPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BirdChooser frame = new BirdChooser();
            frame.setVisible(true);
        });
    }
}
