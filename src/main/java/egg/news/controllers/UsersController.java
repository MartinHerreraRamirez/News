package egg.news.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import egg.news.models.Users;
import egg.news.services.UsersService;

@Controller
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/register")
    public String register(ModelMap model){               
        
        model.addAttribute("rols", usersService.deleteAdminRol());

        return "users-register";
    }

    @PostMapping("/registration")
    public String registration(
    @RequestParam String name,
    @RequestParam String lastname,
    @RequestParam String email,
    @RequestParam String phone,
    @RequestParam String password,
    @RequestParam String password2,
    @RequestParam String idRol,
    @RequestParam MultipartFile file,
    ModelMap model){

        try {
            usersService.createUser(name, lastname, email, phone, password, password2, idRol, file);
            model.put("success", "The user has been created successfully");
            
            return "redirect:/login";
            
        } catch (Exception e) {
            model.put("error", e.getMessage());

            model.put("name", name);
            model.put("lastname", lastname);
            model.put("email", email);
            model.put("phone", phone);
            model.put("file", file);
            
            return "users-register";
        }
    }    

    @GetMapping("/edit")
    public String profile(){
        return "users-profile";
    }

    @PostMapping("/profile")
    public String editProfile(
    @RequestParam String name,
    @RequestParam String lastname,
    @RequestParam String email,
    @RequestParam String phone,
    HttpSession session,
    ModelMap model 
    ){

        try {
            Users user = (Users) session.getAttribute("usersession");
    
            model.put("success", "The data has been updated");

            user.setName(name);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setPhone(phone);
    
            return "/edit/profile";
            
        } catch (Exception e) {
            model.put("error", "error updating data");

            model.put("name", name);
            model.put("lastname", lastname);
            model.put("email", email);
            model.put("phone", phone);

            return "/edit/profile";
        }
    }
}
