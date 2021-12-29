package com.rpsgroup;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ucar.nc2.Attribute;
import ucar.nc2.write.NetcdfFileFormat;
import ucar.nc2.write.NetcdfFormatWriter;

class NetcdfMetadataManager {

	private static Map<String, String[]> acks = new HashMap<String, String[]>();
	
	public NetcdfMetadataManager() {
		
		acks.put("institution", new String[] {
				"University of Connecticut",
				"University of Massachusetts Dartmouth"
		});
		
		acks.put("Creator", new String[] {
				"UMass Dartmouth School for Marine Science and Technology",
				"UConn Department of Marine Sciences"
		});
		
		acks.put("Publisher", new String[] {"University of Connecticut"});
		acks.put("Contributor", new String[] {"University of Massachusetts Dartmouth"});
		
	}	 
	
	public static void main(String[] args) throws IOException {
		
		String f = args[0];
		new NetcdfMetadataManager().addAcks(new File(f));
	}
	
	void addAcks(File nc) throws IOException {

		NetcdfFormatWriter.Builder builder = NetcdfFormatWriter.openExisting(nc.getCanonicalPath())
				.setFormat(NetcdfFileFormat.NETCDF4);
		
		Iterator<String> iter = acks.keySet().iterator();
		
		while (iter.hasNext()) {
			String key = iter.next();

			String newval = String.join(", ", acks.get(key));
			builder.addAttribute(new Attribute(key, newval));

		}
		
		NetcdfFormatWriter w = builder.build();
		
		w.flush();
		w.close();
			
	}

}