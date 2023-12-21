package egg.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import egg.news.models.News;
import egg.news.models.Users;
import egg.news.services.NewsService;
import egg.news.services.UsersService;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private NewsService newsService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<byte[]> imageUser(@PathVariable String id){

        Users user = usersService.findUserById(id);

        byte[] image = user.getImage().getContent();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }    

    @GetMapping("/news/{id}")
    public ResponseEntity<byte[]> imageNews(@PathVariable String id){

        News news = newsService.findNewsById(id);

        byte[] image = news.getImage().getContent();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/details/news/{id}")
    public ResponseEntity<byte[]> detailsNews(@PathVariable String id){
        
        News news = newsService.findNewsById(id);

        byte[] image = news.getImage().getContent();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);        
    }
}
