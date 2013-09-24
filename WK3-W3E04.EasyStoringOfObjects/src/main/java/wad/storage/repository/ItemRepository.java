/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.storage.repository;

import org.springframework.data.repository.CrudRepository;
import wad.storage.domain.Item;

/**
 *
 * @author timosand
 */
public interface ItemRepository extends CrudRepository<Item, Long>{
    
}
