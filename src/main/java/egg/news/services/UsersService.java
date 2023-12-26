package egg.news.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import egg.news.enums.Rol;
import egg.news.exceptions.MyExceptions;
import egg.news.models.Image;
import egg.news.models.Journalist;
import egg.news.models.Users;
import egg.news.repositories.IJournalistRepository;
import egg.news.repositories.IUsersRepository;

@Service
public class UsersService implements UserDetailsService{

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IJournalistRepository journalistRepository;

    @Autowired
    private ImageService imageService;

    public ArrayList<Rol> deleteAdminRol(){
        ArrayList<Rol> rols = new ArrayList<>(Arrays.asList(Rol.values()));
        rols.remove(0); 
        return rols;
    }

    @Transactional
    public void createUser(
    String name,
    String lastname,
    String email,
    String phone,
    String password,
    String password2,
    String idRol,
    MultipartFile file) throws MyExceptions, Exception{

        validate(name, lastname, email, phone, password, password2);        
        
        if(idRol.equals("USER")){
            Users user = new Users();
            
            user.setName(name);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setRol(Rol.USER);
            user.setHighDate(new Date());
            user.setIsActive(true);
            
            Image image = imageService.saveImage(file);
            
            user.setImage(image);
            
            usersRepository.save(user);       

        } else {

            Journalist journalist = new Journalist();

            journalist.setName(name);
            journalist.setLastname(lastname);
            journalist.setEmail(email);
            journalist.setPhone(phone);
            journalist.setPassword(new BCryptPasswordEncoder().encode(password));
            journalist.setRol(Rol.JOURNALIST);  
            journalist.setHighDate(new Date());          
            journalist.setIsActive(true);
            journalist.setSalary(null);

            Image image = imageService.saveImage(file);

            journalist.setImage(image);

            journalistRepository.save(journalist);
        }        
    }

    @Transactional
    public void modifyUser(
    String idUser,
    String name,
    String lastname,
    String email,
    String phone,
    String password,
    String password2,
    MultipartFile file) throws MyExceptions, Exception{

        validate(name, lastname, email, phone, password, password2);

        Optional<Users> responseUser = usersRepository.findById(idUser);

        Users user = null;

        if(responseUser.isPresent()){

            user = responseUser.get();

            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setPassword(new BCryptPasswordEncoder().encode(password));  
            user.setRol(Rol.USER);
            
            String idImage = null;

            if(user.getImage() != null){
                idImage = user.getImage().getId();
            }

            Image image = imageService.modifyImage(file, idImage);

            user.setImage(image);
        }
    }

    public Users findUserById(String id){

        Optional<Users> responseUser = usersRepository.findById(id);

        Users users = null;

        if(responseUser.isPresent()){
            return users = responseUser.get();
        }

        return null;
    }

    public void validate(
    String name, 
    String lastname,
    String email, 
    String phone, 
    String password, 
    String password2) throws MyExceptions{

        if(name.isEmpty() || name == null){
            throw new MyExceptions("The name is empty or null");
        }

        if(lastname.isEmpty() || lastname == null){
            throw new MyExceptions("The name is empty or null");
        }

        if(email.isEmpty() || email == null){
            throw new MyExceptions("The email is empty or null");
        }

        if(email.length() < 8){
            throw new MyExceptions("The email must be at least 8 characters long");
        }

        if(email.length() > 255){
            throw new MyExceptions("The email must be less 255 characters long");
        }

        if(!email.contains("@")){
            throw new MyExceptions("The email doesn't have valid format");
        }

        if(phone.isEmpty() || phone == null){
            throw new MyExceptions("The phone number is empty or null");
        }

        if(phone.length() < 9){
            throw new MyExceptions("The phone number must be at least 9 characters long");
        }

        if(phone.length() > 16){
            throw new MyExceptions("The phone number must be less 16 characters long");
        }

        if(password.isEmpty() || password == null){
            throw new MyExceptions("The password is empty or null");
        }

        if(password2.isEmpty() || password2 == null){
            throw new MyExceptions("The confirm password is empty or null");
        }

        if(password.length() < 8 || password2.length() < 8){
            throw new MyExceptions("The password must be at least 8 charactes long");
        }

        if(password.length() > 64 || password2.length() > 64){
            throw new MyExceptions("The password must be less than 64 characters long");
        }

        if(!password.equals(password2)){
            throw new MyExceptions("Passwords don't match");
        }
    }   

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = null;

        if(username.contains("@")){
            user = usersRepository.findUserByEmail(username);
        } else {
            user = usersRepository.findUserByPhone(username);
        }

        if(user != null){

            List<GrantedAuthority> permit = new ArrayList<>();

            permit.add(new SimpleGrantedAuthority("ROLE_" + user.getRol().toString()));

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attributes.getRequest().getSession(true);

            session.setAttribute("usersession", user);

            return new User(user.getEmail(), user.getPassword(), permit);

        }else{
            return null;
        }
    }        
}
