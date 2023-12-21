package egg.news.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egg.news.models.Journalist;
import egg.news.models.News;
import egg.news.services.JournalistService;
import egg.news.services.NewsService;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private JournalistService JournalistService;

    @Autowired
    private NewsService newsService;
    
    @GetMapping("/register")
    public String registerNews(ModelMap model){

        List<Journalist> journalists = JournalistService.findAllJournalists();
        
        model.addAttribute("journalists", journalists);

        return "news-register";
    } 

    @PostMapping("/registration")
    public String registrationNews(
    @RequestParam String title, 
    @RequestParam String body, 
    @RequestParam MultipartFile file,
    @RequestParam String idJournalist,
    ModelMap model){

        try {            
            newsService.createNews(title, body, file, idJournalist);
            model.put("success", "The news has been created successfully");
            return "news-register";  
            
        } catch (Exception e) {

            List<Journalist> Journalists = JournalistService.findAllJournalists();
            model.addAttribute("Journalists", Journalists);
            model.put("error", e.getMessage());

            return "news-register";    
        }
    }  
    
    @GetMapping("/list")
    public String list(ModelMap model) {        
        List<News> news = newsService.findAllNews();
        model.addAttribute("news", news);
        
        return "news-list";
    }

    @GetMapping("/see/{id}")
    public String seeNew(@PathVariable String id, ModelMap model) {

        News news = newsService.findNewsById(id);

        model.put("news", news);

        return "see-new";        
    }

    @GetMapping("/modify/{id}")
    public String modifyNew(@PathVariable String id, ModelMap model){

        News news = newsService.findNewsById(id);
        List<Journalist> JournalistList = JournalistService.findAllJournalists();
        
        model.put("news", news);
        model.addAttribute("Journalists",JournalistList);

        return "modify-news";
    }

    @PostMapping("/modify/{id}")
    public String modifyNew(
    @PathVariable String id,
    @RequestParam String title,
    @RequestParam String body,
    @RequestParam String idJournalist,
    ModelMap model){

        try {
            newsService.modifyNew(id, title, body, idJournalist);
            model.put("success", "news modified successfully");

            return "redirect:/news/list";
        } catch (Exception e) {
            model.put("error", "error when trying to save changes, try again");

            return "redirect:/news/list";
        }
    }


    @GetMapping("/delete")
    public String deleteNew(News news){

        try{
            newsService.deleteNew(news.getId());
            return "redirect:/news/list" ;

        }catch(Exception e){
            return "redirect:/news/list";
        }
    }
    
}
