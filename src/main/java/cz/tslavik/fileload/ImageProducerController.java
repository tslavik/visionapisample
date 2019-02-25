package cz.tslavik.fileload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ImageProducerController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "load")
    public void loadFiles(@RequestHeader("url") String url,@RequestHeader("Ocp-Apim-Subscription-Key") String key) {
        System.out.println(url);
        try {
            HttpHeaders headers = new HttpHeaders();
                       
            Files.list(Paths.get("./images")).forEach(image -> 
               {                
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                requestHeaders.set("Ocp-Apim-Subscription-Key",key);
                byte[] bytes;
                try {
                    bytes = Files.readAllBytes(image);
                    System.out.println(Base64.decodeBase64(bytes));
                    HttpEntity<byte[]> requestEntity = new HttpEntity<byte[]>(bytes,requestHeaders);
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }           
            }
        );
                
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}