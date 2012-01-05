package com.debortoliwines.openerp.reporting.ui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.debortoliwines.openerp.reporting.di.OpenERPHelper.QueryItem;

public class OpenERPFilterModelsTable extends AbstractTableModel {
	
	private static final long serialVersionUID = 1116228555949996312L;

	private ArrayList<QueryItem> queryItems = new ArrayList<QueryItem>();
	
	@Override
	public String getColumnName(int column) {
		if (column == 0)
			return "#";
		else if (column == 1)
			return "Path";
		else if (column == 2)
			return "Copy #";
		
		return "";
	};
	
	@Override
	public int getColumnCount() {
		return 3;
	}
	
	@Override
	public int getRowCount() {
		return queryItems.size();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return rowIndex;
		else if (columnIndex == 1)
			return queryItems.get(rowIndex).toString();
		else if (columnIndex == 2)
			return queryItems.get(rowIndex).getInstanceNum();

		return null;
	}
	
	public ArrayList<QueryItem> getQueryItem() {
		return queryItems;
	}
	
	public void setQueryItems(ArrayList<QueryItem> queryItems) {
		if (queryItems == null)
		{
			this.queryItems.clear();
		}
		else{
			this.queryItems = queryItems;
		}
		fireTableDataChanged();
	}
}