package br.edu.ifsp.tcc.gbarzagli.embrapa.share.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * @param image jpeg file of the plant's leaf
	 * @return HTTP response status: <br>
	 * <ul>
	 * <li> 201 Post created </li>
	 * <li> 400 Missing some information in the request </li> 
	 * <li> 500 Server internal error </li>
	 * </ul>
	 */
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Object> upload(@RequestParam String sender, @RequestParam Long plantId, @RequestParam MultipartFile image) {
		
		if ((sender != null && !sender.isEmpty()) && (image != null && !image.isEmpty()) && (plantId != null)) {
			
			FileOutputStream fos = null;
			try {
				String imageName = Constants.PREFIX_IMAGE_NAME + new Date().getTime() + Constants.SUFIX_JPEG_NAME;
				File file = new File(imageName);
				if (!file.exists()) {
					file.createNewFile();
				}
				
				fos = new FileOutputStream(file);
				fos.write(image.getBytes());
				fos.flush();
				
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

}
