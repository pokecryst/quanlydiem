package helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {
	
	public static String cryptPass(String pass) {
		 	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	        // Hashing a password
//	        String hashedPassword = encoder.encode(pass);
	        
	        return encoder.encode(pass);
	}

}
