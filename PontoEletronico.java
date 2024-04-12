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
import java.util.Iterator;
import java.util.List;

public class PontoEletronico {
    private List<Ponto> pontos; // Lista de pontos registrados
    private List<Usuario> usuarios; // Lista de usuários cadastrados

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
                // Solicitar nome do usuário
                String nomeUsuario = JOptionPane.showInputDialog(frame, "Digite o nome do usuário:");

                if (nomeUsuario == null || nomeUsuario.isBlank()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, informe o nome do usuário.");
                    return;
                }

                // Solicitar matrícula do usuário
                String matriculaUsuario = JOptionPane.showInputDialog(frame, "Digite a matrícula do usuário:");

                if (matriculaUsuario == null || matriculaUsuario.isBlank()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, informe a matrícula do usuário.");
                    return;
                }

                // Cadastrar o usuário
                cadastrarUsuario(nomeUsuario, matriculaUsuario);
                atualizarExibicaoUsuarios(painelUsuarios);
            }

        });

        // Botão para deletar usuário
        JButton btnDeletarUsuario = new JButton("Deletar usuário");
        btnDeletarUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuarios.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Não há usuários cadastrados.");
                    return;
                }

                // Obter o usuário a ser deletado
                Usuario usuarioSelecionado = obterUsuario();

                // Remover os pontos que contêm o usuário a ser deletado
                pontos.removeIf(ponto -> ponto.getUsuario() == usuarioSelecionado);

                usuarios.remove(usuarioSelecionado);
                JOptionPane.showMessageDialog(frame, "Usuário deletado com sucesso!");
                atualizarExibicaoUsuarios(painelUsuarios);
                atualizarExibicaoPontos(painelPontos, pontos);
            }
        });

        // Botão para registrar ponto de entrada ou saída
        JButton btnRegistrar = new JButton("Registrar Ponto");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obter a data e hora atual
                LocalDateTime dateTimeAtual = LocalDateTime.now();
                String dataHora = dateTimeAtual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                // Obter a matrícula do usuário para registro do ponto
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
                } else if (possuiEntrada && possuiSaida && usuario.getParesCount() < 1) {
                    tipoPonto = "Entrada"; // Permite cadastrar mais um par de entrada
                    usuario.increaseParesCount(); // Incrementa o contador de pares
                } else if (usuario.getParesCount() == 1) {
                    tipoPonto = "Saída";
                    usuario.increaseParesCount(); // Incrementa o contador de pares
                } else {
                    JOptionPane.showMessageDialog(frame, "O usuário já registrou o máximo de entradas permitidas.");
                    return;
                }

                // Registrar o ponto
                Ponto ponto = new Ponto(tipoPonto, dataHora, usuario);
                pontos.add(ponto);
                salvarPonto(ponto);

                // Atualizar a exibição dos pontos registrados
                atualizarExibicaoPontos(painelPontos, pontos);

            }
        });

        // Botão para alterar usuário
        JButton btnAlterarUsuario = new JButton("Alterar Usuário");
        btnAlterarUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtém o usuário a ser alterado
                Usuario usuarioSelecionado = obterUsuario();

                if (usuarioSelecionado == null) {
                    return; // Nenhum usuário selecionado
                }

                // Solicita os novos dados do usuário
                String novoNomeUsuario = JOptionPane.showInputDialog(frame, "Digite o novo nome do usuário:",
                        usuarioSelecionado.getNome());
                String novaMatriculaUsuario = JOptionPane.showInputDialog(frame, "Digite a nova matrícula do usuário:",
                        usuarioSelecionado.getMatricula());

                if (novoNomeUsuario == null || novoNomeUsuario.isBlank() || novaMatriculaUsuario == null
                        || novaMatriculaUsuario.isBlank()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, informe o nome e a matrícula do usuário.");
                    return;
                }

                // Atualiza os dados do usuário
                usuarioSelecionado.setNome(novoNomeUsuario);
                usuarioSelecionado.setMatricula(novaMatriculaUsuario);

                // Atualiza a exibição dos usuários
                atualizarExibicaoUsuarios(painelUsuarios);
                JOptionPane.showMessageDialog(null,
                        "Usuário " + usuarioSelecionado.getNome() + " alterado com sucesso!");
            }
        });

        // Adiciona o botão "Alterar Usuário" ao painel de botões

        // Adicionar os botões ao painel principal
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        painelBotoes.add(btnCadastrarUsuario);
        painelBotoes.add(btnDeletarUsuario);
        painelBotoes.add(btnRegistrar);
        painelBotoes.add(btnAlterarUsuario);

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
        // Remover os pontos relacionados ao usuário
        Iterator<Ponto> iterator = pontos.iterator();
        while (iterator.hasNext()) {
            Ponto ponto = iterator.next();
            if (ponto.getUsuario() == usuario) {
                iterator.remove();
            }
        }

        // Remover o usuário
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

    public void atualizarExibicaoPontos(JPanel painelPontos, List<Ponto> pontos) {
        painelPontos.removeAll();
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

/*
 * O código apresenta uma classe chamada "PontoEletronico" que implementa uma
 * interface gráfica para controle de ponto eletrônico. Explicação passo a
 * passo:
 * 
 * 1. Importações: São importadas as classes necessárias para a interface
 * gráfica e manipulação de eventos.
 * 
 * 2. Definição da classe "PontoEletronico": A classe contém duas listas:
 * "pontos" e "usuarios" para armazenar os pontos registrados e os usuários
 * cadastrados, respectivamente.
 * 
 * 3. Método "exibirInterface": Este método cria a interface gráfica usando a
 * biblioteca Swing. Ele cria uma janela principal, um painel para exibir os
 * pontos registrados e um painel de abas com duas abas: "Pontos" e
 * "Usuários cadastrados".
 * 
 * 4. Botão "Cadastrar Usuário": Este botão abre uma caixa de diálogo para o
 * usuário inserir o nome e a matrícula do usuário a ser cadastrado. Em seguida,
 * o método "cadastrarUsuario" é chamado para realizar o cadastro.
 * 
 * 5. Botão "Deletar usuário": Este botão abre uma caixa de diálogo com uma
 * lista de usuários cadastrados. O usuário pode selecionar um usuário da lista
 * para deletar. O método "deletarUsuario" é chamado para realizar a remoção.
 * 
 * 6. Botão "Registrar Ponto": Este botão registra o ponto de entrada ou saída
 * para um usuário. Ele solicita a matrícula do usuário e verifica se o usuário
 * existe na lista de usuários. Em seguida, verifica o tipo de ponto a ser
 * registrado com base nos pontos já registrados para o usuário. Se o usuário
 * não tiver nenhum ponto registrado, ele registra uma entrada. Se o usuário já
 * tiver uma entrada registrada, ele registra uma saída. Se o usuário já tiver
 * registrado tanto uma entrada quanto uma saída, uma mensagem de erro é
 * exibida.
 * 
 * 7. Métodos auxiliares: Os métodos "cadastrarUsuario", "deletarUsuario",
 * "atualizarExibicaoUsuarios", "obterUsuario", "registrarPonto",
 * "atualizarExibicaoPontos", "lerPontos" e "salvarPonto" são métodos auxiliares
 * para realizar as operações correspondentes.
 * 
 * 8. Finalmente, a classe "PontoEletronico" é instanciada e o método
 * "exibirInterface" é chamado para iniciar a interface gráfica.
 */