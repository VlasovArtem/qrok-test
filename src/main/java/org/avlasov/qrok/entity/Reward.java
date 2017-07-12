package org.avlasov.qrok.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by artemvlasov on 10/07/2017.
 */
@Entity
public class Reward implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REWARD_ID", unique = true, nullable = false)
    private int id;
    private int year;
    private String title;

    public Reward() {}

    public Reward(int year, String title) {
        this.year = year;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reward)) return false;

        Reward reward = (Reward) o;

        if (id != reward.id) return false;
        if (year != reward.year) return false;
        return title != null ? title.equals(reward.title) : reward.title == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + year;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
