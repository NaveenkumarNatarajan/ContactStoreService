package com.tws.contactstore.application;

import java.util.EnumSet;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.tws.contactstore.config.ServiceConfiguration;
import com.tws.contactstore.resource.ContactResource;
import com.tws.contactstore.resource.UserResource;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

	public class ContactStoreApplication extends Application<ServiceConfiguration>{
	public static void main(String[] args) throws Exception {
        new ContactStoreApplication().run(args);
    }
	
	@Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
		
    }

	@Override
	public void run(ServiceConfiguration conf, Environment environment) throws Exception {
		
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		final FilterRegistration.Dynamic cors =
		        environment.servlets().addFilter("CORS", CrossOriginFilter.class);

		// Configure CORS parameters
	    cors.setInitParameter("allowedOrigins", "*");
	    cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
	    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");	    
		final UserResource userResource = new UserResource(conf.getDataSource());
		final ContactResource contactResource = new ContactResource(conf.getDataSource());
		environment.jersey().register(userResource);
		environment.jersey().register(contactResource);
	}
}
