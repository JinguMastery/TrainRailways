package model;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public final static Toolkit kit = Toolkit.getDefaultToolkit() ;
	public final static String projet = System.getProperty("user.dir") ;
	public final static int largeur = kit.getScreenSize().width, hauteur = kit.getScreenSize().height ;
	public static BufferedImage trainModel = toBufferedImage(kit.getImage(projet+"/images/skins/train_redyellow.png"));
	
	
	public MainFrame() {
		setTitle("TRAIN RAILWAYS");
		setSize(largeur, hauteur) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		setResizable(false) ;
		setCursor(new Cursor(Cursor.HAND_CURSOR)) ;
		Container cont = getContentPane() ;
		cont.setLayout(null);
		Level lvl = new Lvl1_1(this) ;
		cont.add(lvl.getPanCase());
		cont.add(lvl.getPanElem());
		
		JMenuBar menu = new JMenuBar();
		JMenu level = new JMenu("LEVEL"), options = new JMenu("OPTIONS");
		JMenuItem restart = new JMenuItem("Restart"), drive = new JMenuItem("Drive"), pause = new JMenuItem("Pause"), quit = new JMenuItem("Quit"),
				help = new JMenuItem("Help"), gotolastcp = new JMenuItem("Go to last cp"), build = new JMenuItem("Stop");
		quit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		level.add(restart); level.add(drive); level.add(build); level.add(pause); level.add(gotolastcp); level.add(quit);
		options.add(help);
		menu.add(level); menu.add(options);
		setJMenuBar(menu);
		
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}
	
	public static BufferedImage toBufferedImage(Image im) {
		// convertit une image en une instance de BufferedImage
        
		if (im instanceof BufferedImage)
        	return (BufferedImage)im ;
        else {
        	int w, h;
        	do {
        		w = im.getWidth(null);
        		h = im.getHeight(null);
        	}
        	while (w == -1 || h == -1);
        	BufferedImage buffIm = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB) ;
        	Graphics g = buffIm.createGraphics() ;
        	g.drawImage(im, 0, 0, null) ;
        	g.dispose() ;
        	return buffIm ;
        }
	}

	public static void setTrainModel(String path) {
		
	}
	
}
