package com.debortoliwines.openerp.reporting.di;

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
import java.util.HashMap;

import org.apache.xmlrpc.XmlRpcException;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpeneERPApiException;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.debortoliwines.openerp.api.Field.FieldType;
import com.debortoliwines.openerp.api.FilterCollection.FilterOperator;
import com.debortoliwines.openerp.reporting.ui.OpenERPFieldInfo;

public class OpenERPHelper {
	
	private HashMap<String, ObjectAdapter> objectAdapterCache = new HashMap<String, ObjectAdapter>(); 

	public Object[][] getData(OpenERPConfiguration config) throws Exception{
		
		String modelName = config.getModelName();
		ArrayList<OpenERPFieldInfo> selectedFields = config.getSelectedFields();
		Session s = getSession(config);
		ArrayList<OpenERPFilterInfo> filters = config.getFilters();
		
		QueryItem root = getQueryItem(modelName, selectedFields, filters);
		
		ArrayList<String> fields = new ArrayList<String>();
		for (OpenERPFieldInfo field : selectedFields){
			fields.add(field.getModelName() + "-|-" + field.getInstanceNum() + "-|-" + field.getFieldName());
		}
		
		try {
			ArrayList<Object[]> data = getData(fields, s, root, false, null);
			return data.toArray(new Object[][]{});
		} catch (Exception e) {
			return null;
		}
	}
	
	public Session getSession(OpenERPConfiguration config) throws Exception{
		int portNumber = config.getPortNumber();
		Session s = new Session(config.getHostName(), portNumber, config.getDatabaseName(), config.getUserName(), config.getPassword());
		s.startSession();
		return s;
	}

	private ArrayList<Object[]> getData(ArrayList<String> fields, Session s, QueryItem item, boolean isChild, Object relatedFieldValue) throws XmlRpcException, OpeneERPApiException{

		ArrayList<Object[]> finalRows = new ArrayList<Object[]>();
		
		if (!objectAdapterCache.containsKey(item.getModelName())){
			objectAdapterCache.put(item.getModelName(), new ObjectAdapter(s, item.getModelName()));
		}
		
		ObjectAdapter adapter = objectAdapterCache.get(item.getModelName());
		RowCollection adapterRows = null;
		
		FilterCollection filters = new FilterCollection();
		ArrayList<OpenERPFilterInfo> itemAdditionalFilters = item.getFilters();
		if (itemAdditionalFilters != null){
			for (OpenERPFilterInfo filter : itemAdditionalFilters){
				if (filter.getOperator().equalsIgnoreCase("not"))
					filters.add(FilterOperator.NOT);
				else if (filter.getOperator().equalsIgnoreCase("or"))
					filters.add(FilterOperator.OR);
	
				try {
					filters.add(filter.getFieldName(), filter.getComparator(), filter.getValue());
				} catch (OpeneERPApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if (isChild){
			Object [] idList = null;
			if (item.getRelationType() == FieldType.MANY2ONE
					&& relatedFieldValue instanceof Object[]
							&& ((Object[]) relatedFieldValue).length == 2){
				idList = new Object[]{((Object[]) relatedFieldValue)[0]};
			}
			else idList = (Object[]) relatedFieldValue;
			
			if (filters.size() == 0){
				adapterRows = adapter.readObject(idList, item.fields.toArray(new String[]{}));
			}
			else {
				if (item.getRelationType() == FieldType.MANY2ONE){
					filters.add(0,"id", "=", idList);
				}
				else{
					filters.add(0,"id", "in", idList);
				}
				
				adapterRows = adapter.searchAndReadObject(filters, item.fields.toArray(new String[]{}));
			}
		}
		else adapterRows = adapter.searchAndReadObject(filters, item.fields.toArray(new String[]{}));
		
		for (Row adapterRow : adapterRows){
			
			ArrayList<Object[]> localRows = new ArrayList<Object[]>();
			
			Object[] localRow = new Object[fields.size()];
			for (String fieldName : item.fields){
				String key = item.getModelName() + "-|-" + item.getInstanceNum() + "-|-" + fieldName;
				int fieldIndex = fields.indexOf(key);
				if (fieldIndex >= 0){
					localRow[fieldIndex] = adapterRow.get(fieldName);
				}
			}
			localRows.add(localRow);
			
			for (QueryItem child : item.childItems){
				Object childIDs = adapterRow.get(child.getRelatedField());
				if (childIDs == null)
					continue;
				
				ArrayList<Object[]> combinedRows = new ArrayList<Object[]>();
				
				ArrayList<Object[]> childRows = getData(fields, s, child, true, childIDs);
				
				for (Object [] row : localRows){
					for (Object [] childRow : childRows){
						Object[] combinedRow = Arrays.copyOf(row, localRow.length);
						// Now copy all child values that were set.  This will include the direct child's fields
						// but also its children.  That is why we don't use child.fields but take everything with values.
						for (int i = 0; i < childRow.length; i++){
							if (childRow[i] != null){
								combinedRow[i] = childRow[i];
							}
						}
						combinedRows.add(combinedRow);
					}
				}
				
				// Do an outer join.  If this 'if' statement is removed, it becomes an inner join.
				if (combinedRows.size() > 0)
					localRows = combinedRows;
			}
			
			finalRows.addAll(localRows);
		}
		
		return finalRows;
		
	}
	
	public QueryItem getQueryItem(String modelName, ArrayList<OpenERPFieldInfo> selectedFields, ArrayList<OpenERPFilterInfo> filters){
		
		QueryItem root = new QueryItem("", FieldType.ONE2MANY, modelName, 1);
		root.setFilters(getFilter(filters, root.toString(), root.getInstanceNum()));
		
		for (OpenERPFieldInfo path : selectedFields){
			buildQueryItems(root, path, filters);
		}
		
		return root;
	}
	
	private ArrayList<OpenERPFilterInfo> getFilter(ArrayList<OpenERPFilterInfo> filters, String modelPath, int instanceNum){
		ArrayList<OpenERPFilterInfo> filterList = new ArrayList<OpenERPFilterInfo>();
		
		if (filters != null){
			for (OpenERPFilterInfo item : filters){
				if (item.getModelPath().equals(modelPath)
						&& item.getInstanceNum() == instanceNum)
				{
					filterList.add(item);
				}
			}
		}
			
		return filterList;
	}
	
	private QueryItem buildQueryItems(QueryItem rootItem, OpenERPFieldInfo field, ArrayList<OpenERPFilterInfo> filters){
		if (field == null)
			return null;
		
		QueryItem parentItem = buildQueryItems(rootItem, field.getParentField(), filters);

		QueryItem item = null;
		if (parentItem == null)
		{
			item = rootItem;
		}
		else{
			String relatedfield = field.getParentField().getFieldName();
			item  = parentItem.getChildQuery(relatedfield, field.getInstanceNum());
			if (item == null){
				item = new QueryItem(relatedfield, field.getParentField().getFieldType(), field.getModelName(), field.getInstanceNum());
				parentItem.addChildQuery(item);
				item.setFilters(getFilter(filters, item.toString(), item.getInstanceNum()));
			}
		}

		item.addField(field.getFieldName());
		return item;
		
	}
	
	public class QueryItem{
		private final String relatedField;
		private final FieldType relationType;
		private final String modelName;
		private final int instanceNum;
		private final ArrayList<String> fields = new ArrayList<String>();
		private ArrayList<OpenERPFilterInfo> filters = new ArrayList<OpenERPFilterInfo>();
		private final ArrayList<QueryItem> childItems = new ArrayList<OpenERPHelper.QueryItem>();
		private QueryItem parentQueryItem = null;
		
		public QueryItem(String relatedField, FieldType relationType, String modelName, int instanceNum){
			this.relatedField = relatedField;
			this.relationType = relationType;
			this.modelName = modelName;
			this.instanceNum = instanceNum;
		}
		
		public void addField(String fieldName){
			if (!fields.contains(fieldName)){
				fields.add(fieldName);
			}
		}
		
		public void addChildQuery(QueryItem item){
			childItems.add(item);
			item.setParentQueryItem(this);
		}
		
		public String getRelatedField(){
			return relatedField;
		}
		
		public ArrayList<OpenERPFilterInfo> getFilters(){
			return filters;
		}
		
		public void setFilters(ArrayList<OpenERPFilterInfo> filters) {
			this.filters = filters;
		}
		
		public String getModelName() {
			return modelName;
		}
		
		public FieldType getRelationType() {
			return relationType;
		}
		
		public int getInstanceNum() {
			return instanceNum;
		}
		
		public ArrayList<QueryItem> getChildItems() {
			return childItems;
		}
		
		public QueryItem getChildQuery(String relatedField, int instanceNum){
			for (QueryItem item : childItems){
				if (item.getRelatedField().equals(relatedField)
						&& item.getInstanceNum() == instanceNum){
					return item;
				}
			}
			return null;
		}
		
		public ArrayList<QueryItem> getAllChildItems(){
			ArrayList<QueryItem> children = new ArrayList<OpenERPHelper.QueryItem>();
			children.addAll(childItems);
			for (QueryItem item : childItems){
				children.addAll(item.getAllChildItems());
			}
			return children;
		}
		
		public void setParentQueryItem(QueryItem parentQueryItem) {
			this.parentQueryItem = parentQueryItem;
		}
		
		@Override
		public String toString() {
			if (this.parentQueryItem == null){
				return "[" + modelName + "]";
			}
			else {
				return parentQueryItem.toString() + ".[" + modelName + "]";
			}
		}
	}
}
