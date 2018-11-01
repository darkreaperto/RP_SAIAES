/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.servicios;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Verifica algunas entradas del usuario por medio de expresiones regulares
 *
 * @author ahoihanabi
 */
public class Regex {

    private static Pattern patron;
    private Matcher matcher;

    private static final String EMAIL_REGEX
            = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String PASSWORD_REGEX
            = "(?=.*[a-z])(?=.*[0-9]).(?=\\S+$).{6,16}"; //(?=.*[A-Z])Mayuscula
    private static final String USERNAME_REGEX
            = "^[A-Za-z0-9_.][^\\s]{4,16}$";
    private static final String NAMES_REGEX = "^[a-zA-Z ]*$";
    private static final String PHONE_REGEX = "^[0-9]{8,8}";
    private static final String PRICE_REGEX = "^\\d{1,10}(\\.\\d{1,2})?$";
    private static final String UNITQUANTITY_REGEX = "^[0-9]*$";//"^[0-9]*$";

    /**
     * Constructor de clase regex
     */
    public Regex() {

    }

    /**
     * Valida una dirección de correo de entrada con EMAIL_REGEX.
     *
     * @param email Email a agregar.
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
     * @param clave Clave a agregar.
     * @return True si la contraseña es válida.
     */
    public boolean validaClave(String clave) {
        patron = Pattern.compile(PASSWORD_REGEX);
        matcher = patron.matcher(clave);
        return matcher.matches();
    }

    /**
     * Valida un nombre de usuario con USERNAME_REGEX.
     *
     * @param nombreUsuario Nombre de usuario a agregar.
     * @return True si el nombre de usuario válido.
     */
    public boolean validaNombreUsuario(String nombreUsuario) {
        patron = Pattern.compile(USERNAME_REGEX);
        matcher = patron.matcher(nombreUsuario);
        return matcher.matches();
    }

    /**
     * Valida nombres.
     *
     * @param nombre Nombre o texto a ingresar.
     * @return True si el nombre es válido.
     */
    public boolean validaNombre(String nombre) {
        patron = Pattern.compile(NAMES_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = patron.matcher(nombre);
        return matcher.matches();
    }

    /**
     * Valida un telefono.
     *
     * @param telefono telefono a ingresar.
     * @return True si el telefono es válido.
     */
    public boolean validaTelefono(String telefono) {
        patron = Pattern.compile(PHONE_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = patron.matcher(telefono);
        return matcher.matches();
    }
    
    /**
     * Valida un precio.
     *
     * @param precio precio a ingresar
     * @return True si el precio es válido.
     */
    public boolean validaPrecio(String precio) {
        patron = Pattern.compile(PRICE_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = patron.matcher(precio);
        return matcher.matches();
    }
    
    /**
     * Valida cantidad de unidades (solo numeros).
     *
     * @param unidad unidad a ingresar
     * @return True si las unidades son válidas.
     */
    public boolean validaEnteros(String unidad) {
        patron = Pattern.compile(UNITQUANTITY_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = patron.matcher(unidad);
        return matcher.matches();
    }
}
