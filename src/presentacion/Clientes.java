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
 * @author dark-reaper
 */
@Entity
@Table(name = "Clientes", catalog = "sai_aes", schema = "")
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c")
    , @NamedQuery(name = "Clientes.findByCodClientes", query = "SELECT c FROM Clientes c WHERE c.codClientes = :codClientes")
    , @NamedQuery(name = "Clientes.findByCodigoPersonas", query = "SELECT c FROM Clientes c WHERE c.codigoPersonas = :codigoPersonas")})
public class Clientes implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_Clientes")
    private Long codClientes;
    @Basic(optional = false)
    @Column(name = "codigo_Personas")
    private long codigoPersonas;

    public Clientes() {
    }

    public Clientes(Long codClientes) {
        this.codClientes = codClientes;
    }

    public Clientes(Long codClientes, long codigoPersonas) {
        this.codClientes = codClientes;
        this.codigoPersonas = codigoPersonas;
    }

    public Long getCodClientes() {
        return codClientes;
    }

    public void setCodClientes(Long codClientes) {
        Long oldCodClientes = this.codClientes;
        this.codClientes = codClientes;
        changeSupport.firePropertyChange("codClientes", oldCodClientes, codClientes);
    }

    public long getCodigoPersonas() {
        return codigoPersonas;
    }

    public void setCodigoPersonas(long codigoPersonas) {
        long oldCodigoPersonas = this.codigoPersonas;
        this.codigoPersonas = codigoPersonas;
        changeSupport.firePropertyChange("codigoPersonas", oldCodigoPersonas, codigoPersonas);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codClientes != null ? codClientes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.codClientes == null && other.codClientes != null) || (this.codClientes != null && !this.codClientes.equals(other.codClientes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "presentacion.Clientes[ codClientes=" + codClientes + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
