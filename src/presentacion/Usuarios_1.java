/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ahoihanabi
 */
@Entity
@Table(name = "Usuarios", catalog = "sai_aes", schema = "")
@NamedQueries({
    @NamedQuery(name = "Usuarios_1.findAll", query = "SELECT u FROM Usuarios_1 u")
    , @NamedQuery(name = "Usuarios_1.findByCodUsuarios", query = "SELECT u FROM Usuarios_1 u WHERE u.codUsuarios = :codUsuarios")
    , @NamedQuery(name = "Usuarios_1.findByNombreUsuarios", query = "SELECT u FROM Usuarios_1 u WHERE u.nombreUsuarios = :nombreUsuarios")
    , @NamedQuery(name = "Usuarios_1.findByClaveUsuarios", query = "SELECT u FROM Usuarios_1 u WHERE u.claveUsuarios = :claveUsuarios")
    , @NamedQuery(name = "Usuarios_1.findByCodRolUsuar", query = "SELECT u FROM Usuarios_1 u WHERE u.codRolUsuar = :codRolUsuar")})
public class Usuarios_1 implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_Usuarios")
    private Long codUsuarios;
    @Basic(optional = false)
    @Column(name = "nombre_Usuarios")
    private String nombreUsuarios;
    @Basic(optional = false)
    @Column(name = "clave_Usuarios")
    private String claveUsuarios;
    @Basic(optional = false)
    @Column(name = "cod_RolUsuar")
    private long codRolUsuar;

    public Usuarios_1() {
    }

    public Usuarios_1(Long codUsuarios) {
        this.codUsuarios = codUsuarios;
    }

    public Usuarios_1(Long codUsuarios, String nombreUsuarios, String claveUsuarios, long codRolUsuar) {
        this.codUsuarios = codUsuarios;
        this.nombreUsuarios = nombreUsuarios;
        this.claveUsuarios = claveUsuarios;
        this.codRolUsuar = codRolUsuar;
    }

    public Long getCodUsuarios() {
        return codUsuarios;
    }

    public void setCodUsuarios(Long codUsuarios) {
        Long oldCodUsuarios = this.codUsuarios;
        this.codUsuarios = codUsuarios;
        changeSupport.firePropertyChange("codUsuarios", oldCodUsuarios, codUsuarios);
    }

    public String getNombreUsuarios() {
        return nombreUsuarios;
    }

    public void setNombreUsuarios(String nombreUsuarios) {
        String oldNombreUsuarios = this.nombreUsuarios;
        this.nombreUsuarios = nombreUsuarios;
        changeSupport.firePropertyChange("nombreUsuarios", oldNombreUsuarios, nombreUsuarios);
    }

    public String getClaveUsuarios() {
        return claveUsuarios;
    }

    public void setClaveUsuarios(String claveUsuarios) {
        String oldClaveUsuarios = this.claveUsuarios;
        this.claveUsuarios = claveUsuarios;
        changeSupport.firePropertyChange("claveUsuarios", oldClaveUsuarios, claveUsuarios);
    }

    public long getCodRolUsuar() {
        return codRolUsuar;
    }

    public void setCodRolUsuar(long codRolUsuar) {
        long oldCodRolUsuar = this.codRolUsuar;
        this.codRolUsuar = codRolUsuar;
        changeSupport.firePropertyChange("codRolUsuar", oldCodRolUsuar, codRolUsuar);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUsuarios != null ? codUsuarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios_1)) {
            return false;
        }
        Usuarios_1 other = (Usuarios_1) object;
        if ((this.codUsuarios == null && other.codUsuarios != null) || (this.codUsuarios != null && !this.codUsuarios.equals(other.codUsuarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "presentacion.Usuarios_1[ codUsuarios=" + codUsuarios + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
