

public class CreateCustomerController
{
    public CreateCustomerController()
    {
        
    }
    
    public static void addInputForReservation (String number, String name)
    {
        Reservation.addReservation(number, name);
        
    }
}