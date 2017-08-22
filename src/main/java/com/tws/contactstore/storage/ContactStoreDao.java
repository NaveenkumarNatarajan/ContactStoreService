package com.tws.contactstore.storage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.tws.contactstore.Contact;
import com.tws.contactstore.User;
import com.tws.contactstore.config.DataSource;

/**
 * <h1>A Contact Store DAO interface contains all required process method declarations</h1> 
 * @author Naveen
 * @since 2017-08-20
 * @version 1.0
 *
 */
public interface ContactStoreDao {
	
	public boolean authenticateUser(DataSource dataSource, String userName, String password) throws SQLException;
	
	public String registerUser(DataSource dataSource, User user);
	
	public boolean isUserAlreadyExist(DataSource dataSource, User user);
	
	public JSONArray getAllContact(DataSource dataSource, String userName, String formatPretty);
	
	public void addNewContact(DataSource dataSource, Contact contact, String userName);
	
	public void deleteContact(DataSource dataSource, String contactId);
}
