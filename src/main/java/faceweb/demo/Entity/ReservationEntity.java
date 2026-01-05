package faceweb.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reservation")
public class ReservationEntity {
    public final static class STATUS{
        public final static int NOT_RECEIVED = 0;
        public final static int RECEIVED = 1;
        public final static int APPROVED = 2;
        public final static int REJECTED = 3;
        public final static int FINISHED = 4;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationID;

    @ManyToOne
    @Column(name = "user")
    private UserEntity user;

    @ManyToOne
    @Column(name = "professional")
    private UserEntity professional;

    @ManyToOne
    @Column(name = "target_style")
    private StyleEntity target_style;

    private int status;
    private String whyRejected;

    private String reservedTime;
    private String timeStamp;

    public String statusString(){
        return switch (this.status){
            case RequestEntity.STATUS.NOT_RECEIVED -> "NOT RECEIVED";
            case RequestEntity.STATUS.RECEIVED -> "RECEIVED";
            case RequestEntity.STATUS.APPROVED -> "APPROVED";
            case RequestEntity.STATUS.REJECTED -> "REJECTED";
            default -> "NOT RECEIVED";
        };
    }
}
