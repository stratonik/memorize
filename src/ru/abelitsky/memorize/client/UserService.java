package ru.abelitsky.memorize.client;

import ru.abelitsky.memorize.shared.dto.UserInfo;

public class UserService {

	private static UserInfo userInfo;

	public static UserInfo getCurrentUserInfo() {
		return userInfo;
	}

	static void setCurrentUserInfo(UserInfo userInfo) {
		UserService.userInfo = userInfo;
	}

}
