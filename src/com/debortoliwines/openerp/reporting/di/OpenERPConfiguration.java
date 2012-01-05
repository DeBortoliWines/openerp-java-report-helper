package com.debortoliwines.openerp.reporting.di;

import java.util.ArrayList;

import com.debortoliwines.openerp.reporting.ui.OpenERPFieldInfo;

public class OpenERPConfiguration {

	private String hostName;
	private int portNumber;
	private String databaseName;
	private String userName;
	private String password;
	private String modelName;
	private DataSource datasource;
	private String customFunctionName;
	private ArrayList<OpenERPFieldInfo> selectedFields = new ArrayList<OpenERPFieldInfo>();
	private ArrayList<OpenERPFilterInfo> filters = new ArrayList<OpenERPFilterInfo>();
	
	public enum DataSource{
		STANDARD,
		CUSTOM
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public int getPortNumber() {
		return portNumber;
	}
	
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	
	public String getDatabaseName() {
		return databaseName;
	}
	
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public DataSource getDatasource() {
		return datasource;
	}
	
	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public String getCustomFunctionName() {
		return customFunctionName;
	}
	
	public void setCustomFunctionName(String customFunctionName) {
		this.customFunctionName = customFunctionName;
	}
	
	public ArrayList<OpenERPFieldInfo> getSelectedFields() {
		return selectedFields;
	}
	
	public void setSelectedFields(ArrayList<OpenERPFieldInfo> selectedFields) {
		this.selectedFields = selectedFields;
	}	
	
	public ArrayList<OpenERPFilterInfo> getFilters() {
		return filters;
	}
	
	public void setFilters(ArrayList<OpenERPFilterInfo> filters) {
		this.filters = filters;
	}
}
