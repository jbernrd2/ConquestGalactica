import edu.illinois.cs.cs125.lib.zen.Zen;

public class Character {
	
		// Constructors
		public Character() {
		}
		
		public Character(String name) {
			new Character();
			this.setName(name);
		}
		
		// Class variables
		private String team;
		private String name;
		private Planet host;
		private boolean isHighlighted;
		
		// Setters
		public void setTeam(String Team) {
			this.team = Team;
		}
		public void setHost(Planet p) {
			this.host = p;
		}
		public void setName(String Name) {
			this.name = Name;
		}
		
		// Getters
		public String getName() {
			return this.name;
		}
		
		//Class functions
		public void show(int num, int xShift, int yShift) {
			int x = (int) host.getX(); int y = (int) host.getY();
			x += xShift; y += num*25 + yShift; 
			Zen.drawText(name, x, y);
		}
		public boolean isHighlighted() {
			return this.isHighlighted;
		}
		public boolean equals(Character c) {
			if (name.equalsIgnoreCase(c.getName()) ) {
				return true;
			} else {
				return false;
			}
		}
}
