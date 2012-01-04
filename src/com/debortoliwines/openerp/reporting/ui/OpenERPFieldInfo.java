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

package com.debortoliwines.openerp.reporting.ui;

import com.debortoliwines.openerp.api.Field.FieldType;

public class OpenERPFieldInfo implements Cloneable{

	private final String modelName;
	private final String fieldName;
	private final OpenERPFieldInfo parentField;
	private final FieldType fieldType;
	private final String childModelName;
	private int instanceNum;
	private String renamedFieldName;
	
	public OpenERPFieldInfo(String modelName, int instanceNum, String fieldName, String renamedFieldName, OpenERPFieldInfo parentField, FieldType fieldType, String childModelName){
		this.modelName = modelName;
		this.fieldName = fieldName;
		this.parentField = parentField;
		this.fieldType = fieldType;
		this.childModelName = childModelName;
		this.instanceNum = instanceNum;
		this.renamedFieldName = renamedFieldName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OpenERPFieldInfo){
			OpenERPFieldInfo target = (OpenERPFieldInfo) obj;
			
			return target.getModelPathName().equals(this.getModelPathName())
					&& target.getFieldName().equals(this.getFieldName())
					&& target.getInstanceNum() == this.getInstanceNum();
		}
		else return super.equals(obj);
	}
	
	@Override
	protected OpenERPFieldInfo clone() {
		return new OpenERPFieldInfo(modelName, instanceNum, fieldName, renamedFieldName, parentField, fieldType, childModelName);
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
	
	public int getInstanceNum() {
		return instanceNum;
	}
	
	public void incrementInstanceNum() {
		this.instanceNum++;
	}
	
	public void setInstanceNum(int instanceNum) {
		this.instanceNum = instanceNum;
	}
	
	public String getRenamedFieldName() {
		return renamedFieldName;
	}
	
	public void setRenamedFieldName(String renamedFieldName) {
		this.renamedFieldName = renamedFieldName;
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
