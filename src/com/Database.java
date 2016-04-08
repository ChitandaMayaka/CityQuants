package com;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Database{

	private Connection connect;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	private void connectToDatabase() throws Exception{
		// Load the MySQL driver and connect to the database
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/citiquantsDB", "root", "root");	
	}
	
	public void defineRule(HttpServletRequest request, HttpServletResponse response){
		try{
			// See if the request was null
			if (request.getParameter("dataField") == null) return;
			
			connectToDatabase();
			
			preparedStatement = connect.prepareStatement("REPLACE INTO `citiquantsdb`.`rules`" + 
				"(`Rule Name`," + 
				"`Data Field`," +
				"`Specification`," +
				"`Boundary Type`," +
				"`Boundary Condition`)" + 
				"VALUES(?, ?, ?, ?, ?);");
			
			preparedStatement.setString(1, request.getParameter("ruleName"));
			preparedStatement.setString(2, request.getParameter("dataField"));
			preparedStatement.setString(3, request.getParameter("specification"));
			preparedStatement.setString(4, request.getParameter("boundaryType"));
			preparedStatement.setString(5, request.getParameter("boundaryCondition"));
			
			preparedStatement.executeUpdate();
			
			// Now check for new violations and add them to the database
			preparedStatement = connect.prepareStatement("SELECT * FROM `citiquantsdb`.`rules` WHERE `Rule Name` = ?;");
			preparedStatement.setString(1, request.getParameter("ruleName"));
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				addViolations(resultSet.getInt("ID"));
			
			// Redirect the user to the Rules page
			response.sendRedirect("rules.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void editRule(HttpServletRequest request, HttpServletResponse response){
		
	}
	
	public void deleteRule(HttpServletRequest request){
		try{
			connectToDatabase();
			
			// First delete all violations of this rule
			deleteViolations(Integer.parseInt(request.getParameter("id")));
			
			// Now delete the rule
			preparedStatement = connect.prepareStatement("DELETE FROM `citiquantsdb`.`rules` WHERE ID = ?;");
			
			preparedStatement.setInt(1, Integer.parseInt(request.getParameter("id")));
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public void uploadData(HttpServletRequest request, HttpServletResponse response){
		try{
			connectToDatabase();
			
			Part filePart = request.getPart("file");
			InputStream inputStream = filePart.getInputStream();
			readExcel(inputStream);
			
			// Check if there are new rule violations
			addViolations(-1);
			
			// Redirect the user to the Rule Violations page
			response.sendRedirect("ruleviolations.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	// Calling addViolations(-1) will add violations from all rules
	private void addViolations(int ruleID) throws SQLException{
		ArrayList<Rule> rules = queryRules(ruleID);
		for (Rule r : rules){
			ArrayList<Violation> violations = r.getViolations();
			
			for (Violation v : violations){
				preparedStatement = connect.prepareStatement("REPLACE INTO `citiquantsdb`.`violations`" + 
						"(`Rule Text`," + 
						"`City`," +
						"`Year`," +
						"`Data Field`)" + 
						"VALUES(?, ?, ?, ?);");
					
					preparedStatement.setString(1, v.getRuleText());
					preparedStatement.setString(2, v.getCity());
					preparedStatement.setString(3, v.getYear());
					preparedStatement.setString(4, v.getDataField());
					
					preparedStatement.executeUpdate();
			}
		}
	}
	
	// Calling deleteViolations(-1) will delete all violations
	private void deleteViolations(int ruleID) throws SQLException{
		if (ruleID != -1){
			ArrayList<Violation> violations = queryRules(ruleID).get(0).getViolations();
			
			for (Violation v : violations){
				preparedStatement = connect.prepareStatement("DELETE FROM `citiquantsdb`.`violations` WHERE `Rule Text` = ?;");
				preparedStatement.setString(1, v.getRuleText());
				preparedStatement.executeUpdate();
			}
		}else{
			preparedStatement = connect.prepareStatement("DELETE FROM `citiquantsdb`.`violations`");
			preparedStatement.executeUpdate();
		}
	}
	
	private void readExcel(InputStream inputStream){
		ArrayList<String> dataFields = new ArrayList<String>();
		try{
			Workbook w = Workbook.getWorkbook(inputStream);
			Sheet sheet = w.getSheet(0);
			
			for (int i = 1; i < sheet.getColumns(); i += 3){
				dataFields.clear();
				dataFields.add(sheet.getCell(i-1, 0).getContents());
				for (int j = 0; j < sheet.getRows(); j++){
					Cell cell = sheet.getCell(i, j);
					if (cell.getContents().equals("")) continue;
					dataFields.add(cell.getContents());
				}
				
				preparedStatement = connect.prepareStatement("REPLACE INTO `citiquantsdb`.`cities`(`Country - City`,`Year`,`Labor Availability 1`,`Labor Availability (bilingual) 1`,`Employed Bilingual Agents 1`,`Annual Attrition Rate`,`Union Presence & Activity`,`Cultural Domain Acuity Model (CDAM)`,`State or province of city`,`Tier Classification`,`Name of city`,`Country Population`,`Population Metro Area`,`Population City`,`Growth rate of city`,`Wage Inflation Rate`,`Labor cost per agent/daily`,`Supervisor`,`Attrition rates (avg reported attrition)`,`Real Estate Cost (per sq.ft/annum)`,`Telecom Cost (Monthly)`,`Teledensity Landline/dial-up`,`Teledensity Mobility`,`Internet Connectivity`,`Electricity Cost p KWh`,`Growth rate of Call Center & BPO Sector`,`service suppliers # in city`,`WTO Free Zone Extension 2015`,`CARICOM/CAFTA-DR MEMBER`,`GDP (bb)`,`GDP per capita`,`GDP real growth rate`,`unemployment`,`Employment in IT/BPO Sector`,`Inflation`,`Corporate Tax Rate %`,`Fringe Benefit %`,`Employment Rate`,`Labor Availability 2`,`Labor Availability (bilingual) 2`,`Employed Bilingual Agents 2`,`4 year university grads`,`2-yr college/technical (per year)`,`stock of graduates`,`annual business graduates`,`literacy rates`)" +
						"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
				
				int index = 1;
				for (String s : dataFields){
					preparedStatement.setObject(index++, s);
				}
				
				preparedStatement.executeUpdate();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<String[]> queryData(String query){
		ArrayList<String[]> ret = new ArrayList<String[]>();
		
		try{
			connectToDatabase();
			
			preparedStatement = connect.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			int columnCount = resultSet.getMetaData().getColumnCount();
			
			while (resultSet.next()){
				String[] s = new String[columnCount];

				for (int i = 1; i <= columnCount; i++){
					s[i-1] = resultSet.getObject(i).toString();
				}
				ret.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}
	
	// Calling queryRules(-1) will return all rules in the database
	public ArrayList<Rule> queryRules(int ruleID){
		ArrayList<Rule> ret = new ArrayList<Rule>();
		boolean needToClose = true;
		
		try{
			needToClose = (connect == null) ? true : connect.isClosed();
			
			connectToDatabase();
			
			if (ruleID == -1)
				preparedStatement = connect.prepareStatement("SELECT * FROM `citiquantsdb`.`rules`;");
			else
				preparedStatement = connect.prepareStatement("SELECT * FROM `citiquantsdb`.`rules` WHERE `ID` = \"" + ruleID + "\";");

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				Rule r = new Rule(resultSet.getInt("ID"),
						resultSet.getString("Rule Name"),
						resultSet.getString("Data Field"),
						resultSet.getString("Specification"),
						resultSet.getString("Boundary Type"),
						resultSet.getString("Boundary Condition"));
				ret.add(r);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (needToClose)
				close();
		}
		return ret;
	}
	
	public ArrayList<Violation> queryViolations(String ruleName){
		ArrayList<Violation> ret = new ArrayList<Violation>();

		try{
			connectToDatabase();
			preparedStatement = connect.prepareStatement("SELECT * FROM `citiquantsdb`.`violations` WHERE `Rule Text` LIKE ?;");
			preparedStatement.setString(1, (ruleName == null ? "" : ruleName) + "%");
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				Violation v = new Violation(resultSet.getString("Rule Text"),
						resultSet.getString("City"),
						resultSet.getString("Year"),
						resultSet.getString("Data Field"));
				ret.add(v);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}
	
	public boolean queryUser(String username, String password){
		try{
			connectToDatabase();
			preparedStatement = connect.prepareStatement("SELECT * FROM `citiquantsdb`.`users` WHERE `Username` = ? AND `Password` = ?;");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next())
				return true;
			return false;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	// Make sure all the connections are closed
	private void close() {
		close(resultSet);
		close(preparedStatement);
		close(connect);
	}
	
	private void close(AutoCloseable c){
		try{
			if (c != null){
				c.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}