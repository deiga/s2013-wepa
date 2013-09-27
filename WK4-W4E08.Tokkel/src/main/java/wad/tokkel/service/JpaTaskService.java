/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tokkel.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.tokkel.models.Project;
import wad.tokkel.models.Task;
import wad.tokkel.repository.TaskRepository;

/**
 *
 * @author timosand
 */
@Service
public class JpaTaskService implements TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectService projectService;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Task> list() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Task read(Integer id) {
        return taskRepository.findOne(id);
    }

    @Override
    @Transactional
    public Task create(Task task) {
        if (task.getProjectId() != null) {
            projectService.read(task.getProjectId()).addTask(task);
        }
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task delete(Integer id) {
        Task deleted = read(id);
        taskRepository.delete(deleted);
        return deleted;
    }

    @Override
    @Transactional(readOnly = false)
    public Iterable<Task> listProjectTasks(Project project) {
        return taskRepository.findByProject(project);
    }

    @Override
    @Transactional
    public Task update(Integer taskId, Task task) {
        Task toUpdate = read(taskId);
        if (task.isStart()) {
            toUpdate.setStartedTime(new Date());
        } 
        if (task.isStop()) {
            toUpdate.setStoppedTime(new Date());
        }
        return toUpdate;
    }
    
}
