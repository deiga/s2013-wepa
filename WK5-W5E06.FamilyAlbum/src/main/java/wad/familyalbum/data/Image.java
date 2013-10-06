package wad.familyalbum.data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Image")
public class Image implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;
    @Lob
    @Column(name = "description", length = 32 * 1024, nullable = false)
    private String description;
    @Column(name = "fileName", length = 256, nullable = false)
    private String fileName;
    @Column(name = "contentType", length = 256, nullable = false)
    private String contentType;
    @Lob
    @Column(name = "data", length = 4 * 1024 * 1024, nullable = false)
    private byte[] data;

    public Image() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String message) {
        this.description = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
