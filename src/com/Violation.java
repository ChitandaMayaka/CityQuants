package com;

public class Violation {

	private String ruleText;
	private String city;
	private String year;
	private String dataField;
	
	public Violation(String ruleText, String city, String year, String dataField){
		this.ruleText = ruleText;
		this.city = city;
		this.year = year;
		this.dataField = dataField;
	}
	
	public String getRuleText(){
		return ruleText;
	}
	
	public String getCity(){
		return city;
	}
	
	public String getYear(){
		return year;
	}
	
	public String getDataField(){
		return dataField;
	}
}
