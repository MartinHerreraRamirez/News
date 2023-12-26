package egg.news.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import egg.news.models.News;
import egg.news.models.Users;
import egg.news.services.JournalistService;
import egg.news.services.NewsService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private JournalistService JournalistService;

    @GetMapping("/dashboard")
    public String dashboard(ModelMap model){

        List<News> news = newsService.findAllNews();
        model.addAttribute("news", news);

        List<Users> Journalists = JournalistService.findAllJournalists();
        model.addAttribute("Journalists",Journalists);

        return "dashboard";
    }    
}
