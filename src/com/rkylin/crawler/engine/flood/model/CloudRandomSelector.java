package com.rkylin.crawler.engine.flood.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.web2data.system.config.entity.Cloud_users;

public class CloudRandomSelector {

	private List<CloudWeight> cloudWeightList = new ArrayList<>();
	private static Random random = new Random();

	public void initializeCloudWeightList(List<Cloud_users> cloudUsersList) {

		for (Cloud_users cloudUserUser : cloudUsersList) {

			CloudWeight cloudWeight = new CloudWeight();
			cloudWeight.setCloudSeq(cloudUserUser.getCloud());
			cloudWeight.setWeight(cloudUserUser.getUsers().length);

			cloudWeightList.add(cloudWeight);
		}
	}

	public CloudWeight selectCloud() {

		CloudWeight selectedCloudWeight = null;

		int totalWeight = 0;

		for (CloudWeight cloudWeight : cloudWeightList) {
			totalWeight += cloudWeight.getWeight();
		}
		
		if (totalWeight == 0) {
			return selectedCloudWeight;
		}
		int randomNumber = random.nextInt(totalWeight);
		// System.out.println(randomNumber);

		int stepNumber = 0;

		// Collections.sort(cloudWeightList);

		for (CloudWeight cloudWeight : cloudWeightList) {

			if ((randomNumber >= stepNumber) && (randomNumber < (cloudWeight.getWeight() + stepNumber))) {
				selectedCloudWeight = cloudWeight;
				break;
			}
			stepNumber += cloudWeight.getWeight();
		}

		return selectedCloudWeight;
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {

			// Random random = new Random();
			// System.out.println(random.nextInt(170));

			CloudRandomSelector randomCloudSelector = new CloudRandomSelector();

			List<CloudWeight> cloudWeightList = new ArrayList<>();

			cloudWeightList.add(new CloudWeight(1, 100));
			cloudWeightList.add(new CloudWeight(2, 50));
			cloudWeightList.add(new CloudWeight(3, 20));

			Collections.sort(cloudWeightList);

			// randomCloudSelector.initializeCloudWeightList(cloudWeightList);

			System.out.println(randomCloudSelector.selectCloud().getCloudSeq());
			System.out.println("-----------------------------");
		}

	}
}
