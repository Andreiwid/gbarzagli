package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Plant;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.model.Post;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.PlantRepository;
import br.edu.ifsp.tcc.gbarzagli.embrapa.share.repository.PostRepository;

/**
 * Rest controller to handle post operations
 * @author <a href="mailTo:gabriel.barzagli@hotmail.com"> Gabriel Viseli Barzagli (gabriel.barzagli@hotmail.com) </a>
 *
 */
@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	PlantRepository plantRepository;
	
	/**
	 * POST endpoint to upload a image to our server
	 * @param sender email of the producer
	 * @param plant id of the corresponding plant
	 * @param images jpeg file of the plant's leaf
	 * @return HTTP response status: <br>
	 * <ul>
	 * <li> 201 Post created </li>
	 * <li> 400 Missing some information in the request </li> 
	 * <li> 500 Server internal error </li>
	 * </ul>
	 */
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Object> upload(@RequestParam String sender, @RequestParam Long plantId, @RequestParam MultipartFile images) {
		if ((sender != null && !sender.isEmpty()) && (images != null && !images.isEmpty()) && (plantId != null)) {
			
			ZipInputStream zip = null;
			FileOutputStream fos = null;
			try {
				String imageName = Constants.PREFIX_IMAGE_NAME + new Date().getTime() + Constants.SUFIX_JPEG_NAME;
				File file = new File(imageName);
				if (!file.exists()) {
					file.createNewFile();
				}
				
				zip = new ZipInputStream(images.getInputStream());
				ZipEntry entry = null;
				
				while ((entry = zip.getNextEntry()) != null) {
					fos = new FileOutputStream(file);
					IOUtils.copy(zip, fos);
				}
				
				Plant plant = plantRepository.findOne(plantId);
				
				Post post = new Post(sender, plant, file.getAbsolutePath());
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
	 * @param username user's e-mail
	 * @return a list with each post
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<Post> getUserPosts(@RequestParam String username) {
		List<Post> userPosts = postRepository.findByUsername(username);
		for (Post post : userPosts) {
			post.setImage("");
		}
		
		return userPosts;
	}
	
	/**
	 * GET endpoint to return the image of a post
	 * @param id id do post
	 * @return the image in jpeg format
	 */
	@RequestMapping(
			value = "/{id}/image", 
			method = RequestMethod.GET, 
			produces = MediaType.IMAGE_JPEG_VALUE
	)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public byte[] getPostImage(@PathVariable("id") Long id) {
		byte[] response = null;
		try {
			Post post = postRepository.findOne(id);
			File image = new File(post.getImage());
			response = new byte[(int) image.length()];
			FileInputStream fis = new FileInputStream(image);
			fis.read(response);
			fis.close();
		} catch (IOException e) {
		}
		
		return response;
	}
	
}
