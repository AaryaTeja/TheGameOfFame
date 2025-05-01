import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen extends JPanel {
    private JButton playAgainButton;
    private JButton endButton;
    private Game game; // Reference to the main game

    public GameOverScreen(Game game, String winner) {
        this.game = game;
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 200)); // Semi-transparent black

        // Winner text
        JLabel winnerLabel = new JLabel(winner + " WINS!", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 48));
        winnerLabel.setForeground(Color.WHITE);
        add(winnerLabel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        playAgainButton = new JButton("Play Again");
        endButton = new JButton("End");

        // Style buttons
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 24));
        endButton.setFont(new Font("Arial", Font.BOLD, 24));

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resetGame(); // Reset the game
                setVisible(false); // Hide this popup
            }
        });

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the game
            }
        });

        buttonPanel.add(playAgainButton);
        buttonPanel.add(endButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}