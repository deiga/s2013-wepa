/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tokkel.controller;

/**
 *
 * @author timosand
 */
public interface TokkelController<T> {
    
    public Iterable<T> list();
    public T read(Integer id);
    public T create(T object);
    public T delete(Integer id);
    
}
