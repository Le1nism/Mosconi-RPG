package main;

import javax.swing.JFrame;

public class Main{
    public static void main(String[] args){
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Chiusura finestra con X
        window.setResizable(false); // Dimensione finestra non modificabile
        window.setTitle("MosconiRPG v0.6.1-beta"); // Nome finestra

        GamePanel gamePanel = new GamePanel(); 
        window.add(gamePanel); // Aggiungi il pannello alla finestra

        // Permette di vedere la finestra
        window.pack(); 

        // Finestra visibile e spawnata al centro
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}