package application;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class SQLManagement {
	
	public final String DBURL; //store the database url information
	public final String USERNAME; //store the user's account username
	public final String PASSWORD; //store the user's account password
	    
	private Connection dbConnect;
	private ResultSet results;
	
	/**
     * Constructor
     * @param DBURL		URL of database
     * @param USERNAME	User that is trying to access the database
     * @param PASSWORD	Password of Authorized user
     */
    public SQLManagement(String DBURL, String USERNAME, String PASSWORD) {
		this.DBURL = DBURL;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
    }
    
    /**
     * Creates connection to database
     */
    public void initializeConnection() {
    	try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
        	System.out.println("SQLException caught");
    		System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    
	/**
     * Closes connection with database
     */
    public void close() {
        try {
            results.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Queries the STUDIOS table for all names, and returns it in a string
     * 
     * @return		A string with a list of all names from the STUDIOS table
     */
    public String showBreth() {
    	String ret = "";
    	
    	try {
    		Statement stmt = dbConnect.createStatement();
    		results = stmt.executeQuery("SELECT Name FROM breth");
    		
    		//This is where you work with the results of your query lols
    		while (results.next()) {
    			ret = ret + results.getString("name") + "\n";
    		}
    		
    		ret = ret.substring(0, ret.length() - 1);
    		
    	} catch (SQLException e) {
    		System.out.println("SQLException caught");
    		System.out.println(e.getLocalizedMessage());
    	}
    	
    	return ret;
    }
    
    public String insertBreth(String name, String locale, String phone) {
    	try {
    		String query = "SELECT Name FROM breth WHERE Name=?";
    		PreparedStatement stmt = dbConnect.prepareStatement(query);
    	
    		stmt.setString(1, name);
    		
    		results = stmt.executeQuery();
    		
    		if (results.next()) {
    			System.out.println("Person Already exists");
    		}
    		
    		String update = "INSERT INTO BRETH (Name, Locale, Phone) VALUES (?,?,?)";
    		stmt = dbConnect.prepareStatement(update);
    		
    		stmt.setString(1, name);
    		stmt.setString(2, locale);
    		stmt.setString(3, phone);
    		
    		int updateInt = stmt.executeUpdate();
    		
    		System.out.println("Members inserted: " + updateInt);
    
    	} catch (SQLException e) {
    		System.out.println("SQLException caught");
    		System.out.println(e.getLocalizedMessage());
    		
    		return "Insert failed. Aborting";
    	} catch (Exception e) {
    		System.out.println(e.getLocalizedMessage());
    		return "Person already exists";
    	}
    	
    	return "Tnsert successful";
    }
    
    public ArrayList<String> searchBrethName(String name) {
    	ArrayList<String> ret = new ArrayList<String>();
    	
    	try {
    		System.out.println("TEST IN: " + name);
    		
    		//if no name or locale is specified, it will search any
    		if (name == null || name.length() < 1) {
    			return ret; //Should be an empty array
    		}
    		
    		String query = "SELECT * FROM BRETH WHERE name=?";
    		query = query.replace('#', ' '); //No SQL injections for me lols
    		PreparedStatement stmt = dbConnect.prepareStatement(query);
    		
    		stmt.setString(1, name);
    		
    		results = stmt.executeQuery();
    		
    		while (results.next()) {
    			System.out.println("Adding " + results.getString("name") + ", " + results.getString("Locale") + ", " + results.getString("Phone"));
    			ret.add(results.getString("name") + ", " + results.getString("Locale") + ", " + results.getString("Phone"));
    		}
    	}  catch (SQLException e) {
    		System.out.println("SQLException caught");
    		System.out.println(e.getLocalizedMessage());
    	}
    	
    	
    	return ret;
    }
    
    public ArrayList<String> searchBrethLocale(String locale) {
    	ArrayList<String> ret = new ArrayList<String>();
    	
    	try {
    		System.out.println("TEST IN: " + locale);
    		
    		//if no name or locale is specified, it will search any
    		if (locale == null || locale.length() < 1) {
    			return ret; //Should be an empty array
    		}
    		
    		String query = "SELECT * FROM BRETH WHERE locale=?";
    		query = query.replace('#', ' '); //No SQL injections for me lols
    		PreparedStatement stmt = dbConnect.prepareStatement(query);
    		
    		stmt.setString(1, locale);
    		
    		results = stmt.executeQuery();
    		
    		while (results.next()) {
    			System.out.println("Adding " + results.getString("name") + ", " + results.getString("Locale") + ", " + results.getString("Phone"));
    			ret.add(results.getString("name") + ", " + results.getString("Locale") + ", " + results.getString("Phone"));
    		}
    	}  catch (SQLException e) {
    		System.out.println("SQLException caught");
    		System.out.println(e.getLocalizedMessage());
    	}
    	
    	
    	return ret;
    }
    
    //TODO Lets deal with export last, I don't wanna think about it rn
    //Looks that time is now, but we'll just leave it for now lolsies
    
}
