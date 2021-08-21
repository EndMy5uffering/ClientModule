package com.client.basepackages;

import com.client.packageing.DataPackage;
import com.client.packageing.annotations.DataPackageConstructor;
import com.client.packageing.annotations.DataPackageDynamic;
import com.client.packageing.annotations.DataPackageID;
import com.client.packageing.annotations.DataPackageLength;

public class CloseConnection extends DataPackage{

	@DataPackageDynamic
	public static boolean IS_DYNAMIC_LENGTH = false;
	
	@DataPackageLength
	public static short PACK_LENGTH = (short)0;

	@DataPackageID
	public static byte[] ID = new byte[] {(byte)0x0, (byte)0x2};
	
	@DataPackageConstructor
	public CloseConnection() {
		super(PACK_LENGTH, IS_DYNAMIC_LENGTH, new byte[0]);
	    this.setId(ID);
	}
	
}
