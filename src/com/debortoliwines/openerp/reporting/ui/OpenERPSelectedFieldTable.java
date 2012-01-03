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
			return "Field Name";
		else if (column == 2)
			return "Path";
		
		return "";
	};
	
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
	public int getColumnCount() {
		return 3;
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
			return fieldPaths.get(rowIndex).getFieldName();
		else if (columnIndex == 2)
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
