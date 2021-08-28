package com.splitwise.service;

import java.util.HashMap;
import java.util.Set;

import com.splitwise.models.Group;

public class GroupService {
	int idsUsed = 0;
	HashMap<Integer, Group> groups; 
	
	public GroupService() {
		groups = new HashMap<>();
	}
	
	public void addGroup(String groupName, Set<Integer> members) {
		Group newGroup = new Group(++idsUsed, groupName, members);
		groups.put(newGroup.getGroupId(), newGroup);
	}
	
	public boolean isMemberOfGroup(int groupId, int userId) throws Exception {
		if(!groups.containsKey(groupId)) {
			throw new Exception("Group not found");
		}
		return groups.get(groupId).getMembers().contains(userId);
	}
	
	public Group findGroup(int groupId) throws Exception{
		if(!groups.containsKey(groupId)) {
			throw new Exception("Group not found");
		}
		return groups.get(groupId);
	}
}
