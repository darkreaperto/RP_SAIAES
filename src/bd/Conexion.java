/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Realiza la conexión con una base de datos mysql y envía consultas,
 * procedimientos y funciones a la misma.
 * @author Aoihanabi
 */
public final class Conexion {
    
    private static Conexion instancia = new Conexion();
    private static Connection conexion;
    private Statement sentencia;
    private ResultSet resultado;
    private CallableStatement procedimiento;
    private static int cantModif;
    
    //STRING DE CONEXIÓN
    private String SERVER = "";
    private String PORT = "";
    private String BD = "";
    private String USER = "";
    private String PASS = "";
    private String TIMEZONE = "";
    private String SSL = "";
    private String PUBLICKEY = "";//allowPublicKeyRetrieval
    
    private ArrayList<String> optionalParams;
    //private static String pruebaPOptional;
    
    /**
     * Constructor de la clase conexión.
     */
    private Conexion() {
        
        setRequiredParams("localhost", "", "sai_aes", "usuario", "usuario2018", 
                "GMT-06:00", "false", "true");
        
        setOptionalParams(new ArrayList<>());
    }
    /**
     * Retorna la instancia única de la clase conexión.
     * @return instancia de la clase.
     */
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    /**
     * Abre la conexión para manipular una base de datos.
     * @return el estado de la conexión.
     */
    public boolean abrirConexion() {
        boolean exito = false;
        
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            if (pruebaConexion()) {
                conexion = DriverManager.getConnection(buildConnString(false), 
                    getUSER(), getPASS());
            } else {
                throw new SQLException();
            }
            
            exito = true;
            System.out.println("Conexion realizada");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            return exito;
        }
    }
    
    /**
     * 
     * @param server
     * @param port
     * @param bd
     * @param user
     * @param pass
     * @param timezone
     * @param ssl
     * @param publickey 
     */
    private void setRequiredParams(String server, String port, String bd, 
            String user, String pass, String timezone, String ssl, String publickey) {
        
        setSERVER(server);
        setPORT(port);
        setBD(bd);
        setUSER(user);
        setPASS(pass);
        setTIMEZONE(timezone);
        setSSL(ssl);
        setPUBLICKEY(publickey);
        
        System.out.println("from Conexion->setRequiredParams");
        System.out.println("server: " + getSERVER() +"\nbd: " + getBD() + 
                "\ntimezone: " + getTIMEZONE() + "\nssl:" + getSSL() +"\npublickey: " + getPUBLICKEY());
    }
    
    private String buildOptionalParams() {
        ArrayList<String> paramsOpt = getOptionalParams();
        
        //prmOptional = "";
        String pruebaPOptional = "";
        for(int i = 0; i<paramsOpt.size(); i++) {
            
            String oneParam = paramsOpt.get(i);
            pruebaPOptional += ("&"+oneParam);
            System.out.println(pruebaPOptional);
        }
        System.out.println("from Conexion->sendOptionalParams");            
        System.out.println(pruebaPOptional);
        return pruebaPOptional;
    }
    
    private String buildConnString(boolean prueba) {
        String stringCon = "";
        String pruebaCon = "";
    
        if(!prueba){
            if(getPORT().isEmpty()) {
                stringCon = "jdbc:mysql://" + getSERVER()+"/"
                        +getBD()+"?"+"useSSL="+getSSL()+"&serverTimezone="+getTIMEZONE()+"&allowPublicKeyRetrieval=" + getPUBLICKEY();
            } else {
                stringCon = "jdbc:mysql://" + getSERVER()+":"+getPORT()+"/"
                        +getBD()+"?"+"useSSL="+getSSL()+"&serverTimezone="+getTIMEZONE()+"&allowPublicKeyRetrieval=" + getPUBLICKEY();
            }
            stringCon += buildOptionalParams();
            System.out.println("from Conexion->setRequiredParams");
            System.out.println("stringCon: " + stringCon);
        } else {
            if(getPORT().isEmpty()) {
                pruebaCon = "jdbc:mysql://" + getSERVER()+"/"
                        +getBD()+"?"+"useSSL="+getSSL()+"&serverTimezone="+getTIMEZONE()+"&allowPublicKeyRetrieval=" + getPUBLICKEY();
            } else {
                pruebaCon = "jdbc:mysql://" + getSERVER()+":"+getPORT()+"/"
                        +getBD()+"?"+"useSSL="+getSSL()+"&serverTimezone="+getTIMEZONE()+"&allowPublicKeyRetrieval=" + getPUBLICKEY();
            }
            pruebaCon += buildOptionalParams(); 
            System.out.println("from Conexion->setRequiredParams");
            System.out.println("pruebaCon: " + pruebaCon);
        }
        
        return prueba ? pruebaCon : stringCon;
    }
    
    private boolean pruebaConexion() {
        
        boolean exito = false;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            try (Connection conPrueba = DriverManager.getConnection(buildConnString(true), 
                    getUSER(), getPASS())) {
                exito = true;
            }
            System.out.println("Prueba: Conexion realizada");
        
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            return exito;
        }
    }
    
     /**
     * Cierra la conexión con la base de datos, no permite manipularla.
     * @return el estado de la desconexión.
     */
    public boolean cerrarConexion() {
        boolean desconexionExitosa = false;
        try {
            conexion.close();
            desconexionExitosa = true;
        } catch (SQLException ex) {
            desconexionExitosa = false;
            System.err.println(ex);
        } finally {
            return desconexionExitosa;
        }
    }
    
    /**
     * Ejecuta en la base de datos solo consultas SELECT que recibe por parámetro,
     * retorna un resultset con los datos de la base que corresponden.
     * @param consulta consulta SQL
     * @return resultado de la consulta SQL.
     * @throws SQLException excepción SQL
     */
    public ResultSet ejecutarConsulta(String consulta) throws SQLException {

        sentencia = conexion.createStatement();
        resultado = sentencia.executeQuery(consulta);
//        
//        if(resultado == null) {
//            setCantActualizados(sentencia.getUpdateCount());
//        }
        return resultado;
    }
    
    /**
     * Ejecuta una modificación en la base de datos (insert, update, delete),
     * Este metodo retorna un valor entero que representa la cantidad de filas
     * que afectadas, acepta consultas que NO sean SELECT.
     * @param consulta consulta SQL
     * @return resultado de la modificacion.
     * @throws SQLException Excepción SQL
     */
    public boolean ejecutarActualizar(String consulta) throws SQLException {

        boolean actualizacionExitosa = false;
        try {
            sentencia = conexion.createStatement();
            actualizacionExitosa = sentencia.executeUpdate(consulta) >= 0;
//            if(resultado.next() == false) {
//                setCantActualizados(sentencia.getUpdateCount());
//            }
        } catch (SQLException ex) {
            actualizacionExitosa = false;
            throw ex;
        } finally {
            return actualizacionExitosa;
        }
    }
    
    /**
     * Ejecuta un procedimiento almacenado sin parámetros de entrada 
     * en la base de datos.
     * @param procedure procedimiento a ejecutar
     * @return resultado del procedimiento.
     * @throws java.sql.SQLException Excepción SQL
     */
    public ResultSet ejecutarProcedimiento(String procedure) 
            throws SQLException {
        
        procedimiento = conexion.prepareCall("{ CALL " + procedure + " }");
        resultado = procedimiento.executeQuery();
//        if(resultado != null) {
//            cantModif = procedimiento.getUpdateCount();
//            setCantActualizados(cantModif);
//            System.out.println(getRegUpdated());
//        }                
        return resultado; 
    }
    
    /**
     * Ejecuta un procedimiento almacenado con parámetros de entrada 
     * en la base de datos.
     * @param procedure Procedimiento a ejecutar
     * @param params Parametros del procedimiento
     * @return resultado del procedimiento.
     * @throws java.sql.SQLException Excepción SQL
     */
    public ResultSet ejecutarProcedimiento(String procedure,
            ArrayList<Object> params) throws SQLException {
        
        procedimiento = conexion.prepareCall("{ CALL "
                + procedure + " }");
        //Recorrer la lista de parametros que se enviarán al 
        //procedimiento almacenado
        for (int i = 0; i < params.size(); i++) {
            //Si el parametro es entero
            if (params.get(i) instanceof Integer){
                int temp = (int) params.get(i);
                procedimiento.setInt(i + 1, temp);
            } else if (params.get(i) instanceof Float) {
                float temp =  (float) params.get(i);
                procedimiento.setFloat(i + 1, temp);
            } else if (params.get(i) instanceof Double) {
                double temp =  (double) params.get(i);
                procedimiento.setDouble(i + 1, temp);
            } else if (params.get(i) instanceof String) {
                String temp = (String) params.get(i);
                procedimiento.setString(i + 1, temp);
            } else if (params.get(i) instanceof Date) {
                Date temp = (Date) params.get(i);
                procedimiento.setDate(i + 1, temp);
            } else if (params.get(i) instanceof Timestamp) {
                Timestamp temp = (Timestamp) params.get(i);
                procedimiento.setTimestamp(i + 1, temp);
            } else if (params.get(i) instanceof Boolean) {
                boolean temp =  (boolean) params.get(i);
                procedimiento.setBoolean(i + 1, temp);
            } else if (params.get(i) == null) {
                int temp = Integer.valueOf(params.get(i).toString());
                procedimiento.setNull(i + 1, temp);
            } else if (params.get(i) instanceof Types) {
                int temp = Integer.valueOf(params.get(i).toString());
                procedimiento.registerOutParameter(i + 1, temp);
            }
        }
        resultado = procedimiento.executeQuery();
        
        System.out.println("from Conexion class: RESULTADO? " + resultado);
        return resultado;
    }
    
    /**
     * Obtiene desde la base los valores/datos de un parámetro y lo convierte 
     * al tipo de dato Java correspondiente
     * @param dato objeto obtenido de la base de datos.
     * @param res ResultSet sql obtenido de la bd.
     * @param colName Nombre de la columna bd de la que se saca el valor
     * @throws SQLException Excepción sql
     */
    public void getParamValue(Object dato, ResultSet res, String colName) 
            throws SQLException{
        //Si el parametro es entero
        if (dato instanceof Integer){
            int temp = (int) res.getInt(colName);            
        } else if (dato instanceof Float) {
            float temp =  (float) res.getFloat(colName);
        } else if (dato instanceof Double) {
            double temp =  (double) res.getDouble(colName);
        } else if (dato instanceof String) {
            String temp = (String) res.getString(colName);
        } else if (dato instanceof Date) {
            Date temp = (Date) res.getDate(colName);
        } else if (dato instanceof Timestamp) {
            Timestamp temp = (Timestamp) res.getTimestamp(colName);
        } else if (dato instanceof Boolean) {
            boolean temp =  (boolean) res.getBoolean(colName);
        } else if (dato == null) {
            int temp = Integer.valueOf(res.toString());
        } else if (dato instanceof Types) {
            int temp = Integer.valueOf(res.toString());
        }
    }
    
    
    /**
     * Establecer la cantidad de registros modificados en la bd tras la 
     * ejecución de la consulta.
     * @param cantidad de registros actualizados
     */
    public void setCantActualizados(int cantidad) {
        this.cantModif = cantidad;
    }
    public int getRegUpdated() {
        return cantModif;
    }

    public String getSERVER() {
        return this.SERVER;
    }

    private void setSERVER(String SERVER) {
        this.SERVER = SERVER;
    }

    public String getPORT() {
        return PORT;
    }

    private void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getBD() {
        return BD;
    }

    private void setBD(String BD) {
        this.BD = BD;
    }

    public String getUSER() {
        return USER;
    }

    private void setUSER(String USER) {
        this.USER = USER;
    }

    public String getPASS() {
        return PASS;
    }

    private void setPASS(String PASS) {
        this.PASS = PASS;
    }

    public String getTIMEZONE() {
        return TIMEZONE;
    }

    private void setTIMEZONE(String TIMEZONE) {
        this.TIMEZONE = TIMEZONE;
    }

    public String getSSL() {
        return this.SSL;
    }

    private void setSSL(String SSL) {
        this.SSL = SSL;
    }
    
    public String getPUBLICKEY() {
        return PUBLICKEY;
    }

    private void setPUBLICKEY(String PUBLICKEY) {
        this.PUBLICKEY = PUBLICKEY;
    }
    
    private void setOptionalParams(ArrayList<String> optionalParams) {
        this.optionalParams = optionalParams;
    }
    
    private ArrayList<String> getOptionalParams() {
        return this.optionalParams;
    }
}
