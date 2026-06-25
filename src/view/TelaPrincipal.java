package view;

import repository.RepositoryMemoria;
import service.ClienteService;
import service.PedidoService;
import service.PrecoService;
import service.SaborService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    private RepositoryMemoria repositoryMemoria;

    private ClienteService clienteService;
    private SaborService saborService;
    private PrecoService precoService;
    private PedidoService pedidoService;

    private JButton botaoPedidos;
    private JButton botaoClientes;
    private JButton botaoSabores;
    private JButton botaoPrecos;
    private JButton botaoStatusPedidos;
    private JButton botaoSair;

    public TelaPrincipal() {
        inicializarServices();
        configurarJanela();
        inicializarComponentes();
        montarLayout();
        configurarEventos();
    }

    private void inicializarServices() {
        repositoryMemoria = new RepositoryMemoria();

        clienteService = new ClienteService(repositoryMemoria);
        saborService = new SaborService(repositoryMemoria);
        precoService = new PrecoService(repositoryMemoria);
        pedidoService = new PedidoService(repositoryMemoria);
    }

    private void configurarJanela() {
        setTitle("Sistema de Pedidos de Pizza");
        setSize(420, 420);
        setMinimumSize(new Dimension(420, 420));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void inicializarComponentes() {
        botaoPedidos = new JButton("Pedidos");
        botaoClientes = new JButton("Clientes");
        botaoSabores = new JButton("Sabores");
        botaoPrecos = new JButton("Preços");
        botaoStatusPedidos = new JButton("Status dos Pedidos");
        botaoSair = new JButton("Sair");
    }

    private void montarLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Sistema de Pedidos de Pizza");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(6, 1, 0, 10));
        painelBotoes.setPreferredSize(new Dimension(320, 300));

        painelBotoes.add(botaoPedidos);
        painelBotoes.add(botaoClientes);
        painelBotoes.add(botaoSabores);
        painelBotoes.add(botaoPrecos);
        painelBotoes.add(botaoStatusPedidos);
        painelBotoes.add(botaoSair);

        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.add(painelBotoes);

        painelPrincipal.add(titulo, BorderLayout.NORTH);
        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        setContentPane(painelPrincipal);
    }

    private void configurarEventos() {
        botaoPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaPedidos();
            }
        });

        botaoClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaClientes();
            }
        });

        botaoSabores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaSabores();
            }
        });

        botaoPrecos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaPrecos();
            }
        });

//        botaoStatusPedidos.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                abrirTelaStatusPedidos();
//            }
//        });

        botaoSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sair();
            }
        });
    }

    private void abrirTelaPedidos() {
        TelaPedido telaPedido = new TelaPedido(clienteService, saborService, pedidoService);
        telaPedido.setVisible(true);
    }

    private void abrirTelaClientes() {
        TelaCliente telaCliente = new TelaCliente(clienteService);
        telaCliente.setVisible(true);
    }

    private void abrirTelaSabores() {
        TelaSabor telaSabor = new TelaSabor(saborService);
        telaSabor.setVisible(true);
    }

    private void abrirTelaPrecos() {
        TelaPreco telaPreco = new TelaPreco(precoService);
        telaPreco.setVisible(true);
    }

//    private void abrirTelaStatusPedidos() {
//        TelaStatusPedido telaStatusPedido = new TelaStatusPedido(pedidoService);
//        telaStatusPedido.setVisible(true);
//    }

    private void sair() {
        int opcao = JOptionPane.showConfirmDialog(
                this,
                "Deseja sair do sistema?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (opcao == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TelaPrincipal telaPrincipal = new TelaPrincipal();
                telaPrincipal.setVisible(true);
            }
        });
    }
}