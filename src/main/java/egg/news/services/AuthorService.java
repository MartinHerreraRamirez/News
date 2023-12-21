package egg.news.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egg.news.exceptions.MyExceptions;
import egg.news.models.Author;
import egg.news.repositories.IAuthorRepository;

@Service
public class AuthorService {

    @Autowired
    private IAuthorRepository authorRepository;

    @Transactional
    public void createAuthor(String name, String lastname) throws MyExceptions{

        validate(name, lastname);

        Author author = new Author();

        author.setName(name);
        author.setLastname(lastname);
        author.setIsPost(true);

        authorRepository.save(author);
    }

    public List<Author> findAllAuthors(){

        List<Author> authors = authorRepository.findAll();
        
        return authors;
    }

    @Transactional
    public void modifyAuthor(String id, String name, String lastname) throws MyExceptions{

        validate(name, lastname);

        Optional<Author> responseAuthor = authorRepository.findById(id);

        if(responseAuthor.isPresent()){

            Author author = responseAuthor.get();

            author.setName(name);
            author.setLastname(lastname);

            authorRepository.save(author);
        }
    }

    @Transactional
    public void deleteAuthor(String id) throws Exception{

        Optional<Author> authorResponse = authorRepository.findById(id);

        if(authorResponse.isPresent()){
            Author author = authorResponse.get();
            author.setIsPost(false);
            authorRepository.save(author);
        }
    }

    public Author getAuthor(String id){

        return authorRepository.getReferenceById(id);
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
