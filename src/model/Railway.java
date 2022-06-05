package model;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;
import java.util.List;

public class Railway {

	private Level lvl;
	private List<Case> way = new ArrayList<Case> ();
	
	public Railway(Level lvl) {
		this.lvl = lvl;
	}
	
	public boolean hasWay() {
		way.clear();
		int x = lvl.getStart().getCol(), y = lvl.getStart().getRow();
		List<String> elemPaths = lvl.getStart().getFixedElemPaths(), elemPaths2 = lvl.getFinish().getFixedElemPaths();
		String path = elemPaths.get(elemPaths.size()-1), pathFinish = elemPaths2.get(elemPaths2.size()-1);
		List<Case> nonVisitedCases = lvl.getFullCases();
		nonVisitedCases.add(lvl.getFinish());
		boolean caseAdded ;
		while (!way.contains(lvl.getFinish())) {
			caseAdded = false;
			for (Case c: nonVisitedCases) {
				String nextPath ;
				if (c == lvl.getFinish())
					nextPath = pathFinish;
				else
					nextPath = c.getElemPath();
				if (c.getCol() == x && c.getRow() == y+1 && (path.contains("rail_vert") || path.contains("leftdown") || path.contains("rightdown")))
					if (nextPath.contains("rail_vert") || nextPath.contains("leftup") || nextPath.contains("rightup"))
						y+=1;
					else
						return false;
				else
					if (c.getCol() == x && c.getRow() == y-1 && (path.contains("rail_vert") || path.contains("rightup") || path.contains("leftup")))
						if (nextPath.contains("rail_vert") || nextPath.contains("leftdown") || nextPath.contains("rightdown"))
							y-=1;
						else
							return false;
					else
						if (c.getRow() == y && c.getCol() == x+1 && (path.contains("rail_horiz") || path.contains("rightup") || path.contains("rightdown")))
							if (nextPath.contains("rail_horiz") || nextPath.contains("leftup") || nextPath.contains("leftdown"))
								x+=1;
							else
								return false;
						else
							if (c.getRow() == y && c.getCol() == x-1 && (path.contains("rail_horiz") || path.contains("leftup") || path.contains("leftdown")))
								if (nextPath.contains("rail_horiz") || nextPath.contains("rightup") || nextPath.contains("rightdown"))
									x-=1;
								else
									return false;
							else
								continue;
				way.add(c);
				nonVisitedCases.remove(c);
				path = nextPath;
				caseAdded = true;
				break;
			}
			if (!caseAdded)
				return false;
		}
		return true;
	}

	public List<Case> getWay() {
		return way;
	}
	
	public void drive() {
		boolean b = hasWay();
		System.out.println(b);
		if (b) {
			boolean right = false, down = false;
			for (int i = 0 ; i < way.size() ; i++) {
				double angle = 0;
				if (i < way.size()-1)
					if (way.get(i).getRow() != way.get(i+1).getRow())
						if (way.get(i).getRow() < way.get(i+1).getRow())
							down = true;
						else
							down = false;
					else
						if (way.get(i).getCol() < way.get(i+1).getCol())
							right = true;
						else
							right = false;
				
				String path = way.get(i).getElemPath();
				if (way.get(i) == lvl.getStart() || way.get(i) == lvl.getFinish()) {
					List<String> elemPaths = way.get(i).getFixedElemPaths();
					path = elemPaths.get(elemPaths.size()-1);
				}
				switch (path.substring(path.indexOf("elements/")+9)) {
				case "rail_horiz.png":
					if (!right)
						angle = 180;
					break;
				case "rail_vert.png":
					if (down)
						angle = 270;
					else
						angle = 90;
					break;
				case "rail_leftdown.png":
					if (down)
						angle = 315;
					else
						if (!right)
							angle = 135;
					break;
				case "rail_leftup.png":
					if (!down)
						angle = 45;
					else
						if (!right)
							angle = 225;
					break;
				case "rail_rightup.png":
					if (!down)
						angle = 135;
					else
						if (right)
							angle = 315;
					break;
				case "rail_rightdown.png":
					if (down)
						angle = 225;
					else
						if (right)
							angle = 45;
					break;
				default:
						
				}
					
				AffineTransform affTr = AffineTransform.getRotateInstance(Math.toRadians(angle), MainFrame.trainModel.getWidth()/2, MainFrame.trainModel.getHeight()/2);
				AffineTransformOp affTrOp = new AffineTransformOp(affTr, AffineTransformOp.TYPE_BILINEAR);
				way.get(i).setAffTrOp(affTrOp);
				way.get(i).setTrain(true);
				way.get(i).repaint();
				/*
				Graphics g = way.get(i).getGraphics();
				g.drawImage(affTrOp.filter(MainFrame.trainModel, null), 0, 0, way.get(i).getWidth(), way.get(i).getHeight(), null);
				g.dispose();*/
			}
			//lvl.getPanCase().repaint();	
		}
		else {
			
		}
		
	}
	
}
