/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.moviedb.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author timosand
 */
@Entity
public class Actor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column
    private Long id;
    @Column
    private String name;
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
