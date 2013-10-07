/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.familyalbum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.familyalbum.data.Image;
import wad.familyalbum.reposirory.ImageRepository;

/**
 *
 * @author timosand
 */
@Service
public class JpaImageService implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Image> list() {
        return imageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Image read(String imageId) {
        return imageRepository.findOne(imageId);
    }

    @Override
    @Transactional
    public void create(Image image) {
        imageRepository.save(image);
    }
}
