import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.obex.*;

public class ObexPutClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        String serverURL = null; // = "btgoep://0019639C4007:6";
        if ((args != null) && (args.length > 0)) {
            serverURL = args[0];
        }
        if (serverURL == null) {
            String[] searchArgs = null;
            // Connect to OBEXPutServer from examples
            // searchArgs = new String[] { "11111111111111111111111111111123" };
            ServicesSearch.main(searchArgs);
            if (ServicesSearch.serviceFound.size() == 0) {
                System.out.println("OBEX service not found");
                return;
            }
            
            
            // Select the first service found
            //� aqui que vai verificar o servi�o
            serverURL = (String)ServicesSearch.serviceFound.elementAt(0);
            
        }

        
        System.out.println("Connecting to " + serverURL);

        //Cria um sec��o e aqui � que inicia a nova thread!
        
        ClientSession clientSession = (ClientSession) Connector.open(serverURL);
        
        
        HeaderSet hsConnectReply = clientSession.connect(null);
        
        //se tiver a resposta HTTP PK
        if (hsConnectReply.getResponseCode() != ResponseCodes.OBEX_HTTP_OK)
        {
            System.out.println("Failed to connect");
            return;
        }

        HeaderSet hsOperation = clientSession.createHeaderSet();
        
        hsOperation.setHeader(HeaderSet.NAME, "Hello.txt");
        hsOperation.setHeader(HeaderSet.TYPE, "text");

        //Create PUT Operation
        Operation putOperation = clientSession.put(hsOperation);

        // Send some text to server
        byte data[] ="Hello".getBytes("UTF-8");
        
        OutputStream os = putOperation.openOutputStream();
        os.write(data);
        os.close();
        System.out.println("To Send");

        putOperation.close();

        clientSession.disconnect(null);

        clientSession.close();
    }
}