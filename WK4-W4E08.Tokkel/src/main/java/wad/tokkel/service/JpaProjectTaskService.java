/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tokkel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.tokkel.models.Project;
import wad.tokkel.models.Task;

/**
 *
 * @author timosand
 */
@Service
public class JpaProjectTaskService implements ProjectTaskService {
    
    @Autowired ProjectService projectService;
    @Autowired TaskService taskService;

    @Override
    @Transactional
    public Task addTaskToProject(Task task, Integer projectId) {
        Project toUpdate = projectService.read(projectId);
        toUpdate.addTask(task);
        return task;
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public Iterable<Task> listProjectTasks(Integer projectId) {
        Project toSearch = projectService.read(projectId);
        return taskService.listProjectTasks(toSearch);
    }
    
    @Override
    @Transactional
    public Task deleteTask(Integer projectId, Integer taskId) {
        Project toChange = projectService.read(projectId);
        Task toDelete = taskService.read(taskId);
        toChange.getTasks().remove(toDelete);
        return taskService.delete(taskId);
    }

    @Override
    @Transactional
    public Task update(Integer taskId, Task task) {
        return taskService.update(taskId, task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTask(Integer taskId) {
        return taskService.read(taskId);
    }

}
