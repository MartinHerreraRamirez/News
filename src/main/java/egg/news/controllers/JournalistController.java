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
@RequestMapping("/journalist")
public class JournalistController {

    @Autowired
    private JournalistService journalistService;
    
    @GetMapping("/list")
    public String list(ModelMap model){
        List<Journalist> journalists = journalistService.findAllJournalists();
        model.addAttribute("journalists", journalists);

        return "Journalist-list";
    }


    @GetMapping("/modify/{id}")
    public String modify(@PathVariable String id, ModelMap model){

        model.put("journalist", journalistService.getJournalistById(id));

        return "journalist-modify";
    }


    @PostMapping("/modify/{id}")
    public String modify(
    @PathVariable String id,   
    @RequestParam String name,
    @RequestParam String lastname,   
    @RequestParam String email,
    @RequestParam String phone,
    @RequestParam String salary,   
    ModelMap model){

        try{
            journalistService.modifyJournalist(id, name, lastname, email, phone, salary);
            model.put("success", "The Journalist has been modified successfully.");
            
            return "redirect:/journalist/list";
        }catch(MyExceptions e){
            model.put("error", e.getMessage());

            model.put("name", name);
            model.put("lastname", lastname);
            model.put("email", email);
            model.put("phone", phone);
            model.put("salary", salary);

            return "journalist-modify";
        }
    }
    

    @GetMapping("/delete")
    public String deleteJournalist(Journalist journalist, ModelMap model){
        
        try {
            
            journalistService.deleteJournalist(journalist.getId());
            return "redirect:/journalist/list";
            
        } catch (Exception e) {
            return "Journalist-list";
        }
    }    
}
