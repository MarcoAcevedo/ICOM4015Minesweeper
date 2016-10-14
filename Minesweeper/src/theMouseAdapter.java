import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class theMouseAdapter extends MouseAdapter {
	private static Random generator = new Random();
	static int mineQuantity =20;
	static int[] minecellholder = new int[mineQuantity];
	boolean[][] mineXY=new boolean[mineQuantity][mineQuantity];
	
	public static void mineGenerator(int quantity, int buffer){
	
	int temp1;
	int temp2;
	for(int i=0;i<quantity;i++){
		for(int j=0;j<buffer;j++){
			if(i==j){
			}
			else{
				do{	
				minecellholder[i] = generator.nextInt(81);
				temp1 = minecellholder[i];
				temp2 = minecellholder[j];
				System.out.println("Im working");
				
				}while(temp1 == temp2 );
			}
			
		}
	}
	return;
}
	
	public int mineXPos(int i){//DONE
		
		if(minecellholder[i]<10){return (minecellholder[i] -1);}
		else{
			if((minecellholder[i]%9)==0){return 8;}
			else{return ((minecellholder[i]%9)-1);}
		}
		
	}
	
	
	public int mineYPos(int i){
		if(minecellholder[i]<10){//This is done
			return 0;}
		else{
			if(minecellholder[i]%9==0){
				return ((minecellholder[i]/9)-1);
			}
			else{
				return(minecellholder[i]/9);
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
						
						for(int i=0;i<mineQuantity;i++){
							mineXY[mineXPos(i)][mineYPos(i)]=true;
						}
						if(mineXY[myPanel.mouseDownGridX][myPanel.mouseDownGridY]){
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]=Color.BLACK;
							myPanel.repaint();
							JOptionPane.showMessageDialog(null, "You Loose");
							System.exit(0);
							System.out.println("Mine Here You Loose");
						}
						else{
							System.out.println("No Mines Here");
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]=Color.LIGHT_GRAY;
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
							//if(myPanelR.colorArray[myPanelR.mouseDownGridX][myPanelR.mouseDownGridX]==Color.RED){
								myPanelR.colorArray[myPanelR.mouseDownGridX][myPanelR.mouseDownGridY] = Color.WHITE;
							//}
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

}