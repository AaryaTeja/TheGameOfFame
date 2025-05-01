import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructions extends JPanel implements ActionListener {
    Layout layout;
    JButton backButton;
    JTextArea instructionsText;
    JScrollPane scrollPane;

    public Instructions(Layout layout) {
        this.layout = layout;
        setLayout(new BorderLayout());

        // Set up the instructions text area
        instructionsText = new JTextArea();
        instructionsText.setEditable(false);
        instructionsText.setLineWrap(true);
        instructionsText.setWrapStyleWord(true);
        instructionsText.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsText.setMargin(new Insets(20, 20, 20, 20));

        // Nicely formatted text block
        String instructions = """
            🎯 Objective:
            Collect coins as currency. Use coins in the shop to buy blocks.
            Build platforms to navigate the map and avoid falling into lava.
            Get 50 coins to win the game!

            🎮 Controls:

            Player 1 (WASD + F):
              - Move Left/Right: A / D
              - Jump: W
              - Place Block: F (Costs 1 block from inventory)
              - Shop: Enter the shop and press F

            Player 2 (IJKL + ';'):
              - Move Left/Right: J / L
              - Jump: I
              - Place Block: ';' (semicolon key, costs 1 block)
              - Shop: Enter the shop and press ';'

            🛒 Shops (Quiz Mechanics):
              - Stand in the shop area and press your block key (F or ';') to start a quiz
              - Answer multiplication questions to convert coins into blocks
              - Each correct answer gives 3 blocks per coin (max 15 blocks per quiz)
              - Exit quiz: Press S (Player 1) or K (Player 2)

            ⚡ Tips:
              - Avoid falling into lava — falling resets your coins and blocks!
              - Build platforms to trap or outplay your opponent
              - Use quizzes smartly to gain the most blocks
            """;

        instructionsText.setText(instructions);

        scrollPane = new JScrollPane(instructionsText);
        add(scrollPane, BorderLayout.CENTER);

        // Set up back button
        backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(this);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            CardLayout cl = (CardLayout) layout.cards.getLayout();
            cl.show(layout.cards, "Start");
        }
    }
}
