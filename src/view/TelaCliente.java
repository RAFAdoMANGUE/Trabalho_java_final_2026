package view;

import exception.CampoInvalidoException;
import exception.ClienteNaoEncontradoException;
import model.Cliente;
import repository.RepositoryMemoria;
import service.ClienteService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TelaCliente extends JFrame {

    private ClienteService clienteService;

    private JTextField campoNome;
    private JTextField campoSobrenome;
    private JTextField campoTelefone;

    private JTextField campoFiltroSobrenome;
    private JTextField campoFiltroTelefone;

    private JButton botaoCadastrar;
    private JButton botaoAtualizar;
    private JButton botaoExcluir;
    private JButton botaoLimparCampos;

    private JButton botaoFiltrar;
    private JButton botaoLimparFiltros;

    private JButton botaoVoltar;

    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;

    private JLabel labelTotalClientes;

    public TelaCliente(ClienteService clienteService) {
        this.clienteService = clienteService;
        configurarJanela();
        inicializarComponentes();
        montarLayout();
        configurarEventos();
        carregarTabela();
    }

    private void configurarJanela() {
        setTitle("Cadastro de Clientes");
        setSize(1050, 650);
        setMinimumSize(new Dimension(950, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void inicializarComponentes() {
        campoNome = new JTextField();
        campoSobrenome = new JTextField();
        campoTelefone = new JTextField();

        campoFiltroSobrenome = new JTextField();
        campoFiltroTelefone = new JTextField();

        botaoCadastrar = new JButton("Cadastrar");
        botaoAtualizar = new JButton("Atualizar");
        botaoExcluir = new JButton("Excluir");
        botaoLimparCampos = new JButton("Limpar campos");

        botaoFiltrar = new JButton("Filtrar");
        botaoLimparFiltros = new JButton("Limpar filtros");

        botaoVoltar = new JButton("Voltar");

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Sobrenome", "Telefone"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaClientes.getTableHeader().setReorderingAllowed(false);
        tabelaClientes.setRowHeight(24);
        tabelaClientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(220);
        tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(260);
        tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(180);

        labelTotalClientes = new JLabel("Total de clientes: 0");
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

        JLabel titulo = new JLabel("Cadastro de Clientes");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitulo = new JLabel("Cadastre, atualize, exclua e filtre clientes da pizzaria");
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

        painelCentral.add(criarPainelLateral(), BorderLayout.WEST);
        painelCentral.add(criarPainelTabela(), BorderLayout.CENTER);

        return painelCentral;
    }

    private JPanel criarPainelLateral() {
        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        painelLateral.setPreferredSize(new Dimension(340, 0));

        JPanel painelDados = criarPainelDadosCliente();
        JPanel painelFiltros = criarPainelFiltros();
        JPanel painelAcoes = criarPainelAcoes();

        painelDados.setMaximumSize(new Dimension(Integer.MAX_VALUE, 165));
        painelFiltros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));
        painelAcoes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

        painelLateral.add(painelDados);
        painelLateral.add(Box.createVerticalStrut(12));
        painelLateral.add(painelFiltros);
        painelLateral.add(Box.createVerticalStrut(12));
        painelLateral.add(painelAcoes);
        painelLateral.add(Box.createVerticalGlue());

        return painelLateral;
    }

    private JPanel criarPainelDadosCliente() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Dados do cliente"));

        GridBagConstraints gbc = criarGridBagConstraints();

        adicionarLinhaFormulario(painel, gbc, "Nome:", campoNome, 0);
        adicionarLinhaFormulario(painel, gbc, "Sobrenome:", campoSobrenome, 1);
        adicionarLinhaFormulario(painel, gbc, "Telefone:", campoTelefone, 2);

        return painel;
    }

    private JPanel criarPainelFiltros() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Filtros de clientes"));

        GridBagConstraints gbc = criarGridBagConstraints();

        adicionarLinhaFormulario(painel, gbc, "Sobrenome:", campoFiltroSobrenome, 0);
        adicionarLinhaFormulario(painel, gbc, "Telefone:", campoFiltroTelefone, 1);

        JPanel painelBotoesFiltro = new JPanel(new GridLayout(1, 2, 8, 0));
        painelBotoesFiltro.add(botaoFiltrar);
        painelBotoesFiltro.add(botaoLimparFiltros);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        painel.add(painelBotoesFiltro, gbc);

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

    private JPanel criarPainelTabela() {
        JPanel painelTabela = new JPanel(new BorderLayout(10, 10));
        painelTabela.setBorder(BorderFactory.createTitledBorder("Clientes cadastrados"));

        JScrollPane scrollTabela = new JScrollPane(
                tabelaClientes,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        JPanel painelInferiorTabela = new JPanel(new BorderLayout());
        painelInferiorTabela.add(labelTotalClientes, BorderLayout.WEST);

        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelTabela.add(painelInferiorTabela, BorderLayout.SOUTH);

        return painelTabela;
    }

    private JPanel criarPainelRodape() {
        JPanel painelRodape = new JPanel(new BorderLayout());

        JLabel mensagem = new JLabel("Selecione um cliente na tabela para atualizar ou excluir.");

        JPanel painelBotaoVoltar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        painelBotaoVoltar.add(botaoVoltar);

        painelRodape.add(mensagem, BorderLayout.WEST);
        painelRodape.add(painelBotaoVoltar, BorderLayout.EAST);

        return painelRodape;
    }

    private GridBagConstraints criarGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void adicionarLinhaFormulario(
            JPanel painel,
            GridBagConstraints gbc,
            String textoLabel,
            JTextField campo,
            int linha
    ) {
        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel label = new JLabel(textoLabel);
        painel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(8, 8, 8, 8);

        painel.add(campo, gbc);
    }

    public void carregarTabela() {
        modeloTabela.setRowCount(0);

        java.util.List<Cliente> clientes = clienteService.mostrarClientes();

        for (Cliente clienteAtual : clientes) {
            modeloTabela.addRow(new Object[]{
                    clienteAtual.getId(),
                    clienteAtual.getNome(),
                    clienteAtual.getSobrenome(),
                    clienteAtual.getTelefone()
            });
        }

        labelTotalClientes.setText("Total de clientes: " + clientes.size());
    }

    public void limparCampos(){
        campoNome.setText("");
        campoSobrenome.setText("");
        campoTelefone.setText("");
        tabelaClientes.clearSelection();
    }

    public void limparFiltros(){
        campoFiltroSobrenome.setText("");
        campoFiltroTelefone.setText("");
        carregarTabela();
    }

    public void configurarEventos(){

        //AÇÕES DOS BOTÕES ----------------------------------------

        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        botaoAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarCliente();
            }
        });

        botaoLimparCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        botaoLimparFiltros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparFiltros();
            }
        });

        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirCliente();
            }
        });

        botaoFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarClientes();
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //aqui falta adicionar quando a tela principal estiver pronta
            }
        });


        //AÇÕES DOS CAMPOS ----------------------------------------

        campoNome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        campoSobrenome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        campoTelefone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });


        //AÇÕES DO MOUSE

        tabelaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                obterIdSelecionado();
            }
        });

        tabelaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    obterCamposSelecionados();
                    obterIdSelecionado();
                }
            }
        });
    }


    public void cadastrarCliente(){

        try {
            String nome = campoNome.getText();
            String sobrenome = campoSobrenome.getText();
            String telefone = campoTelefone.getText();

            clienteService.cadastrarCliente(nome, sobrenome, telefone);

            limparCampos();
            carregarTabela();

            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso. ");
        }
        catch(CampoInvalidoException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }


    public int obterLinhaSelecionada(){
        int linhaSelecionada = tabelaClientes.getSelectedRow();

        if(linhaSelecionada == -1){
            throw new CampoInvalidoException("Nenhuma linha foi selecionada");
        }

        return linhaSelecionada;
    }

    public void obterCamposSelecionados(){
        int linhaSelecionada = obterLinhaSelecionada();

        campoNome.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
        campoSobrenome.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
        campoTelefone.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());

    }

    public int obterIdSelecionado(){
        int linhaSelecionada = obterLinhaSelecionada();
        return Integer.parseInt(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
    }


    public void atualizarCliente(){

        try {

            int idSelecionado = obterIdSelecionado();

            String nomeNovo = campoNome.getText();
            String sobrenomeNovo = campoSobrenome.getText();
            String telefoneNovo = campoTelefone.getText();

            clienteService.editarCliente(idSelecionado, nomeNovo, sobrenomeNovo, telefoneNovo);

            carregarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso. ");
        }catch (CampoInvalidoException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    public void excluirCliente(){
        try{

            int idSelecionado = obterIdSelecionado();

            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir esse cliente?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if(opcao == JOptionPane.YES_OPTION){
                clienteService.excluirCliente(idSelecionado);

                carregarTabela();
                limparCampos();

                JOptionPane.showMessageDialog(this, "Cliente excluido com sucesso.");
            }

        }catch(CampoInvalidoException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void filtrarClientes() {
        try {
            String sobrenome = campoFiltroSobrenome.getText();
            String telefone = campoFiltroTelefone.getText();

            java.util.List<Cliente> clientesFiltrados = clienteService.filtrarClientes(sobrenome, telefone);

            modeloTabela.setRowCount(0);

            for (Cliente clienteAtual : clientesFiltrados) {
                modeloTabela.addRow(new Object[]{
                        clienteAtual.getId(),
                        clienteAtual.getNome(),
                        clienteAtual.getSobrenome(),
                        clienteAtual.getTelefone()
                });
            }

            labelTotalClientes.setText("Total de clientes: " + clientesFiltrados.size());

            //ARRUMAR AQUI AS EXCECOES QUANDO FIZER AS PERSONALIZADAS

        } catch (CampoInvalidoException | ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            limparFiltros();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            //criando temporariamente um objeto clienteService e Repository para testar a tela sem a principal
            RepositoryMemoria repositoryMemoria = new RepositoryMemoria();
            ClienteService clienteService1 = new ClienteService(repositoryMemoria);

            TelaCliente tela = new TelaCliente(clienteService1);
            tela.setVisible(true);
        });
    }
}