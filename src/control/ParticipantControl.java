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
import util.OptionPane;
import util.Text;
import view.participant.CreateParticipant;
import view.participant.ViewParticipant;
import view.participant.ManageParticipant;

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
        CreateParticipant.tblCategoriaParticipante.setModel(categoriaTable);
    }
    
    public void mudaModeloTabelaParticipante() {
        ManageParticipant.tblParticipante.setModel(participanteTable);
    }
    
    public void atualizaTabelaParticipante() {
        listParticipantes = participanteDao.listar();
        participanteTable.clear();
        participanteTable.addListOfObject(listParticipantes);
    }
    
    private void pegaCamposCriarParticipante() {
        campoNome = view.participant.CreateParticipant.tfNome.getText();
        campoEmail = view.participant.CreateParticipant.tfEmail.getText();
        campoCpf = view.participant.CreateParticipant.tfCpf.getText();
        campoTelefone = view.participant.CreateParticipant.tfTelefone.getText();
    }
    
    private void pegaCamposEditarParticipante() {
        campoId = null;
        campoNome = null;
        campoEmail = null;
        campoCpf = null;
        campoTelefone = null;
        campoId = view.participant.ViewParticipant.lblCodigoParticipant.getText();
        campoNome = view.participant.ViewParticipant.tfNome.getText();
        campoEmail = view.participant.ViewParticipant.tfEmail.getText();
        campoCpf = view.participant.ViewParticipant.tfCpf.getText();
        campoTelefone = view.participant.ViewParticipant.tfTelefone.getText();
    }
    
    public void carregaCategoriasNoComboBox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(nomeDasCategorias);
        CreateParticipant.cbCategoria.setModel(model);
    }
    
    public void adicionarCategoriasDoParticipanteAction() {
        String NomeCategoria = (String) CreateParticipant.cbCategoria.getSelectedItem();
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
            OptionPane.msgInfo(Text.SUCESS_CREATE);
        } else {
            OptionPane.msgError(Text.ERROR_CREATE);
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
    
    public void loadFieldsViewParticipantAction() {
        participanteTable = new ParticipantTableModel();
        atualizaTabelaParticipante();
        participante = participanteTable.getObject(ManageParticipant.tblParticipante.getSelectedRow());
        linhaSelecionada = pegaLinhaSelecionadaParticipante();
    }
    
    public void changeFieldsOnView() {
        categoriaTable = new CategoryTableModel();
        ViewParticipant.tblCategoriaParticipante.setModel(categoriaTable);
        view.participant.ViewParticipant.lblCodigoParticipant.setText(String.valueOf(participante.getId()));
        view.participant.ViewParticipant.tfNome.setText(participante.getNome());
        view.participant.ViewParticipant.tfEmail.setText(participante.getEmail());
        view.participant.ViewParticipant.tfCpf.setText(participante.getCpf());
        view.participant.ViewParticipant.tfTelefone.setText(participante.getTelefone());
        categoriaTable.addListOfObject(categoriaDao.listarCatDoParticipante(participante.getId()));
    }
    
    public void deletarParticipanteAction() {
        participante = participanteTable.getObject(pegaLinhaSelecionadaParticipante());
        if (participanteDao.deletar(participante)) {
            participanteTable.removeObject(linhaSelecionada);
            OptionPane.msgInfo(Text.SUCESS_DELETE);
        } else {
            OptionPane.msgInfo(Text.CATEGORY_DEPENDENCY);
        }
        atualizaTabelaParticipante();
        
    }
    
    public int pegaLinhaSelecionadaParticipante() {
        return ManageParticipant.tblParticipante.getSelectedRow();
    }
    
    public int pegaLinhaCategoriaDoParticipante() {
        return CreateParticipant.tblCategoriaParticipante.getSelectedRow();
    }
    
    public void limparCamposCriarParticipante() {
        view.participant.CreateParticipant.tfNome.setText(null);
        view.participant.CreateParticipant.tfCpf.setText(null);
        view.participant.CreateParticipant.tfEmail.setText(null);
        view.participant.CreateParticipant.tfTelefone.setText(null);
        categoriaTable.clear();
    }
}
