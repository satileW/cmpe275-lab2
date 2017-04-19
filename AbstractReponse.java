package edu.sjsu.cmpe275.lab2;

public abstract class AbstractReponse {
	public int code;
	public String msg;
	public void setCode(int code){};
	public int getCode(){
		return code;}
	public void setMsg(String str){};
	public String getMsg(){return msg;}
}
