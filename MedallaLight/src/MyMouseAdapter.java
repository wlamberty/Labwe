import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private int flags = 0;


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
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
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
			Component RClick = e.getComponent();
			while (!(RClick instanceof JFrame)) {
				RClick = RClick.getParent();
				if (RClick == null) {
					return;
				}
			}
			JFrame myFrameRClick = (JFrame) RClick;
			MyPanel myPanelRClick = (MyPanel) myFrameRClick.getContentPane().getComponent(0);
			Insets myInsetsRClick = myFrameRClick.getInsets();
			int x1RClick = myInsetsRClick.left;
			int y1RClick = myInsetsRClick.top;
			e.translatePoint(-x1RClick, -y1RClick);
			int xRClick = e.getX();
			int yRClick = e.getY();
			myPanelRClick.x = xRClick;
			myPanelRClick.y = yRClick;
			myPanelRClick.mouseDownGridX = myPanelRClick.getGridX(xRClick, yRClick);
			myPanelRClick.mouseDownGridY = myPanelRClick.getGridY(xRClick, yRClick);
			myPanelRClick.repaint();
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
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
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
			if(!myPanel.winGame()){
				if(!myPanel.endGame()){
					if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
						//Had pressed outside
						//Do nothing
					} else {
						if ((gridX == -1) || (gridY == -1)) {
							//Is releasing outside
							//Do nothing
						} else {
							if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
								//Released the mouse button on a different cell where it was pressed
								//Do nothing
							} else {
								//Released the mouse button on the same cell where it was pressed

								if(!myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED)){  //if square is flagged cannot color over it

									if(!myPanel.mineCompare(myPanel.mouseDownGridX, myPanel.mouseDownGridY)){

										myPanel.isZero(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
										myPanel.notHideNumber[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = true;
										myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.LIGHT_GRAY;

										myPanel.repaint();

									}

								}
 

							}

						}
					}
				}
				if(myPanel.endGame()){
					System.out.println("You Lost");
				}
			}
			if(myPanel.winGame()){
				System.out.println("You Won");
			}
			//myPanel.repaint();
			break;

		case 3:		//Right mouse button
			Component RClick = e.getComponent();
			while (!(RClick instanceof JFrame)) {
				RClick = RClick.getParent();
				if (RClick == null) {
					return;
				}
			}
			JFrame myFrameRClick = (JFrame)RClick;
			MyPanel myPanelRClick = (MyPanel) myFrameRClick.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsetsRClick = myFrameRClick.getInsets();
			int x1RClick = myInsetsRClick.left;
			int y1RClick = myInsetsRClick.top;
			e.translatePoint(-x1RClick, -y1RClick);
			int xRClick = e.getX();
			int yRClick = e.getY();
			myPanelRClick.x = xRClick;
			myPanelRClick.y = yRClick;
			int gridXRClick = myPanelRClick.getGridX(xRClick, yRClick);
			int gridYRClick = myPanelRClick.getGridY(xRClick, yRClick);
			if(!myPanelRClick.endGame()){
				if ((myPanelRClick.mouseDownGridX == -1) || (myPanelRClick.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridXRClick == -1) || (gridYRClick == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanelRClick.mouseDownGridX != gridXRClick) || (myPanelRClick.mouseDownGridY != gridYRClick)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							//Released the mouse button on the same cell where it was pressed

							if(!myPanelRClick.colorArray[myPanelRClick.mouseDownGridX][myPanelRClick.mouseDownGridY].equals(Color.RED)){ //Controls the amount of possible red flags
								if(flags<10){
									flags++;
									myPanelRClick.colorArray[myPanelRClick.mouseDownGridX][myPanelRClick.mouseDownGridY] = Color.RED;
									myPanelRClick.repaint();
								}

							}
							else{ //In charge of reducing counter when red flag is removed
								flags--;
								myPanelRClick.colorArray[myPanelRClick.mouseDownGridX][myPanelRClick.mouseDownGridY] = Color.WHITE;
								myPanelRClick.repaint();
							}


						}
					}
				}
			}


			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}

}