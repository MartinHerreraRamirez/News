package egg.news.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import egg.news.enums.Rol;
import egg.news.exceptions.MyExceptions;
import egg.news.models.Users;
import egg.news.repositories.IUsersRepository;

@Service
public class JournalistService {

    @Autowired
    private IUsersRepository usersRepository;  

    public List<Users> findAllJournalists(){

        List<Users> journalists = usersRepository.findUsersByRol(Rol.JOURNALIST);
        
        return journalists;
    }

    @Transactional
    public void modifyJournalist(String id, String name, String lastname, String email, String phone, String password, String password2) throws MyExceptions{

        validate(name, lastname, email, phone, password, password2);

        Optional<Users> responseJournalist = usersRepository.findById(id);

        if(responseJournalist.isPresent()){

            Users journalist = responseJournalist.get();

            journalist.setName(name);
            journalist.setLastname(lastname);
            journalist.setEmail(email);
            journalist.setPhone(phone);
            journalist.setPassword(new BCryptPasswordEncoder().encode(password));

            usersRepository.save(journalist);
        }
    }

    @Transactional
    public void deleteJournalist(String id) throws Exception{

        Optional<Users> JournalistResponse = usersRepository.findById(id);

        if(JournalistResponse.isPresent()){
            Users journalist = JournalistResponse.get();

            if(journalist.getIsActive()){
                journalist.setIsActive(false);
                usersRepository.save(journalist);
            } else {
                journalist.setIsActive(true);
                usersRepository.save(journalist);
            }
        }
    }

    public Users getJournalistById(String id){

        return usersRepository.getReferenceById(id);
    }

    private void validate(String name, String lastname, String email, String phone, String password, String password2) throws MyExceptions{

        if(name.isEmpty()){
            throw new MyExceptions("The name is empty");
        }

        if(lastname.isEmpty()){
            throw new MyExceptions("The lastname is empty");
        }

        if(email.isEmpty()){
            throw new MyExceptions("The email is empty");
        }

        if(email.length() < 8){
            throw new MyExceptions("The email can not contain less than 8 characters");
        }

        if(email.length() > 255){
            throw new MyExceptions("The email can not contain more than 255 characters");
        }

        if(phone.isEmpty()){
            throw new MyExceptions("The number phone is empty");
        }

        if(phone.length() < 9){
            throw new MyExceptions("The number phone don't can contain less than 9 characters");
        }

        if(phone.length() > 15){
            throw new MyExceptions("The number phone don't can contain more than 15 characters");
        }

        if(password.isEmpty()){
            throw new MyExceptions("The password is empty");
        }

        if(password2.isEmpty()){
            throw new MyExceptions("You must confirm the password");
        }

        if (password.length() != password2.length()) {
            throw new MyExceptions("Passwords do not match");
        }

        if(!password.equals(password2)){
            throw new MyExceptions("Password and confirmation password are different");
        }
    }    
}
