package view;

import exception.CampoInvalidoException;
import exception.PrecoInvalidoException;
import model.TipoPizza;
import repository.RepositoryMemoria;
import service.PrecoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPreco extends JFrame {

    private PrecoService precoService;

    private JTextField campoPrecoSimples;
    private JTextField campoPrecoEspecial;
    private JTextField campoPrecoPremium;

    private JButton botaoSalvarPrecos;
    private JButton botaoLimparCampos;
    private JButton botaoVoltar;

    private JLabel labelPrecoSimplesAtual;
    private JLabel labelPrecoEspecialAtual;
    private JLabel labelPrecoPremiumAtual;

    public TelaPreco(PrecoService precoService) {
        this.precoService = precoService;

        configurarJanela();
        inicializarComponentes();
        montarLayout();
        configurarEventos();
        carregarPrecos();
    }

    private void configurarJanela() {
        setTitle("Atualização de Preços");
        setSize(750, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void inicializarComponentes() {
        campoPrecoSimples = new JTextField();
        campoPrecoEspecial = new JTextField();
        campoPrecoPremium = new JTextField();

        botaoSalvarPrecos = new JButton("Salvar preços");
        botaoLimparCampos = new JButton("Limpar campos");
        botaoVoltar = new JButton("Voltar");

        labelPrecoSimplesAtual = new JLabel("Preço atual da pizza simples: R$ 0.00 por centímetro quadrado");
        labelPrecoEspecialAtual = new JLabel("Preço atual da pizza especial: R$ 0.00 por centímetro quadrado");
        labelPrecoPremiumAtual = new JLabel("Preço atual da pizza premium: R$ 0.00 por centímetro quadrado");

        labelPrecoSimplesAtual.setFont(new Font("Arial", Font.BOLD, 14));
        labelPrecoEspecialAtual.setFont(new Font("Arial", Font.BOLD, 14));
        labelPrecoPremiumAtual.setFont(new Font("Arial", Font.BOLD, 14));
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

        JLabel titulo = new JLabel("Atualização de Preços");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));

        JLabel subtitulo = new JLabel("Defina o preço por centímetro quadrado para cada tipo de pizza");
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

        painelCentral.add(criarPainelFormulario(), BorderLayout.CENTER);
        painelCentral.add(criarPainelAcoes(), BorderLayout.EAST);

        return painelCentral;
    }

    private JPanel criarPainelFormulario() {
        JPanel painelFormulario = new JPanel(new GridLayout(3, 1, 0, 15));

        painelFormulario.add(criarPainelPrecoSimples());
        painelFormulario.add(criarPainelPrecoEspecial());
        painelFormulario.add(criarPainelPrecoPremium());

        return painelFormulario;
    }

    private JPanel criarPainelPrecoSimples() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Pizza Simples"));

        JLabel labelDescricao = new JLabel("Preço por cm²:");
        JLabel labelExemplo = new JLabel("Exemplo: 0.05");
        labelExemplo.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel painelCampo = new JPanel(new BorderLayout(8, 8));
        painelCampo.add(labelDescricao, BorderLayout.WEST);
        painelCampo.add(campoPrecoSimples, BorderLayout.CENTER);

        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.add(labelPrecoSimplesAtual);
        painelInfo.add(Box.createVerticalStrut(4));
        painelInfo.add(labelExemplo);

        painel.add(painelCampo, BorderLayout.CENTER);
        painel.add(painelInfo, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelPrecoEspecial() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Pizza Especial"));

        JLabel labelDescricao = new JLabel("Preço por cm²:");
        JLabel labelExemplo = new JLabel("Exemplo: 0.07");
        labelExemplo.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel painelCampo = new JPanel(new BorderLayout(8, 8));
        painelCampo.add(labelDescricao, BorderLayout.WEST);
        painelCampo.add(campoPrecoEspecial, BorderLayout.CENTER);

        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.add(labelPrecoEspecialAtual);
        painelInfo.add(Box.createVerticalStrut(4));
        painelInfo.add(labelExemplo);

        painel.add(painelCampo, BorderLayout.CENTER);
        painel.add(painelInfo, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelPrecoPremium() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Pizza Premium"));

        JLabel labelDescricao = new JLabel("Preço por cm²:");
        JLabel labelExemplo = new JLabel("Exemplo: 0.10");
        labelExemplo.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel painelCampo = new JPanel(new BorderLayout(8, 8));
        painelCampo.add(labelDescricao, BorderLayout.WEST);
        painelCampo.add(campoPrecoPremium, BorderLayout.CENTER);

        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.add(labelPrecoPremiumAtual);
        painelInfo.add(Box.createVerticalStrut(4));
        painelInfo.add(labelExemplo);

        painel.add(painelCampo, BorderLayout.CENTER);
        painel.add(painelInfo, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelAcoes() {
        JPanel painelAcoes = new JPanel(new GridLayout(3, 1, 0, 10));
        painelAcoes.setBorder(BorderFactory.createTitledBorder("Ações"));
        painelAcoes.setPreferredSize(new Dimension(180, 0));

        painelAcoes.add(botaoSalvarPrecos);
        painelAcoes.add(botaoLimparCampos);
        painelAcoes.add(botaoVoltar);

        return painelAcoes;
    }

    private JPanel criarPainelRodape() {
        JPanel painelRodape = new JPanel(new BorderLayout());

        JLabel mensagem = new JLabel("Digite valores usando ponto ou vírgula para casas decimais. Exemplo: 0.05 ou 0,05");

        painelRodape.add(mensagem, BorderLayout.WEST);

        return painelRodape;
    }


    //METODOS PARA A LOGICA DA TELA E INTEGRACAO -------------------------------------------------

    public void configurarEventos(){

        botaoSalvarPrecos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPrecos();
            }
        });

        botaoLimparCampos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //aqui falta adicionar quando a tela principal estiver pronta
            }
        });

        campoPrecoSimples.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPrecos();
            }
        });

        campoPrecoEspecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPrecos();
            }
        });

        campoPrecoPremium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPrecos();
            }
        });
    }


    public void carregarPrecos(){

        labelPrecoSimplesAtual.setText(
                "Preço atual da pizza simples: R$ " + precoService.getPrecoSimples() + " por centímetro quadrado"
        );

        labelPrecoEspecialAtual.setText(
                "Preço atual da pizza especial: R$ " + precoService.getPrecoEspecial() + " por centímetro quadrado"
        );

        labelPrecoPremiumAtual.setText(
                "Preço atual da pizza premium: R$ " + precoService.getPrecoPremium() + " por centímetro quadrado"
        );
    }

    public void salvarPrecos(){
        try {

            String precoSimplesStr = campoPrecoSimples.getText();
            String precoEspecialStr = campoPrecoEspecial.getText();
            String precoPremiumStr = campoPrecoPremium.getText();

            if (precoSimplesStr.isBlank() && precoEspecialStr.isBlank() && precoPremiumStr.isBlank()) {
                throw new CampoInvalidoException("Não pode deixar todos os campos vazios");
            }

            if (!precoSimplesStr.isBlank()) {
                double precoSimples = converterPreco(precoSimplesStr);
                precoService.alterarPreco(TipoPizza.SIMPLES, precoSimples);
            }

            if (!precoEspecialStr.isBlank()) {
                double precoEspecial = converterPreco(precoEspecialStr);
                precoService.alterarPreco(TipoPizza.ESPECIAL, precoEspecial);
            }

            if (!precoPremiumStr.isBlank()) {
                double precoPremium = converterPreco(precoPremiumStr);
                precoService.alterarPreco(TipoPizza.PREMIUM, precoPremium);
            }

            carregarPrecos();
            limparCampos();

            JOptionPane.showMessageDialog(this, "Preços atualizados com sucesso.");

        }catch (CampoInvalidoException | PrecoInvalidoException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public double converterPreco(String texto) {
        try {
            texto = texto.trim().replace(",", ".");

            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new CampoInvalidoException("Digite um preço válido. Exemplo: 0.05 ou 0,05");
        }
    }

    public void limparCampos(){
        campoPrecoSimples.setText("");
        campoPrecoEspecial.setText("");
        campoPrecoPremium.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // criando temporariamente um objeto PrecoService e Repository para testar a tela sem a principal
            RepositoryMemoria repositoryMemoria = new RepositoryMemoria();
            PrecoService precoService = new PrecoService(repositoryMemoria);

            TelaPreco tela = new TelaPreco(precoService);
            tela.setVisible(true);
        });
    }
}