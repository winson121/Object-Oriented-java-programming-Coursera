package roadgraph;

import java.util.LinkedList;
import java.util.List;

import geography.GeographicPoint;

public class MapNode {
	private GeographicPoint geoPoint;
	private List<MapEdge> edges;
	
	public MapNode(GeographicPoint geoPoint) {
		this.geoPoint = geoPoint;
		this.edges = new LinkedList<MapEdge>();
	}
	
	public MapNode(GeographicPoint geoPoint, List<MapEdge> edges) {
		this.geoPoint = geoPoint;
		this.edges = edges;
	}
	
	public GeographicPoint getGeographicPoint() {
		return this.geoPoint;
	}
	
	public List<GeographicPoint> getNeighborsLoc(){
		List<GeographicPoint> neighborsLoc = new LinkedList<GeographicPoint>();
		for(MapEdge edge: this.getNodeEdges()) {
			neighborsLoc.add(edge.getEndLoc());
		}
		
		return neighborsLoc;
	}
	
	public List<MapEdge> getNodeEdges() {
		return this.edges;
	}
	
	public void addNeighbor(MapEdge me) {
		this.edges.add(me);
	}
}
