import java.io.File;
import java.util.Stack;
import java.util.LinkedList;
import edu.illinois.cs.cs125.lib.zen.Zen;

public class Planet {
	//-Constructors-//
	public Planet() {		
	}
	
	public Planet(String Name, float xPosition, float yPosition, float xZ, float yZ) {
		new Planet();
		this.setPos(xPosition,yPosition);
		this.setZone(xZ, yZ);
		this.setName(Name);
		this.setDetailLength(Name.length()*10 + 10);
		maxD = name.length();
		this.setMenu(new Menu((int) (xZ + xPosition) ,(int) (yZ + yPosition), 2, "Black", "Black", "Pink"));
		this.addMenuButton("Back");
	}
	
	//-----Class Variables-----//
	private float x; //x position
	private float y; //y position
	private float r = 50;
	private float mZoneX = 50; private float mZoneY = 50;
	private float xZone; //Upper left corner of area to show text
	private float yZone; // Upper left corner of area to show text
	private Planet[] neighbors = new Planet[5];
	private LinkedList<Character> inhabitants = new LinkedList<>();
	private int numInhab = 0; private int numShips;
	private int maxInhab = 10;
	private int detailLength;
	private String name; private String action = "planetMenu";
	private CharMenu charMenu;
	private int maxD;
	private Stack<Ship> dockedShips = new Stack<>();
	private Menu menu;
	
	String color = "dark orange";
	
	
	//-----Class Functions-----//
	void draw() {
		Zen.setColor(color);
		Zen.fillOval((int) this.x,(int) this.y,(int) (2*r),(int) (2*r));
	}
	
	void showMenu(Game g) {
		g.activeMenu = this.getMenu();
		menu.showMenu(g);
	}
	
	void addMenuButton(String s) {
		this.menu.addButton(s);
	}
	
	void highlight() {
		Zen.setColor("red");
		Zen.fillOval((int) this.x - 5,(int) this.y - 5,(int) (2*r) + 10,(int) (2*r) + 10);
		Zen.setColor("black");
	}

	void addNeighbor(Planet p) {
		for (int i = 0; i < 5; i++) {
			if (this.neighbors[i] == null) {
				this.neighbors[i] = p;
				i = 6;
			}
		} 		
	}
	
	boolean checkMouse(int mouseX, int mouseY) {		
		float dsquared = (x + r - mouseX)*(x + r - mouseX) + (y + r - mouseY)*(y + r - mouseY);
		if (dsquared <= r*r) {
			return true;			
		} else {
			return false;
		}
		
	}
	
	boolean checkIfClicked(Game g) {
		if (g.isMouseClicked) {
			if (checkMouse(g.mouseX,g.mouseY) ) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	void addInhab(Character c) {
		if (numInhab < maxInhab) {		
			inhabitants.add(c);
			this.charMenu.addButton(c);		
			c.setHost(this); 
			if (numInhab == 0) { this.menu.addButton("Move Characters","Move Characters"); }
			numInhab++;
		}
		else {
			System.out.print("You have reached the maximum number of Inhabitants"); 
		}
		update();
	}
	
	void removeInhab(String s) {
		int i = 0; String name;
		while (i < numInhab) {
			Character c = inhabitants.get(i);
			name = c.getName();
			if (name.equalsIgnoreCase(s)) {
				inhabitants.remove(c);
				this.charMenu.removeButton(i);
				numInhab--;
				
				if (numInhab == 0) {
					menu.removeButton("Move Characters");
				}
				update();
				return;
			}
			i++;
		}
		System.out.print("Character not found");
	}
	
	void drawNeighbors( ) {
		Zen.setColor("black");
		for (int i = 0; i < neighbors.length; i++) {
			if (neighbors[i] != null) {
				Zen.drawLine((int) (x + r), (int) (y + r), (int) (neighbors[i].getX() + r), (int) (neighbors[i].getY() + r));		
			}
		}
		
	}
	
	void showCharMenu(Game g) {
		this.charMenu.showMenu(g);
	}

	private void checkBoundary() {
		if (x + xZone + detailLength + 10 > 1900) {
			xZone = -xZone;
		}
		if (y + yZone + 50*numInhab > 1000) {
			yZone = -yZone;
		}
	}
	
	void showDetails() {
		Zen.setColor("black");
		Zen.fillRect((int) (xZone + x) - 2, (int) (yZone + y) - 25, detailLength, 40 + 25*numInhab);
		Zen.drawLine((int) (xZone + x) - 2, (int) (yZone + y), (int) (xZone + x) - 2 + detailLength, (int) (yZone + y));
		Zen.setColor("white");
		Zen.fillRect((int) (xZone + x) -1, (int) (yZone + y) - 24, detailLength - 2, 40 - 5);
		if (numInhab != 0) {
			Zen.fillRect((int) (xZone + x) -1, (int) (yZone + y) + 15, detailLength - 2, 25*numInhab - 1);
		}
		
		Zen.setColor("black");
		Zen.drawText(this.name, (int) (this.x +this.xZone), (int) (this.y + this.yZone)); //Show name
		int count = 0;
		for (int i = 0; i < numInhab; i++) {
			inhabitants.get(i).show(i+1, (int) xZone, (int) yZone + 10);
		}	
		
	}

	public void update() {
		int max = maxD; int i = 0; int l;
		while (i < numInhab) {
			l = inhabitants.get(i).getName().length();
			if (l > max) {
				max = l; 
			}
			i++;
		}
		this.detailLength = max*10 + 10;
		checkBoundary();
		
	}
	
	public void addShip(Ship s) {
		s.moveShipTo(this);
		numShips++;
		
	}

	public Ship getShipClick()	{ //Freezes the game while you pick a ship
		boolean looping = true; int i;
		while (looping) {
			i = 0;
			while (i < numShips) {
				if (dockedShips.get(i).isClicked()) {
					return dockedShips.get(i);
				}
			}
		} 
		return null;
	}
	
	public Character getCharAt(int i) {
		return inhabitants.get(i);
	}

	//Setters
	void setPos(float xPos, float yPos) {
		this.x = xPos; this.y = yPos;
	}
	void setZone(float xPos, float yPos) {
		this.xZone = xPos; this.yZone = yPos;
	}
	void setName(String Name) {
		this.name = Name;
	}
	void setDetailLength(int n) {
		this.detailLength = n;
	}
	void setMenu(Menu m) {
		this.menu = m;
		this.menu.setXY((int) (xZone + x), (int) (yZone + y));
		
	}
	void setCharMenu(String Title, String TitleColor, String buttonColor, String BGColor, String TextColor, Planet P) {
		this.charMenu = new CharMenu(Title,TitleColor,buttonColor,BGColor,TextColor,P);
	}
	
	// Getters
	float getX() {
		return this.x;
	}
	float getY() {
		return this.y;
	}
	float getR() {
		return this.r;
	}
	int getNumInhab() {
		return this.numInhab;
	}
	String getAction() {
		return this.action;
	}
	String getName() {
		return this.name;
	}
	Menu getMenu() {
		return this.menu;
	}
	int getXZone() {
		return (int) this.xZone;
	}
	int getYZone() {
		return (int) this.yZone;
	}
	CharMenu getCharMenu() {
		return this.charMenu;
	}
	Planet[] getNeighbors() {
		return this.neighbors;
	}
	LinkedList<Character> getInhabitants() {
		return this.inhabitants;
	}

}
