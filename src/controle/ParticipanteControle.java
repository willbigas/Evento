package controle;

import dao.CategoriaDao;
import dao.ParticipanteDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import modelo.Categoria;
import modelo.Participante;
import modelo.tablemodel.CategoriaTableModel;
import modelo.tablemodel.ParticipanteTableModel;
import util.Mensagem;
import util.Texto;
import view.participante.JanelaCriarParticipante;
import view.participante.JanelaVisualizarParticipante;
import view.participante.JanelaGerenciarParticipante;

/**
 *
 * @author William
 */
public class ParticipanteControle {

    Participante participante;
    ParticipanteDao participanteDao;
    CategoriaDao categoriaDao;
    CategoriaTableModel categoriaTable;
    ParticipanteTableModel participanteTable;
    List<Participante> listParticipantes;
    List<Categoria> listCategorias;
    public static final String[] nomeDasCategorias = {"Exatas", "Programação", "Letras", "Ciencias"};
    Integer linhaSelecionada = 0;

    private String campoId = "";
    private String campoNome = "";
    private String campoEmail = "";
    private String campoCpf = "";
    private String campoTelefone = "";

    public ParticipanteControle() {
        categoriaDao = new CategoriaDao();
        participanteDao = new ParticipanteDao();
        categoriaTable = new CategoriaTableModel();
        participanteTable = new ParticipanteTableModel();
        listParticipantes = new ArrayList<>();
        listCategorias = new ArrayList<>();
        atualizaTabelaParticipante();
        mudaModeloTabelaParticipante();
        mudaModeloTabelaCategoria();

    }

    public void mudaModeloTabelaCategoria() {
        JanelaCriarParticipante.tblCategoriaParticipante.setModel(categoriaTable);
    }

    public void mudaModeloTabelaParticipante() {
        JanelaGerenciarParticipante.tblParticipante.setModel(participanteTable);
    }

    public void atualizaTabelaParticipante() {
        listParticipantes = participanteDao.listar();
        participanteTable.clear();
        participanteTable.addListOfObject(listParticipantes);
    }

    private void pegaCamposCriarParticipante() {
        campoNome = view.participante.JanelaCriarParticipante.tfNome.getText();
        campoEmail = view.participante.JanelaCriarParticipante.tfEmail.getText();
        campoCpf = view.participante.JanelaCriarParticipante.tfCpf.getText();
        campoTelefone = view.participante.JanelaCriarParticipante.tfTelefone.getText();
    }

    private void pegaCamposEditarParticipante() {
        campoId = null;
        campoNome = null;
        campoEmail = null;
        campoCpf = null;
        campoTelefone = null;
        campoId = view.participante.JanelaVisualizarParticipante.lblCodigoParticipant.getText();
        campoNome = view.participante.JanelaVisualizarParticipante.tfNome.getText();
        campoEmail = view.participante.JanelaVisualizarParticipante.tfEmail.getText();
        campoCpf = view.participante.JanelaVisualizarParticipante.tfCpf.getText();
        campoTelefone = view.participante.JanelaVisualizarParticipante.tfTelefone.getText();
    }

    public void carregaCategoriasNoComboBox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(nomeDasCategorias);
        JanelaCriarParticipante.cbCategoria.setModel(model);
    }

    public void adicionarCategoriasDoParticipanteAction() {
        String NomeCategoria = (String) JanelaCriarParticipante.cbCategoria.getSelectedItem();
        Categoria catAdicionada = new Categoria();
        catAdicionada.setId(Integer.MAX_VALUE);
        catAdicionada.setNome(NomeCategoria);
        categoriaTable.addObject(catAdicionada);
        listCategorias.add(catAdicionada);
    }

    public void removerCategoriasDoParticipanteAction() {
        categoriaTable.removeObject(pegaLinhaCategoriaDoParticipante());
        listCategorias.remove(pegaLinhaCategoriaDoParticipante());
    }

    public void criarParticipanteAction() {
        pegaCamposCriarParticipante();
        criarParticipante();
        int idInserido = participanteDao.cadastrar(participante);
        criaNovoParticipante(idInserido);
        limparCamposCriarParticipante();
    }

    private void criaNovoParticipante(int idInserido) {
        if (idInserido != 0) {
            participante.setId(idInserido);
            participanteTable.addObject(participante);
            listParticipantes.add(participante);
            Mensagem.msgInfo(Texto.SUCESS_CREATE);
        } else {
            Mensagem.msgError(Texto.ERROR_CREATE);
        }
    }

    private void criarParticipante() {
        participante = new Participante();
        participante.setId(Integer.MAX_VALUE);
        participante.setNome(campoNome);
        participante.setCpf(campoCpf);
        participante.setEmail(campoEmail);
        participante.setTelefone(campoTelefone);
        participante.setCategorias(listCategorias);
        participante.setAtivo(true);
    }

    public void carregaCamposVisualizarAction() {
        participanteTable = new ParticipanteTableModel();
        atualizaTabelaParticipante();
        participante = participanteTable.getObject(JanelaGerenciarParticipante.tblParticipante.getSelectedRow());
        linhaSelecionada = pegaLinhaSelecionadaParticipante();
    }

    public void modificaCamposNoVisualizar() {
        categoriaTable = new CategoriaTableModel();
        JanelaVisualizarParticipante.tblCategoriaParticipante.setModel(categoriaTable);
        view.participante.JanelaVisualizarParticipante.lblCodigoParticipant.setText(String.valueOf(participante.getId()));
        view.participante.JanelaVisualizarParticipante.tfNome.setText(participante.getNome());
        view.participante.JanelaVisualizarParticipante.tfEmail.setText(participante.getEmail());
        view.participante.JanelaVisualizarParticipante.tfCpf.setText(participante.getCpf());
        view.participante.JanelaVisualizarParticipante.tfTelefone.setText(participante.getTelefone());
        categoriaTable.addListOfObject(categoriaDao.listarCatDoParticipante(participante.getId()));
    }

    public void deletarParticipanteAction() {
        participante = participanteTable.getObject(pegaLinhaSelecionadaParticipante());
        participante.setAtivo(false);
        if (participanteDao.alterar(participante)) {
            participanteTable.updateObject(pegaLinhaSelecionadaParticipante(), participante);
            listParticipantes.set(pegaLinhaSelecionadaParticipante(), participante);
            Mensagem.msgInfo(Texto.SUCESS_DISABLE);
        } else {
            Mensagem.msgInfo(Texto.ERROR_DISABLE);
        }
    }

    public int pegaLinhaSelecionadaParticipante() {
        return JanelaGerenciarParticipante.tblParticipante.getSelectedRow();
    }

    public int pegaLinhaCategoriaDoParticipante() {
        return JanelaCriarParticipante.tblCategoriaParticipante.getSelectedRow();
    }

    public void limparCamposCriarParticipante() {
        view.participante.JanelaCriarParticipante.tfNome.setText(null);
        view.participante.JanelaCriarParticipante.tfCpf.setText(null);
        view.participante.JanelaCriarParticipante.tfEmail.setText(null);
        view.participante.JanelaCriarParticipante.tfTelefone.setText(null);
    }
}
