package Code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SystemBlue {
	
	private Thread thr;
	private SMessage sms;
	private Coordinator cord;
	
	/**
	 * Contructor da Classe do sitema
	 */
	public SystemBlue()
	{
		
		sms=new SMessage("NM");
		cord=new Coordinator(this.sms);
		thr=new Thread(cord);
	}
	
/**
 * Iniciar o envio de mensagens para todos os dispositivos
 * @return
 */
	public String startServer(){
		
		cord=new Coordinator(this.sms);
		thr=new Thread(cord);
		thr.start();
		return "<p> Send ON </p>";
		
	}
	
	/**
	 * Parar o envio de mensagens por todos os dispositivos
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String stopServer(){
		
		thr.stop();
		
		return "<p>Send is OFF</p>";
	}
	
	
	public String setSms(String sms)
	{	
		this.stopServer();
		this.sms.setText(sms);
		this.startServer();
		return "<p>Mensagem alterada com sucesso</p>";
	}
	

	public static void main(String[] args){
		SystemBlue sblue=new SystemBlue();
		int port = 20222;
	    ServerSocket listenSock = null; //the listening server socket
	    Socket sock = null;             //the socket that will actually be used for communication
	  
	    try {
	  
	              listenSock = new ServerSocket(port);
	 
	              while (true)
	              {       //we want the server to run till the end of times
	 
	            	  sock = listenSock.accept();             //will block until connection recieved
	 
	            	  BufferedReader br =    new BufferedReader(new InputStreamReader(sock.getInputStream()));
	                 
	            	  BufferedWriter bw =    new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
	
	            	  String line = "";
	
	            	  while ((line = br.readLine()) != null)
	            	  {
	            		  if(line.equals("start")){
	            			  bw.write(sblue.startServer()+"\n");
	            		  }else
	            			  if(line.equals("stop"))
	            			  {
	            				  bw.write(sblue.stopServer()+"\n");
	            			  }else{//para mudar a mensagem
	            				  		String [] elem=line.split(" ");
	            				  		
	            				  		if((elem[0].equals("-txt"))&&elem.length>2)
	            				  		{
	            				  			bw.write(sblue.setSms(elem[1])+"\n");
	            				  		}
	            				  		else{
	            				  			bw.write("BAD CHAR\n");
	            				  			}
	            				  
	            			  		}
	            				  
	            				  
	            			  		
	            		  System.out.println("Server Recive:"+line);
	            		  bw.write("PHP said: " + line + "\n");
	            		  bw.flush();
	            	  }
	
	            	  //Closing streams and the current socket (not the listening socket!)
	            	  bw.close();
	            	  br.close();
	            	  sock.close();
	              }
	    } catch (IOException ex)
	    {
	    	System.err.print("Erro socket");
	    	ex.printStackTrace();
	    }
}
	
}

