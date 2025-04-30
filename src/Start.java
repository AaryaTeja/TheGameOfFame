import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start extends JPanel implements ActionListener {
    Layout layout;
    Game game;
    JButton playButton;
    JButton instructionButton;

    public Start(Layout layout, Game game){
        this.layout = layout;
        this.game = game;

        setLayout(null);

        JLabel title = new JLabel("The Game Of Fame");
        JLabel subtitle = new JLabel("Arnav J - Aaryateja A");

        playButton = new JButton("Play!");
        instructionButton = new JButton("Instructions");

        playButton.setSize(150, 75);

        playButton.setFont(new Font("Arial", Font.PLAIN, 24));

        playButton.setBackground(new Color(16, 173, 58));
        playButton.setOpaque(true);
        playButton.setForeground(Color.WHITE);

        playButton.setBorder(BorderFactory.createLineBorder(new Color(11, 99, 35), 10));

        playButton.setBounds(720 - (playButton.getWidth() / 2), 600 - playButton.getHeight() - 10, playButton.getWidth(), playButton.getHeight());

        instructionButton.setSize(250, 75);
        instructionButton.setBounds(720 - (instructionButton.getWidth() / 2), 600 + 10, instructionButton.getWidth(), instructionButton.getHeight());

        playButton.addActionListener(this);
        instructionButton.addActionListener(this);

        this.add(playButton);
        this.add(instructionButton);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton){
            CardLayout cl = (CardLayout) layout.cards.getLayout();
            cl.show(layout.cards, "Game");
            game.requestFocusInWindow();
            game.startGame();
        } else if (e.getSource() == instructionButton  ){
            CardLayout cl = (CardLayout) layout.cards.getLayout();
            cl.show(layout.cards, "Instructions");
        }
    }
}
