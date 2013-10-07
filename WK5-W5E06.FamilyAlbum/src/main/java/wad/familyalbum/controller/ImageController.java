/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.familyalbum.controller;

import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wad.familyalbum.data.Image;
import wad.familyalbum.service.ImageService;

/**
 *
 * @author timosand
 */
@Controller
@RequestMapping("images")
public class ImageController {

    @Autowired
    ImageService imageService;

    @RequestMapping(method = RequestMethod.POST)
    public String addImage(@RequestParam("description") String description,
            @RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {

        Image image = new Image();
        image.setDescription(description);
        image.setContentType(file.getContentType());
        image.setData(file.getBytes());
        image.setFileName(file.getOriginalFilename());
        image.setTimestamp(new Date());

        imageService.create(image);
        response.setHeader("X-Image-Id", image.getId());

        return "redirect:/app/album";
    }

    @RequestMapping(value = "{imageId}/download", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable("imageId") String imageId) {
        Image image = imageService.read(imageId);
        if (image == null) {
            return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        }

        byte[] content = image.getData();

        // otsakkeiden luonti
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getContentType()));
        headers.setContentLength(content.length);

        // datan palautus
        return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "{imageId}/download/attachment", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> downloadAsAttachment(@PathVariable("imageId") String imageId) {
        Image image = imageService.read(imageId);
        if (image == null) {
            return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        }

        byte[] content = image.getData();

        // otsakkeiden luonti
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getContentType()));
        headers.setContentLength(content.length);
        headers.set("Content-Disposition", "attachment; filename=\"" + image.getFileName() + "\"");

        // datan palautus
        return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "{imageId}", method = RequestMethod.GET)
    @ResponseBody
    public Image view(@PathVariable("imageId") String imageId) {
        return imageService.read(imageId);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Image> list() {
        return imageService.list();
    }
}
