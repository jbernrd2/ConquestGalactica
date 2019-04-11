import edu.illinois.cs.cs125.lib.zen.Zen;
import java.util.LinkedList;
import java.util.Stack;

public class CharMenu {
	//------------Constructors-------------//
	public CharMenu() {
	}
	
	public CharMenu(String Title, String TitleColor, String ButtonColor, String BGColor, String TextColor, Planet P) {
		title = Title; titleColor = TitleColor; 
		bgColor = BGColor; textColor = TextColor;
		buttonColor = ButtonColor;
		planet = P;
		x = planet.getXZone() + (int) planet.getX(); y = planet.getYZone() + (int) planet.getY();
		width = title.length()*10; height = 45; xI = x; yI = y;
	}	
	
	
	// ------------------ Class Variables ---------------------//
	private String title; private String titleColor;
	private String buttonColor;
	private String bgColor;
	private String textColor;
	private int unitLength = 0;
	public LinkedList<Button> buttonList = new LinkedList<>();
	private Planet planet;      // The planet that this menu belongs too
	
	// Dimensions and location
	private int height;
	private int width;
	private int border = 5;
	private int x,y;
	private int xI,yI;
	private int numCharacters = 0;
	
	
	
	// ------------------ Class functions --------------------//
	public void addButton(Character c) {
		Button b = new Button(buttonColor,c.getName(),textColor,x,y + 50*(numCharacters + 1),width,45);
		buttonList.add(b);
		numCharacters ++;
		
		updateMenu();
	}

	public void removeButton(int i) { //Removes button at index i
		buttonList.remove(i);
		numCharacters--;
		
		updateMenu();
	}
	
	public void showMenu(Game g) {
		// Fill menu background
		Zen.setColor(bgColor);
		Zen.fillRect(x - border,y - border,width + 2*border, height + 2*border);
		
		// Draw title
		Zen.setColor(titleColor);
		Zen.drawText(title, x + 6, y + 27);
		
		// Draw Buttons
		for (int i = 0; i < numCharacters; i++) {
			buttonList.get(i).checkClickAndHighlight(g);	
			buttonList.get(i).drawButton(width);
		}
	}

	public void updateMenu() {
		int i = 0; int max = 17;
		while (i < numCharacters) {
			buttonList.get(i).setXY(x, y + (i)*50);
			int l = buttonList.get(i).getName().length();
			if (l > max ) {
				max = l;
			}
			i++;
		}
		this.width = 10*max;
		this.height = 45 + 50*numCharacters;
		checkBoundary();
	}
	
	public void checkBoundary() {
		if (xI + width + 10 > 1900) {
			x = xI - 300;
		} else { x = xI; }
		if (yI + height + 10 > 1000) {
			y = yI - 200 - 50*numCharacters;
		} else { y = yI; }
	}
	//------Getters-----//
	public int getNumCharacters() {
		return this.numCharacters;
	}
	public LinkedList<Button> getButtonList() {
		return this.buttonList;
	}
	
	//------Setters-----//
	public void setPlanet(Planet P) {
		planet = P;
	}
}
