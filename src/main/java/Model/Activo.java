package Model;

public class Activo {
    private String codigo;
    private String activo;
    private int fabricacion;
    private double valor;
    private String categoria;

    public Activo(String codigo, String activo, String categoria, int fabricacion, double valor) {
        this.codigo = codigo;
        this.activo = activo;
        this.fabricacion = fabricacion;
        this.categoria = categoria;
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public int getFabricacion() {
        return fabricacion;
    }

    public void setFabricacion(int fabricacion) {
        this.fabricacion = fabricacion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Activo{" +
                "codigo='" + codigo + '\'' +
                ", activo='" + activo + '\'' +
                ", fabricacion=" + fabricacion +
                ", valor=" + valor +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
