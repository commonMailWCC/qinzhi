package com.qinzhi.utils;

import com.google.gson.Gson;


public class JsonUtils {

	private static Gson gson = new Gson();
	
	public static <T> T parseJson(String in, Class<T> c) {
		return gson.fromJson(in, c);
	}
	
	public static String toJson(Object o) {
		return gson.toJson(o);
	}
}
