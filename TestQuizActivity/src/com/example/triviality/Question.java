package com.example.triviality;
public class Question {
	private int ID;
	private String QUESTION;
	private String OPTA;
	private String OPTB;
	private String OPTC;
	private String OPTD; // added...
	private String ANSWER;
	private String HINT; // added...
	public Question()
	{
		ID=0;
		QUESTION="";
		OPTA="";
		OPTB="";
		OPTC="";
		OPTD="";
		ANSWER="";
		HINT="";
	}
	public Question(String qUESTION, String oPTA, String oPTB, String oPTC, String oPTD,
			String aNSWER, String hINT) {
		
		QUESTION = qUESTION;
		OPTA = oPTA;
		OPTB = oPTB;
		OPTC = oPTC;
		OPTD = oPTD;
		ANSWER = aNSWER;
		HINT = hINT;
	}
	public int getID()
	{
		return ID;
	}
	public String getQUESTION() {
		return QUESTION;
	}
	public String getOPTA() {
		return OPTA;
	}
	public String getOPTB() {
		return OPTB;
	}
	public String getOPTC() {
		return OPTC;
	}
	public String getOPTD() { // added...
		return OPTD;
	}
	public String getANSWER() {
		return ANSWER;
	}
	public String getHINT() {
		return HINT;
	}
	
	public void setID(int id)
	{
		ID=id;
	}
	public void setQUESTION(String qUESTION) {
		QUESTION = qUESTION;
	}
	public void setOPTA(String oPTA) {
		OPTA = oPTA;
	}
	public void setOPTB(String oPTB) {
		OPTB = oPTB;
	}
	public void setOPTC(String oPTC) {
		OPTC = oPTC;
	}
	public void setOPTD(String oPTD) { //  added...
		OPTD = oPTD;
	}
	public void setANSWER(String aNSWER) {
		ANSWER = aNSWER;
	}
	public void setHINT(String hINT) {
		HINT = hINT;
	}
}
