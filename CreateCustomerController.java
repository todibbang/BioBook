

public class CreateCustomerController
{
    CreateCustomerGUI ccg;
    
    public CreateCustomerController(CreateCustomerGUI ccg)
    {
        this.ccg = ccg;
    }
    
    public static void addInputForReservation (String number, String name)
    {
        Reservation.addReservation(number, name);
    }
    
    public void createActionListeners()
    {
        ccg.getConfirmButton().addActionListener(e -> {
                CreateCustomerController.addInputForReservation(ccg.getNumberField().getText(), ccg.getNameField().getText());
        });
    }
}