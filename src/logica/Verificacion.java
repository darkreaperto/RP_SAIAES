/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ahoihanabi
 */
public class Verificacion {

    private static Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z]).(?=\\S+$).{6,16}";

    public Verificacion() {
        
    }

    /**
     * This method validates the input email address with EMAIL_REGEX pattern
     *
     * @param email
     * @return boolean
     */
    public boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 
     * @param password
     * @return 
     */
    public boolean validatePassword(String password) {
        pattern = Pattern.compile(PASSWORD_REGEX);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
