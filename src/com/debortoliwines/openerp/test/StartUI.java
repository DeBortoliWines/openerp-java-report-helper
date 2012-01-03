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
