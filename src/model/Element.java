package model;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Element extends JButton {

	private String path, prevPath;
	private Level lvl;
	
	public Element(String path, Level lvl) {
		this.path = path;
		this.prevPath = path;
		this.lvl = lvl;
		addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent ev) {
				lvl.setEraseSelected(false);
				if (lvl.getSelectedElem() != Element.this) {
					lvl.getSelectedElem().prevPath = lvl.getSelectedElem().path;
					lvl.setSelectedElem(Element.this);
					for (Case c: lvl.getEmptyCases())
						c.setElemPath(prevPath);
				}
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(MainFrame.kit.getImage(path), 0, 0, 200, 200, null) ;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
