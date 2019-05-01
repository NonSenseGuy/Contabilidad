package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Container {
	private HashMap<String,Integer> actives;
	private HashMap<String,Integer> pasives;
	private HashMap<String,Integer> heritage; 
	private HashMap<String,Integer> entry;
	private HashMap<String,Integer> spend;
	
	public Container() {
		actives = new HashMap<>();
		pasives = new HashMap<>();
		heritage = new HashMap<>();
		entry = new HashMap<>();
		spend = new HashMap<>();
		
	}
	
	
	
	public HashMap<String, Integer> getActives() {
		return actives;
	}



	public HashMap<String, Integer> getPasives() {
		return pasives;
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
		ArrayList<Integer> l = (ArrayList<Integer>) map.values();
		int sum = 0;
		for(int i = 0; i < l.size(); i++) {
			sum += l.get(i);
		}
		return sum; 
	}
	
	public boolean isBalanced() {
		int a = sumValues(this.actives);
		int h = sumValues(this.heritage);
		int p = sumValues(this.pasives);
		boolean cond = a-h-p == 0;
		return cond;
	}
}
