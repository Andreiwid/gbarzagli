package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifsp.tcc.gbarzagli.embrapa.share.Constants;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Image;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Plant;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Post;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.ImageRepository;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.PlantRepository;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.PostRepository;

/**
 * Rest controller to handle post operations
 * 
 * @author <a href="mailTo:gabriel.barzagli@hotmail.com"> Gabriel Viseli Barzagli (gabriel.barzagli@hotmail.com) </a>
 *
 */
@RestController
public class PostRestController extends APIRestController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    ImageRepository imageRepository;

    /**
     * POST endpoint to upload a image to our server
     * 
     * @param sender
     *            email of the producer
     * @param plant
     *            id of the corresponding plant
     * @param images
     *            jpeg file of the plant's leaf
     * @return HTTP response status: <br>
     *         <ul>
     *         <li>201 Post created</li>
     *         <li>400 Missing some information in the request</li>
     *         <li>500 Server internal error</li>
     *         </ul>
     */
    @RequestMapping(value = "post", method = RequestMethod.POST)
    public HttpEntity<Object> upload(@RequestParam String sender, @RequestParam Long plantId, @RequestParam MultipartFile files) {
        if ((sender != null && !sender.isEmpty()) && (files != null && !files.isEmpty()) && (plantId != null)) {
            ZipInputStream zip = null;
            FileOutputStream fos = null;

            try {
                Post post = new Post();
                List<Image> images = null;
                zip = new ZipInputStream(files.getInputStream());
                ZipEntry entry = zip.getNextEntry();
                while (entry != null) {
                    File dir = new File(Constants.IMAGES_DIRECTORY);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    
                    String imageName = Constants.IMAGES_DIRECTORY + "/" 
                                    + Constants.PREFIX_IMAGE_NAME 
                                    + new Date().getTime() 
                                    + Constants.SUFIX_JPEG_NAME;
                    File file = new File(imageName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    fos = new FileOutputStream(file);
                    IOUtils.copy(zip, fos);
                    fos.flush();
                    fos.close();
                    entry = zip.getNextEntry();
                    
                    Image image = new Image();
                    image.setPost(post);
                    image.setPath(file.getAbsolutePath());
                    
                    if (images == null) {
                        images = new ArrayList<>();
                    }
                    images.add(image);
                }

                Plant plant = plantRepository.findOne(plantId);
                post.setSender(sender);
                post.setPlant(plant);
                post.setImages(images);
                postRepository.save(post);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                }
            }

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * GET endpoint to return all posts related to the user
     * 
     * @param username
     *            user's e-mail
     * @return a list with each post
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<Post> getUserPosts(@PathVariable("sender") String sender) {
        List<Post> userPosts = postRepository.findBySender(sender);
        return userPosts;
    }

    /**
     * GET endpoint to return images of a post
     * 
     * @param id post's id
     * @return images inside a file in zip format
     */
    @RequestMapping(value = "/{id}/images", method = RequestMethod.GET, produces = "application/zip", name = "images.zip")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public byte[] getPostImages(@PathVariable("id") Long id) {
        byte[] response = null;
        try {
            Post post = postRepository.findOne(id);

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(byteStream);
            for (Image image : post.getImages()) {
                File file = new File(image.getPath());
                zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                FileInputStream fileInputStream = new FileInputStream(file);
                IOUtils.copy(fileInputStream, zipOutputStream);
                fileInputStream.close();
                zipOutputStream.closeEntry();
            }

            response = byteStream.toByteArray();
            zipOutputStream.close();
            byteStream.close();
        } catch (IOException e) {
        }

        return response;
    }

    

}
