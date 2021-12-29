package com.rpsgroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHolder {
	
	public Properties prop;
	private InputStream propIs;
	
	public String dodsURL;
	public String ncFilename;
	public String ncUrl;
	public String ncCopyCommand;
	public String storageDirectory;
	public String jnaLibDirectory;
	public int storageMaxCount;
	
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
		
		ncFilename = getProp("nc.filename");
		ncUrl = getProp("nc.url");
		
		ncCopyCommand = getProp("nc.copy.cmd");
		storageDirectory = getProp("directory.storage");
		
		jnaLibDirectory = getProp("directory.jna.lib");
		storageMaxCount = Integer.parseInt(getProp("directory.storage.maxcount"));
		
	}
	
	private String getProp(String propName) {
		return (String) prop.get(propName);
	}
	
}
