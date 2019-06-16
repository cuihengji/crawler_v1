package com.rkylin.crawler.engine.flood.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.web2data.system.config.entity.Cloud_users;

public class UserRandomSelector {

	private List<Cloud_users> cloudUsersList = new ArrayList<>();
	private static Random random = new Random();

	public void initializeCloudUsersList(List<Cloud_users> cloudUsersList) {
		this.cloudUsersList = cloudUsersList;
	}

	public int selectUser(int cloudSeq) {

		int selectedCloudUser = -1;

		for (Cloud_users cloud_users : cloudUsersList) {
			if (cloud_users.getCloud() == cloudSeq) {

				int userNumbers = cloud_users.getUsers().length;
				int randomUserIndex = random.nextInt(userNumbers);

				selectedCloudUser = cloud_users.getUsers()[randomUserIndex];
				break;
			}
		}
		return selectedCloudUser;
	}

	public static void main(String[] args) {
		
		List<Cloud_users> cloud_usersList = new ArrayList<>();
		Cloud_users cloud_user = new Cloud_users();
		
		int[] user = {2,3,222,223};
		cloud_user.setCloud(47);
		cloud_user.setUsers(user);
		cloud_usersList.add(cloud_user);
		
		UserRandomSelector selector = new UserRandomSelector();
		selector.initializeCloudUsersList(cloud_usersList);
		System.out.println(selector.selectUser(47));

	}
}
