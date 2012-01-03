package com.debortoliwines.openerp.test;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import com.debortoliwines.openerp.reporting.ui.OpenERPPanel;

public class StartUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JDialog dialog = new JDialog();
			dialog.getContentPane().setLayout(new BorderLayout());
			OpenERPPanel panel = new OpenERPPanel();
			panel.setVisible(true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.getContentPane().add(panel,BorderLayout.CENTER);
			dialog.setBounds(100, 100, 671, 523);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
