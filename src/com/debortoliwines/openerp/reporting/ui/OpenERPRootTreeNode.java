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
 * Custom treeNode to handle the root item and to create child items in the available fields treeview
 * @author Pieter van der Merwe
 * @since  Jan 5, 2012
 */
public class OpenERPRootTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 6644384126284842886L;
	private boolean areChildrenDefined = false;
	private Session session;
	private String modelName;

	public OpenERPRootTreeNode(Session session, String modelName){
		this.session = session;
		this.modelName = modelName;
	}
	
	@Override
	public boolean isLeaf() {
		return(false);
	}

	@Override
	public int getChildCount() {
		if (!areChildrenDefined)
			defineChildNodes();
		return(super.getChildCount());
	}

	private void defineChildNodes() {
		areChildrenDefined = true;
		ObjectAdapter adapter;
		try {
			adapter = new ObjectAdapter(session, modelName);
			for (Field fld : adapter.getFields()){
				add(new OpenERPChildTreeNode(session, new OpenERPFieldInfo(modelName, 1, fld.getName(), fld.getName(), null, fld.getType(), fld.getRelation())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return modelName;
	}
}
