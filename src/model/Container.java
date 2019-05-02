package model;

import java.util.Collection;
import java.util.HashMap;

public class Container {
	private HashMap<String,Integer> actives;
	private HashMap<String,Integer> passives;
	private HashMap<String,Integer> heritage; 
	private HashMap<String,Integer> entry;
	private HashMap<String,Integer> spend;
	
	public Container() {
		actives = new HashMap<>();
		passives = new HashMap<>();
		heritage = new HashMap<>();
		entry = new HashMap<>();
		spend = new HashMap<>();
		
	}
	
	
	
	public HashMap<String, Integer> getActives() {
		return actives;
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
		int a = sumValues(this.actives);
		int h = sumValues(this.heritage);
		int p = sumValues(this.passives);
		
		return a-p-h;
	}
	
	public int totalActives() {
		return sumValues(this.actives);
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
