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

        // Botão para registrar ponto de entrada ou saída
        JButton btnRegistrar = new JButton("Registrar Ponto");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obter a data e hora atual
                LocalDateTime dateTimeAtual = LocalDateTime.now();
                String dataHora = dateTimeAtual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                // Obter o usuário para registro do ponto
                String matriculaUsuario = JOptionPane.showInputDialog(frame, "Digite a matrícula do usuário:");

                // Verificar se o usuário existe
                Usuario usuarioEncontrado = null;
                for (Usuario u : usuarios) {
                    if (u.getMatricula().equals(matriculaUsuario)) {
                        usuarioEncontrado = u;
                        break;
                    }
                }

                final Usuario usuario = usuarioEncontrado;

                if (usuario == null) {
                    JOptionPane.showMessageDialog(frame, "Usuário não encontrado.");
                    return;
                }

                // Verificar o tipo de ponto a ser registrado
                String tipoPonto = "";

                // Verificar se o usuário já possui registro de entrada
                boolean possuiEntrada = pontos.stream()
                        .anyMatch(ponto -> ponto.getUsuario().equals(usuario) && ponto.getTipo().equals("Entrada"));

                // Verificar se o usuário já possui registro de saída
                boolean possuiSaida = pontos.stream()
                        .anyMatch(ponto -> ponto.getUsuario().equals(usuario) && ponto.getTipo().equals("Saída"));

                // Determinar o tipo de ponto a ser registrado
                if (!possuiEntrada && !possuiSaida) {
                    tipoPonto = "Entrada";
                } else if (possuiEntrada && !possuiSaida) {
                    tipoPonto = "Saída";
                } else if (possuiEntrada && possuiSaida) {
                    JOptionPane.showMessageDialog(frame, "Usuário já registrou entrada e saída.");
                    return;
                }

                // Registrar o ponto
                Ponto ponto = new Ponto(tipoPonto, dataHora, usuario);
                pontos.add(ponto);
                salvarPonto(ponto);

                // Atualizar a exibição dos pontos registrados
                atualizarExibicaoPontos(painelPontos);
            }
        });

        // Adicionar os botões ao painel principal
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        painelBotoes.add(btnCadastrarUsuario);
        painelBotoes.add(btnDeletarUsuario);
        painelBotoes.add(btnRegistrar);

        // Adicionar os componentes ao frame
        frame.add(painelBotoes, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Exibir a janela
        frame.setVisible(true);
    }

    public void cadastrarUsuario(String nomeUsuario, String matriculaUsuario) {
        // Verificar se já existe um usuário com a mesma matrícula
        boolean usuarioExistente = usuarios.stream()
                .anyMatch(u -> u.getMatricula().equals(matriculaUsuario));

        if (usuarioExistente) {
            JOptionPane.showMessageDialog(null, "Já existe um usuário com a mesma matrícula.");
            return;
        }

        // Cadastrar o usuário caso não exista um usuário com a mesma matrícula
        Usuario usuario = new Usuario(nomeUsuario, matriculaUsuario);
        usuarios.add(usuario);
        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
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

    public void registrarPonto(String tipo, String dataHora, String matriculaUsuario) {
        Usuario usuarioEncontrado = null;
        for (Usuario u : usuarios) {
            if (u.getMatricula().equals(matriculaUsuario)) {
                usuarioEncontrado = u;
                break;
            }
        }

        final Usuario usuario = usuarioEncontrado;

        if (usuario == null) {
            JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
            return;
        }
        boolean possuiPrimeiraEntrada = pontos.isEmpty();
        boolean possuiSaidaAnterior = pontos.stream()
                .anyMatch(ponto -> ponto.getUsuario().equals(usuario) && ponto.getTipo().equals("Saída"));
        boolean possuiEntradaAnterior = pontos.stream()
                .anyMatch(ponto -> ponto.getUsuario().equals(usuario) && ponto.getTipo().equals("Entrada"));

        if (tipo.equals("Entrada")) {

            if (!possuiSaidaAnterior && !possuiPrimeiraEntrada) {
                JOptionPane.showMessageDialog(null,
                        "Não é possível registrar uma entrada sem ter uma saída anterior.");
                return;
            }
        } else if (tipo.equals("Saída")) {

            if (possuiSaidaAnterior) {
                JOptionPane.showMessageDialog(null,
                        "Não é possível registrar uma saída sem ter uma entrada anterior.");
                return;
            }
        }

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
