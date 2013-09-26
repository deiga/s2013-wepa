package wad.tokkel.models;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "Project")
public class Project extends AbstractModel {
  private static final long serialVersionUID = 1L;

  @Column(name = "name", length = 256, nullable = false)
  private String name;

  @Column(name = "creationTime", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date creationTime;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }
}
