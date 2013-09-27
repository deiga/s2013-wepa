/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tokkel.service;

/**
 *
 * @author timosand
 */
public interface TokkelService<T> {

    T create(T object);

    T delete(Integer id);

    Iterable<T> list();

    T read(Integer id);
    
}
