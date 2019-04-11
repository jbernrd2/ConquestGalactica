//  --------- Imports ----------- //
import java.io.*;
import java.awt.event.KeyEvent;
import edu.illinois.cs.cs125.lib.zen.Zen;

public class ConquestGalactica {
	//--------------Static Variables--------------//
	public static boolean menuOpen;
	
	//--------------Functions--------------//
	public static void drawBackGround() {
		//Draw Background
    	Zen.setColor("light blue");
    	Zen.fillRect(0, 0, 1900, 1000);
    	Zen.setColor("Black");
	}
	
	public static boolean checkHighlight(Planet p) {
		float x = p.getX(); float y = p.getY(); float r = p.getR();
		float mouseX = Zen.getMouseX(); float mouseY = Zen.getMouseY();
		
		float dsquared = (x + r - mouseX)*(x + r - mouseX) + (y + r - mouseY)*(y + r - mouseY);
		if (Math.pow(dsquared, 0.5) <= r) {
			return true;
		}
		return false;
	}
	
	public static Planet[] loadMap(String mapFile) {
		// Initialize Variables
		int numPlanets = 7;
        Planet[] PlanetList = new Planet[numPlanets];
        int[][] nArray = new int[0][0];
        String mainMenuString = "";
		String address = "C:\\Users\\burnt\\eclipse-workspace\\ConquestGalactica\\Resources\\";
		
		mapFile = address + mapFile;
		
		try {
        	BufferedReader br = new BufferedReader(new FileReader(mapFile)); 
        	String st = br.readLine(); 
        	String locations = st.split("@")[0]; String[] neighbors = (st.split("@")[1]).split(";");
        	
        	// Find locations of planets, and put all planets into array
        	String[] Parray = locations.split(";"); nArray = new int[neighbors.length][2];
        	for (int i = 0; i < Parray.length; i++) {
        		
        		String position = Parray[i]; String[] plist = position.split(","); 
        		float x = Float.valueOf(plist[1]); float y = Float.valueOf(plist[2]);
        		float xZ = Float.valueOf(plist[3]); float yZ = Float.valueOf(plist[4]);
        		String planetName = plist[0];
        		
        		for (int j = 0; j < PlanetList.length; j++) {
        			if (PlanetList[j] == null) {
        				PlanetList[j] = new Planet(planetName,x,y,xZ,yZ); j = PlanetList.length;
        			}
        		}
        	}        	
        	// Find the pairs of planets that are connected, and set them as neighbors
        	for (int i = 0; i < neighbors.length; i++) {
        		int i1 = Integer.valueOf(neighbors[i].split(",")[0]);
        		int i2 = Integer.valueOf(neighbors[i].split(",")[1]);
        		
        		PlanetList[i1].addNeighbor(PlanetList[i2]);
        		PlanetList[i2].addNeighbor(PlanetList[i1]);
        	}	
        } catch (Exception e){
        	System.out.print("Error loading map");
        }
		return PlanetList;
	}
	
	//--------------Main: Where the game shall be run--------------//
	public static void main(final String[] unused) {
		// Create Zen window, load buffer and planet sprites
		Zen.create(1900, 1000, "stretch");	
		ClassLoader classLoader = SpriteMoveFlipBuffer.class.getClassLoader();
        File spriteFile = new File(classLoader.getResource("Planet1.png").getFile()); 
        
        //-----------Initialize Variables-------------//
        int numPlanets = 7;
        Planet[] PlanetList = new Planet[numPlanets];
        String mainMenuString = ""; String planetMenuString = "";
        
        //--------------Load Menus--------------//
        File mainMenuFile = new File("C:\\Users\\burnt\\eclipse-workspace\\ConquestGalactica\\Resources\\mainMenu.txt");
        File planetMenuFile = new File("C:\\Users\\burnt\\eclipse-workspace\\ConquestGalactica\\Resources\\PlanetMenu.txt");
        try {
        	BufferedReader br = new BufferedReader(new FileReader(mainMenuFile)); 
        	mainMenuString = br.readLine(); 
        	BufferedReader br1 = new BufferedReader(new FileReader(planetMenuFile)); 
        	planetMenuString = br1.readLine();
        	
        } catch (Exception e) {
        	System.out.println("Error loading menus");
        }
            
        //-----------Initialize Map and Game-----------//
        Game game = new Game(PlanetList);
        PlanetList = loadMap("map1.txt");
        game.planetList = PlanetList;  
        
        for (int i = 0; i < PlanetList.length; i++) {
        	game.planetList[i].setCharMenu("Select Characters","white","white","black","black",PlanetList[i]);  		
        }
        
        //---- Load Menus ----//
        game.loadMenus(); 
        
        //------------ Game Loops -----------//
        
        while (Zen.isRunning()) {
        	if (game.mainState == "setUpGame") {
        		game.setUpGame();
        		//game.chooseMap();
        		game.setUpBoard();
        		game.isRunning = true;
        		game.state = "map";
        	}
        	
        	

        	// Main game loop
        	while (game.isRunning) {
        		drawBackGround();
        		game.update();
        	
        		Zen.flipBuffer();
        	}
        }
		
	}
	
}
