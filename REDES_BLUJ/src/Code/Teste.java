package Code;

public class Teste {
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args){ 
	
		SMessage sms=new SMessage("Teste");
		Coordinator cd = new Coordinator(sms);
		
		Thread th = new Thread(cd);
		th.start();
		System.out.println("começo");
		th.stop();
		
		sms=new SMessage("Teste");
		cd = new Coordinator(sms);
		th = new Thread(cd);
		cd.setText("Pum hacker!");
		th.start();
		
	}
}
