package Code;

import java.util.GregorianCalendar;


public class SMessage {
	
	
	public String text;
	public GregorianCalendar date;
	
	
	public SMessage(String txt){
		this.text=txt;
		this.date=new GregorianCalendar();
	}
	
	
	public void setText (String texto){	
		this.text=texto;
		this.date=new GregorianCalendar();
	}


	public String getText() {
		return text;
	}


	public GregorianCalendar getDate() {
		return date;
	}
	

}
