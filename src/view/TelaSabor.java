package view;

import exception.CampoInvalidoException;
import exception.NenhumaLinhaSelecionadaException;
import exception.SaborInvalidoException;
import model.Sabor;
import model.TipoPizza;
import repository.RepositoryMemoria;
import service.SaborService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TelaSabor extends JFrame {

    private SaborService saborService;

    private JTextField campoNomeSabor;
    private JTextField campoBuscaSabor;
    private JComboBox<TipoPizza> comboTipoPizza;

    private JButton botaoCadastrar;
    private JButton botaoAtualizar;
    private JButton botaoExcluir;
    private JButton botaoLimparCampos;
    private JButton botaoVoltar;

    private JTable tabelaSabores;
    private DefaultTableModel modeloTabela;

    private JLabel labelTotalSabores;

    public TelaSabor(SaborService saborService) {
        this.saborService = saborService;

        configurarJanela();
        inicializarComponentes();
        montarLayout();
        configurarEventos();
        carregarTabela();
    }
    private void configurarJanela() {
        setTitle("Cadastro de Sabores");
        setSize(900, 550);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        campoNomeSabor = new JTextField();
        campoBuscaSabor = new JTextField();

        comboTipoPizza = new JComboBox<>(TipoPizza.values());
        comboTipoPizza.setSelectedIndex(0);

        botaoCadastrar = new JButton("Cadastrar");
        botaoAtualizar = new JButton("Atualizar");
        botaoExcluir = new JButton("Excluir");
        botaoLimparCampos = new JButton("Limpar campos");
        botaoVoltar = new JButton("Voltar");

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Sabor", "Tipo"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaSabores = new JTable(modeloTabela);
        tabelaSabores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaSabores.getTableHeader().setReorderingAllowed(false);
        tabelaSabores.setRowHeight(24);
        tabelaSabores.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tabelaSabores.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabelaSabores.getColumnModel().getColumn(1).setPreferredWidth(300);
        tabelaSabores.getColumnModel().getColumn(2).setPreferredWidth(160);

        labelTotalSabores = new JLabel("Total de sabores: 0");
    }

    private void montarLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        painelPrincipal.add(criarPainelTopo(), BorderLayout.NORTH);
        painelPrincipal.add(criarPainelCentral(), BorderLayout.CENTER);
        painelPrincipal.add(criarPainelRodape(), BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private JPanel criarPainelTopo() {
        JPanel painelTopo = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Cadastro de Sabores");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitulo = new JLabel("Cadastre os sabores e relacione cada um ao tipo da pizza");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel painelTextos = new JPanel();
        painelTextos.setLayout(new BoxLayout(painelTextos, BoxLayout.Y_AXIS));
        painelTextos.add(titulo);
        painelTextos.add(Box.createVerticalStrut(6));
        painelTextos.add(subtitulo);

        painelTopo.add(painelTextos, BorderLayout.WEST);

        return painelTopo;
    }

    private JPanel criarPainelCentral() {
        JPanel painelCentral = new JPanel(new BorderLayout(15, 0));

        painelCentral.add(criarPainelFormulario(), BorderLayout.WEST);
        painelCentral.add(criarPainelTabela(), BorderLayout.CENTER);

        return painelCentral;
    }

    private JPanel criarPainelFormulario() {
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setPreferredSize(new Dimension(300, 0));

        JPanel painelDados = criarPainelDadosSabor();
        JPanel painelAcoes = criarPainelAcoes();

        painelDados.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        painelAcoes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

        painelFormulario.add(painelDados);
        painelFormulario.add(Box.createVerticalStrut(12));
        painelFormulario.add(painelAcoes);
        painelFormulario.add(Box.createVerticalGlue());

        return painelFormulario;
    }

    private JPanel criarPainelDadosSabor() {
        JPanel painel = new JPanel(new GridLayout(4, 1, 8, 8));
        painel.setBorder(BorderFactory.createTitledBorder("Dados do sabor"));

        JLabel labelNome = new JLabel("Nome do sabor:");
        JLabel labelTipo = new JLabel("Tipo da pizza:");

        painel.add(labelNome);
        painel.add(campoNomeSabor);
        painel.add(labelTipo);
        painel.add(comboTipoPizza);

        return painel;
    }

    private JPanel criarPainelAcoes() {
        JPanel painel = new JPanel(new GridLayout(4, 1, 0, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Ações"));

        painel.add(botaoCadastrar);
        painel.add(botaoAtualizar);
        painel.add(botaoExcluir);
        painel.add(botaoLimparCampos);

        return painel;
    }

    private JPanel criarPainelBusca() {
        JPanel painelBusca = new JPanel(new BorderLayout(8, 8));

        JLabel labelBusca = new JLabel("Buscar sabor:");

        painelBusca.add(labelBusca, BorderLayout.WEST);
        painelBusca.add(campoBuscaSabor, BorderLayout.CENTER);

        return painelBusca;
    }

    private JPanel criarPainelTabela() {
        JPanel painelTabela = new JPanel(new BorderLayout(10, 10));
        painelTabela.setBorder(BorderFactory.createTitledBorder("Sabores cadastrados"));

        JScrollPane scrollTabela = new JScrollPane(
                tabelaSabores,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(labelTotalSabores, BorderLayout.WEST);

        painelTabela.add(criarPainelBusca(), BorderLayout.NORTH);
        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelTabela.add(painelInferior, BorderLayout.SOUTH);

        return painelTabela;
    }

    private JPanel criarPainelRodape() {
        JPanel painelRodape = new JPanel(new BorderLayout());

        JLabel mensagem = new JLabel("Selecione um sabor na tabela para atualizar ou excluir.");

        JPanel painelBotaoVoltar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        painelBotaoVoltar.add(botaoVoltar);

        painelRodape.add(mensagem, BorderLayout.WEST);
        painelRodape.add(painelBotaoVoltar, BorderLayout.EAST);

        return painelRodape;
    }

    public void carregarTabela() {
        modeloTabela.setRowCount(0);

        java.util.List<Sabor> sabores = saborService.listarSabores();

        for (Sabor saborAtual : sabores) {
            modeloTabela.addRow(new Object[]{
                    saborAtual.getIdSabor(),
                    saborAtual.getNome(),
                    saborAtual.getTipoPizza()
            });
        }

        labelTotalSabores.setText("Total de sabores: " + sabores.size());

    }

    public void limparCampos() {
        campoNomeSabor.setText("");
        comboTipoPizza.setSelectedIndex(0);
        tabelaSabores.clearSelection();
    }

    public void configurarEventos() {

        //AÇÕES DOS BOTÕES ----------------------------------------

        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarSabor();
            }
        });

        botaoAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarSabor();
            }
        });

        botaoLimparCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirSabor();
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //aqui falta adicionar quando a tela principal estiver pronta
            }
        });

        //AÇÕES DOS CAMPOS -----------------------------------------------
        campoNomeSabor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarSabor();
            }
        });

        //AÇÕES DO MOUSE -----------------------------------------------

        tabelaSabores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                obterIdSelecionado();
            }
        });

        tabelaSabores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    obterCamposSelecionados();
                    obterIdSelecionado();
                }
            }
        });

        //ACOES DO TECLADO ------------------------------------------------
        campoBuscaSabor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarSaboresPorNome();
            }
        });

    }


    public void cadastrarSabor() {

        try {
            String nome = campoNomeSabor.getText();
            TipoPizza tipo = (TipoPizza) comboTipoPizza.getSelectedItem();

            saborService.cadastrarSabor(nome, tipo);

            limparCampos();
            carregarTabela();

            JOptionPane.showMessageDialog(this, "Sabor cadastrado com sucesso. ");
        } catch (CampoInvalidoException | SaborInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public int obterLinhaSelecionada() {
        int linhaSelecionada = tabelaSabores.getSelectedRow();

        if (linhaSelecionada == -1) {
            throw new NenhumaLinhaSelecionadaException("Nenhuma linha foi selecionada");
        }

        return linhaSelecionada;
    }

    public void obterCamposSelecionados() {
        int linhaSelecionada = obterLinhaSelecionada();

        String nome = modeloTabela.getValueAt(linhaSelecionada, 1).toString();

        String tipoTexto = modeloTabela.getValueAt(linhaSelecionada, 2).toString();
        TipoPizza tipo = TipoPizza.valueOf(tipoTexto);

        campoNomeSabor.setText(nome);
        comboTipoPizza.setSelectedItem(tipo);
    }

    public int obterIdSelecionado() {
        int linhaSelecionada = obterLinhaSelecionada();
        return Integer.parseInt(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
    }

    public void atualizarSabor() {

        try {

            int idSelecionado = obterIdSelecionado();

            String nomeNovo = campoNomeSabor.getText();
            TipoPizza tipo = (TipoPizza) comboTipoPizza.getSelectedItem();

            saborService.editarSabor(idSelecionado, nomeNovo, tipo);

            carregarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(this, "Sabor atualizado com sucesso. ");
        } catch (CampoInvalidoException | SaborInvalidoException | NenhumaLinhaSelecionadaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    public void excluirSabor() {
        try {
            int idSelecionado = obterIdSelecionado();

            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir esse sabor?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcao == JOptionPane.YES_OPTION) {
                saborService.excluirSabor(idSelecionado);

                carregarTabela();
                limparCampos();

                JOptionPane.showMessageDialog(this, "Sabor excluído com sucesso.");
            }

        } catch (CampoInvalidoException | SaborInvalidoException | NenhumaLinhaSelecionadaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void filtrarSaboresPorNome() {
        try {
            String nomeBuscado = campoBuscaSabor.getText();

            java.util.List<Sabor> saboresFiltrados = saborService.listarPorSabor(nomeBuscado);

            modeloTabela.setRowCount(0);

            for (Sabor saborAtual : saboresFiltrados) {
                modeloTabela.addRow(new Object[]{
                        saborAtual.getIdSabor(),
                        saborAtual.getNome(),
                        saborAtual.getTipoPizza()
                });
            }

            labelTotalSabores.setText("Total de sabores: " + saboresFiltrados.size());

        } catch (CampoInvalidoException | SaborInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RepositoryMemoria repositoryMemoria = new RepositoryMemoria();
            SaborService saborService = new SaborService(repositoryMemoria);

            TelaSabor tela = new TelaSabor(saborService);
            tela.setVisible(true);
        });
    }

}

