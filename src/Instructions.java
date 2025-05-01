import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructions extends JPanel implements ActionListener {
    Layout layout;
    JButton backButton;
    JTextArea instructionsText;

    public Instructions(Layout layout) {
        this.layout = layout;
        setLayout(new BorderLayout());
        
    
        instructionsText = new JTextArea();
        instructionsText.setEditable(false);
        instructionsText.setLineWrap(true);
        instructionsText.setWrapStyleWord(true);
        instructionsText.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsText.setMargin(new Insets(20, 20, 20, 20));
        
   
        String instructions = """
        		🎯 Objective: 
        		
        		Collect coins to earn currency.Use coins in shops to buy blocks.
        		Build platforms to navigate the map and avoid falling into lava.
        		Outlast your opponent by being the last player standing!
        		🎮 Controls:

        		Player 1 (Left Side - W/A/S/D/F Controls)

        		Move Left: A
        		Move Right: D
        		Jump: W
        		Place Block: F (Costs 1 block from inventory)
        		Shop Interaction: Enter the shop area and press F to access the quiz.
        		Player 2 (Right Side - I/J/K/L/; Controls)

        		Move Left: J
        		Move Right: L
        		Jump: I
        		Place Block: ; (SEMICOLON key, costs 1 block)
        		Shop Interaction: Enter the shop area and press ; to access the quiz.
        		🛒 Shops (Quiz Mechanics):

				When inside a shop (marked area), press the block key (F or ;) to start a math quiz.
				Answer multiplication questions correctly to convert coins into blocks:
				Player 1: Use A, W, or D to select an answer.
				Player 2: Use J, I, or L to select an answer.
				Correct answer: Gain blocks (3 blocks per coin, max 15 blocks per transaction).
				Exit quiz: Press S (Player 1) or K (Player 2).
				⚡ Gameplay Tips:
				
				Avoid lava at the bottom—falling respawns you but resets coins/blocks!
				Build strategically to create paths or trap your opponent.
				Prioritize coins to buy more blocks.
				Use shops wisely—quizzes are the only way to convert coins to blocks.
            """;
        instructionsText.setText(instructions);
        
        
        JScrollPane scrollPane = new JScrollPane(instructionsText);
        add(scrollPane, BorderLayout.CENTER);
        
      
        backButton = new JButton("Back to main menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(this);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

  
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            CardLayout cl = (CardLayout) layout.cards.getLayout();
            cl.show(layout.cards, "Start");
        }
    }
}
