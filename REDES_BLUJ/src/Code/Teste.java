package Code;

public class Teste {
	
	
	public static void main(String[] args){ 
	ProcuraServicos ps=new ProcuraServicos();
	ClienteSend[] cs;
	SMessage sm=new SMessage("Costa.");
	
	int max=0;
	ps.findServicos();
	max=ProcuraServicos.servico.size();
	System.out.println("Encontrados:"+max);
	
	
	Thread[] th=new Thread [max];
	cs=new ClienteSend [max];
	for(int i=0;i<max;i++)
	{
		cs[i]=new ClienteSend(sm, ProcuraServicos.servico.get(i),new BlackList());
		th[i]=new Thread(cs[i]);
		th[i].start();
	}

	
	for(int i=0;i<max;i++){
		try {
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
