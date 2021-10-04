package com.rpsgroup;

import java.io.File;
import java.io.FilenameFilter;
import java.time.Instant;
import java.util.Date;

public class FileManager {
	
	private PropertiesHolder props;
	
	public Date getMostRecentDataTime() {
		
		File dataDir = new File(props.storageDirectory);	
		File[] dataFiles = dataDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.contains(props.ncFilename)) {
					return true;
				}
				return false;
			}
		});
		
		long max = 0L;
		for (int n=0; n<dataFiles.length; n++) {
			
			File f = dataFiles[n];
			long lastMod = f.lastModified();
			
			if (lastMod > max) {
				max = lastMod;
			}
		}
		return new Date(max);
	}
	
	public void setProps(PropertiesHolder props) {
		this.props = props;
	}
	
	public File getDodsOutfile() {
		
		return new File(
				new File(props.downloadDirectory),
				Long.toString(Instant.now().getEpochSecond()) +  "_" + props.dodsFilename);
	}
	
	public File getNC4Outfile() {
		String ncname = props.dodsFilename.replace(".dods", "");
		
		return new File(
				new File(props.storageDirectory),
				Long.toString(Instant.now().getEpochSecond()) +  "_" + ncname);
	}
	
}
