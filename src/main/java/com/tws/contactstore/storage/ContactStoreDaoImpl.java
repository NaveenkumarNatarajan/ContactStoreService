package com.tws.contactstore.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;

import com.tws.contactstore.Contact;
import com.tws.contactstore.User;
import com.tws.contactstore.common.Common;
import com.tws.contactstore.config.DataSource;
/**
 * <h1>A Contact Store DaoImpl class has implementation for all methods declared to serve DAO operation</h1> 
 * @author Naveen
 * @since 2017-08-20
 * @version 1.0
 *
 */
public class ContactStoreDaoImpl implements ContactStoreDao{

	private static ContactStoreDao contactStoreDao = new ContactStoreDaoImpl();
	private static final Logger LOG = LoggerFactory.getLogger(ContactStoreDaoImpl.class);
	public static ContactStoreDao getInstance() {
		return contactStoreDao;
	}
	
	public boolean authenticateUser(DataSource dataSource, String userName, String password) throws SQLException {
		Connection conn = null; boolean isValidUser = false;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
			PreparedStatement pst;
			System.out.println("Username: "+userName+", password: "+password);
			pst = (PreparedStatement) conn.prepareStatement("select userName from user where userName = ? and password = ?");
			pst.setString(1, userName);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				isValidUser = true;
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			LOG.info("User "+userName+" Logged-In !");
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isValidUser;
	}
	
	
	
	public String registerUser(DataSource dataSource, User user) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
			PreparedStatement pst;
			String query = "Insert into user (firstName, lastName, userName, password) values "+ 
						   "( ?, ?, ?, ?)";
			pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setString(1, user.getFirstName());
			pst.setString(2, user.getLastName());
			pst.setString(3, user.getUserName());
			pst.setString(4, user.getPassword());
			pst.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			LOG.info("User: "+user.getFirstName()+" is Registered !");
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Registered Successfully!";
	}

	public JSONArray getAllContact(DataSource dataSource, String userName, String formatPretty) {
		Connection conn = null;
		System.out.println("In Impl");
		JSONArray JSONContactArr = new JSONArray(); 
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
			PreparedStatement pst;
			pst = (PreparedStatement) conn.prepareStatement("select * from contact where user_id = ?");
			pst.setInt(1, getUserId(dataSource, userName));
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
	    	   JSONContactArr.put(formatPretty.equals("default")? Common.formatResponseAsArray(rs):Common.formatResponseAsObj(rs));
			}	
		}catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("JSONContactArr"+JSONContactArr.toString());
		return JSONContactArr;
	}

	public int getUserId(DataSource dataSource, String userName) {
		Connection conn = null; int userId = 0;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
			PreparedStatement pst;
			pst = (PreparedStatement) conn.prepareStatement("select user_Id from user where userName = ?");
			pst.setString(1, userName);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				userId = rs.getInt("user_Id");
			}	
		}catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userId;
	}

	public void addNewContact(DataSource dataSource, Contact contact, String userName) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
			PreparedStatement pst;
			String query = "Insert into contact (firstName, lastName, phone, email, user_id, streetAddress, notes) values"+ 
					" (?, ?, ?, ?, ?, ?, ?)";
			pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setString(1, contact.getFirstName());
			pst.setString(2, contact.getLastName());
			pst.setString(3, contact.getPhoneNumber());
			pst.setString(4, contact.getEmail());
			pst.setInt(5, getUserId(dataSource, userName));
			pst.setString(6, contact.getStreetAddress());
			pst.setString(7, contact.getNotes());
			pst.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			LOG.info("For User: "+userName+" Contact:"+contact.getFirstName()+" is Addded !");
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isUserAlreadyExist(DataSource dataSource, User user) {
		Connection conn = null; boolean isValidUser = false;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
			PreparedStatement pst;
			pst = (PreparedStatement) conn.prepareStatement("select userName from user where userName = ?");
			pst.setString(1, user.getUserName());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				isValidUser = true;
				LOG.info("User "+user.getUserName()+" Already Exist !");
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isValidUser;
	}

	public void deleteContact(DataSource dataSource, String contactId) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
			PreparedStatement pst;
			String query = "delete from contact where contact_id in (?)";
			pst = (PreparedStatement) conn.prepareStatement(query);
			pst.setString(1, contactId);
			pst.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} finally {
			LOG.info("Contacts IDs : "+contactId+" Removed");
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}