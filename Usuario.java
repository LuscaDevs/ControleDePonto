public class Usuario {
    private String nome;
    private String matricula;
    private int pontosCount;

    public Usuario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return nome + " (" + matricula + ")";
    }

    public int getPontosCount() {
        return pontosCount;
    }

    public void setPontosCount(int pontosCount) {
        this.pontosCount = pontosCount;
    }
}