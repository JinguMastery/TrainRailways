package model;

public class Lvl1_1 extends Level {
	
	public Lvl1_1(MainFrame frame) {
		super(10, 12, 2, frame);
		Element elem = new Element(MainFrame.projet+"/images/elements/rail_horiz.png", this);
		elem.setBounds(0, 0, 200, 200);
		getPanElem().add(elem);
		setSelectedElem(elem);
		Element elem2 = new Element(MainFrame.projet+"/images/elements/rail_leftup.png", this);
		elem2.setBounds(0, 200, 200, 200);
		getPanElem().add(elem2);
		String nomImage = null ;
		for (int i = 0 ; i < getLvlDim().height ; i++)
			for (int j = 0 ; j < getLvlDim().width ; j++) {
			if (i == 0 || i == getLvlDim().height-1 && j != 7)
				nomImage = "brique" ;
			else
				if (j == 0 && i != 5 || j == getLvlDim().width-1)
					nomImage = "brique" ;
				else
					nomImage = "sol" ;
			
			Case c = new Case(i, j, MainFrame.projet+"/images/blocks/"+nomImage+".png", this);
			if (i == 3 && j == 7) {
				c.addFixedElemPath(MainFrame.projet+"/images/elements/alarm_off.png");
				c.addFixedElemPath(MainFrame.projet+"/images/elements/rail_vert.png");
			}
			if (i == 5 && j == 0) {
				c.addFixedElemPath(MainFrame.projet+"/images/elements/arrow_right.png");
				c.addFixedElemPath(MainFrame.projet+"/images/elements/rail_horiz.png");
				setStart(c);
			}
			if (i == 5 && j == 7) {
				c.addFixedElemPath(MainFrame.projet+"/images/elements/spinplate_right.png");
			}
			if (i == 9 && j == 7) {
				c.addFixedElemPath(MainFrame.projet+"/images/elements/arrow_down.png");
				c.addFixedElemPath(MainFrame.projet+"/images/elements/rail_vert.png");
				setFinish(c);
			}
			getPanCase().add(c) ;
			addEmptyCase(c);
		}
		
	}

}
