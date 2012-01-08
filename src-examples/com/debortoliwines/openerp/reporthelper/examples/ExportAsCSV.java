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
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.UIManager;

import com.debortoliwines.openerp.reporting.di.OpenERPConfiguration;
import com.debortoliwines.openerp.reporting.di.OpenERPFieldInfo;
import com.debortoliwines.openerp.reporting.di.OpenERPHelper;
import com.debortoliwines.openerp.reporting.ui.OpenERPPanel;

/**
 * Builds on the SaveToXML class and also exports data as CSV to standard out when the dialog is closed
 * @author Pieter van der Merwe
 * @since  Jan 8, 2012
 */
public class ExportAsCSV {

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
      System.out.println();
      
      // If we are calling a custom function set some parameters so you can see it in your custom function
      HashMap<String, Object> customFunctionParameters = new HashMap<String, Object>();
      customFunctionParameters.put("Param1", "test");
      
      // Get the fields.  Note that a custom function must return a field list.  The parameter getFields will be passed to a custom
      // function.
      ArrayList<OpenERPFieldInfo> fields = helper.getFields(config, customFunctionParameters);
      
      // Now get the data
      Object[][] rows = helper.getData(config, customFunctionParameters);
      
      System.out.println("----Results----");
      // Print out the field header
      for (int i = 0; i < fields.size(); i++){
        System.out.print(fields.get(i).getRenamedFieldName());
        if (i == fields.size() - 1)
          System.out.println();
        else System.out.print(',');
      }
      
      // Print out results
      for (Object[] row : rows){
        for (int i = 0; i < fields.size(); i++){
          OpenERPFieldInfo field = fields.get(i);
          
          if (row[i] == null){
            System.out.print("NULL");
          }
          else{
            
            // Do some conversions
            switch (field.getFieldType()) {
            case DATE:
            case DATETIME:
            case FLOAT:
            case TEXT:
            case CHAR:
            case INTEGER:
            case SELECTION:
            case BINARY: // field is still in base64 encoded string, just write it out
              System.out.print(row[i].toString());
              break;
            case BOOLEAN:
              if ((Boolean) row[i] == true)
                System.out.print("yes");
              else System.out.print("no");
              break;
            case MANY2MANY: // returned as Object[] {value1, value2, ...}. Append them together with a / separator
            case ONE2MANY:
              Object[] values = (Object[]) row[i];
              String strValues = "";
              for (Object val : values){
                strValues = strValues + "/" + val.toString();
              }
              System.out.print(strValues.substring(1));
              break;
            case MANY2ONE: // returned as Object[] {id,name}.  Only print the id.
              System.out.print(((Object[]) row[i])[0].toString());
              break;
  
            default:
              break;
            }
          }
          
          if (i == fields.size() - 1)
            System.out.println();
          else System.out.print(',');
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  
}
