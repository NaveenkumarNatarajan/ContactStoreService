package com.tws.contactstore.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Common {

	public static String formatPayloadToJSON(String response){
		
		return response.toString().replaceAll("=", ":").replaceAll("data", "\"data\"");
	}
	
	public static ArrayList<String> formatResponseAsArray(ResultSet rs) throws SQLException{
	   ArrayList<String> contactArr = new ArrayList<String>(); 
 	   contactArr.add(rs.getString("firstName"));
 	   contactArr.add(rs.getString("lastName"));
 	   contactArr.add(rs.getString("phone"));
 	   contactArr.add(rs.getString("email"));
 	   contactArr.add(rs.getString("streetAddress"));
 	   contactArr.add(rs.getString("notes"));
 	   return contactArr;
	}
	
	public static JSONObject formatResponseAsObj(ResultSet rs) throws SQLException{
		JSONObject contactObj = new JSONObject();
		try {
			contactObj.put("contactId", rs.getString("contact_id"));
			contactObj.put("fullName", rs.getString("firstName")+" "+rs.getString("lastName"));
			contactObj.put("firstName", rs.getString("firstName"));
			contactObj.put("lastName", rs.getString("lastName"));
			contactObj.put("phone", rs.getString("phone"));
			contactObj.put("email", rs.getString("email"));
			contactObj.put("streetAddress", rs.getString("streetAddress"));
			contactObj.put("notes", rs.getString("notes"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	 	return contactObj;
	}
}
