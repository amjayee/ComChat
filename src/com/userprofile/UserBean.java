package com.userprofile;

public class UserBean {
	int N_USR_ID;
	String V_NAME;
	Long N_PHONE_NUMBER;
	String V_MAIL;
	String V_IMAGE_URL;
	int N_TOTMSG_SEND;
	int N_TOTMSG_RECEIVE;
	int N_CUR_STAT_ID;
	String V_STATUS;
	public String getV_STATUS() {
		return V_STATUS;
	}
	public void setV_STATUS(String v_STATUS) {
		V_STATUS = v_STATUS;
	}
	String D_CREATED;
	//Current Device of the user
	String V_DEVICE_NAME;
	public int getN_USR_ID() {
		return N_USR_ID;
	}
	public void setN_USR_ID(int n_USR_ID) {
		N_USR_ID = n_USR_ID;
	}
	public String getV_NAME() {
		return V_NAME;
	}
	public void setV_NAME(String v_NAME) {
		V_NAME = v_NAME;
	}
	public Long getN_PHONE_NUMBER() {
		return N_PHONE_NUMBER;
	}
	public void setN_PHONE_NUMBER(Long n_PHONE_NUMBER) {
		N_PHONE_NUMBER = n_PHONE_NUMBER;
	}
	public String getV_MAIL() {
		return V_MAIL;
	}
	public void setV_MAIL(String v_MAIL) {
		V_MAIL = v_MAIL;
	}
	public String getV_IMAGE_URL() {
		return V_IMAGE_URL;
	}
	public void setV_IMAGE_URL(String _V_IMAGE_URL) {
		V_IMAGE_URL = _V_IMAGE_URL;
	}
	public int getN_TOTMSG_SEND() {
		return N_TOTMSG_SEND;
	}
	public void setN_TOTMSG_SEND(int n_TOTMSG_SEND) {
		N_TOTMSG_SEND = n_TOTMSG_SEND;
	}
	public int getN_TOTMSG_RECEIVE() {
		return N_TOTMSG_RECEIVE;
	}
	public void setN_TOTMSG_RECEIVE(int n_TOTMSG_RECEIVE) {
		N_TOTMSG_RECEIVE = n_TOTMSG_RECEIVE;
	}
	public int getN_CUR_STAT_ID() {
		return N_CUR_STAT_ID;
	}
	public void setN_CUR_STAT_ID(int n_CUR_STAT_ID) {
		N_CUR_STAT_ID = n_CUR_STAT_ID;
	}
	public String getD_CREATED() {
		return D_CREATED;
	}
	public void setD_CREATED(String d_CREATED) {
		D_CREATED = d_CREATED;
	}
	public String getV_LAST_AVAILABLE() {
		return V_LAST_AVAILABLE;
	}
	public void setV_LAST_AVAILABLE(String v_LAST_AVAILABLE) {
		V_LAST_AVAILABLE = v_LAST_AVAILABLE;
	}
	String V_LAST_AVAILABLE;
}
