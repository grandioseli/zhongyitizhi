package com.example.zhongyitizhi1.tuling;

public class IsNullOrEmpty {
	public static boolean isEmpty(String str) {
        return str == null || "".equals(str) || "null".equals(str);
	}

    public static boolean isEmptyZero(String str) {
        return str == null || "".equals(str) || "null".equals(str) || "0".equals(str);
    }

    public static boolean isEmptyNoLimited(String str) {
        return str == null || "".equals(str) || "null".equals(str) || "不限".equals(str);
    }
}
