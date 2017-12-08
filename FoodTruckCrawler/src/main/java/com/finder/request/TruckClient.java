package com.finder.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class TruckClient {
	
	URL url;
	String type;
	HttpURLConnection conn;
	
	public HttpURLConnection getConnection(StringBuilder urlString, String httpType, String params) throws IOException{	
		
		this.url = new URL(urlString.append(params==null?"":params).toString());
		this.type = httpType;
				
		return (HttpURLConnection) this.url.openConnection();
	}

}
