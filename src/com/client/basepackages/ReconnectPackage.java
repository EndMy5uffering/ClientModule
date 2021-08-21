package com.client.basepackages;

import com.client.packageing.DataPackage;
import com.client.packageing.annotations.DataPackageConstructor;
import com.client.packageing.annotations.DataPackageDynamic;
import com.client.packageing.annotations.DataPackageID;
import com.client.packageing.annotations.DataPackageLength;

public class ReconnectPackage extends DataPackage{

	@DataPackageDynamic
	public static boolean IS_DYNAMIC_LENGTH = true;
	
	@DataPackageLength
	public static short PACK_LENGTH = (short)4;
	
	@DataPackageID
	public static byte[] ID = new byte[] {(byte)0x0, (byte)0x3};
	
	@DataPackageConstructor(DATA = true)
	public ReconnectPackage(byte[] byteDataRaw) {
		super(PACK_LENGTH, true, byteDataRaw);
		this.setId(ID);
	}
	
}
