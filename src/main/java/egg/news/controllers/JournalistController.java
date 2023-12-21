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
import egg.news.models.Journalist;
import egg.news.services.JournalistService;

@Controller
@RequestMapping("/Journalist")
public class JournalistController {

    @Autowired
    private JournalistService JournalistService;

    @GetMapping("/register")
    public String registerJournalist(){
        return "Journalist-register";
    }


    @PostMapping("/registration")
    public String registrationJournalist(
    @RequestParam String name, 
    @RequestParam String lastname,
    ModelMap model){       

        try {
            JournalistService.createJournalist(name, lastname);
            model.put("success", "The Journalist has been created successfully.");            
            
        } catch (MyExceptions e) {
            model.put("error", e.getMessage());
            return "Journalist-register";
        }

        return "Journalist-register";       
    } 

    
    @GetMapping("/list")
    public String list(ModelMap model){
        List<Journalist> Journalists = JournalistService.findAllJournalists();
        model.addAttribute("Journalists", Journalists);

        return "Journalist-list";
    }


    @GetMapping("/modify/{id}")
    public String modify(@PathVariable String id, ModelMap model){

        model.put("Journalist", JournalistService.getJournalistById(id));

        return "Journalist-modify";
    }


    @PostMapping("/modify/{id}")
    public String modify(
    @PathVariable String id, 
    @RequestParam String name, 
    @RequestParam String lastname, 
    ModelMap model){

        try{
            JournalistService.modifyJournalist(id, name, lastname);
            model.put("success", "The Journalist has been modified successfully.");
    
            return "redirect:/Journalist/list";
        }catch(MyExceptions e){

            model.put("error",e.getMessage());
            return "Journalist-modify";
        }
    }
    

    @GetMapping("/delete")
    public String deleteJournalist(Journalist Journalist, ModelMap model){
        
        try {
            JournalistService.deleteJournalist(Journalist.getId());
            return "redirect:/Journalist/list";
            
        } catch (Exception e) {
            return "Journalist-list";
        }
    }    
}
