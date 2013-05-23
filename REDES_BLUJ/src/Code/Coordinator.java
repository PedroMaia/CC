package Code;

public class Coordinator implements Runnable{
	
	private final static Boolean ON =true;
	private final static Boolean OFF =true;
	
	private Boolean estado;
	private SMessage mensagem;
	private ProcuraServicos ps;
	private BlackList blackl;
	
	
	public Coordinator(SMessage sms){
		this.mensagem=sms;
		this.ps=new ProcuraServicos();
		this.blackl=new BlackList();
		this.estado=ON;
		
	}
	
	
	public Boolean getEstado(){
		return this.estado;
	}
	
	public void setText(String txt){
		
		this.mensagem.setText(txt);
	}
	
	public void setEstado(Boolean f){
		this.estado=f;
	}
	
	
	public void run(){
		
		ClienteSend[] cs;
		
		while(true)
		{
			System.out.println("Texto actual e:"+this.mensagem.getText());
			int max=0;
			ps.findServicos();
			max=ProcuraServicos.servico.size();
			System.out.println("Encontrados:"+max);
			
			
			Thread[] th=new Thread [max];
			cs=new ClienteSend [max];
			for(int i=0;i<max;i++)
			{
				cs[i]=new ClienteSend(this.mensagem, ProcuraServicos.servico.get(i),new BlackList());
				th[i]=new Thread(cs[i]);
				th[i].start();
			}

			
			for(int i=0;i<max;i++){
				try 
				{
					th[i].join();
				} catch (InterruptedException e) 
				{
					System.err.println("erro join");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Chegou UM");
			}
			
			
			
			
		}
		
	}
	

}
