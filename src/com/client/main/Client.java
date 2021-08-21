package com.client.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.client.ClientConnection;
import com.client.packageing.DefaultPackageManager;
import com.client.packageing.PackageManager;
import com.client.packageing.PackageRegistrationManager;
import com.logger.Logger;

public class Client {

	public static Logger logger;
	private static int maxPackageSize = 2048;	
	private int clientTimeOut = -1;


	private PackageManager defaultPackageManager;
	private final PackageRegistrationManager packageRegistrationManager;
	
	private ClientConnection conneciton;
	
	private InetAddress adderss;
	private int port;
	
	public Client(InetAddress address, int port) {
		this(address, port, null);
	}
	
	public Client(InetAddress address, int port, PackageManager defaultPackageManager) {
		this.packageRegistrationManager = new PackageRegistrationManager();
		this.defaultPackageManager = defaultPackageManager != null ? defaultPackageManager : new DefaultPackageManager(this);
		this.adderss = address;
		this.port = port;
	}
	
	public boolean connect() {
		if(this.conneciton != null)
			return false;
		Socket sock;
		try {
			sock = new Socket(this.adderss, port);
			if(this.clientTimeOut > 0) sock.setSoTimeout(this.clientTimeOut);
		} catch (IOException e) {
			return false;
		}
		this.conneciton = new ClientConnection(sock, this.defaultPackageManager);
		this.conneciton.enable();
		return true;
	}
	
	public static Logger getLogger() {
		return logger;
	}

	public static int getMaxPackageSize() {
		return maxPackageSize;
	}

	public static void setMaxPackageSize(int maxPackageSize) {
		Client.maxPackageSize = maxPackageSize;
	}

	public int getClientTimeOut() {
		return clientTimeOut;
	}

	public void setClientTimeOut(int clientTimeOut) {
		this.clientTimeOut = clientTimeOut;
	}

	public PackageManager getDefaultPackageManager() {
		return defaultPackageManager;
	}

	public void setDefaultPackageManager(PackageManager defaultPackageManager) {
		this.defaultPackageManager = defaultPackageManager;
	}

	public PackageRegistrationManager getPackageRegistrationManager() {
		return packageRegistrationManager;
	}

	public ClientConnection getConneciton() {
		return conneciton;
	}

	public void setConneciton(ClientConnection conneciton) {
		this.conneciton = conneciton;
	}
	
}
