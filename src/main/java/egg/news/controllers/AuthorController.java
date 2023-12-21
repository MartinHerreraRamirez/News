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

import egg.news.exceptions.MyExceptions;
import egg.news.models.Author;
import egg.news.services.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/register")
    public String registerAuthor(){
        return "author-register";
    }


    @PostMapping("/registration")
    public String registrationAuthor(
    @RequestParam String name, 
    @RequestParam String lastname,
    ModelMap model){       

        try {
            authorService.createAuthor(name, lastname);
            model.put("success", "The author has been created successfully.");            
            
        } catch (MyExceptions e) {
            model.put("error", e.getMessage());
            return "author-register";
        }

        return "author-register";       
    } 

    
    @GetMapping("/list")
    public String list(ModelMap model){
        List<Author> authors = authorService.findAllAuthors();
        model.addAttribute("authors", authors);

        return "author-list";
    }


    @GetMapping("/modify/{id}")
    public String modify(@PathVariable String id, ModelMap model){

        model.put("author", authorService.getAuthor(id));

        return "author-modify";
    }


    @PostMapping("/modify/{id}")
    public String modify(
    @PathVariable String id, 
    @RequestParam String name, 
    @RequestParam String lastname, 
    ModelMap model){

        try{
            authorService.modifyAuthor(id, name, lastname);
            model.put("success", "The author has been modified successfully.");
    
            return "redirect:/author/list";
        }catch(MyExceptions e){

            model.put("error",e.getMessage());
            return "author-modify";
        }
    }
    

    @GetMapping("/delete")
    public String deleteAuthor(Author author, ModelMap model){
        
        try {
            authorService.deleteAuthor(author.getId());
            return "redirect:/author/list";
            
        } catch (Exception e) {
            return "author-list";
        }
    }    
}
