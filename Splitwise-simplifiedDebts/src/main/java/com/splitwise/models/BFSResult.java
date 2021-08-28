package com.splitwise.models;

import java.util.List;

public class BFSResult {
	private List<Integer> path;
	private double minCost;

	public BFSResult(List<Integer> path, double minCost) {
		this.path = path;
		this.minCost = minCost;
	}

	public double getMinCost() {
		return minCost;
	}

	public void setMinCost(double minCost) {
		this.minCost = minCost;
	}

	public List<Integer> getPath() {
		return path;
	}

	public void setPath(List<Integer> path) {
		this.path = path;
	}

}
