package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class GalleriaArtistiApplication {

public static void main(String[] args) {
SpringApplication.run(GalleriaArtistiApplication.class, args);

/*avviare e copiare la password criptata che viene stampata sulla console

poi va messa sul import.sql

poi rimettere il main come prima */

}
}