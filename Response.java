package edu.sjsu.cmpe275.lab2;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/* this is uniform success status code */

//@XmlRootElement
public class Response extends AbstractReponse {
	//@XmlElement
	public int getCode() {
		return code;
	}
	public void setCode(int code){
		this.code = code;
	}
	//@XmlElement
	public String getMsg() {
		return msg;
	}
	public void setMsg(String str){
		this.msg = str;
	}
	public Response(int statusCode, String str) {
		// TODO Auto-generated constructor stub
		this.code = statusCode;
		this.msg = str;
	}
}
