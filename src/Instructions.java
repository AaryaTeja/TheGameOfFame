import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructions extends JPanel implements ActionListener {
    Layout layout;
    JButton backButton;

    public Instructions(Layout layout){
        this.layout = layout;

        backButton = new JButton("Back");

        backButton.addActionListener(this);

        this.add(backButton);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton){
            CardLayout cl = (CardLayout) layout.cards.getLayout();
            cl.show(layout.cards, "Start");
        }
    }
}
