package view;

import exception.CampoInvalidoException;
import model.Cliente;
import model.EstadoPedido;
import model.Pedido;
import service.PedidoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaStatusPedido extends JFrame {

    private PedidoService pedidoService;

    private JTable tabelaPedidos;
    private DefaultTableModel modeloTabela;

    private JTextField campoBuscaSobrenome;

    private JComboBox<EstadoPedido> comboEstadoPedido;

    private JButton botaoAlterarEstado;
    private JButton botaoExcluirPedido;
    private JButton botaoVoltar;

    private JLabel labelPedidoSelecionado;
    private JLabel labelTotalPedidos;

    private Pedido pedidoSelecionado;

    public TelaStatusPedido(PedidoService pedidoService) {
        this.pedidoService = pedidoService;

        configurarJanela();
        inicializarComponentes();
        montarLayout();
        configurarEventos();
        carregarTabelaPedidos();
    }

    private void configurarJanela() {
        setTitle("Status dos Pedidos");
        setSize(1000, 600);
        setMinimumSize(new Dimension(900, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void inicializarComponentes() {
        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Cliente", "Telefone", "Estado", "Itens", "Total"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaPedidos = new JTable(modeloTabela);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPedidos.getTableHeader().setReorderingAllowed(false);
        tabelaPedidos.setRowHeight(24);
        tabelaPedidos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tabelaPedidos.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabelaPedidos.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabelaPedidos.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaPedidos.getColumnModel().getColumn(3).setPreferredWidth(130);
        tabelaPedidos.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabelaPedidos.getColumnModel().getColumn(5).setPreferredWidth(120);

        campoBuscaSobrenome = new JTextField();

        comboEstadoPedido = new JComboBox<>(EstadoPedido.values());

        botaoAlterarEstado = new JButton("Alterar estado");
        botaoExcluirPedido = new JButton("Excluir pedido");
        botaoVoltar = new JButton("Voltar");

        labelPedidoSelecionado = new JLabel("Pedido selecionado: nenhum");
        labelTotalPedidos = new JLabel("Total de pedidos: 0");
    }

    private void montarLayout() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelConteudo = new JPanel(new BorderLayout(15, 15));
        painelConteudo.setPreferredSize(new Dimension(1100, 620));
        painelConteudo.setBorder(new EmptyBorder(20, 20, 20, 20));

        painelConteudo.add(criarPainelTopo(), BorderLayout.NORTH);
        painelConteudo.add(criarPainelCentral(), BorderLayout.CENTER);
        painelConteudo.add(criarPainelRodape(), BorderLayout.SOUTH);

        painelPrincipal.add(painelConteudo);

        setContentPane(painelPrincipal);
    }

    private JPanel criarPainelTopo() {
        JPanel painelTopo = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Status dos Pedidos");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitulo = new JLabel("Visualize todos os pedidos e altere o estado de cada um");
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

        painelCentral.add(criarPainelTabela(), BorderLayout.CENTER);
        painelCentral.add(criarPainelAcoes(), BorderLayout.EAST);

        return painelCentral;
    }

    private JPanel criarPainelTabela() {
        JPanel painelTabela = new JPanel(new BorderLayout(10, 10));
        painelTabela.setBorder(BorderFactory.createTitledBorder("Pedidos cadastrados"));

        JScrollPane scrollTabela = new JScrollPane(
                tabelaPedidos,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(labelTotalPedidos, BorderLayout.WEST);

        painelTabela.add(criarPainelBusca(), BorderLayout.NORTH);
        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelTabela.add(painelInferior, BorderLayout.SOUTH);

        return painelTabela;
    }

    private JPanel criarPainelBusca() {
        JPanel painelBusca = new JPanel(new BorderLayout(8, 8));

        JLabel labelBusca = new JLabel("Buscar por sobrenome:");

        painelBusca.add(labelBusca, BorderLayout.WEST);
        painelBusca.add(campoBuscaSobrenome, BorderLayout.CENTER);

        return painelBusca;
    }

    private JPanel criarPainelAcoes() {
        JPanel painelAcoes = new JPanel(new BorderLayout());
        painelAcoes.setBorder(BorderFactory.createTitledBorder("Ações"));
        painelAcoes.setPreferredSize(new Dimension(230, 0));

        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(new BoxLayout(painelCampos, BoxLayout.Y_AXIS));

        JLabel labelEstado = new JLabel("Novo estado:");
        labelEstado.setAlignmentX(Component.LEFT_ALIGNMENT);

        comboEstadoPedido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        comboEstadoPedido.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelPedidoSelecionado.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 0, 10));
        painelBotoes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 145));

        painelBotoes.add(botaoAlterarEstado);
        painelBotoes.add(botaoExcluirPedido);
        painelBotoes.add(botaoVoltar);

        painelCampos.add(labelPedidoSelecionado);
        painelCampos.add(Box.createVerticalStrut(15));
        painelCampos.add(labelEstado);
        painelCampos.add(Box.createVerticalStrut(5));
        painelCampos.add(comboEstadoPedido);
        painelCampos.add(Box.createVerticalStrut(20));
        painelCampos.add(painelBotoes);
        painelCampos.add(Box.createVerticalGlue());

        painelAcoes.add(painelCampos, BorderLayout.NORTH);

        return painelAcoes;
    }

    private JPanel criarPainelRodape() {
        JPanel painelRodape = new JPanel(new BorderLayout());

        JLabel mensagem = new JLabel("Selecione um pedido na tabela para alterar o estado ou excluir.");

        painelRodape.add(mensagem, BorderLayout.WEST);

        return painelRodape;
    }

    private void configurarEventos() {
        tabelaPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarPedido();
            }
        });

        campoBuscaSobrenome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                carregarTabelaPedidos();
                limparSelecao();
            }
        });

        botaoAlterarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarEstadoPedido();
            }
        });

        botaoExcluirPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirPedido();
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void carregarTabelaPedidos() {
        try {
            modeloTabela.setRowCount(0);

            String sobrenomeBuscado = campoBuscaSobrenome.getText();

            List<Pedido> pedidos = pedidoService.listarPedidosPorSobrenomeCliente(sobrenomeBuscado);

            for (Pedido pedidoAtual : pedidos) {
                Cliente cliente = pedidoAtual.getCliente();

                modeloTabela.addRow(new Object[]{
                        pedidoAtual.getIdPedido(),
                        cliente.getNome() + " " + cliente.getSobrenome(),
                        cliente.getTelefone(),
                        pedidoAtual.getEstadoPedido(),
                        pedidoAtual.getItens().size(),
                        "R$ " + String.format("%.2f", pedidoService.calcularTotalPedido(pedidoAtual))
                });
            }

            labelTotalPedidos.setText("Total de pedidos: " + pedidos.size());

        } catch (CampoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao carregar pedidos.");
        }
    }

    private void selecionarPedido() {
        try {
            int linhaSelecionada = tabelaPedidos.getSelectedRow();

            if (linhaSelecionada == -1) {
                return;
            }

            int idPedido = Integer.parseInt(modeloTabela.getValueAt(linhaSelecionada, 0).toString());

            pedidoSelecionado = pedidoService.buscaPedidoPorId(idPedido);

            comboEstadoPedido.setSelectedItem(pedidoSelecionado.getEstadoPedido());

            labelPedidoSelecionado.setText("Pedido selecionado: #" + pedidoSelecionado.getIdPedido());

        } catch (CampoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao selecionar pedido.");
        }
    }

    private void alterarEstadoPedido() {
        try {
            validarPedidoSelecionado();

            EstadoPedido novoEstado = (EstadoPedido) comboEstadoPedido.getSelectedItem();

            pedidoService.alterarEstadoPedido(pedidoSelecionado, novoEstado);

            carregarTabelaPedidos();
            selecionarPedidoNaTabela(pedidoSelecionado.getIdPedido());

            JOptionPane.showMessageDialog(this, "Estado do pedido alterado com sucesso.");

        } catch (CampoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao alterar estado do pedido.");
        }
    }

    private void excluirPedido() {
        try {
            validarPedidoSelecionado();

            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir o pedido selecionado?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcao == JOptionPane.YES_OPTION) {
                pedidoService.excluirPedido(pedidoSelecionado.getIdPedido());

                pedidoSelecionado = null;

                carregarTabelaPedidos();
                limparSelecao();

                JOptionPane.showMessageDialog(this, "Pedido excluído com sucesso.");
            }

        } catch (CampoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao excluir pedido.");
        }
    }

    private void selecionarPedidoNaTabela(int idPedido) {
        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            int idAtual = Integer.parseInt(modeloTabela.getValueAt(i, 0).toString());

            if (idAtual == idPedido) {
                tabelaPedidos.setRowSelectionInterval(i, i);
                return;
            }
        }
    }

    private void limparSelecao() {
        pedidoSelecionado = null;
        tabelaPedidos.clearSelection();
        comboEstadoPedido.setSelectedIndex(0);
        labelPedidoSelecionado.setText("Pedido selecionado: nenhum");
    }

    private void validarPedidoSelecionado() {
        if (pedidoSelecionado == null) {
            throw new CampoInvalidoException("Selecione um pedido primeiro.");
        }
    }
}