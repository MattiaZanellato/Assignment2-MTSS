////////////////////////////////////////////////////////////////////
// [Mattia] [Zanellato] [1222398]
// [Federico] [Marchi] [1224445]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.Model;

import java.time.LocalDate;

public class User {
    private int id;
    private LocalDate birthDate;
    private String name;
    private String surname;

    public User(int id, LocalDate birthDate, String name, String surname) {
        this.id = id;
        this.birthDate = birthDate;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

}
