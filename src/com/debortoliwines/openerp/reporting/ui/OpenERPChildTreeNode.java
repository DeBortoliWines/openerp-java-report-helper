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

import javax.swing.tree.DefaultMutableTreeNode;

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Session;
import com.debortoliwines.openerp.reporting.di.OpenERPFieldInfo;

/**
 * Custom treeNode to handle child objects in the available fields treeview
 * @author Pieter van der Merwe
 * @since  Jan 5, 2012
 */
public class OpenERPChildTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 3495864713177401698L;

	private boolean areChildrenDefined = false;
	private Session session;
	private OpenERPFieldInfo fieldInfo;

	public OpenERPChildTreeNode(Session session, OpenERPFieldInfo fieldInfo){
		this.session = session;
		this.fieldInfo = fieldInfo;
	}

	@Override
	public boolean isLeaf() {
		return fieldInfo.getRelatedChildModelName().length() == 0;
	}

	@Override
	public int getChildCount() {
	  // This function gets called as a user expands a node.
	  // Now dynamically add child records
		if (!areChildrenDefined)
			defineChildNodes();
		return super.getChildCount();
	}

	private void defineChildNodes() {
		areChildrenDefined = true;
		ObjectAdapter adapter;
		try {
			adapter = new ObjectAdapter(session, fieldInfo.getRelatedChildModelName());
	    // Dynamically add child records
			for (Field field : adapter.getFields()){
				add(new OpenERPChildTreeNode(session, new OpenERPFieldInfo(fieldInfo.getRelatedChildModelName(), 1, field.getName(), field.getName(), fieldInfo, field.getType(), field.getRelation())));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the OpenERPFieldInfo object associated with this node
	 * @return
	 */
	public OpenERPFieldInfo getFieldInfo(){
		return fieldInfo;
	}

	@Override
	public String toString() {
		return fieldInfo.getFieldName() + (fieldInfo.getRelatedChildModelName().length() > 0 ? " (" + fieldInfo.getRelatedChildModelName() + ")" : "" );
	}
}
