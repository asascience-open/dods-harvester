package com.rpsgroup;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarvesterMain {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	private static final PropertiesHolder props = new PropertiesHolder();
	
	private DodsToNetcdf4Converter converter = new DodsToNetcdf4Converter();
	private HttpHandler http = new HttpHandler();
	
	private FileManager file = new FileManager();
	private Logger log = LoggerFactory.getLogger(HarvesterMain.class);
	
	public static void main(String[] args) throws MalformedURLException, Exception {
		
		HarvesterMain runner = new HarvesterMain();	
		
		try {
			runner.converter.setProps(props);
			runner.http.setProps(props);
			runner.file.setProps(props);
			
			runner.http.setFileManager(runner.file);
			runner.http.setDateFormat(sdf);
			
			Date remoteModTime = runner.http.getLastModified(new URL(props.dodsURL));
			Date mostRecentLocal = runner.file.getMostRecentDataTime();
			
			runner.log.info("Remote Last-Modified: " + sdf.format(remoteModTime));
			runner.log.info(" Local Last-Modified: " + sdf.format(mostRecentLocal));
			
			Boolean firstRun = mostRecentLocal.compareTo(new Date(0L)) == 0;
			
			if (remoteModTime.after(mostRecentLocal) || firstRun) {
				
				File dodsFile = runner.http.downloadDods();
				runner.log.info("Downloaded .dods file: " + dodsFile.toString());
				
				File netcdf = runner.file.getNC4Outfile();
				runner.converter.convert(dodsFile, netcdf);
				
				runner.log.info("Converted .dods to netCDF4: " + netcdf.toString());
				Files.delete(dodsFile.toPath());
				
				runner.log.info("Deleted .dods file");
				
			} else {
				runner.log.info("Nothing to do here, we have latest data. Goodbye.");
			}
		
		} catch (Exception ex) {
		
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			
			ex.printStackTrace(pw);
			runner.log.error(sw.toString());
		}
		
		// 4. Run compliance checks.
	}

}