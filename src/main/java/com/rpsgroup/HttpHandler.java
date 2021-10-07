package com.rpsgroup;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class HttpHandler {
	
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private SimpleDateFormat sdf;
	
    public Date getLastModified(URL url) throws Exception {
    	
    	HttpRequestFactory factory =  HTTP_TRANSPORT.createRequestFactory();
        HttpRequest request = factory.buildHeadRequest(new GenericUrl(url));
        
        String lastMod = request.execute().getHeaders().getLastModified();
        
        return sdf.parse(lastMod);
        
    }

	public void setProps(PropertiesHolder props) {
	}

	public void setFileManager(FileManager fmgr) {	}

	public void setDateFormat(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
    	
}