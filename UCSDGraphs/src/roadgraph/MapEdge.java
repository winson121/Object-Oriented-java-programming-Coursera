package roadgraph;

import geography.GeographicPoint;

public class MapEdge {
	private GeographicPoint startLoc;
	private GeographicPoint endLoc;
	private String roadName;
	private String roadType;
	private double roadLen;
	private double speedLimit;
	
	public MapEdge(GeographicPoint start, GeographicPoint end, String roadName, String roadType, double length, double speedLimit) {
		this.startLoc = start;
		this.endLoc = end;
		this.roadName = roadName;
		this.roadType = roadType;
		this.roadLen = length;
		this.speedLimit = speedLimit;
	}
	public GeographicPoint getEndLoc() {
		return endLoc;
	}
	public void setEndLoc(GeographicPoint endLoc) {
		this.endLoc = endLoc;
	}
	public GeographicPoint getStartLoc() {
		return startLoc;
	}
	public void setStartLoc(GeographicPoint startLoc) {
		this.startLoc = startLoc;
	}
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	public double getRoadLen() {
		return roadLen;
	}
	public void setRoadLen(double roadLen) {
		this.roadLen = roadLen;
	}
	public String getRoadType() {
		return roadType;
	}
	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}
	
	public void setSpeedLimit(double speedLimit) {
		this.speedLimit = speedLimit;
	}
	public double getSpeedLimit() {
		return this.speedLimit;
	}
	
}
