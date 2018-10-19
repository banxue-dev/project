package com.banxue.utils;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", ResultCode.SUCCESS_CODE);
		put("msg", "操作成功");
	}

	public static R error() {
		return error(ResultCode.ERROR_CODE, "操作失败");
	}

	public static R error(String msg) {
		return error(ResultCode.ERROR_CODE, msg);
	}

	public static R error(String code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	public static R okdata(Object data) {
		R r = new R();
		r.put("code", ResultCode.SUCCESS_CODE);
		r.put("msg", "操作成功");
		r.put("data", data);
		return r;
	}

	public static R ok(String code, String msg,Object data) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}
	public static R ok( String msg,Object data) {
		R r = new R();
		r.put("code", ResultCode.SUCCESS_CODE);
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
