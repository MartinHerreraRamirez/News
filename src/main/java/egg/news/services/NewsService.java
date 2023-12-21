package egg.news.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egg.news.exceptions.MyExceptions;
import egg.news.models.Author;
import egg.news.models.Image;
import egg.news.models.News;
import egg.news.repositories.IAuthorRepository;
import egg.news.repositories.INewsRepository;

@Service
public class NewsService {

    @Autowired
    private INewsRepository newsRepository;

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void createNews(String title, String body, MultipartFile file, String idAuthor) throws MyExceptions, Exception{        

        validate(title, body, idAuthor);

        News news = new News();
        Author author = authorRepository.findById(idAuthor).get();

        news.setTitle(title);
        news.setBody(body);
        news.setPostDate(new Date());
        news.setIsPost(true);                
        news.setAuthor(author);

        Image image = imageService.saveImage(file);
        news.setImage(image);

        newsRepository.save(news);
    }

    public List<News> findAllNews(){
        
        List<News> news = newsRepository.findAll();

        return news;
    }

    @Transactional
    public void modifyNew(String id, String title, String body, String idAuthor ) throws MyExceptions{

        validate(title, body, idAuthor);

        Optional<News> responseNews = newsRepository.findById(id);
        Optional<Author> responseAuthor = authorRepository.findById(idAuthor);

        Author autor = new Author();

        if(responseAuthor.isPresent()){

            autor = responseAuthor.get();
        }

        if(responseNews.isPresent()){

            News news = responseNews.get();

            news.setTitle(title);
            news.setBody(body);
            news.setPostDate(new Date());
            news.setAuthor(autor);

            newsRepository.save(news);
        }
    }

    public void deleteNew(String id){
        newsRepository.deleteById(id);
    }

    public News findNewsById(String id){

        Optional<News> newsResponse = newsRepository.findById(id);       
        
        News news = new News();
        
        if(newsResponse.isPresent()){
            return news = newsResponse.get();
        }

        return null;        
    }
    
    private void validate(String title, String body, String idAuthor) throws MyExceptions{

        if(title.isEmpty()){
            throw new MyExceptions("The title is empty");
        }

        if(body.isEmpty()){
            throw new MyExceptions("The body is empty");
        }

        if(idAuthor.isEmpty() || idAuthor == null){
            throw new MyExceptions("The author is empty");
        }
    }
}
