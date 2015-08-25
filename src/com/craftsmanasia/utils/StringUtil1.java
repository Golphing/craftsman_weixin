package com.craftsmanasia.utils;

class StringUtil1{
	public static boolean isNull(Object obj) {
		boolean flag = false;
		if (null == obj || "".equals(obj))
			flag = true;
		return flag;

	}
}