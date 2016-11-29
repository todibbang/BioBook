
/**
 * Write a description of class Demo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Demo
{
    public Demo()
    {
        MySQL dbh = MySQL.getInstance();
        
        
        String s = "INSERT INTO seats (row, col, roomId) VALUES ";
        
        for(int roomId = 0; roomId < 3; roomId++) {
            for(int row = 0; row < 10; row++) {
                for(int col = 1; col < 11; col++) {
                    s += "('" + String.copyValueOf(Character.toChars(65 + row)) + "'," + col + "," + roomId + "), ";
                }

            }

        }
        s = s.substring(0, s.length() - 2);
        System.out.println(s);
        //dbh.executeCommand(s);
        
        s = "INSERT INTO showingReservations (showingId, reservationId, seatId) VALUES ";
        
        for(int i = 1; i < 7; i++) {
            s += "(" + i + ","+ i + "," + i + "), ";
        }
        s = s.substring(0, s.length() - 2);
        System.out.println(s);
        dbh.executeCommand(s);
    }
}
