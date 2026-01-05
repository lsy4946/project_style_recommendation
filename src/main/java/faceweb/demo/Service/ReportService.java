package faceweb.demo.Service;

import faceweb.demo.DTO.ReportDTO;
import faceweb.demo.Entity.ReportEntity;
import faceweb.demo.Entity.UserEntity;
import faceweb.demo.Repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final DateTimeFormatter formatter;
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public boolean saveReportProcess(ReportDTO reportDTO, UserEntity user, String object_type, long object_id){
        ReportEntity reportEntity = new ReportEntity();

        if(reportDTO.getReportReason().equals("other"))
            reportEntity.setContent(reportDTO.getReportContent());
        else
            reportEntity.setContent(reportDTO.getReportReason());
        reportEntity.setReporter(user);
        reportEntity.setTitle(reportDTO.getReportTitle());
        reportEntity.setObject_id(object_id);
        reportEntity.setObject_type(object_type);
        reportEntity.setStatus("pending");
        reportEntity.setTimeStamp(LocalDateTime.now().format(formatter));

        reportRepository.save(reportEntity);

        return true;
    }
    public void saveReportProcess(ReportEntity report){
        reportRepository.save(report);
    }

    public List<ReportEntity> getRecent15Report(){
        return reportRepository.getTop15ByOrdOrderByReport_idDesc();
    }

    public ReportEntity findByReportID(long reportID){
        return reportRepository.getReferenceById(reportID);
    }
}
