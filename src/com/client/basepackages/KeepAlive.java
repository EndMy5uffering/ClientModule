package com.client.basepackages;

import com.client.packageing.DataPackage;
import com.client.packageing.annotations.DataPackageConstructor;
import com.client.packageing.annotations.DataPackageDynamic;
import com.client.packageing.annotations.DataPackageID;
import com.client.packageing.annotations.DataPackageLength;

public class KeepAlive extends DataPackage{

	@DataPackageDynamic
	public static boolean IS_DYNAMIC_LENGTH = false;
	
	@DataPackageLength
	public static short PACK_LENGTH = (short)0;
	
	@DataPackageID
	public static byte[] ID = new byte[] {0x0,0x1};
	
	@DataPackageConstructor
	public KeepAlive() {
		super(PACK_LENGTH, false, new byte[] {});
		this.setId(ID);
	}
}
