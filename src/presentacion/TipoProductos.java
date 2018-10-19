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
@Table(name = "TipoProductos", catalog = "sai_aes", schema = "")
@NamedQueries({
    @NamedQuery(name = "TipoProductos.findAll", query = "SELECT t FROM TipoProductos t")
    , @NamedQuery(name = "TipoProductos.findByCodTipoProductos", query = "SELECT t FROM TipoProductos t WHERE t.codTipoProductos = :codTipoProductos")
    , @NamedQuery(name = "TipoProductos.findByDescTipoProductos", query = "SELECT t FROM TipoProductos t WHERE t.descTipoProductos = :descTipoProductos")
    , @NamedQuery(name = "TipoProductos.findByEstadoTipoProductos", query = "SELECT t FROM TipoProductos t WHERE t.estadoTipoProductos = :estadoTipoProductos")})
public class TipoProductos implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_TipoProductos")
    private Long codTipoProductos;
    @Basic(optional = false)
    @Column(name = "desc_TipoProductos")
    private String descTipoProductos;
    @Basic(optional = false)
    @Column(name = "estado_TipoProductos")
    private String estadoTipoProductos;

    public TipoProductos() {
    }

    public TipoProductos(Long codTipoProductos) {
        this.codTipoProductos = codTipoProductos;
    }

    public TipoProductos(Long codTipoProductos, String descTipoProductos, String estadoTipoProductos) {
        this.codTipoProductos = codTipoProductos;
        this.descTipoProductos = descTipoProductos;
        this.estadoTipoProductos = estadoTipoProductos;
    }

    public Long getCodTipoProductos() {
        return codTipoProductos;
    }

    public void setCodTipoProductos(Long codTipoProductos) {
        Long oldCodTipoProductos = this.codTipoProductos;
        this.codTipoProductos = codTipoProductos;
        changeSupport.firePropertyChange("codTipoProductos", oldCodTipoProductos, codTipoProductos);
    }

    public String getDescTipoProductos() {
        return descTipoProductos;
    }

    public void setDescTipoProductos(String descTipoProductos) {
        String oldDescTipoProductos = this.descTipoProductos;
        this.descTipoProductos = descTipoProductos;
        changeSupport.firePropertyChange("descTipoProductos", oldDescTipoProductos, descTipoProductos);
    }

    public String getEstadoTipoProductos() {
        return estadoTipoProductos;
    }

    public void setEstadoTipoProductos(String estadoTipoProductos) {
        String oldEstadoTipoProductos = this.estadoTipoProductos;
        this.estadoTipoProductos = estadoTipoProductos;
        changeSupport.firePropertyChange("estadoTipoProductos", oldEstadoTipoProductos, estadoTipoProductos);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTipoProductos != null ? codTipoProductos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProductos)) {
            return false;
        }
        TipoProductos other = (TipoProductos) object;
        if ((this.codTipoProductos == null && other.codTipoProductos != null) || (this.codTipoProductos != null && !this.codTipoProductos.equals(other.codTipoProductos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "presentacion.TipoProductos[ codTipoProductos=" + codTipoProductos + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
