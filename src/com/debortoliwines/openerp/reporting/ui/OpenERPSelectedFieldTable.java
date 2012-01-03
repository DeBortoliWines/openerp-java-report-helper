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
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.AbstractTableModel;


public class OpenERPSelectedFieldTable extends AbstractTableModel {
	private static final long serialVersionUID = -6328970275546027138L;

	private final ArrayList<OpenERPFieldInfo> fieldPaths = new ArrayList<OpenERPFieldInfo>();
	
	@Override
	public String getColumnName(int column) {
		if (column == 0)
			return "#";
		else if (column == 1)
			return "Instance";
		else if (column == 2)
			return "Field Name";
		else if (column == 3)
			return "Original Field Name";
		else if (column == 4)
			return "Path";
		
		return "";
	};
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 1
				|| columnIndex == 2){
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
		
		if (columnIndex == 1)
			fieldPaths.get(rowIndex).setInstanceNum(Integer.parseInt(aValue.toString()));
		else if (columnIndex == 2)
			fieldPaths.get(rowIndex).setRenamedFieldName(aValue.toString());
	}
	
	@Override
	public int getColumnCount() {
		return 5;
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

		return null;
	}
	
	public ArrayList<OpenERPFieldInfo> getFieldPaths() {
		return fieldPaths;
	}
	
	public void removeField(int [] indexes){
		Arrays.sort(indexes);
		
		for (int i = indexes.length -1; i >=0; i--){
			fieldPaths.remove(indexes[i]);
		}
		fireTableDataChanged();
	}
}
