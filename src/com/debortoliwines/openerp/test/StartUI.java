package com.debortoliwines.openerp.test;

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
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JDialog;

import com.debortoliwines.openerp.reporting.di.OpenERPConfiguration;
import com.debortoliwines.openerp.reporting.di.OpenERPHelper;
import com.debortoliwines.openerp.reporting.ui.OpenERPPanel;

public class StartUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			OpenERPHelper helper = new OpenERPHelper();
			String configFilePath = "/tmp/myfile.xml";
			
			OpenERPConfiguration config = new OpenERPConfiguration();
			File configFile = new File(configFilePath);
			
			if (configFile.exists()){
				XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(configFile)));
				config = (OpenERPConfiguration) decoder.readObject();
				decoder.close();
			}
			
			JDialog dialog = new JDialog();
			dialog.getContentPane().setLayout(new BorderLayout());
			OpenERPPanel panel = new OpenERPPanel();
			panel.setConfiguration(config);
			panel.setVisible(true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.getContentPane().add(panel,BorderLayout.CENTER);
			dialog.setBounds(100, 100, 1024, 768);
			dialog.setModal(true);
			dialog.setVisible(true);
			
			
			config = panel.getConfiguration();
			
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(configFile)));
			helper.setupXMLEncoder(encoder);
			encoder.writeObject(config);
	        encoder.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
