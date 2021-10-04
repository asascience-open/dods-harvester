package com.rpsgroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHolder {
	
	public Properties prop;
	private InputStream propIs;
	
	public String dodsURL;
	public String dodsFilename;
	public String ncFilename;
	public String downloadDirectory;
	public String storageDirectory;
	public String jnaLibDirectory;
	
	public PropertiesHolder() {
		
		prop = new Properties();
		propIs = this.getClass()
				.getClassLoader()
				.getResourceAsStream("application.properties");
		
		try {
			prop.load(propIs);
			propIs.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dodsURL = getProp("dods.url");
		dodsFilename = getProp("dods.filename");
		ncFilename = getProp("nc.filename");
		
		downloadDirectory = getProp("directory.download");
		storageDirectory = getProp("directory.storage");
		jnaLibDirectory = getProp("directory.jna.lib");
		
	}
	
	private String getProp(String propName) {
		return (String) prop.get(propName);
	}
	
}
