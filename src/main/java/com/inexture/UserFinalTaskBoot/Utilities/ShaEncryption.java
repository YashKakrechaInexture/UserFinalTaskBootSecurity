package com.inexture.UserFinalTaskBoot.Utilities;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class has password Encryption method which returns encrypted password. It accepts string password and
 * returns SHA256 encrypted password.
 * @author Yash
 *
 */
public class ShaEncryption implements PasswordEncoder {

	private static final PasswordEncoder INSTANCE = new ShaEncryption();

	private ShaEncryption(){}

	static final Logger LOG = Logger.getLogger(ShaEncryption.class);
	
	/**
	 * This method converts not encrypted password to sha256 encrypted password.
	 * @param rawPassword - raw user password
	 * @return password - SHA256 encrypted password
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		LOG.debug("Inside Password Encryptor.");
		try {
			//returns encrypted password
			return toHexString(getSHA(rawPassword.toString()));
		}catch(NoSuchAlgorithmException e) {
			LOG.error("Something went wrong! Exception : {}",e);
			return rawPassword.toString();
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return this.encode(rawPassword.toString()).equals(encodedPassword);
	}

	public static PasswordEncoder getInstance() {
		return INSTANCE;
	}

	public static ShaEncryption getConstructor(){
		return new ShaEncryption();
	}
	
	/**
	 * This method converts string password to encrypted byte array in sha256 using message digest.
	 * @param input - string password
	 * @return byte[] - encrypted byte array of string password
	 * @throws NoSuchAlgorithmException - throws No Such Algorithm Exception
	 */
	private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
	
    /**
     * This method converts encrypted byte array to string password.
     * @param hash - byte array of password
     * @return password - encrypted password in string
     */
    private static String toHexString(byte[] hash)
    {  
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        
        while(hexString.length() < 32){  
            hexString.insert(0, '0');  
        }
        
        return hexString.toString();
    }  
}
