package faceweb.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "images"
)
public class ImageEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long imgId;
    @Column(
            nullable = false
    )
    private int userId;
    @Column(
            nullable = false
    )
    private String filename;
    @Column(
            nullable = false
    )
    private String filepath;

    public Long getImgId() {
        return this.imgId;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public void setImgId(final Long imgId) {
        this.imgId = imgId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public void setFilepath(final String filepath) {
        this.filepath = filepath;
    }

    public ImageEntity() {
    }
}
