public class Usuario {
    private String nome;
    private String matricula;
    private int paresCount;

    public Usuario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.paresCount = 0;
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

    public int getParesCount() {
        return this.paresCount;
    }

    public void increaseParesCount() {
        this.paresCount++;
    }
}