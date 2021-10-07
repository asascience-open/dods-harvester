package com.rpsgroup;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarvesterMain {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	private static final PropertiesHolder props = new PropertiesHolder();
	
	private HttpHandler http = new HttpHandler();
	
	private FileManager file = new FileManager();
	private Logger log = LoggerFactory.getLogger(HarvesterMain.class);
	
	public static void main(String[] args) throws MalformedURLException, Exception {
		run(props.ncUrl);
	}
	
	private String getNcCopyCommand(String url) {
		return props.ncCopyCommand + " " + url + " " + file.getNC4Outfile().toString();
	}
	
	private static void run(String url) throws MalformedURLException, Exception {
		
		HarvesterMain runner = new HarvesterMain();	
		
		try {
			runner.file.setProps(props);
			runner.http.setProps(props);
			
			runner.http.setFileManager(runner.file);
			runner.http.setDateFormat(sdf);
			
			// Why dods url? It accepts HEAD requests and returns last-mod time, others don't.
			Date remoteModTime = runner.http.getLastModified(new URL(props.dodsURL));
			Date mostRecentLocal = runner.file.getMostRecentDataTime();
			
			runner.log.info("Remote Last-Modified: " + sdf.format(remoteModTime));
			runner.log.info(" Local Last-Modified: " + sdf.format(mostRecentLocal));
			
			Boolean firstRun = mostRecentLocal.compareTo(new Date(0L)) == 0;
			
			if (remoteModTime.after(mostRecentLocal) || firstRun) {
				runner.log.info("Downloading latest data...");
				
				String command = runner.getNcCopyCommand(url);
				Process copyProc = Runtime.getRuntime().exec(command);
				
				int exitCode = copyProc.waitFor();
				
				if (exitCode != 0) {
					runner.log.error("Fatal Error execing '" + command + "'. Exit code: " + Integer.toString(exitCode));
				} else {
					runner.log.info("Successfully exec'd '" + command + "'.");
				}
				
				while (runner.file.getFileCount() > props.storageMaxCount) {	
					
					Path oldest = runner.file.getOldestFile().toPath();
					runner.log.info("Exceeded storage max count, deleting oldest: " + oldest);
					
					Files.delete(oldest);
				}
				
			} else {
				runner.log.info("Nothing to do here, we have latest data.");
			}
		
		} catch (Exception ex) {
		
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			
			ex.printStackTrace(pw);
			runner.log.error(sw.toString());
		
		} finally {
			
			runner.log.info("Done.");
		}
		
		// 4. Run compliance checks.
	}

}
