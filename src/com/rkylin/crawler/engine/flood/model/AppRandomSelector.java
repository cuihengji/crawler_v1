package com.rkylin.crawler.engine.flood.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppRandomSelector {

	 private List<AppWeight>  appWeightList = new ArrayList<AppWeight>();    
	 private static Random random = new Random(); 
	 
	 public void initializeAppWeightList(List<AppWeight> appWeightList){
		 this.appWeightList.addAll(appWeightList);
	 }
	 
	public AppWeight selectApp() {

		AppWeight selectedAppWeight = null;
		
		int totalWeight = 0;

		for (AppWeight appWeight : appWeightList) {
			totalWeight += appWeight.getWeight();
		}
		
		int randomNumber = random.nextInt(totalWeight);
		
		int stepNumber = 0;

		for (AppWeight appWeight : appWeightList) {

			if ((randomNumber >= stepNumber) && (randomNumber <= (appWeight.getWeight() + stepNumber))) {
				selectedAppWeight = appWeight;
				break;
			}
			stepNumber += appWeight.getWeight();
		}

		return selectedAppWeight;
	}
	
	
	public static void main(String[] args) {
		
		
		 Random random = new Random(); 
		 System.out.println(random.nextInt(170));
		
		AppRandomSelector randomAppSelector = new AppRandomSelector();
		
		List<AppWeight>  appWeightList = new ArrayList<>();
		
		appWeightList.add(new AppWeight(1, 100));
		appWeightList.add(new AppWeight(2, 50));
		appWeightList.add(new AppWeight(3, 20));
		
		randomAppSelector.initializeAppWeightList(appWeightList);
		
		System.out.println(randomAppSelector.selectApp().getAppSeq());
	}
}
