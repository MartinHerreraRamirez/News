package egg.news.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egg.news.exceptions.MyExceptions;
import egg.news.models.Journalist;
import egg.news.models.Image;
import egg.news.models.News;
import egg.news.repositories.IJournalistRepository;
import egg.news.repositories.INewsRepository;

@Service
public class NewsService {

    @Autowired
    private INewsRepository newsRepository;

    @Autowired
    private IJournalistRepository journalistRepository;

    @Autowired
    private ImageService imageService;

    @Transactional
    public void createNews(String title, String body, MultipartFile file, String idJournalist) throws MyExceptions, Exception{        

        validate(title, body, idJournalist);

        News news = new News();
        Journalist journalist = journalistRepository.findById(idJournalist).orElseThrow(() -> new MyExceptions("Journalist not found"));

        news.setTitle(title);
        news.setBody(body);
        news.setPostDate(new Date());
        news.setIsPost(true);
        news.setJournalist(journalist);

        Image image = imageService.saveImage(file);
        news.setImage(image);

        journalist.getMyNews().add(news);
        journalistRepository.save(journalist);
    }

    public List<News> findAllNews(){
        
        List<News> news = newsRepository.findAll();

        return news;
    }

    public List<News> findNewsByJournalistId(String id){

        List<News> news = newsRepository.findNewsByJournalistId(id);

        return news;
    }

    @Transactional
    public void modifyNew(String id, String title, String body, String idJournalist ) throws MyExceptions{

        validate(title, body, idJournalist);

        Optional<News> responseNews = newsRepository.findById(id);
        Optional<Journalist> responseJournalist = journalistRepository.findById(idJournalist);

        Journalist journalist = new Journalist();

        if(responseJournalist.isPresent()){

            journalist = responseJournalist.get();
        }

        if(responseNews.isPresent()){

            News news = responseNews.get();

            news.setTitle(title);
            news.setBody(body);
            news.setPostDate(new Date());
            news.setJournalist(journalist);

            newsRepository.save(news);
        }
    }

    public void deleteNew(String id){
        newsRepository.deleteById(id);
    }

    public News findNewsById(String id){

        Optional<News> newsResponse = newsRepository.findById(id);             
        
        if(newsResponse.isPresent()){
            News news = new News();
            return news = newsResponse.get();
        }

        return null;        
    }
    
    private void validate(String title, String body, String idJournalist) throws MyExceptions{

        if(title.isEmpty()){
            throw new MyExceptions("The title is empty");
        }

        if(body.isEmpty()){
            throw new MyExceptions("The body is empty");
        }

        if(idJournalist.isEmpty() || idJournalist == null){
            throw new MyExceptions("The Journalist is empty");
        }
    }
}
