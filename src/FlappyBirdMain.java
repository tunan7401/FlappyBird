
import javax.swing.*;

public class FlappyBirdMain {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        MainMenuPanel mainMenu = new MainMenuPanel();

        frame.add(mainMenu);
        frame.setSize(400, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
