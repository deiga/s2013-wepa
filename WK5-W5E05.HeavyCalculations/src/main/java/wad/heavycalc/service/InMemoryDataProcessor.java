package wad.heavycalc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import wad.heavycalc.domain.CalculationTask;

@Service
public class InMemoryDataProcessor implements DataProcessingService {

    private Map<String, CalculationTask> tasks;

    public InMemoryDataProcessor() {
        this.tasks = new HashMap<String, CalculationTask>();
    }
    @Override
    public void sendForProcessing(CalculationTask task) {
        this.tasks.put(task.getId(), task);
        task.setStatus("Sent for processing");

        // NOTE! DO NOT CHANGE THIS AWESOME ALGORITHM!
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(InMemoryDataProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        task.setStatus("Processing");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(InMemoryDataProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        String reverse = new StringBuilder(task.getData()).reverse().toString();
        
        task.setResult(reverse);
        task.setStatus("Finished");
    }

    @Override
    public CalculationTask getCalculationTask(String id) {
        if (!tasks.containsKey(id)) {
            throw new IllegalArgumentException("No such order: " + id);
        }

        return tasks.get(id);
    }
    
    @Override
    public List<CalculationTask> getCalculationTasks() {
        return new ArrayList<CalculationTask>(tasks.values());
    }
}
