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

/**
 * Stores filter information.  The key that links a filter to a table is the modelPath and instanceNumber.
 * The modelPath and instanceNumber are used later by OpenERPQueryItem to link a filter to a query call
 * @author Pieter van der Merwe
 * @since  Jan 5, 2012
 */
public class OpenERPFilterInfo implements Cloneable{

  private String modelPath;
  private int instanceNum;
  private String operator;
  private String fieldName;
  private String comparator;
  private Object value;

  /**
   * Default constructor for XMLEncoder to work
   */
  public OpenERPFilterInfo(){

  }

  /**
   * Constructor
   * @param modelPath The model path this filter relates to
   * @param instanceNum The instance number of the model path this filter relates to
   * @param operator AND/OR/NOT
   * @param fieldName Field name to use in the filter
   * @param comparator Comparator to use in the filter.  Check the OpenERPJavaAPI.FilterHelper for comparators
   * @param value Value to use in the filter
   */
  public OpenERPFilterInfo(String modelPath, int instanceNum, String operator, String fieldName, String comparator, Object value){
    this.modelPath = modelPath;
    this.instanceNum = instanceNum;
    this.operator = operator;
    this.fieldName = fieldName;
    this.comparator = comparator;
    this.value = value;
  }
  
  @Override
  public OpenERPFilterInfo clone() {
    OpenERPFilterInfo newInstance = new OpenERPFilterInfo(this.getModelPath(), this.getInstanceNum(), this.getOperator(), this.getFieldName(), this.getComparator(), this.getValue());
    newInstance.setFieldName(this.getFieldName());
    return newInstance;
  }

  /**
   * Get the model path this filter relates to
   * @return
   */
  public String getModelPath() {
    return modelPath;
  }

  /**
   * Set the model path this filter relates to
   * @param modelPath
   */
  public void setModelPath(String modelPath) {
    this.modelPath = modelPath;
  }

  /**
   * Get the instance number of the model path this filter relates to
   * @return
   */
  public int getInstanceNum() {
    return instanceNum;
  }

  /**
   * Set the instance number of the model path this filter relates to
   * @param instanceNum
   */
  public void setInstanceNum(int instanceNum) {
    this.instanceNum = instanceNum;
  }

  /**
   * Get the operator for the filter
   * @return
   */
  public String getOperator() {
    return operator;
  }

  /**
   * Set the operator for the filter (AND/OR/NOT)
   * @param operator
   */
  public void setOperator(String operator) {
    this.operator = operator;
  }

  /**
   * Get the field name that will be used in the filter
   * @return
   */
  public String getFieldName() {
    return fieldName;
  }

  /**
   * Set the field name that will be used in the filter
   * @param fieldName
   */
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  /**
   * Get the comparator that will compare the field to the value
   * @return
   */
  public String getComparator() {
    return comparator;
  }

  /**
   * Set the comparator that will compare the field to the value.  See OpenERPJavaAPI.FilterHelper for valid options.
   * @param comparator
   */
  public void setComparator(String comparator) {
    this.comparator = comparator;
  }

  /**
   * Get the value that will be compared against the field
   * @return
   */
  public Object getValue() {
    return value;
  }

  /**
   * Set the value that will be compared against the field
   * @param value
   */
  public void setValue(Object value) {
    this.value = value;
  }
}
