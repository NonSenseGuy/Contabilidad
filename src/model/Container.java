package model;

import java.util.Collection;
import java.util.HashMap;

public class Container {
	private HashMap<String,Double> activeCurrent;
	private HashMap<String,Double> activeNotCurrent;
	private HashMap<String,Double> passives;
	private HashMap<String,Double> heritage; 
	private HashMap<String,Double> entry;
	private HashMap<String,Double> spend;
	
	public Container() {
		activeCurrent = new HashMap<>();
		activeNotCurrent = new HashMap<>();
		passives = new HashMap<>();
		heritage = new HashMap<>();
		entry = new HashMap<>();
		spend = new HashMap<>();
		
	}
	
	public HashMap<String, Double> getActivesCurrent() {
		return activeCurrent;
	}
	
	public HashMap<String, Double> getActivesNotCurrent() {
		return activeNotCurrent;
	}

	public HashMap<String, Double> getPassives() {
		return passives;
	}



	public HashMap<String, Double> getHeritage() {
		return heritage;
	}



	public HashMap<String, Double> getEntry() {
		return entry;
	}



	public HashMap<String, Double> getSpend() {
		return spend;
	}



	public double sumValues(HashMap<String,Double> map) {
		Collection<Double> list = map.values();
		double sum = 0;
		for(double i: list) {
			sum += i;
		}
		return sum; 
	}
	
	public double diff() {
		double a = sumValues(activeCurrent);
		double anc = sumValues(activeNotCurrent);
		double h = sumValues(heritage);
		double p = sumValues(passives);
		
		return (a+anc)-p-h;
	}
	
	public double totalActivesC() {
		return sumValues(this.activeCurrent);
	}
	
	public double totalActivesNC() {
		return sumValues(this.activeNotCurrent);
	}
	
	public double totalPassives() {
		return sumValues(this.passives);
	}
	
	public double totalHeritage() {
		return sumValues(this.heritage);
	}
	
	public double totalEntry() {
		return sumValues(this.entry);
	}
	
	public double totalSpend() {
		return sumValues(this.entry);
	}
	
	public double getUtility() {
		double entry = sumValues(this.entry);
		double spend = sumValues(this.spend);
		return entry-spend;
	}
	
	public boolean isBalanced() {
		return diff() == 0; 
	}
}
