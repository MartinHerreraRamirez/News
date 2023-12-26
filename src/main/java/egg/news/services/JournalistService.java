package egg.news.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void modifyJournalist(String id, String name, String lastname) throws MyExceptions{

        validate(name, lastname);

        Optional<Users> responseJournalist = usersRepository.findById(id);

        if(responseJournalist.isPresent()){

            Users Journalist = responseJournalist.get();

            Journalist.setName(name);
            Journalist.setLastname(lastname);

            usersRepository.save(Journalist);
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

    private void validate(String name, String lastname) throws MyExceptions{

        if(name.isEmpty()){
            throw new MyExceptions("The name is empty");
        }

        if(lastname.isEmpty()){
            throw new MyExceptions("The lastname is empty");
        }
    }    
}
