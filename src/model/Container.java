package model;

import java.util.Collection;
import java.util.HashMap;

public class Container {
	private HashMap<String,Integer> activeCurrent;
	private HashMap<String,Integer> activeNotCurrent;
	private HashMap<String,Integer> passives;
	private HashMap<String,Integer> heritage; 
	private HashMap<String,Integer> entry;
	private HashMap<String,Integer> spend;
	
	public Container() {
		activeCurrent = new HashMap<>();
		activeNotCurrent = new HashMap<>();
		passives = new HashMap<>();
		heritage = new HashMap<>();
		entry = new HashMap<>();
		spend = new HashMap<>();
		
	}
	
	public HashMap<String, Integer> getActivesCurrent() {
		return activeCurrent;
	}
	
	public HashMap<String, Integer> getActivesNotCurrent() {
		return activeNotCurrent;
	}

	public HashMap<String, Integer> getPassives() {
		return passives;
	}



	public HashMap<String, Integer> getHeritage() {
		return heritage;
	}



	public HashMap<String, Integer> getEntry() {
		return entry;
	}



	public HashMap<String, Integer> getSpend() {
		return spend;
	}



	public int sumValues(HashMap<String,Integer> map) {
		Collection<Integer> list = map.values();
		int sum = 0;
		for(int i: list) {
			sum += i;
		}
		return sum; 
	}
	
	public int diff() {
		int a = sumValues(this.activeCurrent);
		int anc = sumValues(this.activeNotCurrent);
		int h = sumValues(this.heritage);
		int p = sumValues(this.passives);
		
		return (a+anc)-p-h;
	}
	
	public int totalActivesC() {
		return sumValues(this.activeCurrent);
	}
	
	public int totalActivesNC() {
		return sumValues(this.activeNotCurrent);
	}
	
	public int totalPassives() {
		return sumValues(this.passives);
	}
	
	public int totalHeritage() {
		return sumValues(this.heritage);
	}
	
	public int totalEntry() {
		return sumValues(this.entry);
	}
	
	public int totalSpend() {
		return sumValues(this.entry);
	}
	
	public int getUtility() {
		int entry = sumValues(this.entry);
		int spend = sumValues(this.spend);
		return entry-spend;
	}
	
	public boolean isBalanced() {
		return diff() == 0; 
	}
}
