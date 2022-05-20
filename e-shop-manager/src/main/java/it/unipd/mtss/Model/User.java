////////////////////////////////////////////////////////////////////
// [Mattia] [Zanellato] [1222398]
// [Federico] [Marchi] [1224445]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.Model;

import java.util.Date;

public class User {
    private int id;
    private Date birthDate;
    private String name;
    private String surname;

    public User(int id, Date birthDate, String name, String surname) {
        this.id = id;
        this.birthDate = birthDate;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

}
