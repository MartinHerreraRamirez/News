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
    private IJournalistRepository JournalistRepository;

    @Transactional
    public void createJournalist(String name, String lastname) throws MyExceptions{

        validate(name, lastname);

        Journalist Journalist = new Journalist();

        Journalist.setName(name);
        Journalist.setLastName(lastname);
        Journalist.setIsActive(true);

        JournalistRepository.save(Journalist);
    }

    public List<Journalist> findAllJournalists(){

        List<Journalist> Journalists = JournalistRepository.findAll();
        
        return Journalists;
    }

    @Transactional
    public void modifyJournalist(String id, String name, String lastname) throws MyExceptions{

        validate(name, lastname);

        Optional<Journalist> responseJournalist = JournalistRepository.findById(id);

        if(responseJournalist.isPresent()){

            Journalist Journalist = responseJournalist.get();

            Journalist.setName(name);
            Journalist.setLastName(lastname);

            JournalistRepository.save(Journalist);
        }
    }

    @Transactional
    public void deleteJournalist(String id) throws Exception{

        Optional<Journalist> JournalistResponse = JournalistRepository.findById(id);

        if(JournalistResponse.isPresent()){
            Journalist Journalist = JournalistResponse.get();
            Journalist.setIsActive(false);
            JournalistRepository.save(Journalist);
        }
    }

    public Journalist getJournalistById(String id){

        return JournalistRepository.getReferenceById(id);
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
