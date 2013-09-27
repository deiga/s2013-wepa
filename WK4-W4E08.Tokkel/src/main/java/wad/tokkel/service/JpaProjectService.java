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
import wad.tokkel.repository.ProjectRepository;

/**
 *
 * @author timosand
 */
@Service
public class JpaProjectService implements ProjectService {
    
    @Autowired
    ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Project> list() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Project read(Integer id) {
        return projectRepository.findOne(id);
    }

    @Override
    @Transactional
    public Project create(Project project) {
        project.setCreationTime(new Date());
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project delete(Integer id) {
        Project deleted = read(id);
        projectRepository.delete(deleted);
        return deleted;
    }
    
}
