package com.hjsolutions.facturacion.service.dto;
 
import java.util.List;

public class InformatioInvoice {
    
    private Enterprise enterprise;
    private Invoice invoice;
    private Client client;
    private Saldo pendiente;
    private Saldo consumo;
    private String piePagina;
    private String observacion;
    private Pago pago;
    private Pqr pqr;
    private Serial serial;

     
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Pqr getPqr() {
        return pqr;
    }

    public void setPqr(Pqr pqr) {
        this.pqr = pqr;
    }

    public Serial getSerial() {
        return serial;
    }

    public void setSerial(Serial serial) {
        this.serial = serial;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Saldo getPendiente() {
        return pendiente;
    }

    public void setPendiente(Saldo pendiente) {
        this.pendiente = pendiente;
    }

    public static class Serial{
        private String codigo;
        private String mostrar;
        public String getCodigo() {
            return codigo;
        }
        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }
        public String getMostrar() {
            return mostrar;
        }
        public void setMostrar(String mostrar) {
            this.mostrar = mostrar;
        }

        
    }

    public static class Pqr{
        private String telefono;
        private String direccion;
        private String email;
        public String getTelefono() {
            return telefono;
        }
        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
        public String getDireccion() {
            return direccion;
        }
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        
    }

    public static class Pago{
        private String fecha;
        private String punto;
        private String monto;
        public String getFecha() {
            return fecha;
        }
        public void setFecha(String fecha) {
            this.fecha = fecha;
        }
        public String getPunto() {
            return punto;
        }
        public void setPunto(String punto) {
            this.punto = punto;
        }
        public String getMonto() {
            return monto;
        }
        public void setMonto(String monto) {
            this.monto = monto;
        }

        
    }

    public static class Client{

        private String referencia;
        private String nombre;
        private String document;
        private String dirInstalacion;
        private String dirCorrespondencia;
        public String getReferencia() {
            return referencia;
        }
        public void setReferencia(String referencia) {
            this.referencia = referencia;
        }
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public String getDocument() {
            return document;
        }
        public void setDocument(String document) {
            this.document = document;
        }
        public String getDirInstalacion() {
            return dirInstalacion;
        }
        public void setDirInstalacion(String dirInstalacion) {
            this.dirInstalacion = dirInstalacion;
        }
        public String getDirCorrespondencia() {
            return dirCorrespondencia;
        }
        public void setDirCorrespondencia(String dirCorrespondencia) {
            this.dirCorrespondencia = dirCorrespondencia;
        }
        
    }

    public static class Saldo{
        private List<Items> saldo;
        private Float base;
        private Float impuesto;
        private Float total;



        public List<Items> getSaldo() {
            return saldo;
        }
        public void setSaldo(List<Items> saldo) {
            this.saldo = saldo;
        }
        public Float getBase() {
            return base;
        }
        public void setBase(Float base) {
            this.base = base;
        }
        public Float getImpuesto() {
            return impuesto;
        }
        public void setImpuesto(Float impuesto) {
            this.impuesto = impuesto;
        }
        public Float getTotal() {
            return total;
        }
        public void setTotal(Float total) {
            this.total = total;
        }
        
    }

    public static class Items{
        private String concepto;
        private String cantidad;
        private String vUnitario;
        private String subTotal;
        public String getConcepto() {
            return concepto;
        }
        public void setConcepto(String concepto) {
            this.concepto = concepto;
        }
        public String getCantidad() {
            return cantidad;
        }
        public void setCantidad(String cantidad) {
            this.cantidad = cantidad;
        }
        public String getvUnitario() {
            return vUnitario;
        }
        public void setvUnitario(String vUnitario) {
            this.vUnitario = vUnitario;
        }
        public String getSubTotal() {
            return subTotal;
        }
        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }
        
    }

    public static class Invoice{
        private String cufe;
        private String prefijo;
        private String consecutivo;
        private String fechaEmision;
        private String pagoOportuno;
        private String mesPagar;
        private String fechaSuspensión;
        private String planAquirido;

        

        public String getCufe() {
            return cufe;
        }
        public void setCufe(String cufe) {
            this.cufe = cufe;
        }
        public String getPrefijo() {
            return prefijo;
        }
        public void setPrefijo(String invoice) {
            this.prefijo = invoice;
        }
        public String getConsecutivo() {
            return consecutivo;
        }
        public void setConsecutivo(String consecutivo) {
            this.consecutivo = consecutivo;
        }
        public String getFechaEmision() {
            return fechaEmision;
        }
        public void setFechaEmision(String fechaEmision) {
            this.fechaEmision = fechaEmision;
        }
        public String getPagoOportuno() {
            return pagoOportuno;
        }
        public void setPagoOportuno(String pagoOportuno) {
            this.pagoOportuno = pagoOportuno;
        }
        public String getMesPagar() {
            return mesPagar;
        }
        public void setMesPagar(String mesPagar) {
            this.mesPagar = mesPagar;
        }
        public String getFechaSuspensión() {
            return fechaSuspensión;
        }
        public void setFechaSuspensión(String fechaSuspensión) {
            this.fechaSuspensión = fechaSuspensión;
        }
        public String getPlanAquirido() {
            return planAquirido;
        }
        public void setPlanAquirido(String planAquirido) {
            this.planAquirido = planAquirido;
        }
        
    }

    public static class Enterprise{
        private String name;        
        private String address;
        private String url;
        private String resolutions;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public String getResolutions() {
            return resolutions;
        }
        public void setResolutions(String resolutions) {
            this.resolutions = resolutions;
        }
    }

    public Saldo getConsumo() {
        return consumo;
    }

    public void setConsumo(Saldo consumo) {
        this.consumo = consumo;
    }

    public String getPiePagina() {
        return piePagina;
    }

    public void setPiePagina(String piePagina) {
        this.piePagina = piePagina;
    }

    
}
