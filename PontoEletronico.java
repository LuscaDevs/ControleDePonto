import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PontoEletronico {
    private List<Ponto> pontos;
    private List<Usuario> usuarios;

    public PontoEletronico() {
        pontos = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void exibirInterface() {
        // Criar a janela principal
        JFrame frame = new JFrame("Controle de Ponto Eletrônico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Criar painel para exibição dos pontos registrados
        JPanel painelPontos = new JPanel();
        painelPontos.setLayout(new BoxLayout(painelPontos, BoxLayout.Y_AXIS));

        // Criar o painel de abas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Painel para exibição dos usuários cadastrados
        JPanel painelUsuarios = new JPanel();
        painelUsuarios.setLayout(new BoxLayout(painelUsuarios, BoxLayout.Y_AXIS));

        // Adicionar o painel de pontos à aba "Pontos"
        tabbedPane.addTab("Pontos", painelPontos);

        // Adicionar o painel de usuários à aba "Usuários cadastrados"
        tabbedPane.addTab("Usuários cadastrados", painelUsuarios);
        // Botão para cadastrar usuário
        JButton btnCadastrarUsuario = new JButton("Cadastrar Usuário");
        btnCadastrarUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomeUsuario = JOptionPane.showInputDialog(frame, "Digite o nome do usuário:");

                if (nomeUsuario == null || nomeUsuario.isBlank()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, informe o nome do usuário.");
                    return;
                }

                String matriculaUsuario = JOptionPane.showInputDialog(frame, "Digite a matrícula do usuário:");

                if (matriculaUsuario == null || matriculaUsuario.isBlank()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, informe a matrícula do usuário.");
                    return;
                }

                cadastrarUsuario(nomeUsuario, matriculaUsuario);
                JOptionPane.showMessageDialog(frame, "Usuário cadastrado com sucesso!");
                atualizarExibicaoUsuarios(painelUsuarios);
            }

        });

        // Botão para deletar usuário
        JButton btnDeletarUsuario = new JButton("Deletar usuário");
        btnDeletarUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuarios.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Não há usuários cadastrados.");
                    return;
                }

                Usuario usuarioSelecionado = obterUsuario();
                usuarios.remove(usuarioSelecionado);
                JOptionPane.showMessageDialog(frame, "Usuário deletado com sucesso!");
                atualizarExibicaoUsuarios(painelUsuarios);
            }
        });

        // Botão para registrar ponto de entrada
        JButton btnEntrada = new JButton("Registrar Entrada");
        btnEntrada.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuarios.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Não há usuários cadastrados. Por favor, cadastre um usuário primeiro.");
                    return;
                }

                // Obter a data e hora atual
                LocalDateTime dateTimeAtual = LocalDateTime.now();
                String dataHora = dateTimeAtual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                Usuario usuarioSelecionado = obterUsuario();
                if (usuarioSelecionado != null) {
                    registrarPonto("Entrada", dataHora, usuarioSelecionado);
                    JOptionPane.showMessageDialog(frame,
                            "Ponto de entrada registrado para o usuário: " + usuarioSelecionado.getNome());
                    atualizarExibicaoPontos(painelPontos);
                }
            }
        });

        // Botão para registrar ponto de saída
        JButton btnSaida = new JButton("Registrar Saída");
        btnSaida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuarios.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Não há usuários cadastrados. Por favor, cadastre um usuário primeiro.");
                    return;
                }

                // Obter a data e hora atual
                LocalDateTime dateTimeAtual = LocalDateTime.now();
                String dataHora = dateTimeAtual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                Usuario usuarioSelecionado = obterUsuario();
                if (usuarioSelecionado != null) {
                    registrarPonto("Saída", dataHora, usuarioSelecionado);
                    JOptionPane.showMessageDialog(frame,
                            "Ponto de saída registrado para o usuário: " + usuarioSelecionado.getNome());
                    atualizarExibicaoPontos(painelPontos);
                }
            }
        });

        // Adicionar os botões ao painel principal
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        painelBotoes.add(btnCadastrarUsuario);
        painelBotoes.add(btnDeletarUsuario);
        painelBotoes.add(btnEntrada);
        painelBotoes.add(btnSaida);

        // Adicionar os componentes ao frame
        frame.add(painelBotoes, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Exibir a janela
        frame.setVisible(true);
    }

    public void cadastrarUsuario(String nomeUsuario, String matriculaUsuario) {
        Usuario usuario = new Usuario(nomeUsuario, matriculaUsuario);
        usuarios.add(usuario);
    }

    public void deletarUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }

    private void atualizarExibicaoUsuarios(JPanel painelUsuarios) {
        painelUsuarios.removeAll();
        for (Usuario usuario : usuarios) {
            painelUsuarios.add(new JLabel(usuario.toString()));
        }
        painelUsuarios.revalidate();
        painelUsuarios.repaint();
    }

    private Usuario obterUsuario() {
        Object[] usuariosArray = usuarios.toArray();
        if (usuariosArray.length > 0) {
            Usuario usuarioSelecionado = (Usuario) JOptionPane.showInputDialog(null, "Selecione o usuário:",
                    "Selecionar Usuário", JOptionPane.QUESTION_MESSAGE, null, usuariosArray, usuariosArray[0]);
            return usuarioSelecionado;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Não há usuários cadastrados. Por favor, cadastre um usuário primeiro.");
            return null;
        }
    }

    public void registrarPonto(String tipo, String dataHora, Usuario usuario) {
        Ponto ponto = new Ponto(tipo, dataHora, usuario);
        pontos.add(ponto);
        salvarPonto(ponto);
    }

    public void atualizarExibicaoPontos(JPanel painelPontos) {
        painelPontos.removeAll();
        List<Ponto> pontos = lerPontos();
        for (Ponto ponto : pontos) {
            painelPontos.add(new JLabel(ponto.getTipo() + " - " + ponto.getDataHora() + " - "
                    + ponto.getUsuario().getNome() + " (" + ponto.getUsuario().getMatricula() + ")"));
        }
        painelPontos.revalidate();
        painelPontos.repaint();
    }

    public List<Ponto> lerPontos() {
        // Código para ler os pontos de um arquivo de texto ou de outra fonte de dados
        // Retorna a lista de pontos lidos
        // Neste exemplo, estamos retornando apenas os pontos em memória
        return pontos;
    }

    public void salvarPonto(Ponto ponto) {
        try {
            FileWriter fileWriter = new FileWriter("pontos.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(ponto.getTipo() + "," + ponto.getDataHora() + "," + ponto.getUsuario().getNome() + ","
                    + ponto.getUsuario().getMatricula());

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
