package faceweb.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDTO {
    private String reportTitle;
    private String reportReason;
    private String reportContent;
    private long object_id;
}
