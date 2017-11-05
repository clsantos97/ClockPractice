package logic;

import java.awt.Color;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;

public class ThreadLabel implements Runnable {

	private JLabel label;
	private String name;

	public ThreadLabel(JLabel label, String name) {
		super();
		this.label = label;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			Random rand = new Random();
			for (;;) {
				float r = rand.nextFloat();
				float g = rand.nextFloat();
				float b = rand.nextFloat();
				Color c = new Color(r, g, b);
				label.setForeground(c);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.INFO, "End (" + name + ")");
		}
	}
}
