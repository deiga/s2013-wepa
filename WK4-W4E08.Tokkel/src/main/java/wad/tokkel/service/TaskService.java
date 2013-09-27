/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tokkel.service;

import wad.tokkel.models.Project;
import wad.tokkel.models.Task;

/**
 *
 * @author timosand
 */
public interface TaskService extends TokkelService<Task> {
    
    Iterable<Task> listProjectTasks(Project project);

    public Task update(Integer taskId, Task task);
    
}
