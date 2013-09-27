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
import wad.tokkel.models.Project;
import wad.tokkel.service.ProjectService;

/**
 *
 * @author timosand
 */
@Controller
@RequestMapping(value = "projects")
public class ProjectController  implements TokkelController<Project> {
    
    @Autowired
    ProjectService projectService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Project> list() {
        return projectService.list();
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Project read(@PathVariable("id") Integer id) {
        return projectService.read(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Project create(@RequestBody Project object) {
        return projectService.create(object);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Project delete(@PathVariable("id") Integer id) {
        return projectService.delete(id);
    }
    
}
