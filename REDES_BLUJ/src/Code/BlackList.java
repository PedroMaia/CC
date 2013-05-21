package Code;

import java.util.*;

public class BlackList {
	
	private static final float horas=6;
	public List<String> listUrl ;
	public GregorianCalendar dLast;
	
	public BlackList(){
		listUrl=new ArrayList<String>();
	}
	
	
	
	public boolean add (String url){
		
		GregorianCalendar today=new GregorianCalendar();
		
		
		if(dLast.getTimeInMillis()<(today.getTimeInMillis())+(horas*60*60*1000))
		{
			this.listUrl.clear();
		}
		
		if(url!=null&&this.listUrl.add(url))
		{

			return true;
		}
		return false;
	}
	

}
