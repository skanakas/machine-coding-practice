package com.splitwise.models;

import java.util.Set;

public class Group {
	int groupId;
	Set<Integer> members;
	String groupName;

	public Group(int groupId, String groupName, Set<Integer> members) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.members = members;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public Set<Integer> getMembers() {
		return members;
	}

	public void setMembers(Set<Integer> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", members=" + members + "]";
	}
}
