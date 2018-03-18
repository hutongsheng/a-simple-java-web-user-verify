package com.hutongsheng.serviceImpl;

import com.hutongsheng.serviceI.UserServiceI;

public class UserServiceImpl implements UserServiceI{

	@Override
	public boolean checkUserIsValid(String username, String password) {
		if("hutongsheng".equalsIgnoreCase(username)&&
				"password".equals(password))
			return true ;
		return false;
	}

}
