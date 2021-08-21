package com.client.packageing;

import com.client.main.Client;

public class DefaultPackageManager extends PackageManager{

	public DefaultPackageManager(Client server) {
		super(server, DefaultPackageManager.class);
	}

}
