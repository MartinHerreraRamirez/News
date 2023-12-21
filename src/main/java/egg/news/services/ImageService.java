package egg.news.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egg.news.models.Image;
import egg.news.repositories.IImageRepository;

@Service
public class ImageService {

    @Autowired
    private IImageRepository imageRepository;

    public Image saveImage(MultipartFile file) throws Exception{

        if(file != null){

            try {
                Image image = new Image();
    
                image.setName(file.getName());
                image.setMime(file.getContentType());
                image.setContent(file.getBytes());

                return imageRepository.save(image);
                
            } catch (Exception e) {
                
            }
        }      
        return null;  
    }

    public Image modifyImage(MultipartFile file, String idImage) throws Exception{

        if(file != null){  
                      
            try {                
                Optional<Image> responseImage = imageRepository.findById(idImage);
    
                if(responseImage.isPresent()){
    
                    Image image = responseImage.get();
    
                    image.setName(file.getName());
                    image.setMime(file.getContentType());
                    image.setContent(file.getBytes());
    
                    return imageRepository.save(image);
                }
            } catch (Exception e) {

            }
        }

        return null;
    }    
}
