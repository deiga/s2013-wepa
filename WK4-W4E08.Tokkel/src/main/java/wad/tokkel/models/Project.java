package wad.tokkel.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
    @OneToMany(orphanRemoval = true, cascade={CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Task> tasks;

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
    
    public void addTask(Task task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
        }
        task.setProject(this);
    }

    public Project() {
        tasks = new HashSet<Task>();
    }

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
