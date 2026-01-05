package faceweb.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "professionalprofile")
public class ProfessionalProfileEntity {
    @Id
    private Long id;

    private String address;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String intro;
    private String photo_src;

    @ManyToOne
    @JoinColumn(name = "owner")
    UserEntity owner;
}
