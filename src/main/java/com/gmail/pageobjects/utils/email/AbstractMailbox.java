package com.gmail.pageobjects.utils.email;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMailbox implements Mailbox {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractMailbox.class);
	static final String INBOX_FOLDER_NAME = "INBOX";
	static final String TIMEOUT_MILLIS = "90000"; // 1.5 minutes

	Store store = null;
	Folder openedFolder = null;
	protected String userName;
	protected String password;
	protected String domain;
	protected String protocol;
	protected String incomingServer;

	AbstractMailbox(String userName, String domain, String password) {
		this.userName = userName;
		this.domain = domain;
		this.password = password;
		Runtime.getRuntime().addShutdownHook(getCleanUpThread(this));
	}

	private <T extends AbstractMailbox> Thread getCleanUpThread(final T instance) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				LOG.info("Session closing for '{}'", instance.toString());
				instance.close();
			}
		});
	}

	protected void close() {
		try {
			if (openedFolder != null) {
				openedFolder.close(true);
				openedFolder = null;
			}
			if (store != null) {
				store.close();
				store = null;
			}
		} catch (MessagingException e) {
			LOG.info("Could not close mailbox", e);
		}
	}

	void initConnectionAndOpenFolder(String folderName, int accessMode) throws MessagingException {
		if (store == null || !store.isConnected()) {
			close();
			Properties props = new Properties();
			props.setProperty("mail.store.protocol", protocol);
			props.setProperty("mail." + protocol + ".connectiontimeout", TIMEOUT_MILLIS);
			props.setProperty("mail." + protocol + ".timeout", TIMEOUT_MILLIS);
			props.setProperty("mail." + protocol + ".wtiretimeout", TIMEOUT_MILLIS);
			Session session = Session.getInstance(props);
			store = session.getStore();
			store.connect(incomingServer, userName, password);
		}
		if (openedFolder == null || !openedFolder.isOpen()
				|| !(openedFolder.getName().equalsIgnoreCase(folderName) && openedFolder.getMode() == accessMode)) {
			openedFolder = store.getFolder(folderName);
			openedFolder.open(accessMode);
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getIncomingServer() {
		return incomingServer;
	}

	public void setIncomingServer(String incomingServer) {
		this.incomingServer = incomingServer;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "{" + "userName='" + userName + '\'' + ", domain='" + domain + '\''
				+ ", protocol='" + protocol + '\'' + ", incomingServer='" + incomingServer + '\''
				+ ", store connected='" + (store != null && store.isConnected()) + '\'' + ", folder opened='"
				+ (openedFolder != null && openedFolder.isOpen()) + '\'' + '}';
	}

}
