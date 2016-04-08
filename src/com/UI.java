 wpackage com;

import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UI {

	private Database database = new Database();
	
	public String displayRules(){
		String ret = "";
		
		ArrayList<Rule> rules = database.queryRules(-1);
		int index = 1;
		for (Rule r : rules){
			ret += "<form method=\"post\" action=\"rules.jsp\">" +
					"<TR>" + 
					"<TD><input type=\"hidden\" name=\"id\" value=\"" + r.getID() + "\">" + index++ + "</TD>" +
					"<TD>" + r.getRuleName() + "</TD>" +
					"<TD>" + r.getDataField() + "</TD>" +
					"<TD>" + r.getSpecification() + "</TD>" +
					"<TD>" + r.getBoundaryType() + "</TD>" +
					"<TD>" + r.getBoundaryCondition() + "</TD>" +
					"<TD><button type=\"submit\" class=\"btn btn-default\" name=\"button\" value=\"edit\">Edit</button></TD>" + 
					"<TD><button type=\"submit\" class=\"btn btn-default\" name=\"button\" value=\"delete\">Delete</button></TD></TR>" +
					"</form>";
		}
		
		return ret;
	}
	
	public String displayRulesDropdown(HttpServletRequest request){
		String ret = "";
		
		String selected = request.getParameter("ruleFilter");
		
		ArrayList<Rule> rules = database.queryRules(-1);
		for (Rule r : rules){
			ret += "<option";
			if (r.getRuleName().equals(selected)){
				ret += " selected=\"true\"";
			}
			ret += ">" + r.getRuleName() + "</option>";
		}
		
		return ret;
	}
	
	public String displayDataFieldsDropdown(HttpSession session){
		String ret = "";
		try{
			Scanner reader = new Scanner(session.getServletContext().getResourceAsStream("dataFields.txt"));
			while (reader.hasNext()){
				ret += "<option>" + reader.nextLine() + "</option>";
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public String displayRuleViolations(HttpServletRequest request){
		String ret = "";
		
		ArrayList<Violation> violations = database.queryViolations(request.getParameter("ruleFilter"));
		for (Violation v : violations){
			ret += "<TR>" +
					"<TD>" + v.getRuleText() + "</TD>" +
					"<TD>" + v.getCity() + "</TD>" +
					"<TD>" + v.getYear() + "</TD>" +
					"<TD>" + v.getDataField() + "</TD></TR>";
		}
		
		return ret;
	}
}
