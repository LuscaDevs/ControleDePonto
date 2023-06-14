public class Ponto {
    private String tipo;
    private String dataHora;
    private Usuario usuario;

    public Ponto(String tipo, String dataHora, Usuario usuario) {
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}