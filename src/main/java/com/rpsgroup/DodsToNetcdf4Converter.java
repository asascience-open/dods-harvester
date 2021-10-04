package com.rpsgroup;

import java.io.File;
import java.io.IOException;

import ucar.nc2.NetcdfFile;
import ucar.nc2.dataset.NetcdfDatasets;
import ucar.nc2.write.NetcdfCopier;
import ucar.nc2.write.NetcdfFileFormat;
import ucar.nc2.write.NetcdfFormatWriter;


public class DodsToNetcdf4Converter {
	
	private PropertiesHolder props = new PropertiesHolder();
	
	public void convert(File dodsInFile, File nc4OutFile) throws IOException {
    
		NetcdfFile ncfile = NetcdfDatasets.openFile("file:" + dodsInFile.toString(), null);
        NetcdfFormatWriter.Builder builder = NetcdfFormatWriter.createNewNetcdf4(
					NetcdfFileFormat.NETCDF4,
					nc4OutFile.toString(),
					null);
			
		System.setProperty("jna.library.path", props.jnaLibDirectory);
		NetcdfCopier copier = NetcdfCopier.create(ncfile, builder);
			
		NetcdfFile out = copier.write(null);
			
		ncfile.close();
		out.close();
    }
	
	public void setProps(PropertiesHolder props) {
		this.props = props;
	}
}
