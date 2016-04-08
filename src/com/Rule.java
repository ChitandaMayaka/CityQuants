package com;

import java.util.ArrayList;

public class Rule {
	private int ID;
	private String ruleName;
	private String dataField;
	private String specification;
	private String boundaryType;
	private String boundaryCondition;
	
	private Database database = new Database();
	
	public Rule(int ID, String ruleName, String dataField, String specification, String boundaryType, String boundaryCondition){
		this.ID = ID;
		this.ruleName = ruleName;
		this.dataField = dataField;
		this.specification = specification;
		this.boundaryType = boundaryType;
		this.boundaryCondition = boundaryCondition;
	}
	
	// Return the opposite operator to determine when a data field violates the rule
	private String getOperator(){
		switch(boundaryType){
		case "Equal to":
			return "<>";
			
		case "Not equal to":
			return "=";
			
		case "Less than":
			return ">=";
			
		case "Less than or equal to":
			return ">";
			
		case "Greater than":
			return "<=";
			
		case "Greater than or equal to":
			return "<";
			
			default:
				return null;
		}
	}
	
	public ArrayList<Violation> getViolations(){
		ArrayList<Violation> ret = new ArrayList<Violation>();
		String query;
		ArrayList<String[]> results;
		ArrayList<String[]> results2;
		
		switch(specification){
		case "Value":
			query = "SELECT `Name of city`, `Year`, `" + dataField + "` FROM `citiquantsdb`.`cities` " +
					"WHERE `" + dataField + "` " + getOperator() + " \"" + boundaryCondition + "\";";
			results = database.queryData(query);
			for (String[] s : results){
				ret.add(new Violation(toString(), s[0], s[1], s[2]));
			}
			break;
			
		case "Average Value":
			query = "SELECT DISTINCT `Name of city` FROM `citiquantsdb`.`cities`;";
			results = database.queryData(query);
			for(String[] s : results){
				query = "SELECT n, y, `" + dataField + "` FROM " + 
						"(SELECT `Name of City` as n, `Year` as y,`" + dataField + "` FROM `citiquantsdb`.`cities` " + 
						"WHERE `Name of city` = \"" + s[0] + "\") AS Tbl " +
						"WHERE (SELECT AVG(`" + dataField + "`) AS average FROM " + 
						"(SELECT `Name of City` as n, `Year` as y, `" + dataField + "` FROM `citiquantsdb`.`cities` " +
						"WHERE `Name of city` = \"" + s[0] + "\") AS Tbl) " + getOperator() + " \"" + boundaryCondition + "\";";
				results2 = database.queryData(query);
				for (String[] s2 : results2){
					ret.add(new Violation(toString(), s2[0], s2[1], s2[2]));
				}
			}
			break;
			
		case "Growth Rate":
			query = "SELECT DISTINCT `Name of city` FROM `citiquantsdb`.`cities`;";
			results = database.queryData(query);
			for(String[] s : results){
				query = "SELECT n, y, `" + dataField + "` FROM " + 
						"(SELECT `Name of City` as n, `Year` as y,`" + dataField + "` FROM `citiquantsdb`.`cities` " + 
						"WHERE `Name of city` = \"" + s[0] + "\") AS Tbl ORDER BY y;";
				results2 = database.queryData(query);
				for (int i = 0; i < results2.size() - 1; i++){
					String[] s2 = results2.get(i);
					String[] s3 = results2.get(i+1);
					try{
						// Calculate the growth rate between consecutive data points
						double growthRate = (Double.parseDouble(s3[2]) - Double.parseDouble(s2[2]))/Double.parseDouble(s2[2]);
						if (violatesBoundary(growthRate, getOperator())){
							if (ret.size() > 0 && ret.get(ret.size() - 1).getYear() != s2[1])
								ret.add(new Violation(toString(), s2[0], s2[1], s2[2]));
							ret.add(new Violation(toString(), s3[0], s3[1], s3[2]));
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			break;
			
		case "Standard Deviation":
			query = "SELECT DISTINCT `Name of city` FROM `citiquantsdb`.`cities`;";
			results = database.queryData(query);
			for(String[] s : results){
				query = "SELECT n, y, `" + dataField + "` FROM " + 
						"(SELECT `Name of City` as n, `Year` as y,`" + dataField + "` FROM `citiquantsdb`.`cities` " + 
						"WHERE `Name of city` = \"" + s[0] + "\") AS Tbl ORDER BY y;";
				results2 = database.queryData(query);
				try{
					ArrayList<Double> dataPoints = new ArrayList<Double>();
					
					// Calculate the standard deviation of the data points
					double sd = 0;
					for (int i = 0; i < results2.size(); i++){
						double num = Double.parseDouble(results2.get(i)[2]);
						sd += num;
						dataPoints.add(num);
					}
					double mean = sd / dataPoints.size();
					
					sd = 0;
					for (int i = 0; i < dataPoints.size(); i++){
						sd += Math.pow(dataPoints.get(i), 2);
					}
					sd /= dataPoints.size();
					
					sd -= (mean*mean);
					sd = Math.sqrt(sd);
					
					if (violatesBoundary(sd, getOperator())){
						for (int i = 0; i < results2.size(); i++){
							ret.add(new Violation(toString(), results2.get(i)[0], results2.get(i)[1], results2.get(i)[2]));
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			break;
			
		default:
			break;
		}
		
		return ret;
	}
	
	// Test whether a given data point and the boundary condition satisfy a boolean condition 
	private boolean violatesBoundary(double dataPoint, String operator){
		try{
			double boundary = Double.parseDouble(boundaryCondition);
			
			switch(operator){
			case "=": return dataPoint == boundary;
			case "<>": return dataPoint != boundary;		
			case "<": return dataPoint < boundary;	
			case "<=": return dataPoint <= boundary;
			case ">": return dataPoint > boundary;
			case ">=": return dataPoint >= boundary;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	public int getID(){
		return ID;
	}
	
	public String getRuleName(){
		return ruleName;
	}
	
	public String getDataField(){
		return dataField;
	}
	
	public String getSpecification(){
		return specification;
	}
	
	public String getBoundaryType(){
		return boundaryType;
	}
	
	public String getBoundaryCondition(){
		return boundaryCondition;
	}
	
	public String toString(){
		return ruleName + ": " + 
			Character.toUpperCase(specification.charAt(0)) + specification.substring(1).toLowerCase() +
			" of \"" + dataField + "\" must be " 
			+ boundaryType.toLowerCase() + " " 
			+ boundaryCondition;
	}
}
