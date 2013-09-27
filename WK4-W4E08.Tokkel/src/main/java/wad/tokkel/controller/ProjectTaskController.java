/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tokkel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.tokkel.models.Task;
import wad.tokkel.service.ProjectTaskService;

/**
 *
 * @author timosand
 */
@Controller
@RequestMapping(value = "projects/{id}/tasks")
public class ProjectTaskController {
    
    @Autowired
    ProjectTaskService projectTaskService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Task> list(@PathVariable("id") Integer projectId) {
        return projectTaskService.listProjectTasks(projectId);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public Task read(@PathVariable("id") Integer projectId, @PathVariable("taskId") Integer taskId) {
        return projectTaskService.getTask(taskId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Task create(@PathVariable("id") Integer projectId, @RequestBody Task object) {
        
        return projectTaskService.addTaskToProject(object, projectId);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Task delete(@PathVariable("id") Integer projectId, @PathVariable("taskId") Integer taskId) {
        return projectTaskService.deleteTask(projectId, taskId);
    }
    
    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    @ResponseBody
    public Task update(@PathVariable("taskId") Integer taskId, @RequestBody Task task) {
        return projectTaskService.update(taskId, task);
    }
    
}
