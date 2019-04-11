import edu.illinois.cs.cs125.lib.zen.Zen;

public class Button {
	
	//Default constructor
	public Button() {
	}
	
	//Constructor
	public Button(String Color, String Text, String TextColor, int X, int Y, int Width, int Height) {
		new Button();
		this.setColor(Color); 
		this.setText(Text); this.setAction(Text);
		this.setXY(X,Y); 
		this.setDim(Width,Height);
		this.setTextColor(TextColor);
	}
		
	//Default Variables
	private String text; 
	private String color, textColor, hColor = "light pink";
	private int x,y; //XY positions of upper left corner of button
	private int w,h; //width and height of box
	private String action;
	private boolean selected = false;
	
	
	//Public Functions
	public void drawButton() {
		if (selected) {
			this.highlight();
		}
		Zen.setColor(this.color);
		Zen.fillRect(x, y, w, h);
		Zen.setColor(textColor);    // Draw text in center of button
		Zen.drawText(text, x  + 15, y + h/2 + 5);
		
	}

	
	public void drawButton(int W) {
		w = W;
		if (selected) {
			this.highlight();
		}
		Zen.setColor(this.color);
		Zen.fillRect(x, y, w, h);
		Zen.setColor(textColor);    // Draw text in center of button
		Zen.drawText(text, x  + 15, y + h/2 + 5);
	}
	
	public void checkClickAndHighlight(Game g) {
		int mouseX = g.mouseX; int mouseY = g.mouseY;
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			Zen.setColor("red");
			Zen.fillRect(x - 5, y - 5, w + 10, h + 10);
			if (g.isMouseClicked) {
				g.command = action;
			} else {
				g.command = null;
			}
		} 
	}
	
	public String chooseCharacter(Game g) {
		int mouseX = g.mouseX; int mouseY = g.mouseY;
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			Zen.setColor("hColor");
			Zen.fillRect(x - 5, y - 5, w + 10, h + 10);
			if (g.isMouseClicked) {
				return text;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public void highlight() {
		Zen.setColor(hColor);
		Zen.fillRect(x - 5, y - 5, w + 10, h + 10);
	}
	
	public boolean checkButtonClick(int mouseX, int mouseY) {
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getLength() {
		return this.text.length();
	}

	public void select() {
		if (selected) {	
			selected = false;
		} else {
			selected = true;
		}
	}
	
	//Getters
	public String getColor() {
		return this.color;
	}
	public String getAction() {
		return text;
	}
	public String getTextColor() {
		return this.textColor;
	}
	public boolean isSelected() {
		return this.selected;
	}
	public String getName() {
		return this.text;
	}
	
	//Setters
	public void setText(String Text) {
		this.text = Text;
	}
	public void setAction(String s) {
		this.action = s;
	}
	public void setXY(int X, int Y) {
		this.x = X; this.y = Y;
	}
	public void setDim(int W, int H) {
		this.w = W; this.h = H;
	}
	public void setWidth(int W) {
		this.w = W;
	}
	public void setColor(String Color) {
		this.color = Color;
	}
	public void setTextColor(String Color) {
		this.textColor = Color;
	}  
	public void setHColor(String Color) {
		hColor = Color;
	}
}