package com.rpsgroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class HttpHandler {
	
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private PropertiesHolder props;
	private FileManager fmgr; 
	private SimpleDateFormat sdf;
	
    public Date getLastModified(URL url) throws Exception {
    	
    	HttpRequestFactory factory =  HTTP_TRANSPORT.createRequestFactory();
        HttpRequest request = factory.buildHeadRequest(new GenericUrl(url));
        
        String lastMod = request.execute().getHeaders().getLastModified();
        
        return sdf.parse(lastMod);
        
    }
    
    public File downloadDods() throws IOException {
    	
    	URL getUrl = new URL(props.dodsURL);
    	InputStream webIs = getUrl.openStream();
    	
    	ReadableByteChannel readableByteChannel = Channels.newChannel(webIs);
    	
    	File outFile = fmgr.getDodsOutfile();
    	
    	try (FileOutputStream fileOutputStream = new FileOutputStream(outFile);
    		FileChannel fileChannel = fileOutputStream.getChannel()) {
        	
    		fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    	}
    	
    	return outFile;
    }

	public void setProps(PropertiesHolder props) {
		this.props = props;
	}

	public void setFileManager(FileManager fmgr) {
		this.fmgr = fmgr;
	}

	public void setDateFormat(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
    	
}