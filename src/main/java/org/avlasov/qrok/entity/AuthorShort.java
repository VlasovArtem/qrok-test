package org.avlasov.qrok.entity;

import java.util.List;

/**
 * Created by artemvlasov on 12/07/2017.
 */
public class AuthorShort {

    private String firstName;
    private String lastName;
    private int age;
    private List<String> books;

    public AuthorShort(String firstName, String lastName, int age, List<String> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.books = books;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public List<String> getBooks() {
        return books;
    }
}
