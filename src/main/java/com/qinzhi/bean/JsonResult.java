package com.qinzhi.bean;

public class JsonResult {
    public static final String STATUS_SUCCESS = "1";
    public static final String STATUS_FAILED = "0";
    private String status;
    private String message;
    
    public JsonResult(boolean status, String message){
        this.status = status ? STATUS_SUCCESS : STATUS_FAILED;
        this.message = message;
    }

    public JsonResult(String status, String message){
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

	public void setStatus(String status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
