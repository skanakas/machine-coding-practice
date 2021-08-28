package com.splitwise.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.splitwise.models.BFSResult;
import com.splitwise.models.ConsolidatedExpenseData;

public class BFSHelper {
	
	class Node {
		private int currNode;
		private List<Integer> parent;
		private double minCost;

		public int getCurrNode() {
			return currNode;
		}

		public void setCurrNode(int currNode) {
			this.currNode = currNode;
		}

		public List<Integer> getParent() {
			return parent;
		}

		public void setParent(List<Integer> parent) {
			this.parent = parent;
		}

		public double getMinCost() {
			return minCost;
		}

		public void setMinCost(double minCost) {
			this.minCost = minCost;
		}

		public Node(int currNode, List<Integer> parent, double minCost) {
			this.currNode = currNode;
			this.parent = parent;
			this.minCost = minCost;
		}

	}

	private Map<Integer, Map<Integer, Double>> debitMap;
	private UserService userService;

	public BFSHelper(ConsolidatedExpenseData data, UserService userService) {
		debitMap = data.getDebitMap();
		this.userService = userService;
	}

	public BFSResult findPath(int destNode) {
		Queue<Node> queue = new LinkedList<>();
		queue.add(new Node(destNode, new ArrayList<>(), Double.MAX_VALUE));
		boolean[] visited = new boolean[userService.getAllUserIds().size() + 1];
		visited[destNode] = true;
		while (!queue.isEmpty()) {
			Node curr = queue.poll();
			Map<Integer, Double> neighbours = debitMap.get(curr.currNode);
			if (neighbours != null) {
				for (Integer eachNeighbour : neighbours.keySet()) {
					double updatedCost = Math.min(curr.minCost, neighbours.get(eachNeighbour));
					if (eachNeighbour == destNode) {
						BFSResult result = new BFSResult(curr.parent, updatedCost);
						result.getPath().add(curr.currNode);
						return result;
					}
					if (!visited[eachNeighbour]) {
						visited[eachNeighbour] = true;
						List<Integer> thisParent = new ArrayList<>();
						thisParent.addAll(curr.parent);
						thisParent.add(curr.currNode);
						queue.add(new Node(eachNeighbour, thisParent, updatedCost));
					}
				}
			}
		}
		return null;
	}

}
