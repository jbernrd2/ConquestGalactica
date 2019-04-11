import edu.illinois.cs.cs125.lib.zen.Zen;
import java.util.LinkedList;

public class Menu extends CharMenu {
	Menu() {
		
	}
	Menu(int X, int Y, int Border, String BGColor, String TextColor, String ButtonColor) {
		xN = X; yN = Y; x = X; y = Y;
		bgColor = BGColor; textColor = TextColor;
		border = Border;
		buttonColor = ButtonColor;
	}
	Menu(int Border, String BGColor, String TextColor, String ButtonColor) {
		bgColor = BGColor; textColor = TextColor;
		buttonColor = ButtonColor;
	}
	Menu(int X, int Y, int Border, String BGColor, String TextColor, String ButtonColor, String Title) {
		xN = X; yN = Y; x = X; y = Y;
		bgColor = BGColor; textColor = TextColor;
		border = Border;
		buttonColor = ButtonColor;
		title = Title; n = 1;
	}
	
	//--------Class Variables-------//
	private String buttonColor,bgColor;
	private String textColor;
	private String title;
	
	private int x,y,xN,yN;
	private int height,width;
	private int numButtons = 0;
	private int border;
	private int n = 0; // This is for the extra shift induced when there is a title
	
	//--------Class functions-------//
	public void addButton(String text) {
			Button b = new Button(buttonColor,text,textColor,x,y + 50*(numButtons + 1 + n),width,45);
			buttonList.add(b);
			numButtons ++;

			updateMenu();
	}
	public void addButton(String text, String action) {
		Button b = new Button(buttonColor,text,textColor,x,y + 50*(numButtons + 1 + n),width,45);
		b.setAction(action); 
		buttonList.add(b); 
		numButtons ++;
		
		updateMenu();
	}

	public void showMenu(Game g) {
		// Fill menu background
		Zen.setColor(bgColor);
		Zen.fillRect(x - border,y - border,width + 2*border, height + 2*border);
		
		// Draw Title
		if (title != null) {
			Zen.setColor(textColor);
			Zen.drawText(title, x + 5, y + 25);
		}
		
		// Draw Buttons
		for (int i = 0; i < numButtons; i++) {
			buttonList.get(i).checkClickAndHighlight(g);	
			buttonList.get(i).drawButton(width);
		}
	}
	
	public void removeButton(String s) {
		int i = 0; String name;
		while (i < numButtons) {	
			name = buttonList.get(i).getName();
			if (name.equalsIgnoreCase(s)) {
				buttonList.remove(i);
				numButtons--;
				updateMenu();
				return;
			}
			i++;
		}
	}
	
	public void updateMenu() {
		int i = 0; int max = 17;
		while (i < numButtons) {
			buttonList.get(i).setXY(x, y + (i + n)*50);
			/*int l = buttonList.get(i).getName().length();
			if (l > max ) {
				max = l;
			}*/
			i++;
		}
		updateWidth();
		this.height = 45 + 50*(numButtons - 1 + n);
	}
	
	public void updateWidth() {
		int i = 0; int max = 17; int m;
		while (i < numButtons) {
			m = buttonList.get(i).getLength();
			if (m > max) {
				max = m;
			}
			i++;
		}
		if (title != null) {
			if (title.length() > max) {
				max = title.length();
			}
		}
		width = max*10;
	}

	//---Setters---//
	public void setXY(int X, int Y) {
		this.x = X; this.y = Y;
	}

}
