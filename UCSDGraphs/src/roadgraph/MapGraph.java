/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	HashMap<GeographicPoint, MapNode> vertices;
	Map<String, Double> speedLimits = new HashMap<String, Double>();
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		initializeSpeedLimits();
		vertices = new HashMap<GeographicPoint, MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	
	private void initializeSpeedLimits() {
		speedLimits.put("secondary_link", 60d);
		speedLimits.put("primary_link", 65d);
		speedLimits.put("tertiary_link", 70d);
		speedLimits.put("trunk_link", 80d);
		speedLimits.put("motorway_link", 100d);
		speedLimits.put("motorway", 130d);
		speedLimits.put("trunk", 120d);
		speedLimits.put("primary", 95d);
		speedLimits.put("secondary", 125d);
		speedLimits.put("tertiary", 85d);
		speedLimits.put("living_street", 25d);
		speedLimits.put("residential", 30d);
		speedLimits.put("unclassified", 40d);
		
	}
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return this.vertices.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return this.vertices.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		int numEdges = 0;
		
		Iterator<Entry<GeographicPoint, MapNode>> iter = this.vertices.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<GeographicPoint, MapNode> newMap = 
					(Map.Entry<GeographicPoint, MapNode>) iter.next();
			
			numEdges += newMap.getValue().getNodeEdges().size();
		}
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if (location == null || this.vertices.containsKey(location)) {
			return false;
		}
		
		vertices.put(location, new MapNode(location));
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		if (!this.vertices.containsKey(from) || !this.vertices.containsKey(to)) {
			throw new IllegalArgumentException("The Intersection has not been added to the graph!");
		}
		
		if (roadType == null || roadName == null) {
			throw new IllegalArgumentException("Road parameter must not be null!");
		}
		
		if (length <= 0) {
			throw new IllegalArgumentException("Length of road between intersection must be positive!");
		}
		MapEdge newEdge = new MapEdge(from, to, roadName, roadType, length, this.speedLimits.get(roadType));
		
		// add the edge to the vertex that connected to location 'from' and 'to'
		MapNode fromVertex = this.vertices.get(from);
		fromVertex.addNeighbor(newEdge);
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		if (this.vertices.get(start) == null || this.vertices.get(goal) == null) {
			return null;
		}
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		boolean found = bfsSearch(start, goal, parentMap, nodeSearched);
		
		if (!found) {
			return new LinkedList<GeographicPoint>();
		}
		
		List<GeographicPoint> routes = reconstructPath(goal, parentMap);
		// Hook for visualization.  See writeup.
		for (GeographicPoint loc: routes) {
			nodeSearched.accept(loc);
		}
		//nodeSearched.accept(next.getLocation());

		return routes;
	}
	
	private boolean bfsSearch(GeographicPoint start, GeographicPoint goal, HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched)
	{	
		Set<MapNode> visited = new HashSet<MapNode>();
		List<MapNode> queue = new LinkedList<MapNode>();
		boolean found = false;
		//add starting node to the queue
		queue.add(this.vertices.get(start));
		visited.add(this.vertices.get(start));
		while (!queue.isEmpty())
		{
			MapNode curr = queue.remove(0);
			if (curr.getGeographicPoint().equals(goal))
			{
				found = true;
				break;
			}
			List<GeographicPoint> childrenLoc = curr.getNeighborsLoc();
			for(GeographicPoint nextLoc: childrenLoc) {
				// if vertex not visited before, add to visited set
				
				MapNode nextNode = this.vertices.get(nextLoc);
				if (!visited.contains(nextNode)) {
					nodeSearched.accept(nextLoc);
					visited.add(nextNode);
					queue.add(nextNode);
					parentMap.put(nextNode, curr);
				}
			}
		}
		return found;
	}
	
	private List<GeographicPoint> reconstructPath(GeographicPoint goal, HashMap<MapNode, MapNode> parentMap)
	{
		List<GeographicPoint> path = new LinkedList<GeographicPoint>();
		path.add(goal);
		MapNode currNode = this.vertices.get(goal);
		while (parentMap.get(currNode) != null) {
			path.add(0, parentMap.get(currNode).getGeographicPoint());
			currNode = parentMap.get(currNode);
		}
		return path;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		if (this.vertices.get(start) == null || this.vertices.get(goal) == null) {
			return null;
		}
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		
		boolean found = this.dijkstraSearch(start, goal, parentMap, nodeSearched);
		
		if (!found) {
			return new LinkedList<GeographicPoint>();
		}
		
		List<GeographicPoint> routes = reconstructPath(goal, parentMap);
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

		return routes;
	}
	
	private boolean dijkstraSearch(GeographicPoint start, GeographicPoint goal, HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched) {
		boolean found = false;
		// set up priority queue, visited node set.
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		PriorityQueue<PriorityLocationNode> pqueue = new PriorityQueue<PriorityLocationNode>();
		
		// copy the GeographicPoint key of vertices and initialize the distances traversed
		Map<GeographicPoint, Double> distanceOverTime = new HashMap<GeographicPoint, Double>();
		for(GeographicPoint key: this.getVertices()) {
			distanceOverTime.put(key, Double.POSITIVE_INFINITY);
		}
		distanceOverTime.put(start, 0d);
		// add the start node to pqueue
		PriorityLocationNode pln = new PriorityLocationNode(start, 0d);
		pqueue.add(pln);
		
		while (!pqueue.isEmpty()) {
			PriorityLocationNode currLoc = pqueue.remove();
			MapNode currNode = this.vertices.get(currLoc.getLoc());
			nodeSearched.accept(currLoc.getLoc());
			if (!visited.contains(currNode.getGeographicPoint())) {
				
				visited.add(currNode.getGeographicPoint());
				if (currNode.getGeographicPoint().equals(goal)) {
					found = true;
					break;
				}
				List<MapEdge> edges = currNode.getNodeEdges();
				for (MapEdge edge: edges) {
					double distTravelledOverTime = distanceOverTime.get(currLoc.getLoc()) + edge.getRoadLen() / edge.getSpeedLimit();
					if (distTravelledOverTime < distanceOverTime.get(edge.getEndLoc())) {
						PriorityLocationNode nextPLoc = new PriorityLocationNode(edge.getEndLoc(), distTravelledOverTime);
						distanceOverTime.put(nextPLoc.getLoc(), nextPLoc.getPriorityVal());
						pqueue.add(nextPLoc);
						parentMap.put(vertices.get(nextPLoc.getLoc()), currNode);
					}
				}
			}
		}
		return found;
	}
	

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		if (this.vertices.get(start) == null || this.vertices.get(goal) == null) {
			return null;
		}
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		
		boolean found = this.aStarSearch(start, goal, parentMap, nodeSearched);
		
		if (!found) {
			return new LinkedList<GeographicPoint>();
		}
		
		List<GeographicPoint> routes = reconstructPath(goal, parentMap);
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

		return routes;
	}
	
	private boolean aStarSearch(GeographicPoint start, GeographicPoint goal, HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched) {
		boolean found = false;
		// set up priority queue, visited node set.
		Set<GeographicPoint> visited = new HashSet<GeographicPoint>();
		PriorityQueue<PriorityLocationNode> pqueue = new PriorityQueue<PriorityLocationNode>();
		
		// copy the GeographicPoint key of vertices and initialize the distances traversed
		Map<GeographicPoint, Double> distanceOverTime = new HashMap<GeographicPoint, Double>();
		for(GeographicPoint key: this.getVertices()) {
			distanceOverTime.put(key, Double.POSITIVE_INFINITY);
		}
		distanceOverTime.put(start, 0d);
		// add the start node to pqueue
		PriorityLocationNode pln = new PriorityLocationNode(start, 0d);
		pqueue.add(pln);
		
		while (!pqueue.isEmpty()) {
			PriorityLocationNode currLoc = pqueue.remove();
			MapNode currNode = this.vertices.get(currLoc.getLoc());
			nodeSearched.accept(currLoc.getLoc());
			if (!visited.contains(currNode.getGeographicPoint())) {
				
				visited.add(currNode.getGeographicPoint());
				if (currNode.getGeographicPoint().equals(goal)) {
					found = true;
					break;
				}
				List<MapEdge> edges = currNode.getNodeEdges();
				for (MapEdge edge: edges) {
					double gn = distanceOverTime.get(currLoc.getLoc()) + edge.getRoadLen() / edge.getSpeedLimit();
					double hn = edge.getEndLoc().distance(goal);
					double fn = gn + hn;
					if (gn < distanceOverTime.get(edge.getEndLoc())) {
						PriorityLocationNode nextPLoc = new PriorityLocationNode(edge.getEndLoc(), fn);
						distanceOverTime.put(edge.getEndLoc(), gn);
						pqueue.add(nextPLoc);
						parentMap.put(vertices.get(nextPLoc.getLoc()), currNode);
					}
				}
			}
		}
		return found;
	}
	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
//		/*
//		MapGraph simpleTestMap = new MapGraph();
//		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
//		
//		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
//		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
//		
//		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
//		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
//		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
//		
//		
//		MapGraph testMap = new MapGraph();
//		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
//		
//		// A very simple test using real data
//		testStart = new GeographicPoint(32.869423, -117.220917);
//		testEnd = new GeographicPoint(32.869255, -117.216927);
//		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
//		testroute = testMap.dijkstra(testStart,testEnd);
//		testroute2 = testMap.aStarSearch(testStart,testEnd);
//		
//		
//		// A slightly more complex test using real data
//		testStart = new GeographicPoint(32.8674388, -117.2190213);
//		testEnd = new GeographicPoint(32.8697828, -117.2244506);
//		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
//		testroute = testMap.dijkstra(testStart,testEnd);
//		testroute2 = testMap.aStarSearch(testStart,testEnd);
//		*/
//		
//		
//		/* Use this code in Week 3 End of Week Quiz */
//		/*MapGraph theMap = new MapGraph();
//		System.out.print("DONE. \nLoading the map...");
//		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
//		System.out.println("DONE.");
//
//		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
//		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
//		
//		
//		List<GeographicPoint> route = theMap.dijkstra(start,end);
//		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
//
//		*/
		
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);

		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		System.out.println(route);
		System.out.println(route2);
	}
	
}
