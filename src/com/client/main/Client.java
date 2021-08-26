package com.client.main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.client.ClientConnection;
import com.client.ClientDisconnectCallback;
import com.client.ClientPackageReceiveCallback;
import com.client.ClientTimeOutCallback;
import com.client.packageing.DataPackage;
import com.client.packageing.DefaultPackageManager;
import com.client.packageing.PackageManager;
import com.client.packageing.PackageRegistrationManager;
import com.client.packageing.UnknownPackageCallback;
import com.logger.Logger;
import com.logger.PrintMode;
import com.logger.PrintingType;

public class Client {

	public static Logger logger = new Logger(PrintingType.Console, PrintMode.EVENT);
	private static int maxPackageSize = 2048;	
	private int clientTimeOut = -1;

	private PackageManager defaultPackageManager;
	private final PackageRegistrationManager packageRegistrationManager;
	
	private ClientConnection connection;
	
	private InetAddress adderss;
	private int port;
	
	private List<ClientPackageReceiveCallback> callback = new ArrayList<ClientPackageReceiveCallback>();
	private UnknownPackageCallback unknownPackageCallback = null;
	private ClientTimeOutCallback clientTimeOutCallback = null;
	private ClientDisconnectCallback clientDisconnectCallback = null;
	
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
		if(this.connection != null)
			return false;
		Socket sock;
		try {
			sock = new Socket(this.adderss, port);
			if(this.clientTimeOut > 0) sock.setSoTimeout(this.clientTimeOut);
		} catch (IOException e) {
			return false;
		}
		this.connection = new ClientConnection(sock, this.defaultPackageManager);
		this.connection.setClientPackageReceiveCallback(callback);
		this.connection.setUnknownPackageCallback(unknownPackageCallback);
		this.connection.setClientTimeOutCallback(clientTimeOutCallback);
		this.connection.setClientDisconnectCallback(clientDisconnectCallback);
		this.connection.setPackageManager(defaultPackageManager);
		this.connection.enable();
		return true;
	}
	
	public void disable() {
		if(this.connection != null) {
			this.connection.disable();
		}
	}
	
	/**
	 * Will send a data package to the client that is connected.<br>
	 * 
	 * @param data
	 * */
	public void send(DataPackage data) {
		if(this.connection != null) {
			this.connection.send(data);
		}
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
		return connection;
	}

	public void setConneciton(ClientConnection conneciton) {
		this.connection = conneciton;
	}
	
	/**
	 * Sets a callback for unknown packages.<br>
	 * When a unknown package was read by the connection the given function will be called.<br>
	 * After the function execution the client connection will be closed to prevent errors caused by unknown data in the input stream.
	 * 
	 * @param unknownPackageCallback A handler function that is called for unknown packages.
	 * */
	public void setUnknownPackageCallback(UnknownPackageCallback unknownPackageCallback) {
		this.unknownPackageCallback = unknownPackageCallback;
	}

	/**
	 * Sets a callback function that is invoked when the client is disabled and stopped.<b>
	 * This can happen when an error occurs while receiving a package or due to a time out.<b>
	 * In the best case the client should disconnect without any errors.
	 * 
	 * @param clientDisconnectCallback The callback that is invoked on a client disconnect.
	 * */
	public void setClientDisconnectCallback(ClientDisconnectCallback clientDisconnectCallback) {
		this.clientDisconnectCallback = clientDisconnectCallback;
	}
	
	/**
	 * Sets the timeout callback function that will be called when a connection did not receive any packages for a set time (timeout).<br>
	 * The function will be called before the connection is closed an disposed of.<br>
	 * Right after execution of the callback the connection will be closed and disposed of.
	 * @param timeout The timeout function that will be executed when a connection timed out
	 * */
	public void setClientTimeOutCallback(ClientTimeOutCallback timeout) {
		this.clientTimeOutCallback = timeout;
	}
	
	/**
	 * Sets the package manager for the client connection.<br>
	 * 
	 * @param packageManager The package manager that will be used by the connection. <b>Can not be null!</b>
	 * 
	 * @throws NullPointerException When packageManager == null
	 * */
	public void setPackageManager(PackageManager packageManager) {
		if(packageManager == null)
			throw new NullPointerException("Package Manager can not be null!");
		this.connection.setPackageManager(packageManager);
	}
	
	/**
	 * Adds a callback function for the client to call when a package has been received.
	 * 
	 * @param callback A callback function that takes the <b>package</b> as <b>DataPackage</b> and the <b>connection</b>.
	 * */
	public void addClientPackageReceiveCallback(ClientPackageReceiveCallback callback) {
		this.callback.add(callback);
	}
	
	/**
	 * Sets the list of callback functions for the connection to invoke when a package has been received.
	 * 
	 * @param callback A list of callback functions that takes the <b>package</b> as <b>DataPackage</b> and the <b>connection</b>.
	 * */
	public void setClientPackageReceiveCallback(List<ClientPackageReceiveCallback> callback) {
		this.callback = callback;
	}
	
}
