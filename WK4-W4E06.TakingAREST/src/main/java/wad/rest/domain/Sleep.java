package wad.rest.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "SLEEP")
public class Sleep implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SLEEP_ID")
    private Long id;
    
    @DateTimeFormat(pattern = "d.M.y H.m")
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "SLEEP_START")
    @NotNull
    private Date start;
    
    @DateTimeFormat(pattern = "d.M.y H.m")
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "SLEEP_END")
    @NotNull
    private Date end;
    
    @Column(name = "SLEEP_FEELING")
    @NotBlank
    private String feeling;

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
