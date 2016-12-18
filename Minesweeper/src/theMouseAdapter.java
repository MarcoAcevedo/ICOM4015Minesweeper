import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class theMouseAdapter extends MouseAdapter {
	private static Random generator = new Random();
	static int mineQuantity =thePanel.numberOfMines;
	public int tilesCounter=0;
	public static HashMap<Integer,Integer> mineHolder = new HashMap<Integer,Integer>();
	boolean[][] mineXY=new boolean[mineQuantity][mineQuantity];
	
	
	
	public static void mineGenerator(){
		int buffer =0;
		for(int i=0; i<mineQuantity;i++){
			System.out.println("For Loop");
			buffer=generator.nextInt(81);
			if(mineHolder.containsValue(buffer)){
				do{
					buffer=generator.nextInt(81);
					
				}while(mineHolder.containsValue(buffer));
				mineHolder.put(i,buffer);
			}else{
					mineHolder.put(i,buffer);
			}
		}
		
	}
	
	
	public void minePosTranslate(){
		for(int i=0;i<mineHolder.size();i++){
			mineXY[mineXPos(i)][mineYPos(i)]=true;
		}
	}
	
	
	public int mineXPos(int i){ //DONE
		
		if(mineHolder.get(i)<10){return (mineHolder.get(i) -1);}
		else{
			if((mineHolder.get(i)%9)==0){return 8;}
			else{return ((mineHolder.get(i)%9)-1);}
		}
		
	}
	
	
	
	public int mineYPos(int i){//This is done
		if(mineHolder.get(i)<10){
			return 0;}
		else{
			if(mineHolder.get(i)%9==0){
				return ((mineHolder.get(i)/9)-1);
			}
			else{
				return(mineHolder.get(i)/9);
			}//working here
		}
	}
	
	
	
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			thePanel myPanel = (thePanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
			
		case 3:		//Right mouse button
			
			Component d = e.getComponent();
			while (!(d instanceof JFrame)) {
				d = d.getParent();
				if (d == null) {
					return;
				}
			}
			
			JFrame myFrameR = (JFrame) d;
			thePanel myPanelR = (thePanel) myFrameR.getContentPane().getComponent(0);
			Insets myInsetsR = myFrameR.getInsets();
			int x1R = myInsetsR.left;
			int y1R = myInsetsR.top;
			e.translatePoint(-x1R, -y1R);
			int xR = e.getX();
			int yR = e.getY();
			myPanelR.x = xR;
			myPanelR.y = yR;
			myPanelR.mouseDownGridX = myPanelR.getGridX(xR, yR);
			myPanelR.mouseDownGridY = myPanelR.getGridY(xR, yR);
			myPanelR.repaint();
			break;
			
		default:    //Some other button (2 = Middle mouse button, etc.)
					//Do nothing
			break;
		}
	}
	
	
	
	public void mouseReleased(MouseEvent e) {
		minePosTranslate();
		
		switch (e.getButton()) {
		
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			
			JFrame myFrame = (JFrame)c;
			thePanel myPanel = (thePanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
			}
			else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
				}
				else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
					}
					else {//Insert Code Here for left click action
						
						
						if(mineXY[myPanel.mouseDownGridX][myPanel.mouseDownGridY]){
							System.out.println("Mine Here You Loose");
							for(int i=0;i<9;i++){
								for(int j=0;j<9;j++){
									if(mineXY[i][j]){
										myPanel.colorArray[i][j]=Color.BLACK;
									}
								}
							}
							myPanel.repaint();
							JOptionPane.showMessageDialog(null, "You Loose");
							System.exit(0);
							
						}
						else{
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]=Color.GRAY;
							checkForBombs(myPanel,myPanel.mouseDownGridX,myPanel.mouseDownGridY);
							myPanel.repaint();
							for(int i=0;i<9;i++){
								for(int j=0;j<9;j++){
									if(myPanel.colorArray[i][j]!=Color.WHITE){
										tilesCounter++;
									}
								}
							}if(tilesCounter==81){JOptionPane.showMessageDialog(null, "You Win");
							System.exit(0);}else{tilesCounter=0;}
						}
					}
				}
			}
			myPanel.repaint();
			break;
			
		case 3://Right mouse button
			
			Component d = e.getComponent();
			while (!(d instanceof JFrame)) {
				d = d.getParent();
				if (d == null) {
					return;
				}
			}
			
			JFrame myFrameR = (JFrame)d;
			thePanel myPanelR = (thePanel) myFrameR.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsetsR = myFrameR.getInsets();
			int x1R = myInsetsR.left;
			int y1R = myInsetsR.top;
			e.translatePoint(-x1R, -y1R);
			int xR = e.getX();
			int yR = e.getY();
			myPanelR.x = xR;
			myPanelR.y = yR;
			int gridXR = myPanelR.getGridX(xR, yR);
			int gridYR = myPanelR.getGridY(xR, yR);
			
			if (((myPanelR.mouseDownGridX == -1) || (myPanelR.mouseDownGridY == -1))) {
				//Had pressed outside and released outside
			}
			else {
				if(((gridXR == -1) || (gridYR == -1))){
					//is releasing outside
					
				}
				else{
					if ((myPanelR.mouseDownGridX != gridXR) || (myPanelR.mouseDownGridY != gridYR)) {
					//Released the mouse button on a different cell where it was pressed
					}
					else {
						if(myPanelR.colorArray[myPanelR.mouseDownGridX][myPanelR.mouseDownGridY] == Color.WHITE){
							myPanelR.colorArray[myPanelR.mouseDownGridX][myPanelR.mouseDownGridY] = Color.RED;
						}
						else{
							if(myPanelR.colorArray[myPanelR.mouseDownGridX][myPanelR.mouseDownGridY]==Color.RED){
								myPanelR.colorArray[myPanelR.mouseDownGridX][myPanelR.mouseDownGridY] = Color.WHITE;
							}
						}
					}
				}
			}
			myPanelR.repaint();
			break;
			
		default://Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		
		}
	}



	private void checkForBombs(thePanel panel, int x, int y) {



		int counter = 0;

		if((x -1 >= 0 && x -1 < 9)
				&&  (y >= 0 && y < 9) 
				&& mineXY[x -1][y]){
			counter ++;
		} 
		if((x -1 >= 0 && x -1 < 9)
				&&  (y-1 >= 0 && y -1 < 9) 
				&& mineXY[x -1][ y -1]){
			counter ++;
		} 

		if((x  >= 0 && x < 9)
				&&  (y-1 >= 0 && y-1 < 9) 
				&& mineXY[x][ y-1]){
			counter ++;
		} 
		if((x +1 >= 0 && x + 1< 9)
				&&  (y >= 0 && y < 9) 
				&& mineXY[x + 1][ y]){
			counter ++;
		} 
		if((x + 1 >= 0 && x + 1 < 9)
				&&  (y + 1>= 0 && y + 1 < 9) 
				&& mineXY[x + 1][ y + 1]){
			counter ++;
		} 
		if((x >= 0 && x < 9)
				&&  (y + 1>= 0 && y + 1< 9) 
				&& mineXY[x][ y + 1]){
			counter ++;
		} 
		if((x -1 >= 0 && x -1 < 9)
				&&  (y + 1 >= 0 && y + 1 < 9) 
				&& mineXY[x -1][ y + 1]){
			counter ++;
		} 
		if((x + 1 >= 0 && x + 1 < 9)
				&&  (y - 1 >= 0 && y - 1 < 9) 
				&& mineXY[x + 1][ y - 1]){
			counter ++;
		} 
		//Set The Number on Tile
		if (counter > 0) {

			Color newColor = Color.LIGHT_GRAY;
			panel.colorArray[x][y] = newColor;	
			panel.proximity[x][y] =  counter + "";

		} else {
			//If Not Mine Found
			if((x - 1 >= 0 && x - 1 < 9)
					&&  (y >= 0 && y < 9) 
					&& !panel.colorArray[x - 1][y].equals(Color.GRAY) 
					&& !panel.colorArray[x - 1][y].equals(Color.RED) 
					&& !mineXY[x - 1][ y]){
				Color newColor =  Color.GRAY;
				panel.colorArray[x - 1][y] = newColor;
				checkForBombs(panel, x - 1, y);	
			} 
			if((x - 1 >= 0 && x - 1 < 9)
					&&  (y -1 >= 0 && y -1 < 9) 
					&& !panel.colorArray[x - 1][y -1].equals(Color.GRAY) 
					&& !panel.colorArray[x - 1][y -1].equals(Color.RED) 
					&& !mineXY[x - 1][ y -1]){
				Color newColor =  Color.GRAY;
				panel.colorArray[x - 1][y -1] = newColor;
				checkForBombs(panel, x - 1, y -1);
			} 
			if((x - 1 >= 0 && x - 1 < 9)
					&&  (y + 1 >= 0 && y + 1 < 9) 
					&& !panel.colorArray[x - 1][y + 1].equals(Color.GRAY) 
					&& !panel.colorArray[x - 1][y + 1].equals(Color.RED) 
					&& !mineXY[x - 1][ y + 1]){
				Color newColor =  Color.GRAY;
				panel.colorArray[x - 1][y + 1] = newColor;
				checkForBombs(panel, x - 1, y + 1);
			} 
			if((x >= 0 && x < 9)
					&&  (y -1 >= 0 && y -1 < 9) 
					&& !panel.colorArray[x][y -1].equals(Color.GRAY) 
					&& !panel.colorArray[x][y -1].equals(Color.RED) 
					&& !mineXY[x][ y -1]){
				Color newColor =  Color.GRAY;
				panel.colorArray[x][y -1] = newColor;
				checkForBombs(panel, x, y -1);
			} 
			if((x >= 0 && x < 9)
					&&  (y + 1 >= 0 && y + 1 < 9) 
					&& !panel.colorArray[x][y + 1].equals(Color.GRAY) 
					&& !panel.colorArray[x][y + 1].equals(Color.RED) 
					&& !mineXY[x][y + 1]){
				Color newColor =  Color.GRAY;
				panel.colorArray[x][y + 1] = newColor;
				checkForBombs(panel, x, y + 1);
			} 
			if((x + 1 >= 0 && x + 1 < 9)
					&&  (y >= 0 && y < 9) 
					&& !panel.colorArray[x + 1][y].equals(Color.GRAY) 
					&& !panel.colorArray[x + 1][y].equals(Color.RED) 
					&& !mineXY[x + 1][ y]){
				Color newColor =  Color.GRAY;
				panel.colorArray[x + 1][y] = newColor;
				checkForBombs(panel, x + 1, y);
			} 
			if((x + 1 >= 0 && x + 1 < 9)
					&&  (y - 1>= 0 && y -1 < 9) 
					&& !panel.colorArray[x + 1][y -1].equals(Color.GRAY) 
					&& !panel.colorArray[x + 1][y - 1].equals(Color.RED) 
					&& !mineXY[x + 1][y - 1]){
				Color newColor =  Color.GRAY;
				panel.colorArray[x + 1][y - 1] = newColor;
				checkForBombs(panel, x + 1, y - 1);
			} 
			if((x + 1 >= 0 && x + 1 < 9)
					&&  (y + 1 >= 0 && y + 1 < 9) 
					&& !panel.colorArray[x + 1][y + 1].equals(Color.GRAY) 
					&& !panel.colorArray[x + 1][y + 1].equals(Color.RED) 
					&& !mineXY[x + 1][ y + 1]){
				Color newColor =  Color.GRAY;
				panel.colorArray[x + 1][y + 1] = newColor;
				checkForBombs(panel, x + 1, y + 1);
			} 
		}
	}





	
	
}