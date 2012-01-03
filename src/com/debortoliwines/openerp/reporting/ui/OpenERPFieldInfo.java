package com.debortoliwines.openerp.reporting.ui;
import com.debortoliwines.openerp.api.Field.FieldType;

public class OpenERPFieldInfo {

	private final String modelName;
	private final String fieldName;
	private final OpenERPFieldInfo parentField;
	private final FieldType fieldType;
	private final String childModelName;
	
	public OpenERPFieldInfo(String modelName, String fieldName, OpenERPFieldInfo parentField, FieldType fieldType, String childModelName){
		this.modelName = modelName;
		this.fieldName = fieldName;
		this.parentField = parentField;
		this.fieldType = fieldType;
		this.childModelName = childModelName;

	}

	public OpenERPFieldInfo getParentField() {
		return parentField;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public FieldType getFieldType() {
		return fieldType;
	}
	
	public String getChildModelName() {
		return childModelName;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public String getModelPathName(){
		if (parentField == null){
			return "[" + modelName + "]";
		}
		else {
			return parentField.getModelPathName() + ".[" + modelName + "]";
		}
	}
	
}
