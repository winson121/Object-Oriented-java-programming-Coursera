package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGen {
	
	List<Node> nodes = new ArrayList<Node>();
	
	private Node isIn(String w) {
		for(Node n: nodes) {
			if (n.word == w) {
				return n;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("Hello");
		sb.insert(5, "a");
		System.out.println(sb);
	}
}


class Node {
	List<String> b = new ArrayList<String>();
	String word;
	
	public Node(String w) {
		this.word = w;
	}
}