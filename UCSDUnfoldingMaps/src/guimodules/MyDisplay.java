package guimodules;

import processing.core.PApplet;

public class MyDisplay extends PApplet{
	
	public void setup() {
		size(400, 600);
		background(255, 120, 139);
	}
	
	public void draw() {
		fill(255, 255, 9);
		ellipse(200, 200, 390, 390);
		fill(0, 0, 0);
		ellipse(120, 130, 50, 70);
		ellipse(280, 130, 50, 70);
		
		noFill();
		arc(176, 280, 40, 40, 0, PI);
		arc(215, 280, 40, 40, 0, PI);
		
	}
	
}
