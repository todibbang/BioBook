

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * The test class Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ObjectTesting
{
    /**
     * Default constructor for test class Test
     */
    public ObjectTesting()
    {
        
    }

    @Test
    public void MovieObjectTest()
    {
        Movie movie1 = new Movie(9, "TestFilm", 120, "image.jpg", "Jeg lorte film");
        assertEquals("Jeg lorte film", movie1.getDescription());
        assertNotNull(movie1.getTitle());
        assertEquals("TestFilm", movie1.getTitle());
        assertEquals(120, movie1.getPlaytime());
        assertEquals(9, movie1.getMovieId());
    }

    @Test
    public void SeatObjectTest()
    {
        Seat seat1 = new Seat(1, "A", 1, 2, 3);
        assertEquals(1, seat1.getCol());
        assertNotNull(seat1.getRow());
        assertEquals("A", seat1.getRow());
        assertEquals(1, seat1.getSeatId());
        assertEquals(false, seat1.getSeatInSequence());
        assertEquals(false, seat1.getSeatTaken());
        seat1.setSeatTaken(true);
    }

    @Test
    public void ObjectReservationInformationTest()
    {
        Movie movie1 = new Movie(1, "Dum film", 123, "image.jpg", "Lort");
        Reservation reservat1 = new Reservation(movie1, "12/12/12", "14:00", 1, 4, 5);
        assertEquals("12/12/12", reservat1.getDate());
        assertNotNull(reservat1.getMovie());
        assertNotNull(reservat1.getTime());
        assertEquals(5, reservat1.getShowingId());
        reservat1.addSeatId(2);
    }
}






