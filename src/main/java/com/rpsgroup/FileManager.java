package com.rpsgroup;

import java.io.File;
import java.io.FilenameFilter;
import java.time.Instant;
import java.util.Date;

public class FileManager {
	
	private PropertiesHolder props;
	
	private FilenameFilter filter = new FilenameFilter() {
		
		@Override
		public boolean accept(File dir, String name) {
			if (name.contains(props.ncFilename)) {
				return true;
			}
			return false;
		}
	};
	
	public Date getMostRecentDataTime() {
		
		File dataDir = new File(props.storageDirectory);	
		File[] dataFiles = dataDir.listFiles(filter);
		
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
	
	public File getOldestFile() {
		
		File dataDir = new File(props.storageDirectory);	
		File[] dataFiles = dataDir.listFiles(filter);
		
		long min = Long.MAX_VALUE;
		File oldest = null;
		
		for (int n=0; n<dataFiles.length; n++) {
			
			File f = dataFiles[n];
			long lastMod = f.lastModified();
			
			if (lastMod < min) {
				min = lastMod;
				oldest = f;
			}
		}
		return oldest;
	}
	
	public int getFileCount() {
		File dataDir = new File(props.storageDirectory);	
		return dataDir.listFiles(filter).length;
	}
	
	public void setProps(PropertiesHolder props) {
		this.props = props;
	}
	
	public File getNC4Outfile() {
		return new File(
				new File(props.storageDirectory),
				Long.toString(Instant.now().getEpochSecond()) +  "_" + props.ncFilename);
	}
	
}
