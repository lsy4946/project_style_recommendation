package faceweb.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockDTO {
    private String whyBlocked; // 차단 사유
    private String dataType; // 차단기간 데이터형(월, 일, 시간)
    private int blockLength; // 차단기간
}
