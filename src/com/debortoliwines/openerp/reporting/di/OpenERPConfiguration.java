package com.debortoliwines.openerp.reporting.di;

/*
 *   This file is part of OpenERPJavaReportHelper
 *
 *   OpenERPJavaAPI is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   OpenERPJavaAPI is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with OpenERPJavaAPI.  If not, see <http://www.gnu.org/licenses/>.
 *
 *   Copyright 2012 De Bortoli Wines Pty Limited (Australia)
 */

import java.util.ArrayList;

/**
 * Stores the configuration that is used by the OpenERPJavaReportHelper to fetch the specified data.
 * An instance can be encoded with an XMLEncoder.  Use the OpenERPHelper class to help setup the XMLEncoder. 
 * @author Pieter van der Merwe
 * @since  Jan 5, 2012
 *
 */
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

  /**
   * The available data sources.  
   * If STANDARD, a search is done using the fields and filters.
   * If CUSTOM, a custom procedure is called specified by customFunctionName.
   * @author Pieter van der Merwe
   * @since  Jan 5, 2012
   **/
  public enum DataSource{
    STANDARD,
    CUSTOM
  }

  /**
   * Gets the OpenERP host name that will be connected. 
   * @return
   */
  public String getHostName() {
    return hostName;
  }

  /**
   * Sets the OpenERP host name that will be connected.
   * @param hostName Host name to connect to.  Can be an IP address or Host Name
   */
  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  /**
   * Gets the port number that will be used to connect to the OpenERP server
   * @return
   */
  public int getPortNumber() {
    return portNumber;
  }

  /**
   * Sets the port number that will be used to connect to the OpenERP server
   * @param portNumber
   */
  public void setPortNumber(int portNumber) {
    this.portNumber = portNumber;
  }

  /**
   * Gets the database name that will be used on the OpenERP server.
   * @return
   */
  public String getDatabaseName() {
    return databaseName;
  }

  /**
   * Sets the database name that will be used on the OpenERP server.
   * @param databaseName
   */
  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  /**
   * Gets the user name that will be used to authenticate against the database
   * @return
   */
  public String getUserName() {
    return userName;
  }

  /**
   * Sets the user name that will be used to authenticate against the database 
   * @param userName
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * Gets the password that will be used to authenticate against the database
   * @return
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password that will be used to authenticate against the database
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Get the model name that will be used to call the custom function or be the main model of a search
   * @return
   */
  public String getModelName() {
    return modelName;
  }

  /**
   * Set the model name that will be used to call the custom function or be the main model of a search
   * @param modelName
   */
  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  /**
   * Gets the data source access method that will be used to fetch data
   * @return
   */
  public DataSource getDatasource() {
    return datasource;
  }

  /**
   * Sets the data source access method that will be used to fetch data
   * @param datasource
   */
  public void setDatasource(DataSource datasource) {
    this.datasource = datasource;
  }

  /**
   * Gets the custom function that will be called if the access method is CUSTOM
   * @return
   */
  public String getCustomFunctionName() {
    return customFunctionName;
  }

  /**
   * Sets the custom function that will be called if the access method is CUSTOM
   * @param customFunctionName
   */
  public void setCustomFunctionName(String customFunctionName) {
    this.customFunctionName = customFunctionName;
  }

  /**
   * Gets the fields that are selected to be retrieved if the access method is STANDARD
   * @return
   */
  public ArrayList<OpenERPFieldInfo> getSelectedFields() {
    return selectedFields;
  }

  /**
   * Sets the fields that are selected to be retrieved if the access method is STANDARD
   * @param selectedFields
   */
  public void setSelectedFields(ArrayList<OpenERPFieldInfo> selectedFields) {
    this.selectedFields = selectedFields;
  }	

  /**
   * Gets the filters for each table accessed if the data source is STANDARD
   * @return
   */
  public ArrayList<OpenERPFilterInfo> getFilters() {
    return filters;
  }

  /**
   * Sets the filters for each table accessed if the data source is STANDARD
   * @param filters
   */
  public void setFilters(ArrayList<OpenERPFilterInfo> filters) {
    this.filters = filters;
  }
}
