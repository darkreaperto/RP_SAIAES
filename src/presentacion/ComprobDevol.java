/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author dark-reaper
 */
@Entity
@Table(name = "ComprobDevol", catalog = "sai_aes", schema = "")
@NamedQueries({
    @NamedQuery(name = "ComprobDevol.findAll", query = "SELECT c FROM ComprobDevol c")
    , @NamedQuery(name = "ComprobDevol.findByCodComprobDevol", query = "SELECT c FROM ComprobDevol c WHERE c.codComprobDevol = :codComprobDevol")
    , @NamedQuery(name = "ComprobDevol.findByFechaComprobDevol", query = "SELECT c FROM ComprobDevol c WHERE c.fechaComprobDevol = :fechaComprobDevol")
    , @NamedQuery(name = "ComprobDevol.findByCodLinPedSal", query = "SELECT c FROM ComprobDevol c WHERE c.codLinPedSal = :codLinPedSal")
    , @NamedQuery(name = "ComprobDevol.findByCodigoClientes", query = "SELECT c FROM ComprobDevol c WHERE c.codigoClientes = :codigoClientes")
    , @NamedQuery(name = "ComprobDevol.findByCodUsuarios", query = "SELECT c FROM ComprobDevol c WHERE c.codUsuarios = :codUsuarios")})
public class ComprobDevol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_ComprobDevol")
    private Long codComprobDevol;
    @Basic(optional = false)
    @Column(name = "fecha_ComprobDevol")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaComprobDevol;
    @Basic(optional = false)
    @Column(name = "cod_LinPedSal")
    private long codLinPedSal;
    @Basic(optional = false)
    @Column(name = "codigo_Clientes")
    private long codigoClientes;
    @Basic(optional = false)
    @Column(name = "cod_Usuarios")
    private long codUsuarios;

    public ComprobDevol() {
    }

    public ComprobDevol(Long codComprobDevol) {
        this.codComprobDevol = codComprobDevol;
    }

    public ComprobDevol(Long codComprobDevol, Date fechaComprobDevol, long codLinPedSal, long codigoClientes, long codUsuarios) {
        this.codComprobDevol = codComprobDevol;
        this.fechaComprobDevol = fechaComprobDevol;
        this.codLinPedSal = codLinPedSal;
        this.codigoClientes = codigoClientes;
        this.codUsuarios = codUsuarios;
    }

    public Long getCodComprobDevol() {
        return codComprobDevol;
    }

    public void setCodComprobDevol(Long codComprobDevol) {
        this.codComprobDevol = codComprobDevol;
    }

    public Date getFechaComprobDevol() {
        return fechaComprobDevol;
    }

    public void setFechaComprobDevol(Date fechaComprobDevol) {
        this.fechaComprobDevol = fechaComprobDevol;
    }

    public long getCodLinPedSal() {
        return codLinPedSal;
    }

    public void setCodLinPedSal(long codLinPedSal) {
        this.codLinPedSal = codLinPedSal;
    }

    public long getCodigoClientes() {
        return codigoClientes;
    }

    public void setCodigoClientes(long codigoClientes) {
        this.codigoClientes = codigoClientes;
    }

    public long getCodUsuarios() {
        return codUsuarios;
    }

    public void setCodUsuarios(long codUsuarios) {
        this.codUsuarios = codUsuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codComprobDevol != null ? codComprobDevol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprobDevol)) {
            return false;
        }
        ComprobDevol other = (ComprobDevol) object;
        if ((this.codComprobDevol == null && other.codComprobDevol != null) || (this.codComprobDevol != null && !this.codComprobDevol.equals(other.codComprobDevol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "presentacion.ComprobDevol[ codComprobDevol=" + codComprobDevol + " ]";
    }
    
}
