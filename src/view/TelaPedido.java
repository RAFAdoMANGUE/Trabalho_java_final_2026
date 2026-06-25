package view;

import exception.CampoInvalidoException;
import exception.ClienteNaoEncontradoException;
import exception.SaborInvalidoException;
import model.*;
import repository.RepositoryMemoria;
import service.ClienteService;
import service.PedidoService;
import service.SaborService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TelaPedido extends JFrame {

    private ClienteService clienteService;
    private SaborService saborService;
    private PedidoService pedidoService;

    private Cliente clienteSelecionado;
    private Pedido pedidoSelecionado;
    private List<Cliente> clientesEncontrados = new ArrayList<>();

    private JTextField campoTelefoneCliente;
    private JTextField campoValorMedida;
    private JTextField campoQuantidade;

    private JLabel labelClienteNome;
    private JLabel labelClienteTelefone;
    private JLabel labelPedidoAtual;
    private JLabel labelAjudaMedida;
    private JLabel labelResultadoCalculo;
    private JLabel labelTotalPedido;

    private JComboBox<String> comboForma;
    private JComboBox<String> comboModoEntrada;
    private JComboBox<Object> comboSabor1;
    private JComboBox<Object> comboSabor2;

    private JButton botaoBuscarCliente;
    private JButton botaoNovoPedido;
    private JButton botaoCalcularMedida;
    private JButton botaoAdicionarPizza;
    private JButton botaoAtualizarItem;
    private JButton botaoRemoverItem;
    private JButton botaoCancelarPedido;
    private JButton botaoLimparCampos;
    private JButton botaoFinalizarPedido;
    private JButton botaoVoltar;

    private JTable tabelaClientes;
    private JTable tabelaPedidos;
    private JTable tabelaItens;

    private DefaultTableModel modeloClientes;
    private DefaultTableModel modeloPedidos;
    private DefaultTableModel modeloItens;

    public TelaPedido(ClienteService clienteService, SaborService saborService, PedidoService pedidoService) {
        this.clienteService = clienteService;
        this.saborService = saborService;
        this.pedidoService = pedidoService;

        configurarJanela();
        inicializarComponentes();
        montarLayout();
        configurarEventos();
        carregarSabores();
        carregarTodosClientes();
        atualizarTextoAjuda();
    }

    private void configurarJanela() {
        setTitle("Cadastro de Pedido");
        setSize(1250, 760);
        setMinimumSize(new Dimension(1100, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void inicializarComponentes() {
        campoTelefoneCliente = new JTextField();
        campoValorMedida = new JTextField();
        campoQuantidade = new JTextField("1");

        labelClienteNome = new JLabel("Cliente selecionado: nenhum");
        labelClienteTelefone = new JLabel("Telefone: -");
        labelPedidoAtual = new JLabel("Pedido atual: nenhum pedido selecionado");
        labelAjudaMedida = new JLabel("Informe o lado/raio em cm ou a área em cm².");
        labelResultadoCalculo = new JLabel(formatarResultado("Resultado: -"));
        labelTotalPedido = new JLabel("Total do pedido: R$ 0.00");
        labelTotalPedido.setFont(new Font("Arial", Font.BOLD, 20));

        comboForma = new JComboBox<>(new String[]{"QUADRADO", "CIRCULO", "TRIANGULO"});
        comboModoEntrada = new JComboBox<>(new String[]{"Por dimensão", "Por área"});
        comboForma.setMaximumRowCount(3);
        comboModoEntrada.setMaximumRowCount(2);

        comboSabor1 = new JComboBox<>();
        comboSabor2 = new JComboBox<>();

        botaoBuscarCliente = new JButton("Buscar cliente");
        botaoNovoPedido = new JButton("Novo pedido");
        botaoCalcularMedida = new JButton("Calcular");
        botaoAdicionarPizza = new JButton("Adicionar pizza");
        botaoAtualizarItem = new JButton("Atualizar item");
        botaoRemoverItem = new JButton("Remover item");
        botaoCancelarPedido = new JButton("Cancelar pedido");
        botaoLimparCampos = new JButton("Limpar campos");
        botaoFinalizarPedido = new JButton("Finalizar pedido");
        botaoVoltar = new JButton("Voltar");

        modeloClientes = criarModelo("ID", "Nome", "Sobrenome", "Telefone");
        modeloPedidos = criarModelo("ID", "Estado", "Itens", "Total");
        modeloItens = criarModelo("Item", "Forma", "Sabores", "Área cm²", "Qtd.", "Subtotal");

        tabelaClientes = new JTable(modeloClientes);
        tabelaPedidos = new JTable(modeloPedidos);
        tabelaItens = new JTable(modeloItens);

        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private DefaultTableModel criarModelo(String... colunas) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (String coluna : colunas) {
            modelo.addColumn(coluna);
        }

        return modelo;
    }

    private void montarLayout() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(12, 12));
        painelPrincipal.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel titulo = new JLabel("Cadastro de Pedido");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));

        painelPrincipal.add(titulo, BorderLayout.NORTH);
        painelPrincipal.add(criarPainelCentral(), BorderLayout.CENTER);
        painelPrincipal.add(criarPainelRodape(), BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel(new BorderLayout(12, 12));

        painel.add(criarPainelSuperior(), BorderLayout.NORTH);
        painel.add(criarPainelMeio(), BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createTitledBorder("Cliente e pedido"));

        JPanel busca = new JPanel(new BorderLayout(8, 8));
        busca.add(new JLabel("Telefone:"), BorderLayout.WEST);
        busca.add(campoTelefoneCliente, BorderLayout.CENTER);
        busca.add(botaoBuscarCliente, BorderLayout.EAST);

        JPanel dados = new JPanel(new GridLayout(3, 1, 4, 4));
        dados.add(labelClienteNome);
        dados.add(labelClienteTelefone);
        dados.add(labelPedidoAtual);

        painel.add(busca, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);
        painel.add(dados, BorderLayout.SOUTH);
        painel.setPreferredSize(new Dimension(0, 220));

        return painel;
    }

    private JPanel criarPainelMeio() {
        JPanel painel = new JPanel(new BorderLayout(12, 12));

        painel.add(criarPainelPizza(), BorderLayout.WEST);
        painel.add(criarPainelDireito(), BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelPizza() {
        JPanel painel = new JPanel(new BorderLayout(0, 8));
        painel.setBorder(BorderFactory.createTitledBorder("Dados da pizza"));
        painel.setPreferredSize(new Dimension(370, 0));

        JPanel painelCima = new JPanel(new BorderLayout(0, 10));
        painelCima.setBorder(new EmptyBorder(8, 8, 8, 8));

        painelCima.add(criarPainelCamposPizza(), BorderLayout.NORTH);
        painelCima.add(criarPainelBotoesPizza(), BorderLayout.CENTER);

        painel.add(painelCima, BorderLayout.NORTH);
        painel.add(criarPainelRegras(), BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelCamposPizza() {
        JPanel painel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int linha = 0;

        adicionarLinhaFormulario(painel, gbc, linha++, "Forma:", comboForma);
        adicionarLinhaFormulario(painel, gbc, linha++, "Entrada:", comboModoEntrada);
        adicionarLinhaValor(painel, gbc, linha++);

        gbc.gridx = 1;
        gbc.gridy = linha++;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        labelAjudaMedida.setPreferredSize(new Dimension(210, 25));
        painel.add(labelAjudaMedida, gbc);

        gbc.gridx = 1;
        gbc.gridy = linha++;
        labelResultadoCalculo.setPreferredSize(new Dimension(210, 45));
        painel.add(labelResultadoCalculo, gbc);

        adicionarLinhaFormulario(painel, gbc, linha++, "Sabor 1:", comboSabor1);
        adicionarLinhaFormulario(painel, gbc, linha++, "Sabor 2:", comboSabor2);
        adicionarLinhaFormulario(painel, gbc, linha++, "Quantidade:", campoQuantidade);

        return painel;
    }

    private void adicionarLinhaFormulario(JPanel painel, GridBagConstraints gbc, int linha, String texto, JComponent componente) {
        JLabel label = new JLabel(texto);
        label.setPreferredSize(new Dimension(85, 25));

        componente.setPreferredSize(new Dimension(210, 30));

        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(componente, gbc);
    }

    private void adicionarLinhaValor(JPanel painel, GridBagConstraints gbc, int linha) {
        JLabel label = new JLabel("Valor:");
        label.setPreferredSize(new Dimension(85, 25));

        campoValorMedida.setPreferredSize(new Dimension(120, 30));
        botaoCalcularMedida.setPreferredSize(new Dimension(85, 30));

        JPanel painelValor = new JPanel(new BorderLayout(6, 0));
        painelValor.add(campoValorMedida, BorderLayout.CENTER);
        painelValor.add(botaoCalcularMedida, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(painelValor, gbc);
    }

    private JPanel criarPainelBotoesPizza() {
        JPanel painel = new JPanel(new GridLayout(5, 1, 0, 6));
        painel.setPreferredSize(new Dimension(320, 180));

        painel.add(botaoAdicionarPizza);
        painel.add(botaoAtualizarItem);
        painel.add(botaoRemoverItem);
        painel.add(botaoCancelarPedido);
        painel.add(botaoLimparCampos);

        return painel;
    }

    private JPanel criarPainelRegras() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Regras de medida"));

        JTextArea texto = new JTextArea(
                "Quadrado: lado entre 10 e 40 cm\n" +
                        "Círculo: raio entre 7 e 23 cm\n" +
                        "Triângulo: lado entre 20 e 60 cm\n" +
                        "Área: entre 100 e 1600 cm²\n" +
                        "Cada pizza pode ter até 2 sabores"
        );

        texto.setEditable(false);
        texto.setFocusable(false);
        texto.setBackground(painel.getBackground());
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);

        painel.add(texto, BorderLayout.NORTH);

        return painel;
    }

    private JPanel criarPainelDireito() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));

        JPanel painelPedidos = new JPanel(new BorderLayout(8, 8));
        painelPedidos.setBorder(BorderFactory.createTitledBorder("Pedidos do cliente"));
        painelPedidos.setPreferredSize(new Dimension(0, 190));

        painelPedidos.add(new JScrollPane(tabelaPedidos), BorderLayout.CENTER);

        JPanel botoesPedido = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoesPedido.setPreferredSize(new Dimension(0, 45));
        botoesPedido.add(botaoNovoPedido);

        painelPedidos.add(botoesPedido, BorderLayout.SOUTH);

        JPanel painelItens = new JPanel(new BorderLayout(8, 8));
        painelItens.setBorder(BorderFactory.createTitledBorder("Itens do pedido selecionado"));
        painelItens.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);

        JPanel inferiorItens = new JPanel(new BorderLayout());
        inferiorItens.setPreferredSize(new Dimension(0, 45));
        inferiorItens.add(labelTotalPedido, BorderLayout.EAST);

        painelItens.add(inferiorItens, BorderLayout.SOUTH);

        painel.add(painelPedidos, BorderLayout.NORTH);
        painel.add(painelItens, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelRodape() {
        JPanel painel = new JPanel(new BorderLayout());

        JLabel mensagem = new JLabel("Busque por parte do telefone, selecione o cliente e depois selecione o pedido.");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(botaoFinalizarPedido);
        botoes.add(botaoVoltar);

        painel.add(mensagem, BorderLayout.WEST);
        painel.add(botoes, BorderLayout.EAST);

        return painel;
    }

    private void configurarEventos() {
        botaoBuscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });

        tabelaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarCliente();
            }
        });

        tabelaPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selecionarPedido();
            }
        });

        tabelaItens.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    carregarItemNosCampos();
                }
            }
        });

        botaoNovoPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarNovoPedido();
            }
        });

        botaoCalcularMedida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularMedida();
            }
        });

        botaoAdicionarPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarPizza();
            }
        });

        botaoAtualizarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarItem();
            }
        });

        botaoRemoverItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerItem();
            }
        });

        botaoCancelarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarPedido();
            }
        });

        botaoLimparCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCamposPizza();
            }
        });

        botaoFinalizarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarPedido();
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        comboForma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTextoAjuda();
            }
        });

        comboModoEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarTextoAjuda();
            }
        });
    }

    private void carregarSabores() {
        try {
            comboSabor1.removeAllItems();
            comboSabor2.removeAllItems();

            comboSabor1.addItem("Selecione o primeiro sabor");
            comboSabor2.addItem("Sem segundo sabor");

            for (Sabor sabor : saborService.listarSabores()) {
                comboSabor1.addItem(sabor);
                comboSabor2.addItem(sabor);
            }

            configurarRenderizador(comboSabor1);
            configurarRenderizador(comboSabor2);

        } catch (CampoInvalidoException | SaborInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao carregar sabores.");
        }
    }

    private void configurarRenderizador(JComboBox<Object> combo) {
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice, boolean selecionado, boolean foco) {
                super.getListCellRendererComponent(lista, valor, indice, selecionado, foco);

                if (valor instanceof Sabor) {
                    Sabor sabor = (Sabor) valor;
                    setText(sabor.getNome() + " (" + sabor.getTipoPizza() + ")");
                } else if (valor != null) {
                    setText(valor.toString());
                }

                return this;
            }
        });
    }

    private void carregarTodosClientes() {
        try {
            clientesEncontrados = clienteService.mostrarClientes();

            modeloClientes.setRowCount(0);

            for (Cliente cliente : clientesEncontrados) {
                modeloClientes.addRow(new Object[]{
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getSobrenome(),
                        cliente.getTelefone()
                });
            }

        } catch (CampoInvalidoException | ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao carregar clientes.");
        }
    }

    private void buscarClientes() {
        try {
            String telefone = campoTelefoneCliente.getText();

            if (telefone == null || telefone.isBlank()) {
                carregarTodosClientes();
                limparClienteEPedidoSelecionado();
                return;
            }

            clientesEncontrados = clienteService.buscarPorTelefone(telefone);

            modeloClientes.setRowCount(0);

            for (Cliente cliente : clientesEncontrados) {
                modeloClientes.addRow(new Object[]{
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getSobrenome(),
                        cliente.getTelefone()
                });
            }

            limparClienteEPedidoSelecionado();

        } catch (CampoInvalidoException | ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao buscar clientes.");
        }
    }

    private void selecionarCliente() {
        try {
            int linha = tabelaClientes.getSelectedRow();

            if (linha == -1) {
                return;
            }

            int idCliente = Integer.parseInt(modeloClientes.getValueAt(linha, 0).toString());

            clienteSelecionado = null;

            for (Cliente cliente : clientesEncontrados) {
                if (cliente.getId().equals(idCliente)) {
                    clienteSelecionado = cliente;
                    break;
                }
            }

            if (clienteSelecionado == null) {
                throw new CampoInvalidoException("Cliente não encontrado.");
            }

            labelClienteNome.setText("Cliente selecionado: " + clienteSelecionado.getNome() + " " + clienteSelecionado.getSobrenome());
            labelClienteTelefone.setText("Telefone: " + clienteSelecionado.getTelefone());

            pedidoSelecionado = pedidoService.buscarOuCriarPedidoAberto(clienteSelecionado);

            carregarTabelaPedidos();
            selecionarPedidoNaTabela(pedidoSelecionado.getIdPedido());
            carregarTabelaItens();
            atualizarPedidoAtual();

        } catch (CampoInvalidoException | ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao selecionar cliente.");
        }
    }

    private void criarNovoPedido() {
        try {
            validarClienteSelecionado();

            pedidoSelecionado = pedidoService.criarNovoPedido(clienteSelecionado);

            carregarTabelaPedidos();
            selecionarPedidoNaTabela(pedidoSelecionado.getIdPedido());
            carregarTabelaItens();
            atualizarPedidoAtual();

            JOptionPane.showMessageDialog(this, "Novo pedido criado com sucesso.");

        } catch (CampoInvalidoException | ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao criar novo pedido.");
        }
    }

    private void selecionarPedido() {
        try {
            int linha = tabelaPedidos.getSelectedRow();

            if (linha == -1) {
                return;
            }

            int idPedido = Integer.parseInt(modeloPedidos.getValueAt(linha, 0).toString());

            pedidoSelecionado = pedidoService.buscaPedidoPorId(idPedido);

            carregarTabelaItens();
            atualizarPedidoAtual();

        } catch (CampoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao selecionar pedido.");
        }
    }

    private void carregarTabelaPedidos() {
        try {
            modeloPedidos.setRowCount(0);

            if (clienteSelecionado == null) {
                return;
            }

            for (Pedido pedido : pedidoService.listarPedidosPorCliente(clienteSelecionado)) {
                modeloPedidos.addRow(new Object[]{
                        pedido.getIdPedido(),
                        pedido.getEstadoPedido(),
                        pedido.getItens().size(),
                        "R$ " + String.format("%.2f", pedidoService.calcularTotalPedido(pedido))
                });
            }

        } catch (CampoInvalidoException | ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao carregar pedidos.");
        }
    }

    private void carregarTabelaItens() {
        try {
            modeloItens.setRowCount(0);

            if (pedidoSelecionado == null) {
                labelTotalPedido.setText("Total do pedido: R$ 0.00");
                return;
            }

            int numero = 1;

            for (ItemPedido item : pedidoSelecionado.getItens()) {
                modeloItens.addRow(new Object[]{
                        numero,
                        item.getPizza().getForma().descricao(),
                        montarSabores(item),
                        String.format("%.2f", item.getPizza().calculaArea()),
                        item.getQuantidade(),
                        "R$ " + String.format("%.2f", item.calculaSubTotal(pedidoService.getTabelaPreco()))
                });

                numero++;
            }

            labelTotalPedido.setText("Total do pedido: R$ " + String.format("%.2f", pedidoService.calcularTotalPedido(pedidoSelecionado)));

        } catch (CampoInvalidoException | SaborInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao carregar itens do pedido.");
        }
    }

    private void calcularMedida() {
        try {
            String resultado = pedidoService.calcularResultadoMedida(
                    obterForma(),
                    converterDouble(campoValorMedida.getText()),
                    entradaPorArea()
            );

            labelResultadoCalculo.setText(formatarResultado(resultado));

        } catch (CampoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao calcular medida.");
        }
    }

    private void adicionarPizza() {
        try {
            validarPedidoSelecionado();

            if (entradaPorArea()) {
                pedidoService.adicionarPizzaPorArea(
                        pedidoSelecionado,
                        obterForma(),
                        converterDouble(campoValorMedida.getText()),
                        obterSabor1(),
                        obterSabor2(),
                        converterInteiro(campoQuantidade.getText())
                );
            } else {
                pedidoService.adicionarPizzaPorDimensao(
                        pedidoSelecionado,
                        obterForma(),
                        converterDouble(campoValorMedida.getText()),
                        obterSabor1(),
                        obterSabor2(),
                        converterInteiro(campoQuantidade.getText())
                );
            }

            atualizarTabelasDepoisDeAlterar();
            limparCamposPizza();

            JOptionPane.showMessageDialog(this, "Pizza adicionada ao pedido.");

        } catch (CampoInvalidoException | SaborInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao adicionar pizza.");
        }
    }

    private void atualizarItem() {
        try {
            validarPedidoSelecionado();

            int linha = tabelaItens.getSelectedRow();

            if (linha == -1) {
                throw new CampoInvalidoException("Selecione um item para atualizar.");
            }

            pedidoService.atualizarItem(
                    pedidoSelecionado,
                    linha,
                    obterForma(),
                    converterDouble(campoValorMedida.getText()),
                    entradaPorArea(),
                    obterSabor1(),
                    obterSabor2(),
                    converterInteiro(campoQuantidade.getText())
            );

            atualizarTabelasDepoisDeAlterar();
            limparCamposPizza();

            JOptionPane.showMessageDialog(this, "Item atualizado.");

        } catch (CampoInvalidoException | SaborInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao atualizar item.");
        }
    }

    private void removerItem() {
        try {
            validarPedidoSelecionado();

            int linha = tabelaItens.getSelectedRow();

            if (linha == -1) {
                throw new CampoInvalidoException("Selecione um item para remover.");
            }

            pedidoService.removerItem(pedidoSelecionado, linha);

            atualizarTabelasDepoisDeAlterar();
            limparCamposPizza();

            JOptionPane.showMessageDialog(this, "Item removido.");

        } catch (CampoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao remover item.");
        }
    }

    private void cancelarPedido() {
        try {
            validarPedidoSelecionado();

            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja cancelar/excluir o pedido selecionado?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcao == JOptionPane.YES_OPTION) {
                pedidoService.excluirPedido(pedidoSelecionado.getIdPedido());

                pedidoSelecionado = null;

                carregarTabelaPedidos();
                carregarTabelaItens();
                atualizarPedidoAtual();

                JOptionPane.showMessageDialog(this, "Pedido cancelado.");
            }

        } catch (CampoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao cancelar pedido.");
        }
    }

    private void finalizarPedido() {
        try {
            validarPedidoSelecionado();

            if (pedidoSelecionado.getItens().isEmpty()) {
                throw new CampoInvalidoException("Não é possível finalizar um pedido vazio.");
            }

            JOptionPane.showMessageDialog(this, "Pedido finalizado. O status poderá ser alterado na tela de status.");

            limparTelaDepoisDeFinalizarPedido();

        } catch (CampoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao finalizar pedido.");
        }
    }

    private void limparTelaDepoisDeFinalizarPedido() {
        campoTelefoneCliente.setText("");

        clienteSelecionado = null;
        pedidoSelecionado = null;

        labelClienteNome.setText("Cliente selecionado: nenhum");
        labelClienteTelefone.setText("Telefone: -");
        labelPedidoAtual.setText("Pedido atual: nenhum pedido selecionado");

        modeloPedidos.setRowCount(0);
        modeloItens.setRowCount(0);

        labelTotalPedido.setText("Total do pedido: R$ 0.00");

        limparCamposPizza();

        tabelaClientes.clearSelection();
        tabelaPedidos.clearSelection();
        tabelaItens.clearSelection();

        carregarTodosClientes();
    }

    private void carregarItemNosCampos() {
        try {
            int linha = tabelaItens.getSelectedRow();

            if (linha == -1 || pedidoSelecionado == null) {
                return;
            }

            ItemPedido item = pedidoSelecionado.getItens().get(linha);

            campoQuantidade.setText(String.valueOf(item.getQuantidade()));
            comboModoEntrada.setSelectedItem("Por dimensão");

            Forma forma = item.getPizza().getForma();

            if (forma instanceof Quadrado) {
                comboForma.setSelectedItem("QUADRADO");
                campoValorMedida.setText(String.valueOf(((Quadrado) forma).getLado()));
            } else if (forma instanceof Circulo) {
                comboForma.setSelectedItem("CIRCULO");
                campoValorMedida.setText(String.valueOf(((Circulo) forma).getRaio()));
            } else if (forma instanceof Triangulo) {
                comboForma.setSelectedItem("TRIANGULO");
                campoValorMedida.setText(String.valueOf(((Triangulo) forma).getLado()));
            }

            List<Sabor> sabores = item.getPizza().getSabor();

            if (sabores.size() > 0) {
                comboSabor1.setSelectedItem(sabores.get(0));
            }

            if (sabores.size() > 1) {
                comboSabor2.setSelectedItem(sabores.get(1));
            } else {
                comboSabor2.setSelectedIndex(0);
            }

            labelResultadoCalculo.setText(formatarResultado(
                    "Resultado: área calculada: " + String.format("%.2f", item.getPizza().calculaArea()) + " cm²"
            ));

        } catch (CampoInvalidoException | SaborInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro inesperado ao carregar item nos campos.");
        }
    }

    private void atualizarTabelasDepoisDeAlterar() {
        carregarTabelaPedidos();

        if (pedidoSelecionado != null) {
            selecionarPedidoNaTabela(pedidoSelecionado.getIdPedido());
        }

        carregarTabelaItens();
        atualizarPedidoAtual();
    }

    private void selecionarPedidoNaTabela(int idPedido) {
        for (int i = 0; i < modeloPedidos.getRowCount(); i++) {
            int idAtual = Integer.parseInt(modeloPedidos.getValueAt(i, 0).toString());

            if (idAtual == idPedido) {
                tabelaPedidos.setRowSelectionInterval(i, i);
                return;
            }
        }
    }

    private void limparCamposPizza() {
        comboForma.setSelectedIndex(0);
        comboModoEntrada.setSelectedIndex(0);
        campoValorMedida.setText("");
        campoQuantidade.setText("1");
        comboSabor1.setSelectedIndex(0);
        comboSabor2.setSelectedIndex(0);
        labelResultadoCalculo.setText(formatarResultado("Resultado: -"));
        tabelaItens.clearSelection();
        atualizarTextoAjuda();
    }

    private void limparClienteEPedidoSelecionado() {
        clienteSelecionado = null;
        pedidoSelecionado = null;

        labelClienteNome.setText("Cliente selecionado: nenhum");
        labelClienteTelefone.setText("Telefone: -");
        labelPedidoAtual.setText("Pedido atual: nenhum pedido selecionado");

        modeloPedidos.setRowCount(0);
        modeloItens.setRowCount(0);
        labelTotalPedido.setText("Total do pedido: R$ 0.00");
    }

    private void atualizarPedidoAtual() {
        if (pedidoSelecionado == null) {
            labelPedidoAtual.setText("Pedido atual: nenhum pedido selecionado");
        } else {
            labelPedidoAtual.setText("Pedido atual: #" + pedidoSelecionado.getIdPedido() + " - " + pedidoSelecionado.getEstadoPedido());
        }
    }

    private void atualizarTextoAjuda() {
        if (entradaPorArea()) {
            labelAjudaMedida.setText("Informe a área em cm².");
        } else if (obterForma().equals("CIRCULO")) {
            labelAjudaMedida.setText("Informe o raio do círculo em cm.");
        } else {
            labelAjudaMedida.setText("Informe o lado em cm.");
        }
    }

    private String montarSabores(ItemPedido item) {
        String texto = "";

        List<Sabor> sabores = item.getPizza().getSabor();

        for (int i = 0; i < sabores.size(); i++) {
            texto += sabores.get(i).getNome();

            if (i < sabores.size() - 1) {
                texto += " / ";
            }
        }

        return texto;
    }

    private String formatarResultado(String texto) {
        return "<html><body style='width: 210px'>" + texto + "</body></html>";
    }

    private String obterForma() {
        return comboForma.getSelectedItem().toString();
    }

    private boolean entradaPorArea() {
        return comboModoEntrada.getSelectedItem().toString().equals("Por área");
    }

    private Sabor obterSabor1() {
        Object item = comboSabor1.getSelectedItem();

        if (!(item instanceof Sabor)) {
            throw new CampoInvalidoException("Selecione o primeiro sabor.");
        }

        return (Sabor) item;
    }

    private Sabor obterSabor2() {
        Object item = comboSabor2.getSelectedItem();

        if (item instanceof Sabor) {
            return (Sabor) item;
        }

        return null;
    }

    private double converterDouble(String texto) {
        try {
            if (texto == null || texto.isBlank()) {
                throw new NumberFormatException();
            }

            return Double.parseDouble(texto.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new CampoInvalidoException("Digite um número válido.");
        }
    }

    private int converterInteiro(String texto) {
        try {
            if (texto == null || texto.isBlank()) {
                throw new NumberFormatException();
            }

            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new CampoInvalidoException("Digite uma quantidade válida.");
        }
    }

    private void validarClienteSelecionado() {
        if (clienteSelecionado == null) {
            throw new CampoInvalidoException("Selecione um cliente primeiro.");
        }
    }

    private void validarPedidoSelecionado() {
        if (pedidoSelecionado == null) {
            throw new CampoInvalidoException("Selecione ou crie um pedido primeiro.");
        }
    }
}