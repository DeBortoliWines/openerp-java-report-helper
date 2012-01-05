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

import javax.swing.table.AbstractTableModel;
import com.debortoliwines.openerp.reporting.di.OpenERPQueryItem;

/**
 * Custom table model to hold modelPaths.  It is used to selected filters by modelPath.
 * @author Pieter van der Merwe
 * @since  Jan 5, 2012
 */
public class OpenERPFilterModelsTable extends AbstractTableModel {
	
	private static final long serialVersionUID = 1116228555949996312L;

	private ArrayList<OpenERPQueryItem> queryItems = new ArrayList<OpenERPQueryItem>();
	
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
			return queryItems.get(rowIndex).getModelPath();
		else if (columnIndex == 2)
			return queryItems.get(rowIndex).getInstanceNum();

		return null;
	}
	
	public ArrayList<OpenERPQueryItem> getQueryItem() {
		return queryItems;
	}
	
	public void setQueryItems(ArrayList<OpenERPQueryItem> queryItems) {
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