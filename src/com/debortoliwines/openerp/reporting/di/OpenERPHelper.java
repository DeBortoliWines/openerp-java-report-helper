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

package com.debortoliwines.openerp.reporting.di;
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
import com.debortoliwines.openerp.reporting.ui.OpenERPFieldInfo;


public class OpenERPHelper {
	
	private HashMap<String, ObjectAdapter> objectAdapterCache = new HashMap<String, ObjectAdapter>(); 

	public Object[][] getData(Session s, String modelName, ArrayList<OpenERPFieldInfo> selectedFields){
		
		QueryItem root = new QueryItem("", FieldType.ONE2MANY, modelName, 1);
		
		for (OpenERPFieldInfo path : selectedFields){
			buildQueryItems(root, path);
		}
		
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

	private ArrayList<Object[]> getData(ArrayList<String> fields, Session s, QueryItem item, boolean isChild, Object relatedFieldValue) throws XmlRpcException, OpeneERPApiException{

		ArrayList<Object[]> finalRows = new ArrayList<Object[]>();
		
		if (!objectAdapterCache.containsKey(item.getModelName())){
			objectAdapterCache.put(item.getModelName(), new ObjectAdapter(s, item.getModelName()));
		}
		
		ObjectAdapter adapter = objectAdapterCache.get(item.getModelName());
		RowCollection adapterRows = null;
		
		FilterCollection filters = new FilterCollection();
		if (isChild){
			Object [] idList = null;
			if (item.getRelationType() == FieldType.MANY2ONE
					&& relatedFieldValue instanceof Object[]
							&& ((Object[]) relatedFieldValue).length == 2){
				idList = new Object[]{((Object[]) relatedFieldValue)[0]};
			}
			else idList = (Object[]) relatedFieldValue;
					
			adapterRows = adapter.readObject(idList, item.fields.toArray(new String[]{}));
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
				localRows = combinedRows;
			}
			
			finalRows.addAll(localRows);
		}
		
		return finalRows;
		
	}
	
	/**
	 * Builds QueryItem objects that specifies the fields that needs to be fetched from every object and how
	 * the object relates to the parent
	 * @param rootItem
	 * @param field
	 * @return
	 */
	private QueryItem buildQueryItems(QueryItem rootItem, OpenERPFieldInfo field){
		if (field == null)
			return null;
		
		QueryItem parentItem = buildQueryItems(rootItem, field.getParentField());

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
		private final FilterCollection filters = new FilterCollection();
		private final ArrayList<QueryItem> childItems = new ArrayList<OpenERPHelper.QueryItem>();
		
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
		}
		
		public String getRelatedField(){
			return relatedField;
		}
		
		public FilterCollection getFilters(){
			return filters;
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
		
		public QueryItem getChildQuery(String relatedField, int instanceNum){
			for (QueryItem item : childItems){
				if (item.getRelatedField().equals(relatedField)
						&& item.getInstanceNum() == instanceNum){
					return item;
				}
			}
			return null;
		}
	}
}
