package faceweb.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long report_id;

    private String title;

    private String content;

    private String object_type;

    private long object_id;

    private String status;

    private String timeStamp;

    @ManyToOne
    @JoinColumn(name = "reporter")
    private UserEntity reporter;
}
