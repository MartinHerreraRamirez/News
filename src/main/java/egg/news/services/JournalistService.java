package egg.news.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.news.enums.Role;
import egg.news.exceptions.MyExceptions;
import egg.news.models.Journalist;
import egg.news.repositories.IJournalistRepository;

@Service
public class JournalistService {     

    @Autowired
    private IJournalistRepository journalistRepository;

    public List<Journalist> findAllJournalists(){

        List<Journalist> journalists = journalistRepository.findUsersByRol(Role.JOURNALIST);
        
        return journalists;
    }

    @Transactional
    public void modifyJournalist(String id, String name, String lastname, String email, String phone, String salary) throws MyExceptions{

        validate(name, lastname, email, phone, salary);

        Optional<Journalist> responseJournalist = journalistRepository.findById(id);

        if(responseJournalist.isPresent()){

            Journalist journalist = responseJournalist.get();

            journalist.setName(name);
            journalist.setLastname(lastname);
            journalist.setEmail(email);
            journalist.setPhone(phone);
            journalist.setSalary(salary);

            journalistRepository.save(journalist);
        }
    }

    @Transactional
    public void deleteJournalist(String id) throws Exception{

        Optional<Journalist> JournalistResponse = journalistRepository.findById(id);

        if(JournalistResponse.isPresent()){
            Journalist journalist = JournalistResponse.get();

            if(journalist.getIsActive()){
                journalist.setIsActive(false);
                journalistRepository.save(journalist);
            } else {
                journalist.setIsActive(true);
                journalistRepository.save(journalist);
            }
        }
    }

    public Journalist getJournalistById(String id){

        return journalistRepository.getReferenceById(id);
    }

    public Journalist findJournalistWithNews(String id){
        return journalistRepository.findJournalistWithNews(id);
    }

    private void validate(
    String name, 
    String lastname, 
    String email, 
    String phone, 
    String salary) throws MyExceptions{

        if(name.isEmpty() || name == null){
            throw new MyExceptions("The name don't can be empty or null");
        }

        if(lastname.isEmpty() || lastname == null){
            throw new MyExceptions("The lastname don't can be empty or null");
        }

        if(email.isEmpty() || email == null){
            throw new MyExceptions("The email don't can be empty or null");
        }

        if(!email.contains("@")){
            throw new MyExceptions("The email does not contain the '@' character.");
        }

        if(email.length() < 8){
            throw new MyExceptions("The email can not contain less than 8 characters");
        }

        if(email.length() > 255){
            throw new MyExceptions("The email can not contain more than 255 characters");
        }

        if(phone.isEmpty() || phone == null){
            throw new MyExceptions("The number phone is empty or null");
        }

        if(phone.length() < 9){
            throw new MyExceptions("The number phone don't can contain less than 9 characters");
        }

        if(phone.length() > 15){
            throw new MyExceptions("The number phone don't can contain more than 15 characters");
        }

        if(salary.isEmpty() || salary == null){
            throw new MyExceptions("The salary don't cant be empty or null");
        }

    }    
}
