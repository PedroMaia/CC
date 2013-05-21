package Code;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.microedition.io.Connector;
import javax.obex.ClientSession;
import javax.obex.HeaderSet;
import javax.obex.Operation;
import javax.obex.ResponseCodes;

public class ClienteSend implements Runnable {
	
	SMessage sms;
	String url;
	BlackList lst;
	
	public ClienteSend(SMessage sms,String url,BlackList l){
		
		this.sms=sms;
		this.url=url;
		this.lst=l;
	}
	
	public void run(){
		
		System.out.println("Connecting to " + this.url);


        
        ClientSession clientSession = null;
		try 
		{
			clientSession = (ClientSession) Connector.open(this.url);
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.err.println("Erro ao criar sessão");
			e.printStackTrace();
		}
        
        
        HeaderSet hsConnectReply = null;
		try 
		{
			hsConnectReply = clientSession.connect(null);
		} catch (IOException e)
		
		{
			System.err.println("Erro ao ligar");
			e.printStackTrace();
		}
        
        //se tiver a resposta HTTP PK
        try {
			if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK)
			{
			    System.out.println("Failed to connect Not http!!!!");
			    return;
			}
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.err.println("Erro Criar");
			e.printStackTrace();
		}

        HeaderSet hsOperation = clientSession.createHeaderSet();
        
        hsOperation.setHeader(HeaderSet.NAME, "ementa.txt");
        hsOperation.setHeader(HeaderSet.TYPE, "text");

        //Create PUT Operation
        Operation putOperation = null;
		try 
		{
			putOperation = clientSession.put(hsOperation);
		} catch (IOException e)
		{
			System.err.println("Erro sessionPut");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
        // Send some text to server
        byte data[] = null;
		try 
		{
			data = sms.getText().getBytes("iso-8859-1");
			
		} catch (UnsupportedEncodingException e) 
		{
			System.err.println("Erro enviar Texto");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        OutputStream os = null;
		try {
			os = putOperation.openOutputStream();
		} catch (IOException e) 
		{
			System.err.println("OutPut");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        try 
        {
			os.write(data);
		} catch (IOException e) {
			System.err.println("Write");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try 
        {
			os.close();
		} catch (IOException e)
		{
			
			System.err.println("toClose");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("To Send");

        try 
        {
			putOperation.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try 
        {
			clientSession.disconnect(null);
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try {
			clientSession.close();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
