package egg.news.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.news.exceptions.MyExceptions;
import egg.news.models.Journalist;
import egg.news.repositories.IJournalistRepository;

@Service
public class JournalistService {

    @Autowired
    private IJournalistRepository journalistRepository;   

    public List<Journalist> findAllJournalists(){

        List<Journalist> journalists = journalistRepository.findAll();
        
        return journalists;
    }

    @Transactional
    public void modifyJournalist(String id, String name, String lastname) throws MyExceptions{

        validate(name, lastname);

        Optional<Journalist> responseJournalist = journalistRepository.findById(id);

        if(responseJournalist.isPresent()){

            Journalist Journalist = responseJournalist.get();

            Journalist.setName(name);
            Journalist.setLastName(lastname);

            journalistRepository.save(Journalist);
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

    private void validate(String name, String lastname) throws MyExceptions{

        if(name.isEmpty()){
            throw new MyExceptions("The name is empty");
        }

        if(lastname.isEmpty()){
            throw new MyExceptions("The lastname is empty");
        }
    }    
}
