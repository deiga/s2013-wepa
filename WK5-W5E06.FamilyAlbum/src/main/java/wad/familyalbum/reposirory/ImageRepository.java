/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.familyalbum.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.familyalbum.data.Image;

/**
 *
 * @author timosand
 */
public interface ImageRepository extends JpaRepository<Image, String>{
    
}
