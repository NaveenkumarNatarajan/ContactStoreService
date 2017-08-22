package com.tws.contactstore.config;

import io.dropwizard.Configuration;

public class ServiceConfiguration extends Configuration {

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
