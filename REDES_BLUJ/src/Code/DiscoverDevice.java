package Code;

import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.*;

public class DiscoverDevice {

	public Vector <RemoteDevice> ldevice ;
	DiscoveryListener listener ;
	
	
public DiscoverDevice(){
		
		this.ldevice=new Vector<RemoteDevice>();
		
	}

	
/*Encontra os dispositivos na rede	*/
public void find()
{
		
		
		 final Object inquiryCompletedEvent = new Object();

		 //limpa a lista antiga!
	       this.ldevice.clear();
	   
	       this.listener = new DiscoveryListener()
	       {

	            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod)
	            {
	                System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
	                ldevice.addElement(btDevice);
	                
	                try {
	                    System.out.println("     name " + btDevice.getFriendlyName(false));
	                	} 
	                	catch (IOException cantGetDeviceName)
	                	{}
	                
	            }

	            public void inquiryCompleted(int discType)
	            {
	                System.out.println("Device Inquiry completed!");
	                synchronized(inquiryCompletedEvent)
	                {
	                    inquiryCompletedEvent.notifyAll();
	                }
	            }

	            public void serviceSearchCompleted(int transID, int respCode) {
	            }

	            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
	            }
	        };
	        

	        synchronized(inquiryCompletedEvent)
	        {
	            boolean started=false;
				try {
					started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
				} catch (BluetoothStateException e)
					{
					// TODO Auto-generated catch block
					System.out.println("ERRO");
					e.printStackTrace();
					}
				
				
	            if (started)
	            {
	                System.out.println("wait for device inquiry to complete...");
	                try {
						inquiryCompletedEvent.wait();
					} catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                System.out.println(this.ldevice.size() +  " device(s) found");
	            }
	        }
	}





/*Retorna a lista de dispositivos encontrados*/
public Vector<RemoteDevice> getLdevice() {
	return ldevice;
}


}
