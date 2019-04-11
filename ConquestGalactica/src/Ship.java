import java.io.File;
import java.util.LinkedList;

import edu.illinois.cs.cs125.lib.zen.Zen;

public class Ship extends Planet{

	// Constructors
	public Ship() {
	}
	
	public Ship(Planet p, Team t) {
		activePlanet = p; activeTeam = t; 
		shipColor = t.getColor(); double r = p.getR();	
		cogX = p.getX() + r; cogY = p.getY() + r;
	}
	
	// Class variables
	Planet activePlanet;
	Team activeTeam;
	
	long t0 = 0;
	long baseTime = 0;
	double orbitRadius = 125;
	double orbitAngle = 0;
	double speed = 0;
	double vMax = 1;
	double shipRadius;
	double cogX, cogY;
	private int numInhab = 0; private int maxInhab = 5;
	private Planet hostPlanet;
	
	private LinkedList<Character> inhabitants = new LinkedList<>();
	private CharMenu charMenu;
	
	int diam = 30;
	int x; int y;

	
	String shipColor;
	boolean steadyState = false;
	
	
	// Class functions
	public void drawShip() {
		updatePosition();
		Zen.setColor("black");
		Zen.fillOval(x,y,diam,diam);
		
		Zen.setColor(shipColor);
		Zen.fillOval(x + 1, y + 1, diam - 2, diam - 2);
	}
	
	public void updatePosition() {
		long dT = System.currentTimeMillis() - t0;
		/*if (steadyState == true) {
			orbitAngle += 0.01*vMax;  
		} else {
			float t = (float) System.currentTimeMillis() - baseTime;
			speed = t/(t + (float) 1)*vMax;
			orbitAngle += speed*t;
			if (speed > vMax) {
				steadyState = true;
			}
		}*/
		orbitAngle += 0.01;
		x = (int) (orbitRadius*Math.cos(orbitAngle) - shipRadius + cogX);
		y = (int) (orbitRadius*Math.sin(orbitAngle) - shipRadius + cogY);	
	}
	
	public void updateShip(Team t, Planet p) {		
		activeTeam = t; activePlanet = p;
		cogX = activePlanet.getX(); cogY = activePlanet.getY();
	}
	
	public void addInhab(Character c) {
		if (numInhab < maxInhab) {		
			inhabitants.add(c);
			this.charMenu.addButton(c);		
			c.setHost(this); 
			numInhab++;
		}
		else {
			System.out.print("You have reached the maximum number of Inhabitants"); 
		}
		update();
	}

	public void moveShipTo(Planet p) {
		this.hostPlanet = p; cogX = p.getX(); cogY = p.getY();
	}
	
	public boolean isClicked( ) {
		int x = Zen.getMouseClickX(); int y = Zen.getMouseClickY();
		return checkMouse(x,y);
	}
}
