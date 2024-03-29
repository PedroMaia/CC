package Code;



import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

public class ProcuraServicos {
	
    static final UUID OBEX_FILE_TRANSFER = new UUID(0x1106);
    static final UUID OBEX_OBJECT_PUSH = new UUID(0x1105);
    static final UUID OBEX=new UUID(0x0008);

    public static Vector <String> servico=new Vector<String>();
    public  DiscoverDevice ddiv;
    
    
    public ProcuraServicos()
    {
    	this.ddiv=new DiscoverDevice();
    }
    
    
   
    
    public void findServicos(){
	this.ddiv.find();
    	
    	

        UUID serviceUUID = OBEX_OBJECT_PUSH;
        
 
        final Object serviceSearchCompletedEvent = new Object();

        DiscoveryListener listener = new DiscoveryListener() 
        {
        	

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod){
            }

            public void inquiryCompleted(int discType) {
            }

            
            
            public void servicesDiscovered(int transID, ServiceRecord[] servRecord)
            {
            	
            	
                for (int i = 0; i < servRecord.length; i++) 
                {
                    String url = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
                    if (url == null) 
                    {
                        continue;
                    }
                    
                    //adiciona o servico
                    ProcuraServicos.servico.add(url);
                    
                    
                    DataElement serviceName = servRecord[i].getAttributeValue(0x0100);
                    if (serviceName != null)
                    {
                        System.out.println("service " + serviceName.getValue() + " found " + url);
                    } else
                    	{
                        System.out.println("service found " + url);
                    	}
                }
            }

    
            
            public void serviceSearchCompleted(int transID, int respCode)
            {
                System.out.println("service search completed!");
                synchronized(serviceSearchCompletedEvent)
                {
                    serviceSearchCompletedEvent.notifyAll();
                }
            }

        };


        UUID[] searchUuidSet = new UUID[] { serviceUUID,OBEX };
        int[] attrIDs =  new int[]
        {
                0x0100 // Service name
        };

        for(Enumeration<RemoteDevice> en = ddiv.getLdevice().elements(); en.hasMoreElements(); )
        {
            RemoteDevice btDevice = (RemoteDevice)en.nextElement();

            synchronized(serviceSearchCompletedEvent) 
            {
                try {
					System.out.println("search services on " + btDevice.getBluetoothAddress() + " " + btDevice.getFriendlyName(false));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					
					System.out.println("Erro1");
					e1.printStackTrace();
				}
                try {
					LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrIDs, searchUuidSet, btDevice, listener);
				} catch (BluetoothStateException e) {
					// TODO Auto-generated catch block
					System.out.println("Erro.2");
					e.printStackTrace();
				}
                
                try {
					serviceSearchCompletedEvent.wait();
				} catch (InterruptedException e) {
					System.out.println("Erro.2");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }

    }



    

    
    public Vector<String> getServico() {
		return servico;
	}
    
    
}
    
   


