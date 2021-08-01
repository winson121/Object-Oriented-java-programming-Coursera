package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		if (getAltitude() > 3000){
			pg.fill(150, 120, 70);
			pg.ellipse(x, y, 5, 5);
			
		}
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		String country = getCountry();
		String code = getCode();
		String city = getCity();
		String name = getName();
		pg.pushStyle();
		
		pg.rectMode(PConstants.CORNER);
		
		pg.stroke(110);
		pg.fill(255,255,255);
		pg.rect(x, y + 15, pg.textWidth(name + "-"+ country + " " + city + " " + code) +6, 18, 5);
		
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.fill(0);
		pg.text(name + "-"+ country + " " + city + " " + code, x + 3 , y +18);
		
		
		pg.popStyle();
		
	}
	
	
	public String getCountry() {
		return (String) getProperty("country");	
		
	}
	
	public String getCode() {
		return (String) getProperty("code");	
		
	}
	
	public String getCity() {
		return (String) getProperty("city");	
		
	}
	
	public String getName() {
		return (String) getProperty("name");	
		
	}
	
	public int getAltitude() {
		return Integer.parseInt(getProperty("altitude").toString());
	}
}
