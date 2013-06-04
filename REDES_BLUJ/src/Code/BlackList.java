package Code;

import java.util.*;

public class BlackList {
	
	private static final float horas=6;
	public Map<String,String> mapUrl;
	public GregorianCalendar dLast;
	
	public BlackList(){
		mapUrl=new HashMap<String,String>();
	}
	
	public BlackList(HashMap<String,String> l){
		for(String s:l.values()){
			mapUrl.put(s,s);
		}
	}
	
	public Map<String,String> getUrl(){
        Map<String,String> res = new HashMap<String,String>();
        for(String s : mapUrl.values()) {
            res.put(s, s);
        }
        return res;
    }

	
	public boolean add (String url){
		
		GregorianCalendar today=new GregorianCalendar();
		
		
		if(dLast.getTimeInMillis()<(today.getTimeInMillis())+(horas*60*60*1000))
		{
			this.mapUrl.clear();
		}
		
		if(url!=null&&this.mapUrl.put(url,url)!=null)
		{

			return true;
		}
		return false;
	}
	
	public boolean existe(String url){
		return mapUrl.containsKey(url);
	}
	
	public String toString() {
        StringBuilder s = new StringBuilder("<h1>ListaUrl</h1>");
        for (String si :mapUrl.values()) {
			s.append("<p>");
            s.append(si.toString() + "</p>");
        }
        return s.toString();
    }

}
