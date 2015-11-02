package rsvclient;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI extends Remote{	
	//String sayHello() throws RemoteException; //test exception 
	public String BookingSummary() throws RemoteException;
    public String BookingList() throws RemoteException;
    public String reserveSeat(String ClassName, String Passenger, int SeatNumber) throws RemoteException;
}
