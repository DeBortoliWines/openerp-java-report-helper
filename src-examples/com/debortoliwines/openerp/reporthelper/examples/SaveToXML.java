package com.debortoliwines.openerp.reporthelper.examples;

/*
 * Copyright 2012 De Bortoli Wines Pty Limited (Australia). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import javax.swing.UIManager;

import com.debortoliwines.openerp.reporting.di.OpenERPConfiguration;
import com.debortoliwines.openerp.reporting.di.OpenERPHelper;
import com.debortoliwines.openerp.reporting.ui.OpenERPPanel;

/**
 * Simple example that saves the configuration to file after the dialog is closed and loads the configuration file
 * if saved previously when the application starts.
 * @author Pieter van der Merwe
 * @since  Jan 8, 2012
 */
public class SaveToXML {

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    try {
      
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      
      OpenERPHelper helper = new OpenERPHelper();
      String configFilePath = System.getProperty("java.io.tmpdir") + File.separator + "myfile.xml";
      
      // Read previously saved configuration
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
      
      
      // Now that the dialog is closed, save the configuration
      config = panel.getConfiguration();
      
      XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(configFile)));
      helper.setupXMLEncoder(encoder);
      encoder.writeObject(config);
      encoder.close();
      
      System.out.println("Configuration saved to " + configFilePath);
      
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
