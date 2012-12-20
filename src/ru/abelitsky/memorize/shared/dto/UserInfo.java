package ru.abelitsky.memorize.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserInfo implements IsSerializable {

	private String userName;
	private boolean admin;
	private String logoutUrl;

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public String getUserName() {
		return userName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
