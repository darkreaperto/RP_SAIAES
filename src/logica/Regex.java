/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Verifica algunas entradas del usuario por medio de expreciones regulares
 *
 * @author ahoihanabi
 */
public class Regex {

    private static Pattern patron;
    private Matcher matcher;

    private static final String EMAIL_REGEX
            = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String PASSWORD_REGEX
            = "(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z]).(?=\\S+$).{6,16}";
    private static final String USER_REGEX
            = "^[A-Za-z0-9_.][^\\s]{6,16}$";

    public Regex() {

    }

    /**
     * Valida una dirección de correo de entrada con EMAIL_REGEX.
     *
     * @param email
     * @return true si el correo es válido
     */
    public boolean validaEmail(String email) {
        patron = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = patron.matcher(email);
        return matcher.matches();
    }

    /**
     * Valida una contraseña de entrada con PASSWORD_REGEX.
     *
     * @param clave
     * @return true si la contraseña es válida
     */
    public boolean validaClave(String clave) {
        patron = Pattern.compile(PASSWORD_REGEX);
        matcher = patron.matcher(clave);
        return matcher.matches();
    }

    /**
     * Valida un nombre de usuario con USER_REGEX.
     *
     * @param nombreUsuario
     * @return true si el nombre de usuario válido
     */
    public boolean validaNombreUsuario(String nombreUsuario) {
        patron = Pattern.compile(USER_REGEX);
        matcher = patron.matcher(nombreUsuario);
        return matcher.matches();
    }
}
