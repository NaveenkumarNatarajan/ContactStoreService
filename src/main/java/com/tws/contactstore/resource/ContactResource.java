package com.tws.contactstore.resource;


import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import com.tws.contactstore.Contact;
import com.tws.contactstore.common.Common;
import com.tws.contactstore.config.DataSource;
import com.tws.contactstore.storage.ContactStoreDaoImpl;

/**
 * <h1>A Contact Store Resource Class that resolves all contact related services</h1>
 * @author Naveen
 * @since 2017-08-20
 * @version 1.0
 *
 */
@Path("/api.tws.com/v1/user")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

	private final DataSource dataSource;
	
	public ContactResource (DataSource  dataSource) {
		this.dataSource = dataSource;
	}
	
	@GET
	@Path("/{userName}/contact")
	public String getAllContact(@PathParam("userName") String userName,
				@QueryParam("pretty") @DefaultValue("default") String formatPretty) throws SQLException {
			JSONArray contactList = new JSONArray();
			Map<String,String> contactMap = new HashMap<String,String>(); 
			if(formatPretty.equals("default")){
				contactList = ContactStoreDaoImpl.getInstance().getAllContact(dataSource, userName.toLowerCase(), formatPretty);
				contactMap.put("data", contactList.toString());
				return Common.formatPayloadToJSON(contactMap.toString());
			}else
				return ContactStoreDaoImpl.getInstance().getAllContact(dataSource, userName.toLowerCase(), formatPretty).toString();
	}
	
	@POST
	@Path("/{userName}/contact")
	public Response addNewContact(@PathParam("userName") String userName, Contact contact) throws SQLException {
			ContactStoreDaoImpl.getInstance().addNewContact(dataSource, contact, userName.toLowerCase());
			return Response.status(Response.Status.OK).entity("Added Successfully").type(MediaType.TEXT_PLAIN).build();
	}
	
	@DELETE
	@Path("/{userName}/contact")
	public Response deleteContact(@PathParam("userName") String userName, String contactId) throws SQLException {
			ContactStoreDaoImpl.getInstance().deleteContact(dataSource, contactId);
			return Response.status(Response.Status.OK).entity("Deleted Successfully").type(MediaType.TEXT_PLAIN).build();
	}
}
