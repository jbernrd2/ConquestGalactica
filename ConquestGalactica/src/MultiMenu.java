import edu.illinois.cs.cs125.lib.zen.Zen;

public class MultiMenu extends Menu{
	MultiMenu(int X, int Y, int Border, String BGColor, String TextColor, String ButtonColor, int NumColumns, int Width, int Height) {
		xN = X; yN = Y; x = X; y = Y;
		bgColor = BGColor; textColor = TextColor;
		border = Border;
		numColumns = NumColumns;
		buttonColor = ButtonColor;
		width = Width; height = Height;
		
	}

	//--------Class Variables-------//
	private String buttonColor,bgColor;
	private String textColor;
	private String title;	
	private int x,y,xN,yN;
	private int height,width;
	private int calculatedWidth;
	private int numButtons = 0;
	private int numX, numY;
	private int border;
	private int numColumns;
	
	//--------Class functions-------//
	public void addButton(String text) {
		Button b = new Button(buttonColor,text,textColor,x,y + 50*(numButtons + 1),width,45);
		buttonList.add(b);
		numButtons ++;

		updateMenu();
	}
	public void addButton(String text, String action) {
		Button b = new Button(buttonColor,text,textColor,x,y + 50*(numButtons + 1),width,45);
		b.setAction(action); 
		buttonList.add(b); 
		numButtons ++;
		
		updateMenu();
	}

	public void addButtons(String[] Characters) {
		for (int i = 0; i < Characters.length; i++) {
			this.addButton(Characters[i],"Team Select"); 
		}
	}
	
	public void organizeMenu() {
		calculatedWidth = findButtonLength(); int i = 0; 
		int xLoc = x; int yLoc = y; //Start at top left corner of menu
		while (i < numButtons) {
			if (yLoc < y + height - 5) {
				buttonList.get(i).setXY(xLoc, yLoc + 5);
				buttonList.get(i).setWidth(calculatedWidth);
				yLoc += 50;
				i++;
			} else {
				yLoc = y; xLoc += calculatedWidth + 5;
			}
		}
	}
	
	public int findButtonLength() { //Find maximum string length of all the buttons
		int i = 0, width = 0; int l;
		while (i < numButtons) {
			l = buttonList.get(i).getName().length()*10;
			if (l > width) {
				width = l;
			} i++;
		}
		return width;
	}
	
	public void showMenu(Game g) {
		// Fill menu background
		Zen.setColor(bgColor); String s; 
		Zen.fillRect(x - border,y - border,width + 2*border, height + 2*border);
		
		// Draw Title
		if (title != null) {
			Zen.setColor(textColor);
			Zen.drawText(title, x + 5, y + 25);
		}
		
		// Draw Buttons and check for character selections
		g.activeName = null;
		for (int i = 0; i < buttonList.size(); i++) {
			s = buttonList.get(i).chooseCharacter(g);
			if (s != null) {
				buttonList.remove(i);
				g.activeName = s;
			}
			buttonList.get(i).drawButton();				
		}
	}

	// Getters
	public int getCalculatedWidth() {
		return calculatedWidth;
	}
}

