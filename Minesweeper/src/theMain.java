import javax.swing.JFrame;

public class theMain {
	public static void main(String[] args) {
		
		JFrame myFrame = new JFrame("MineCreeper");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(400, 400);
		
		//theMouseAdapter game = new theMouseAdapter();

		thePanel myPanel = new thePanel();
		myFrame.add(myPanel);

		theMouseAdapter myMouseAdapter = new theMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);
	}

}
