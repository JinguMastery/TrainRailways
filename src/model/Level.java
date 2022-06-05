package model;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Level {
	
	private Dimension lvlDim ;
	private int nCases, nElem, indAct ;
	private JPanel panCase = new JPanel(), panElem = new JPanel();
	private Element selectedElem ;
	private List<Case> emptyCases = new ArrayList<Case> (), fullCases = new ArrayList<Case> ();
	private List<Boolean> actions = new ArrayList<Boolean> ();
	private Case start, finish;
	private Railway way ;
	private JButton backward, forward, erase, drive, build;
	private boolean eraseSelected;
	
	
	@SuppressWarnings("serial")
	public Level(int x, int y, int n, MainFrame frame) {
		lvlDim = new Dimension(y, x) ;
		nCases = x*y ;
		nElem = n;
		panCase.setLayout(new GridLayout(x, y)) ;
		panElem.setLayout(null);
		panCase.setBounds(0, 0, MainFrame.largeur-200, MainFrame.hauteur-60);
		panElem.setBounds(MainFrame.largeur-200, 0, 200, MainFrame.hauteur-60);
		panElem.setBackground(Color.gray);
		way = new Railway(this);
		backward = new JButton() {
				
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(MainFrame.kit.getImage(MainFrame.projet+"/images/elements/arrow_backward.png"), 0, 0, 100, 100, null) ;
				}
				
		};
		forward = new JButton() {
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainFrame.kit.getImage(MainFrame.projet+"/images/elements/arrow_forward.png"), 0, 0, 100, 100, null) ;
			}
			
		};
		erase = new JButton() {
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainFrame.kit.getImage(MainFrame.projet+"/images/elements/bulldozer.png"), 0, 0, 200, 200, null) ;
			}
			
		};
		drive = new JButton() {
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainFrame.kit.getImage(MainFrame.projet+"/images/elements/go.png"), 0, 0, 100, 100, null) ;
			}
			
		};
		build = new JButton() {
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(MainFrame.kit.getImage(MainFrame.projet+"/images/elements/stop.png"), 0, 0, 100, 100, null) ;
			}
			
		};
		backward.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (indAct > 0)
					indAct--;
				boolean notErase = actions.get(indAct);
				Case c;
				if (notErase) {
					c = fullCases.get(fullCases.size()-1);
					fullCases.remove(c);
					emptyCases.add(c);
				}
				else {
					c = emptyCases.get(emptyCases.size()-1);
					c.setElemPath(c.getPrevElemPath());
					fullCases.add(c);
					emptyCases.remove(c);
				}
				forward.setEnabled(true);
				if (indAct == 0)
					backward.setEnabled(false);
				c.repaint();
			}
			
		});
		forward.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean notErase = actions.get(indAct);
				Case c;
				if (notErase) {
					c = emptyCases.get(emptyCases.size()-1);
					c.setElemPath(c.getPrevElemPath());
					fullCases.add(c);
					emptyCases.remove(c);
				}
				else {
					c = fullCases.get(fullCases.size()-1);
					fullCases.remove(c);
					emptyCases.add(c);
				}
				backward.setEnabled(true);
				if (indAct < actions.size())
					indAct++;
				if (indAct == actions.size())
					forward.setEnabled(false);
				c.repaint();
			}
			
		});
		erase.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				eraseSelected = true;
			}
			
		});
		drive.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				way.drive();
			}
			
		});
		build.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		backward.setEnabled(false);
		forward.setEnabled(false);
		backward.setBounds(0, panElem.getHeight()-200, 100, 100);
		forward.setBounds(100, panElem.getHeight()-200, 100, 100);
		erase.setBounds(0, panElem.getHeight()-400, 200, 200);
		drive.setBounds(0, panElem.getHeight()-100, 100, 100);
		build.setBounds(100, panElem.getHeight()-100, 100, 100);
		panElem.add(backward); panElem.add(forward); panElem.add(erase); panElem.add(drive); panElem.add(build);
	}
	
	public JPanel getPanCase() {
		return panCase ;
	}
	
	public JPanel getPanElem() {
		return panElem ;
	}

	public Dimension getLvlDim() {
		return lvlDim;
	}

	public int getnCases() {
		return nCases;
	}

	public int getnElem() {
		return nElem;
	}

	public Element getSelectedElem() {
		return selectedElem;
	}

	public void setSelectedElem(Element selectedElem) {
		this.selectedElem = selectedElem;
	}
	
	public void addEmptyCase(Case c) {
		emptyCases.add(c);
	}
	
	public void removeEmptyCase(Case c) {
		emptyCases.remove(c);
	}

	public List<Case> getEmptyCases() {
		return emptyCases;
	}
	
	public void addFullCase(Case c) {
		fullCases.add(c);
	}
	
	public void removeFullCase(Case c) {
		fullCases.remove(c);
	}

	public List<Case> getFullCases() {
		return fullCases;
	}
	
	public void addAction(boolean b) {
		actions.add(b);
	}

	public List<Boolean> getActions() {
		return actions;
	}

	public Case getStart() {
		return start;
	}

	public void setStart(Case start) {
		this.start = start;
	}

	public Case getFinish() {
		return finish;
	}

	public void setFinish(Case finish) {
		this.finish = finish;
	}

	public JButton getBackward() {
		return backward;
	}

	public JButton getForward() {
		return forward;
	}

	public JButton getErase() {
		return erase;
	}

	public boolean isEraseSelected() {
		return eraseSelected;
	}

	public void setEraseSelected(boolean eraseSelected) {
		this.eraseSelected = eraseSelected;
	}

	public int getIndAct() {
		return indAct;
	}

	public void incIndAct() {
		indAct++;
	}
	
}

