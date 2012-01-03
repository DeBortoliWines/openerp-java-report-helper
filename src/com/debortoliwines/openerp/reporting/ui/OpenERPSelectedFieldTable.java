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
