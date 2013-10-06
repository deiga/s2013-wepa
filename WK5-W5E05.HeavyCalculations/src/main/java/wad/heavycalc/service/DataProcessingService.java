package wad.heavycalc.service;

import java.util.List;
import wad.heavycalc.domain.CalculationTask;

public interface DataProcessingService {

    void sendForProcessing(CalculationTask task);
    CalculationTask getCalculationTask(String id);
    List<CalculationTask> getCalculationTasks();
}
