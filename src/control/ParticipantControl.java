package control;

import dao.CategoryDao;
import dao.ParticipantDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import model.Category;
import model.Participant;
import model.tablemodel.CategoryTableModel;
import model.tablemodel.ParticipantTableModel;
import util.Mensagem;
import util.Texto;
import view.participant.JanelaCriarParticipante;
import view.participant.JanelaVisualizarParticipante;
import view.participant.JanelaGerenciarParticipante;

/**
 *
 * @author William
 */
public class ParticipantControl {
    
    Participant participante;
    ParticipantDao participanteDao;
    CategoryDao categoriaDao;
    CategoryTableModel categoriaTable;
    ParticipantTableModel participanteTable;
    List<Participant> listParticipantes;
    List<Category> listCategorias;
    public static final String[] nomeDasCategorias = {"Exatas", "Programação", "Letras", "Ciencias"};
    Integer linhaSelecionada = 0;
    
    private String campoId = "";
    private String campoNome = "";
    private String campoEmail = "";
    private String campoCpf = "";
    private String campoTelefone = "";
    
    public ParticipantControl() {
        categoriaDao = new CategoryDao();
        participanteDao = new ParticipantDao();
        categoriaTable = new CategoryTableModel();
        participanteTable = new ParticipantTableModel();
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
        campoNome = view.participant.JanelaCriarParticipante.tfNome.getText();
        campoEmail = view.participant.JanelaCriarParticipante.tfEmail.getText();
        campoCpf = view.participant.JanelaCriarParticipante.tfCpf.getText();
        campoTelefone = view.participant.JanelaCriarParticipante.tfTelefone.getText();
    }
    
    private void pegaCamposEditarParticipante() {
        campoId = null;
        campoNome = null;
        campoEmail = null;
        campoCpf = null;
        campoTelefone = null;
        campoId = view.participant.JanelaVisualizarParticipante.lblCodigoParticipant.getText();
        campoNome = view.participant.JanelaVisualizarParticipante.tfNome.getText();
        campoEmail = view.participant.JanelaVisualizarParticipante.tfEmail.getText();
        campoCpf = view.participant.JanelaVisualizarParticipante.tfCpf.getText();
        campoTelefone = view.participant.JanelaVisualizarParticipante.tfTelefone.getText();
    }
    
    public void carregaCategoriasNoComboBox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(nomeDasCategorias);
        JanelaCriarParticipante.cbCategoria.setModel(model);
    }
    
    public void adicionarCategoriasDoParticipanteAction() {
        String NomeCategoria = (String) JanelaCriarParticipante.cbCategoria.getSelectedItem();
        Category catAdicionada = new Category();
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
        listCategorias = null;
        limparCamposCriarParticipante();
    }
    
    private void criaNovoParticipante(int idInserido) {
        if (idInserido != 0) {
            participante.setId(idInserido);
            participanteTable.addObject(participante);
            Mensagem.msgInfo(Texto.SUCESS_CREATE);
        } else {
            Mensagem.msgError(Texto.ERROR_CREATE);
        }
    }
    
    private void criarParticipante() {
        participante = new Participant();
        participante.setId(Integer.MAX_VALUE);
        participante.setNome(campoNome);
        participante.setCpf(campoCpf);
        participante.setEmail(campoEmail);
        participante.setTelefone(campoTelefone);
        participante.setCategorias(listCategorias);
    }
    
    public void carregaCamposVisualizarAction() {
        participanteTable = new ParticipantTableModel();
        atualizaTabelaParticipante();
        participante = participanteTable.getObject(JanelaGerenciarParticipante.tblParticipante.getSelectedRow());
        linhaSelecionada = pegaLinhaSelecionadaParticipante();
    }
    
    public void modificaCamposNoVisualizar() {
        categoriaTable = new CategoryTableModel();
        JanelaVisualizarParticipante.tblCategoriaParticipante.setModel(categoriaTable);
        view.participant.JanelaVisualizarParticipante.lblCodigoParticipant.setText(String.valueOf(participante.getId()));
        view.participant.JanelaVisualizarParticipante.tfNome.setText(participante.getNome());
        view.participant.JanelaVisualizarParticipante.tfEmail.setText(participante.getEmail());
        view.participant.JanelaVisualizarParticipante.tfCpf.setText(participante.getCpf());
        view.participant.JanelaVisualizarParticipante.tfTelefone.setText(participante.getTelefone());
        categoriaTable.addListOfObject(categoriaDao.listarCatDoParticipante(participante.getId()));
    }
    
    public void deletarParticipanteAction() {
        participante = participanteTable.getObject(pegaLinhaSelecionadaParticipante());
        if (participanteDao.deletar(participante)) {
            participanteTable.removeObject(linhaSelecionada);
            Mensagem.msgInfo(Texto.SUCESS_DELETE);
        } else {
            Mensagem.msgInfo(Texto.CATEGORY_DEPENDENCY);
        }
        atualizaTabelaParticipante();
        
    }
    
    public int pegaLinhaSelecionadaParticipante() {
        return JanelaGerenciarParticipante.tblParticipante.getSelectedRow();
    }
    
    public int pegaLinhaCategoriaDoParticipante() {
        return JanelaCriarParticipante.tblCategoriaParticipante.getSelectedRow();
    }
    
    public void limparCamposCriarParticipante() {
        view.participant.JanelaCriarParticipante.tfNome.setText(null);
        view.participant.JanelaCriarParticipante.tfCpf.setText(null);
        view.participant.JanelaCriarParticipante.tfEmail.setText(null);
        view.participant.JanelaCriarParticipante.tfTelefone.setText(null);
        categoriaTable.clear();
    }
}
