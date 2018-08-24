package main;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Application {

	public static void main(String[] args) {		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {				
				GUIFrame window = new GUIFrame("Test");
				
				window.setSize(500, 500);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setVisible(true);
				window.setLocationRelativeTo(null);
				window.addComponentListener(new ComponentListener(){
					
					@Override
					public void componentResized(ComponentEvent arg0) {
						window.catchResize();
					}

					@Override
					public void componentHidden(ComponentEvent arg0) {}

					@Override
					public void componentMoved(ComponentEvent arg0) {}

					@Override
					public void componentShown(ComponentEvent arg0) {}
				});
			}
		});
	}
}