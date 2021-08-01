package roadgraph;

import geography.GeographicPoint;

public class PriorityLocationNode implements Comparable<PriorityLocationNode>{
	private double priorityValue;
	private GeographicPoint loc;
	public PriorityLocationNode(GeographicPoint geoPoint, double pval) {
		this.setLoc(geoPoint);
		this.priorityValue = pval;
	}
	
	public void setPriorityVal(double pval) {
		this.priorityValue = pval;
	}
	
	public double getPriorityVal() {
		return this.priorityValue;
	}
	
	@Override
	public int compareTo(PriorityLocationNode pmn) {
		float compareVal = Double.compare(this.priorityValue, pmn.priorityValue);
		if (compareVal < 0) {
			return -1;
		} else if (compareVal > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	public GeographicPoint getLoc() {
		return loc;
	}

	public void setLoc(GeographicPoint loc) {
		this.loc = loc;
	}

}
