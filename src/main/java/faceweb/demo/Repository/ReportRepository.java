package faceweb.demo.Repository;

import faceweb.demo.Entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    @Query(value = "select * from report order by report_id desc limit 15", nativeQuery = true)
    List<ReportEntity> getTop15ByOrdOrderByReport_idDesc();
}
