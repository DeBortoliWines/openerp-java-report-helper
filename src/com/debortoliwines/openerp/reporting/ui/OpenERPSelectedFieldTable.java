package com.debortoliwines.openerp.reporting.ui;

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
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;

import com.debortoliwines.openerp.reporting.di.OpenERPFieldInfo;

/**
 * Custom table model to handle selected field data
 * @author Pieter van der Merwe
 * @since  Jan 5, 2012
 */
public class OpenERPSelectedFieldTable extends AbstractTableModel {
	private static final long serialVersionUID = -6328970275546027138L;

	private ArrayList<OpenERPFieldInfo> fieldPaths = new ArrayList<OpenERPFieldInfo>();
	
	@Override
	public String getColumnName(int column) {
		if (column == 0)
			return "#";
		else if (column == 1)
			return "Copy #";
		else if (column == 2)
			return "Field Name";
		else if (column == 3)
			return "Original Field Name";
		else if (column == 4)
			return "Path";
		else if (column == 5)
      return "Sort #";
		else if (column == 6)
      return "Direction";
		
		return "";
	};
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 1 || columnIndex == 2 || columnIndex == 5 || columnIndex == 6){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addField(int index, OpenERPFieldInfo fieldPath) {
		if (index < 0){
			this.fieldPaths.add(fieldPath);
		}
		else{ 
			this.fieldPaths.add(index,fieldPath);
		}
		fireTableDataChanged();
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex >= fieldPaths.size())
			return;

		// Most fields use an integer value.  If it isn't a valid value, make it 0
    int iValue = 0;

    if (columnIndex == 1 
        || columnIndex == 5){
      try{
        iValue = Integer.parseInt(aValue.toString());
      }
      catch (Exception e){
        iValue = 0;
      }
    }
		
		if (columnIndex == 1){
			fieldPaths.get(rowIndex).setInstanceNum(iValue);
		}
		else if (columnIndex == 2)
			fieldPaths.get(rowIndex).setRenamedFieldName(aValue.toString());
		else if (columnIndex == 5){
		  fieldPaths.get(rowIndex).setSortIndex(iValue);
		}
    else if (columnIndex == 6){
      iValue = 0;
      if (aValue.toString().equals("Descending"))
          iValue = 1;
      
      fieldPaths.get(rowIndex).setSortDirection(iValue);
    }
	}
	
	@Override
	public int getColumnCount() {
		return 7;
	}
	
	@Override
	public int getRowCount() {
		return fieldPaths.size();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return rowIndex;
		else if (columnIndex == 1)
			return fieldPaths.get(rowIndex).getInstanceNum();
		else if (columnIndex == 2)
			return fieldPaths.get(rowIndex).getRenamedFieldName();
		else if (columnIndex == 3)
			return fieldPaths.get(rowIndex).getFieldName();
		else if (columnIndex == 4)
			return fieldPaths.get(rowIndex).getModelPathName();
		else if (columnIndex == 5)
      return fieldPaths.get(rowIndex).getSortIndex();
		else if (columnIndex == 6){
		  if (fieldPaths.get(rowIndex).getSortDirection() == 0)
		    return "Ascending";
		  else return "Descending";
		}

		return null;
	}
	
	public ArrayList<OpenERPFieldInfo> getFieldPaths() {
		return fieldPaths;
	}
	
	public void setFieldPaths(ArrayList<OpenERPFieldInfo> fieldPaths) {
		if (fieldPaths == null)
		{
			this.fieldPaths.clear();
		}
		else{
			this.fieldPaths = fieldPaths;
		}
		fireTableDataChanged();
	}
	
	public void removeField(int [] indexes){
		Arrays.sort(indexes);
		
		for (int i = indexes.length -1; i >=0; i--){
			fieldPaths.remove(indexes[i]);
		}
		fireTableDataChanged();
	}
}
