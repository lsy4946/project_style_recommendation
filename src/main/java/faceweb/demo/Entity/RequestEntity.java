package faceweb.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RequestEntity {

    public final static class STATUS{
        public final static int NOT_RECEIVED = 0;
        public final static int RECEIVED = 1;
        public final static int APPROVED = 2;
        public final static int REJECTED = 3;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private String title;
    private String content;
    private int status;
    private String timeStamp;

    @ManyToOne
    @JoinColumn(name = "requester")
    private UserEntity requester;

    public String statusString(){
        return switch (this.status){
            case STATUS.NOT_RECEIVED -> "NOT RECEIVED";
            case STATUS.RECEIVED -> "RECEIVED";
            case STATUS.APPROVED -> "APPROVED";
            case STATUS.REJECTED -> "REJECTED";
            default -> "NOT RECEIVED";
        };
    }
}
