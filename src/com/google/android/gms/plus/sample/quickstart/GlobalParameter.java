package com.google.android.gms.plus.sample.quickstart;



public class GlobalParameter {
	static boolean existFlag = false;
	public final static String profilePicURL = "https://plus.google.com/s2/photos/profile/me";
	static String mail_subject = "Thank you for registering with BandWidthManager";
	public static final String picPath = "/ComChat/Pics/";
	public static String getMail_subject() {
		return mail_subject;
	}
	public static void setMail_subject(String _mail_subject) {
		mail_subject = _mail_subject;
	}
	public static String getMail_body() {
		return mail_body;
	}
	public static void setMail_body(String _mail_body) {
		mail_body = _mail_body;
	}
	static String mail_salute = "Hi <b>[USERID]</b>,";
	public static String getMail_salute() {
		return mail_salute;
	}
	public static void setMail_salute(String mail_salute) {
		GlobalParameter.mail_salute = mail_salute;
	}
	static String mail_body= "<br/> <br/> Thanks for registering with BandWidthManager. Please fill the below feedback form. <br/> <br/> <a href=\"https://docs.google.com/forms/d/1qX5m4rKU3IZ7UZLohinAdJ6RKPkyPfQnL40e_3IVbBY/viewform\">BandWidthManager Feedback Form</a> <br/><br/> Cheers,<br/> BandWidthManager Team";

}
