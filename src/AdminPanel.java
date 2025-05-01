import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel extends JFrame {
    private Game game;
    
    public AdminPanel(Game game) {
        this.game = game;
        setTitle("Admin Controls");
        setSize(400, 300);
        setLayout(new GridLayout(0, 2, 5, 5));
        
        // Player 1 Controls
        add(new JLabel("Player 1 (Blue)"));
        JPanel p1Panel = new JPanel(new FlowLayout());
        JButton p1Add = new JButton("+1 Point");
        JButton p1Remove = new JButton("-1 Point");
        p1Panel.add(p1Add);
        p1Panel.add(p1Remove);
        add(p1Panel);
        
        // Player 2 Controls
        add(new JLabel("Player 2 (Red)"));
        JPanel p2Panel = new JPanel(new FlowLayout());
        JButton p2Add = new JButton("+1 Point");
        JButton p2Remove = new JButton("-1 Point");
        p2Panel.add(p2Add);
        p2Panel.add(p2Remove);
        add(p2Panel);
        
        // Game Controls
        add(new JLabel("Game Controls"));
        JPanel gamePanel = new JPanel(new FlowLayout());
        JButton endGame = new JButton("End Game");
        JButton resetGame = new JButton("Reset Game");
        gamePanel.add(endGame);
        gamePanel.add(resetGame);
        add(gamePanel);
        
        // Teleport Controls
        add(new JLabel("Teleport Players"));
        JPanel teleportPanel = new JPanel(new FlowLayout());
        JButton tpCenter = new JButton("To Center");
        JButton tpShops = new JButton("To Shops");
        teleportPanel.add(tpCenter);
        teleportPanel.add(tpShops);
        add(teleportPanel);
        
        // Spawn Controls
        add(new JLabel("Spawn Items"));
        JPanel spawnPanel = new JPanel(new FlowLayout());
        JButton spawnCoin = new JButton("Coin");
        JButton spawnBlock = new JButton("Block");
        spawnPanel.add(spawnCoin);
        spawnPanel.add(spawnBlock);
        add(spawnPanel);
        
        // Add action listeners
        p1Add.addActionListener(e -> game.addPlayerScore(1, 1));
        p1Remove.addActionListener(e -> game.addPlayerScore(1, -1));
        p2Add.addActionListener(e -> game.addPlayerScore(2, 1));
        p2Remove.addActionListener(e -> game.addPlayerScore(2, -1));
        
        endGame.addActionListener(e -> game.forceEndGame());
        resetGame.addActionListener(e -> game.resetGame());
        
        tpCenter.addActionListener(e -> game.teleportPlayers("center"));
        tpShops.addActionListener(e -> game.teleportPlayers("shops"));
        
        spawnCoin.addActionListener(e -> game.spawnItem("coin"));
        spawnBlock.addActionListener(e -> game.spawnItem("block"));
    }
}