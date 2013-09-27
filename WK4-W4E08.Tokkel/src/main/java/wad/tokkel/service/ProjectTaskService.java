/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tokkel.service;

import wad.tokkel.models.Task;

/**
 *
 * @author timosand
 */
public interface ProjectTaskService {

    public Iterable<Task> listProjectTasks(Integer projectId);

    public Task addTaskToProject(Task object, Integer projectId);

    public Task deleteTask(Integer projectId, Integer taskId);

    public Task update(Integer taskId, Task task);

    public Task getTask(Integer taskId);
    
}
