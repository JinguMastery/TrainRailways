package model;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Case extends JButton {
	
	private String path, elemPath, prevElemPath;
	private List<String> fixedElemPaths = new ArrayList<String> () ;
	private Level lvl;
	private int width, height ;
	private boolean isFlipped, hasTrain;
	private int row, col ;
	private AffineTransformOp affTrOp ;
	
	public Case(int x, int y, String path, Level lvl) {
		row = x;
		col = y;
		this.path = path;
		this.lvl = lvl;
		elemPath = lvl.getSelectedElem().getPath();
		width = lvl.getPanCase().getWidth()/lvl.getLvlDim().width;
		height = lvl.getPanCase().getHeight()/lvl.getLvlDim().height;
		addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent ev) {
				if (!lvl.isEraseSelected()) {
					if (path.contains("sol") && lvl.getEmptyCases().contains(Case.this)) {
						boolean hasElem = false ;
						for (String s: fixedElemPaths)
							if (s.contains("arrow") || s.contains("alarm"))
								hasElem = true;
						if (!hasElem) {
							lvl.removeEmptyCase(Case.this);
							lvl.addFullCase(Case.this);
							lvl.addAction(true);
							lvl.incIndAct();
							lvl.getForward().setEnabled(false);
							lvl.getBackward().setEnabled(true);
							prevElemPath = elemPath;
						}
					}
				}
				else {
					if (lvl.getFullCases().contains(Case.this)) {
						lvl.addEmptyCase(Case.this);
						lvl.removeFullCase(Case.this);
						lvl.addAction(false);
						lvl.incIndAct();
						lvl.getForward().setEnabled(false);
						lvl.getBackward().setEnabled(true);
					}
				}
			}
			
		});
		addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
			}

			public void mouseMoved(MouseEvent e) {
				if (!lvl.isEraseSelected()) {
					Graphics g = getGraphics();
					if (isFlipped) {
						g.drawImage(MainFrame.kit.getImage(path), 0, 0, width, height, null) ;
						for (String s: fixedElemPaths)
							g.drawImage(MainFrame.kit.getImage(s), 0, 0, width, height, null) ;
						isFlipped = false;
					}
					g.drawImage(MainFrame.kit.getImage(lvl.getSelectedElem().getPath()), 0, 0, width, height, null);
					g.dispose();
				}
			}
			
		});
		addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3 && lvl.getEmptyCases().contains(Case.this)) {
					String path = lvl.getSelectedElem().getPath();
					switch (path.substring(path.indexOf("elements/")+9)) {
					case "rail_horiz.png":
						elemPath = MainFrame.projet+"/images/elements/rail_vert.png";
						break;
					case "rail_vert.png":
						elemPath = MainFrame.projet+"/images/elements/rail_horiz.png";
						break;
					case "rail_leftdown.png":
						elemPath = MainFrame.projet+"/images/elements/rail_leftup.png";
						break;
					case "rail_leftup.png":
						elemPath = MainFrame.projet+"/images/elements/rail_rightup.png";
						break;
					case "rail_rightup.png":
						elemPath = MainFrame.projet+"/images/elements/rail_rightdown.png";
						break;
					case "rail_rightdown.png":
						elemPath = MainFrame.projet+"/images/elements/rail_leftdown.png";
						break;
					default:
							
					}
					for (Case c: lvl.getEmptyCases())
						c.elemPath = elemPath;
					isFlipped = true ;
					lvl.getSelectedElem().setPath(elemPath);
					lvl.getSelectedElem().repaint();
				}
					
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}
			
		});
	}
	
	public Case(int x, int y, String path, Level lvl, String... paths) {
		this(x, y, path, lvl);
		for (String s: paths)
			fixedElemPaths.add(s);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(MainFrame.kit.getImage(path), 0, 0, width, height, null) ;
		for (String s: fixedElemPaths)
			g.drawImage(MainFrame.kit.getImage(s), 0, 0, width, height, null) ;
		if (lvl.getFullCases().contains(Case.this)) {
			boolean hasElem = false ;
			for (String s: fixedElemPaths)
				if (s.contains("arrow") || s.contains("alarm"))
					hasElem = true;
			if (!hasElem)
				g.drawImage(MainFrame.kit.getImage(elemPath), 0, 0, width, height, null);
		}
		if (hasTrain && affTrOp != null)
			g.drawImage(affTrOp.filter(MainFrame.trainModel, null), 0, 0, width, height, null);
	}

	public List<String> getFixedElemPaths() {
		return fixedElemPaths;
	}

	public void addFixedElemPath(String fixedElemPath) {
		fixedElemPaths.add(fixedElemPath);
	}
	
	public void removeFixedElemPath(String fixedElemPath) {
		fixedElemPaths.remove(fixedElemPath);
	}

	public String getElemPath() {
		return elemPath;
	}

	public void setElemPath(String elemPath) {
		this.elemPath = elemPath;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public String getPrevElemPath() {
		return prevElemPath;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public AffineTransformOp getAffTrOp() {
		return affTrOp;
	}

	public void setAffTrOp(AffineTransformOp affTrOp) {
		this.affTrOp = affTrOp;
	}

	public boolean hasTrain() {
		return hasTrain;
	}

	public void setTrain(boolean hasTrain) {
		this.hasTrain = hasTrain;
	}

}
