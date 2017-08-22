package com.tws.contactstore.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

import com.tws.contactstore.User;
import com.tws.contactstore.config.DataSource;
import com.tws.contactstore.storage.ContactStoreDaoImpl;
/**
 * <h1>A Contact Store User Resource Class that resolves all User services</h1>
 * <p>
 * 	  <br>Authenticate-User API to validate login credential who tries to Sign-In</br>
 *    <br>Register-User API to create New user</br>
 * </p> 
 * @author Naveen
 * @since 2017-08-20
 * @version 1.0
 *
 */
@Path("/api.tws.com/v1/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

private final DataSource dataSource;
	
	public UserResource (DataSource  dataSource) {
		this.dataSource = dataSource;
	}
	
	@POST
	@Path("/authenticate")
	public Response authenticateUser(User user) throws SQLException {
		boolean isValidUser = false;
		isValidUser = ContactStoreDaoImpl.getInstance().authenticateUser(dataSource, user.getUserName().toLowerCase(),user.getPassword());
		System.out.println("Authenticate User: " + isValidUser);
		if(isValidUser){
			return Response.status(Response.Status.OK).entity(isValidUser).type(MediaType.TEXT_PLAIN).build();
		}else
			return Response.status(Response.Status.UNAUTHORIZED).entity(isValidUser).type(MediaType.TEXT_PLAIN).build();
	}
	
	@POST
	@Path("/register")
	public Response registerUser(User user) throws SQLException {
		if(ContactStoreDaoImpl.getInstance().isUserAlreadyExist(dataSource, user)){
			return Response.status(Response.Status.OK).entity("User Already Exist !").type(MediaType.TEXT_PLAIN).build();
		}else
			return Response.status(Response.Status.OK).entity(ContactStoreDaoImpl.getInstance().
					registerUser(dataSource, user)).type(MediaType.TEXT_PLAIN).build();
	}
}
