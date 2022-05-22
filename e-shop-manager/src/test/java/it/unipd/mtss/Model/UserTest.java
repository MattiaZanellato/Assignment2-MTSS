////////////////////////////////////////////////////////////////////
// [Mattia] [Zanellato] [1222398]
// [Federico] [Marchi] [1224445]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.Model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    User user;

    @Before
    public void setup() {
        user = new User(1, LocalDate.parse("2000-05-05"), "Mario", "Rossi");
    }
    
    @Test
    public void testGetIdUser() {
        int id = user.getId();
        assertEquals(1, id);
    }

    @Test
    public void testGetDateUser() {
        LocalDate date = user.getBirthDate();
        assertEquals(LocalDate.parse("2000-05-05"), date);
    }

    @Test
    public void testGetNameUser() {
        String name = user.getName();
        assertEquals("Mario", name);
    }

    @Test
    public void testGetSurnameUser() {
        String surname = user.getSurname();
        assertEquals("Rossi", surname);
    }
}
