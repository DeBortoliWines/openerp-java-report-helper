package com.debortoliwines.openerp.reporting.di;

public class OpenERPFilterInfo {
	
	private String modelPath;
	private int instanceNum;
	private String operator;
	private String fieldName;
	private String comparator;
	private Object value;
	
	// Default constructor for XML serialization to work
	public OpenERPFilterInfo(){
		
	}
	
	public OpenERPFilterInfo(String modelPath, int instanceNum, String operator, String fieldName, String comparator, Object value){
		this.modelPath = modelPath;
		this.instanceNum = instanceNum;
		this.operator = operator;
		this.comparator = comparator;
		this.value = value;
	}
	
	public String getModelPath() {
		return modelPath;
	}
	
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}
	
	public int getInstanceNum() {
		return instanceNum;
	}
	
	public void setInstanceNum(int instanceNum) {
		this.instanceNum = instanceNum;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getComparator() {
		return comparator;
	}
	
	public void setComparator(String comparator) {
		this.comparator = comparator;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}
