/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.familyalbum.service;

import wad.familyalbum.data.Image;

/**
 *
 * @author timosand
 */
public interface ImageService {

    public Iterable<Image> list();

    public Image read(String imageId);

    public void create(Image image);
}
