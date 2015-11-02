package rsvclient;

import java.io.IOException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class rsvclient {
	private rsvclient() {};

	public static void main(String[] args) throws IOException{		
		Registry reg;
		try {
			reg = LocateRegistry.getRegistry("localhost", 1095);
			RMI r = (RMI) reg.lookup("Rsvserver");
            System.out.println(r.BookingSummary());
		} catch (RemoteException e) {        
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
