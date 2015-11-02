package rsvserver;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class rsvserver implements RMI  
{   
	int businessRates[][];
	int economyRates[][];
	int sumBusinessSeats = 5;
	int sumEconomySeats = 25;
	List<String[]> FlightSeats;  
	Boolean isBooked = false;
	
	public rsvserver() 
	{    
		String[] AssignedSeats;  
		FlightSeats = new ArrayList<String[]>();
		            
		for (int i=0; i< sumBusinessSeats + sumEconomySeats; i++) 
		{
			AssignedSeats  = new String[2];
			if (i < sumBusinessSeats) { AssignedSeats[0]="business";}
			else { AssignedSeats[0]="economy";}
			AssignedSeats[1]="";
			FlightSeats.add(AssignedSeats);
		}       
		//assign business seats and rates
		businessRates = new int[2][2];
		businessRates[0][0]=3;
		businessRates[0][1]=500;
		businessRates[1][0]=2;
		businessRates[1][1]=800;
		
		//assign economy seats and rates
		economyRates = new int[3][2];
		economyRates[0][0]=10;
		economyRates[0][1]=200;
		economyRates[1][0]=10;
		economyRates[1][1]=300;
		economyRates[2][0]=5;
		economyRates[2][1]=450;                  
	}   

	public String BusinessBooking() 
	{
		String businessClassBooked = "business class:\n";

		for (int i=0;i<businessRates.length;i++)
		{
			businessClassBooked = businessClassBooked + businessRates[i][0] + " seats at $" + businessRates[i][1] + " each\n";
		}
		businessClassBooked = businessClassBooked + "Seat numbers: ";

		for (int i=0; i < sumBusinessSeats; i++)
		{
			if (FlightSeats.get(i)[1].equals(""))
			{
				if (isBooked) { businessClassBooked = businessClassBooked + ",";}
				else { isBooked = true;}
				businessClassBooked = businessClassBooked + (i+1);
			}
		}
		businessClassBooked = businessClassBooked + "\n";       
		return businessClassBooked;
	}   

	public String EconomyBooking() 
	{
		String economyClassBooked = "economy class:\n";

		for (int i=0; i <economyRates.length; i++)
		{
			economyClassBooked = economyClassBooked + economyRates[i][0] + " seats at $" + economyRates[i][1] + " each\n";
		}
		
		economyClassBooked = economyClassBooked + "Seat numbers: ";

		for (int i=sumBusinessSeats; i < sumBusinessSeats + sumEconomySeats; i++)
		{
			if (FlightSeats.get(i)[1].equals(""))
			{
				if (isBooked) { economyClassBooked = economyClassBooked + ",";}
				else { isBooked = true; }
				economyClassBooked = economyClassBooked + (i+1);
			}
		}
		economyClassBooked = economyClassBooked + "\n";       
		return economyClassBooked;
	}

	public String BookingSummary() 
	{
		return BusinessBooking() + EconomyBooking();
	}

	public String BookingList() 
	{
		String businessClassBooked = "";

		for (int i=0;i<sumBusinessSeats+sumEconomySeats;i++)
		{
			if (!FlightSeats.get(i)[1].equals(""))
			{
				businessClassBooked = businessClassBooked + FlightSeats.get(i)[1] + " " + FlightSeats.get(i)[0] + " " + (i+1) + "\n";
			}
		}
		businessClassBooked = businessClassBooked + "\n";       
		return businessClassBooked;
	}

	public String reserveSeat(String ClassName, String Passenger, int SeatNumber) 
	{
		String businessClassBooked = "";

		if (SeatNumber<1 || SeatNumber>sumBusinessSeats+sumEconomySeats)
			businessClassBooked = "Failed to reserve: invalid seat number\n";
		else if (!FlightSeats.get(SeatNumber-1)[0].equals(ClassName))
			businessClassBooked = "Failed to reserve: invalid seat number\n";
		else if (!FlightSeats.get(SeatNumber-1)[1].equals(""))
			businessClassBooked = "Failed to reserve: seat not available\n";
		else {
			FlightSeats.get(SeatNumber - 1)[1]=Passenger;
			businessClassBooked = "Successfully reserved seat #" + SeatNumber + " for passenger " + Passenger;
		}

		return businessClassBooked;
	} 
	public static void main(String[] args) 
	{       
		try 
		{
			Registry reg = LocateRegistry.createRegistry(1095);
			rsvserver obj = new rsvserver();

			RMI r = (RMI) UnicastRemoteObject.exportObject(obj, 1095);
			reg.rebind("rsvserver", r);
		} catch (Exception ex) { 
			//diagnostic tool that tells what and where exception happens
			ex.printStackTrace();
		}

		rsvserver Booking = new rsvserver();
		//test cases
		System.out.println(Booking.reserveSeat("business", "Alice", 2));
		System.out.println(Booking.reserveSeat("business", "Bruce", 5));               
		System.out.println(Booking.reserveSeat("economy", "Mellisa", 10));
		System.out.println(Booking.reserveSeat("economy", "John", 12));
		System.out.println(Booking.reserveSeat("economy", "Eric", 13));
		System.out.println(Booking.BookingList());         
		System.out.println(Booking.BookingSummary());
	}
}