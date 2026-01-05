package faceweb.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "block")
public class BlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long blockID;

    public String whyBlocked;

    public String duration;

    @ManyToOne
    @JoinColumn(name="user_id")
    public UserEntity blockedUser;
}
