package egg.news.controllers;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egg.news.models.News;
import egg.news.models.Users;
import egg.news.services.NewsService;

@Controller
@RequestMapping("/")
public class GeneralController {  

    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String index(ModelMap model){

        List<News> news = newsService.findAllNews();
        Collections.reverse(news);

        model.put("news", news);
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model){

        if(error != null){
            model.put("error", "invalid credentials");
        }
        return "login";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/start")
    public String start(HttpSession session) {

        Users currentUser = (Users) session.getAttribute("usersession");

        if(currentUser.getRol().toString().equals("ADMIN")){
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/login";

        }
        
    }    
}
