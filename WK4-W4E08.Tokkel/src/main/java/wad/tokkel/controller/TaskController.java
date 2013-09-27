/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tokkel.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.tokkel.models.Task;
import wad.tokkel.service.TaskService;

/**
 *
 * @author timosand
 */
@Controller
@RequestMapping(value = "tasks")
public class TaskController implements TokkelController<Task> {
    
    @Autowired
    TaskService taskService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Task> list() {
        return taskService.list();
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Task read(@PathVariable("id") Integer id) {
        return taskService.read(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Task create(@RequestBody Task object) {
        return taskService.create(object);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Task delete(@PathVariable("id") Integer id) {
        return taskService.delete(id);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Task update(@PathVariable("id") Integer taskId, @RequestBody Task task) {
        return taskService.update(taskId, task);
    }
    
}
