package com.debortoliwines.openerp.reporting.ui;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.xmlrpc.XmlRpcException;

import com.debortoliwines.openerp.api.Field;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpeneERPApiException;
import com.debortoliwines.openerp.api.Session;


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
				add(new OpenERPChildTreeNode(session, new OpenERPFieldInfo(modelName, fld.getName(), null, fld.getType(), fld.getRelation())));
			}
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OpeneERPApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return modelName;
	}
}
