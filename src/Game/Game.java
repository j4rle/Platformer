package Game;

import javax.swing.*;

public class Game{

	public static void main(String[] args) {
		JFrame window = new JFrame("QuestMan");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new GamePanel());
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
	}
}
