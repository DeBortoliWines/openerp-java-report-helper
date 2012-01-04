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

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import java.awt.GridLayout;

import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.debortoliwines.openerp.reporting.di.OpenERPConfiguration;
import com.debortoliwines.openerp.reporting.di.OpenERPConfiguration.DataSource;
import com.debortoliwines.openerp.reporting.di.OpenERPHelper;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class OpenERPPanel extends JPanel {

	private static final long serialVersionUID = -8365272838537964196L;
	
	private final JPanel contentPanel = new JPanel();
	private JTree availableTree;
	DefaultTreeModel model = new DefaultTreeModel(new DefaultMutableTreeNode("root"));
	private final DataFlavor nodesFlavor;
	private JTable selectedTable;
	private OpenERPSelectedFieldTable tableModel = new OpenERPSelectedFieldTable();
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JTextField txtHost;
	private JTextField txtPort;
	private JTextField txtUsername;
	private JComboBox cmbDatabase;
	private JComboBox cmbModelName;
	private JPasswordField pwdPassword;
	private JTextField txtCustomFunction;
	private JComboBox cmbDataSource;
	
	/**
	 * Create the dialog.
	 * @throws ClassNotFoundException 
	 */
	public OpenERPPanel() throws ClassNotFoundException {
		setBounds(100, 100, 671, 523);
		setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPanel.add(tabbedPane, BorderLayout.CENTER);
			{
				JPanel pnlDataSource = new JPanel();
				tabbedPane.addTab("Data Source", null, pnlDataSource, null);
				{
					txtHost = new JTextField();
					txtHost.addFocusListener(new FocusAdapter() {
						@Override
						public void focusLost(FocusEvent e) {
							populateDatabase();
							populateModelCombo();
						}
					});
					txtHost.setColumns(10);
				}
				{
					txtPort = new JTextField();
					txtPort.addFocusListener(new FocusAdapter() {
						@Override
						public void focusLost(FocusEvent e) {
							populateDatabase();
							populateModelCombo();
						}
					});
					txtPort.setColumns(10);
				}
				{
					txtUsername = new JTextField();
					txtUsername.addFocusListener(new FocusAdapter() {
						@Override
						public void focusLost(FocusEvent e) {
							populateModelCombo();
						}
					});
					txtUsername.setColumns(10);
				}
				{
					cmbDatabase = new JComboBox();
					cmbDatabase.addFocusListener(new FocusAdapter() {
						@Override
						public void focusLost(FocusEvent e) {
							populateModelCombo();
						}
					});
				}
				{
					cmbModelName = new JComboBox();
					cmbModelName.addFocusListener(new FocusAdapter() {
						@Override
						public void focusGained(FocusEvent e) {
							populateModelCombo();
						}
					});
				}
				
				pwdPassword = new JPasswordField();
				pwdPassword.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						populateModelCombo();
					}
				});
				
				JLabel lblPort = new JLabel("Port:");
				lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
				
				JLabel lblHost = new JLabel("Host:");
				lblHost.setHorizontalAlignment(SwingConstants.RIGHT);
				
				JLabel lblDatabase = new JLabel("Database:");
				lblDatabase.setHorizontalAlignment(SwingConstants.RIGHT);
				
				JLabel lblUsername = new JLabel("Username:");
				lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
				
				JLabel lblPassword = new JLabel("Password:");
				lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
				
				JLabel lblModelName = new JLabel("Model Name:");
				lblModelName.setHorizontalAlignment(SwingConstants.RIGHT);
				cmbDataSource = new JComboBox();
				cmbDataSource.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dataSourceChanged();
					}
				});
				cmbDataSource.setModel(new DefaultComboBoxModel(new String[] {"Standard Search", "Custom Function"}));
				JLabel lblDataSource = new JLabel("Data Source:");
				lblDataSource.setHorizontalAlignment(SwingConstants.RIGHT);
				JLabel lblCustomDataFunction = new JLabel("Custom Data Function:");
				lblCustomDataFunction.setHorizontalAlignment(SwingConstants.RIGHT);
				txtCustomFunction = new JTextField();
				txtCustomFunction.setColumns(10);
				
				JButton btnTest = new JButton("Test");
				btnTest.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String modelName = (cmbModelName.getSelectedItem() == null ? "" : cmbModelName.getSelectedItem().toString());
							getObjectAdapter(modelName);
							JOptionPane.showMessageDialog(null, "Connection was successful", "Success", JOptionPane.INFORMATION_MESSAGE);
						}
						catch (Exception e){
							JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				GroupLayout gl_pnlDataSource = new GroupLayout(pnlDataSource);
				gl_pnlDataSource.setHorizontalGroup(
					gl_pnlDataSource.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDataSource.createSequentialGroup()
							.addGap(66)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblPort, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHost, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCustomDataFunction)
								.addComponent(lblModelName, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDatabase, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDataSource))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.LEADING)
								.addComponent(btnTest)
								.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(cmbModelName, Alignment.LEADING, 0, 224, Short.MAX_VALUE)
									.addComponent(cmbDatabase, Alignment.LEADING, 0, 224, Short.MAX_VALUE)
									.addComponent(txtUsername)
									.addComponent(cmbDataSource, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(pwdPassword, 211, 211, Short.MAX_VALUE)
									.addComponent(txtCustomFunction, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
									.addComponent(txtPort, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
									.addComponent(txtHost)))
							.addContainerGap(193, Short.MAX_VALUE))
				);
				gl_pnlDataSource.setVerticalGroup(
					gl_pnlDataSource.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDataSource.createSequentialGroup()
							.addGap(40)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblHost)
								.addComponent(txtHost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPort))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.BASELINE)
								.addComponent(cmbDatabase, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDatabase))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblUsername))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.LEADING)
								.addComponent(pwdPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPassword))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.BASELINE)
								.addComponent(cmbDataSource, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDataSource))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.BASELINE)
								.addComponent(cmbModelName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblModelName))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDataSource.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtCustomFunction, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCustomDataFunction))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnTest)
							.addContainerGap(171, Short.MAX_VALUE))
				);
				pnlDataSource.setLayout(gl_pnlDataSource);
			}
			{
				JPanel pnlSearchFields = new JPanel();
				tabbedPane.addTab("Search Fields", null, pnlSearchFields, null);
				pnlSearchFields.setLayout(new GridLayout(0, 2, 0, 0));
				{
					{
						{
							{
								JPanel panel_1 = new JPanel();
								pnlSearchFields.add(panel_1);
								panel_1.setLayout(new BorderLayout(0, 0));
								{
									JPanel panel_2 = new JPanel();
									FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
									flowLayout.setAlignment(FlowLayout.LEFT);
									panel_1.add(panel_2, BorderLayout.NORTH);
									{
										lblNewLabel = new JLabel("Available Fields");
										panel_2.add(lblNewLabel);
									}
								}
								{
									scrollPane = new JScrollPane();
									panel_1.add(scrollPane);
									{
										availableTree = new JTree();
										availableTree.setModel(model);
										availableTree.setDragEnabled(true);
										availableTree.setTransferHandler(new TreeViewTransferHandler());
										scrollPane.setViewportView(availableTree);
									}
								}
							}
							{
								JPanel panel_1 = new JPanel();
								pnlSearchFields.add(panel_1);
								panel_1.setLayout(new BorderLayout(0, 0));
								{
									JPanel panel_2 = new JPanel();
									panel_1.add(panel_2, BorderLayout.NORTH);
									panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
									{
										JLabel lblNewLabel_1 = new JLabel("Selected Fields");
										panel_2.add(lblNewLabel_1);
									}
								}
								JScrollPane scrollPane_1 = new JScrollPane();
								panel_1.add(scrollPane_1);
								selectedTable = new JTable();
								selectedTable.addKeyListener(new KeyAdapter() {
									@Override
									public void keyReleased(KeyEvent e) {
										if (e.getKeyCode() == KeyEvent.VK_DELETE){
											tableModel.removeField(selectedTable.getSelectedRows());
										}
									}
								});
								scrollPane_1.setViewportView(selectedTable);
								selectedTable.setShowVerticalLines(false);
								selectedTable.setFillsViewportHeight(true);
								selectedTable.setModel(tableModel);
								selectedTable.setDragEnabled(true);
								selectedTable.setTransferHandler(new TableTransferHandler());
								selectedTable.getColumnModel().getColumn(0).setMaxWidth(25);
								selectedTable.getColumnModel().getColumn(1).setMaxWidth(75);
								selectedTable.getColumnModel().getColumn(2).setMaxWidth(200);
								selectedTable.getColumnModel().getColumn(2).setPreferredWidth(150);
								selectedTable.getColumnModel().getColumn(3).setMaxWidth(200);
								selectedTable.getColumnModel().getColumn(3).setPreferredWidth(150);
								selectedTable.getColumnModel().getColumn(4).setPreferredWidth(150);
							}
						}
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnLoad = new JButton("Load");
				btnLoad.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							model = new DefaultTreeModel(new OpenERPRootTreeNode(getSession(),cmbModelName.getSelectedItem().toString()));
							availableTree.setModel(model);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				{
					JButton btnPreview = new JButton("Preview");
					btnPreview.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try {
								new OpenERPHelper().getData(getSession(), cmbModelName.getSelectedItem().toString(), tableModel.getFieldPaths());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
					buttonPane.add(btnPreview);
				}
				buttonPane.add(btnLoad);
			}
		}
		
		nodesFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=\"" 
				+ OpenERPChildTreeNode.class.getName() + "\"");
	}
	
	public void setConfiguration(OpenERPConfiguration config){
		txtHost.setText(config.getHostName());
		txtPort.setText(Integer.toString(config.getPortNumber()));
		
		cmbDatabase.removeAllItems();
		populateDatabase();
		cmbDatabase.setSelectedItem(config.getDatabaseName());
		txtUsername.setText(config.getUserName());
		pwdPassword.setText(config.getPassword());
		
		cmbModelName.removeAllItems();
		populateModelCombo();
		cmbDataSource.setSelectedItem(config.getDatasource());
		cmbModelName.setSelectedItem(config.getModelName());
		txtCustomFunction.setText(config.getCustomFunctionName());
		
		dataSourceChanged();
		tableModel.setFieldPaths(config.getSelectedFields());
		
	}
	
	@SuppressWarnings("deprecation")
	public OpenERPConfiguration getConfiguration(){
		OpenERPConfiguration config = new OpenERPConfiguration();
		config.setHostName(txtHost.getText());
		int port = 0;
		try{
			port = Integer.parseInt(txtPort.getText());
		}
		catch(Exception e){}
		config.setPortNumber(port);
		config.setDatabaseName(cmbDatabase.getSelectedItem().toString());
		config.setUserName(txtUsername.getText());
		config.setPassword(pwdPassword.getText());
		config.setDatasource((cmbDataSource.getSelectedIndex() == 0 ? DataSource.STANDARD : DataSource.CUSTOM));
		config.setModelName(cmbModelName.getSelectedItem().toString());
		config.setCustomFunctionName(txtCustomFunction.getText());
		
		config.setSelectedFields(tableModel.getFieldPaths());
		
		return config;
		
	}
	
	private void dataSourceChanged(){
		if (cmbDataSource.getSelectedIndex() == DataSource.STANDARD.ordinal()){
			txtCustomFunction.setEnabled(false);
		}
		else{
			txtCustomFunction.setEnabled(true);
		}
	}
	
	private void populateDatabase(){
		if (cmbDatabase.getItemCount() > 0
				|| txtHost.getText().length() == 0
				|| txtPort.getText().length() == 0)
			return;
		
		try{
			int portNumber = Integer.parseInt(txtPort.getText());
			ArrayList<String> dbList = OpenERPXmlRpcProxy.getDatabaseList(txtHost.getText(),portNumber);
			dbList.add(0, "");
			cmbDatabase.setModel(new DefaultComboBoxModel(dbList.toArray(new String[0])));
		}
		catch (Exception e){
			
		}
	}
	
	private Session getSession() throws Exception{
		int portNumber = Integer.parseInt(txtPort.getText());
		@SuppressWarnings("deprecation")
		Session s = new Session(txtHost.getText(), portNumber, cmbDatabase.getSelectedItem().toString(), txtUsername.getText(), pwdPassword.getText());
		s.startSession();
		return s;
	}
	
	private ObjectAdapter getObjectAdapter(String modelName) throws Exception{
		ObjectAdapter modelAdapter = new ObjectAdapter(getSession(), modelName);
		return modelAdapter;
	}
	
	@SuppressWarnings("deprecation")
	private void populateModelCombo(){
		if (cmbModelName.getItemCount() > 0
				|| txtHost.getText().length() == 0
				|| txtPort.getText().length() == 0
				|| cmbDatabase.getSelectedItem() == null
				|| txtUsername.getText().length() == 0
				|| pwdPassword.getText().length() == 0)
			return;
		
		try{
			ObjectAdapter modelAdapter = getObjectAdapter("ir.model");
			RowCollection models = modelAdapter.searchAndReadObject(null, new String[] {"model"});
			String[] modelList = new String[models.size() + 1];
			modelList[0] = "";
			for (int i = 0; i < models.size(); i++){
				modelList[i + 1] = models.get(i).get("model").toString();
			}
			cmbModelName.setModel(new DefaultComboBoxModel(modelList));
		}
		catch (Exception e){
			
		}
	}
	
	public class TreeViewTransferHandler extends TransferHandler{

		private static final long serialVersionUID = -5578590246826642097L;
		
		@Override
		public int getSourceActions(JComponent c) {
			return COPY;
		}

		@Override
		public Transferable createTransferable(JComponent c) {
			JTree tree = (JTree)c;  
	        TreePath[] paths = tree.getSelectionPaths();
	        ArrayList<OpenERPFieldInfo> fieldPaths = new ArrayList<OpenERPFieldInfo>();
	        for (TreePath path : paths){
	            // Make up a node array of copies for transfer and  
	            // another for/of the nodes that will be removed in  
	            // exportDone after a successful drop.
	        	if (path.getLastPathComponent() instanceof OpenERPChildTreeNode){
		        	OpenERPChildTreeNode node = (OpenERPChildTreeNode) path.getLastPathComponent();
		        	fieldPaths.add(node.getFieldPath());
	        	}
	        }  
            return new DataTransfer(fieldPaths);
		}

		@Override
		public void exportDone(JComponent c, Transferable t, int action) {
		}
		
		@Override
		public boolean canImport(TransferSupport support){
			return false;
		}
		
		@Override
		public boolean importData(TransferSupport support){
			return true;
		}
	}
	
	public class TableTransferHandler extends TransferHandler{

		private static final long serialVersionUID = -5578590246826642097L;
		
		@Override
		public int getSourceActions(JComponent c) {
		    return MOVE;
		}

		@Override
		public Transferable createTransferable(JComponent c) {
			JTable table = (JTable)c;  
			
			ArrayList<OpenERPFieldInfo> fieldPaths = new ArrayList<OpenERPFieldInfo>();
	        for (int i : table.getSelectedRows()){
	        	fieldPaths.add(tableModel.getFieldPaths().get(i));
	        }
	        
	        return new DataTransfer(fieldPaths);
		}

		@Override
		public void exportDone(JComponent c, Transferable t, int action) {
		}
		
		@Override
		public boolean canImport(TransferSupport support){
			if (!support.isDrop())
				return false;
			
			return true;
		}
		
		@Override
		public boolean importData(TransferSupport support){
			int dropRow = ((JTable.DropLocation) support.getDropLocation()).getRow();
			
			try {
				ArrayList<?> data = (ArrayList<?>) support.getTransferable().getTransferData(nodesFlavor);
				for (Object field : data){
				
					OpenERPFieldInfo targetField = ((OpenERPFieldInfo) field).clone();
					
					if (support.getDropAction() == MOVE
							|| targetField.getParentField() == null){
						
						int originalIndex = tableModel.getFieldPaths().indexOf(targetField);
						if (originalIndex >= 0)
							tableModel.getFieldPaths().remove(originalIndex);
					}
					else{
						while(tableModel.getFieldPaths().indexOf(targetField) >= 0){
							targetField.incrementInstanceNum();
						}
						
						if (targetField.getInstanceNum() > 1){
							targetField.setRenamedFieldName(targetField.getFieldName() + "_" + targetField.getInstanceNum());
						}
					}
					
					tableModel.addField(dropRow, targetField);
				}
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}
	
	public class DataTransfer implements Transferable{

		private final ArrayList<OpenERPFieldInfo> fieldPaths;
		
		public DataTransfer(ArrayList<OpenERPFieldInfo> fieldPaths){
			this.fieldPaths = fieldPaths;
		}
		
		@Override
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			return fieldPaths;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[]{nodesFlavor};
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.equals(nodesFlavor);
		}
		
	}
}
