package faceweb.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StyleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long styleid;

    private String filePath;

    private int person_see_up_dos;

    private int person_hair_length;

    private int rate;

    private String description;

    @ManyToOne
    @JoinColumn(name="user")
    private UserEntity user;
}
