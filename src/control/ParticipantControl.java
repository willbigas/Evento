package control;

import dao.CategoryDao;
import dao.ParticipantDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.Category;
import model.Participant;
import model.tablemodel.CategoryTableModel;
import model.tablemodel.ParticipantTableModel;
import view.participant.CreateParticipant;
import view.participant.EditParticipant;
import view.participant.ManageParticipant;

/**
 *
 * @author William
 */
public class ParticipantControl {

    Participant PARTICIPANT;
    ParticipantDao PARTICIPANT_DAO;
    CategoryDao CATEGORY_DAO;
    CategoryTableModel CATEGORY_TABLE;
    ParticipantTableModel PARTICIPANT_TABLE;
    List<Participant> PARTICIPANT_LIST;
    List<Category> CATEGORY_LIST;
    public static final String[] nomeDasCategorias = {"Exatas", "Programação", "Letras", "Ciencias"};
    Integer INDEX_SELECTED = 0;

    private String TF_ID = "";
    private String TF_NOME = "";
    private String TF_EMAIL = "";
    private String TF_CPF = "";
    private String TF_TELEFONE = "";

    public ParticipantControl() {
        CATEGORY_DAO = new CategoryDao();
        PARTICIPANT_DAO = new ParticipantDao();
        CATEGORY_TABLE = new CategoryTableModel();
        PARTICIPANT_TABLE = new ParticipantTableModel();
        PARTICIPANT_LIST = new ArrayList<>();
        CATEGORY_LIST = new ArrayList<>();
        updateJTableParticipant();
        setModelOfTableParticipant();
        setModelOfTableCategory();

    }

    public void setModelOfTableCategory() {
        CreateParticipant.tblCategoriaParticipante.setModel(CATEGORY_TABLE);
    }

    public void setModelOfTableParticipant() {
        ManageParticipant.tblParticipante.setModel(PARTICIPANT_TABLE);
    }

    public void updateJTableParticipant() {
        PARTICIPANT_LIST = PARTICIPANT_DAO.listar();
        PARTICIPANT_TABLE.clear();
        PARTICIPANT_TABLE.addListOfObject(PARTICIPANT_LIST);
    }

    private void getFieldsInsert() {
        TF_NOME = view.participant.CreateParticipant.tfNome.getText();
        TF_EMAIL = view.participant.CreateParticipant.tfEmail.getText();
        TF_CPF = view.participant.CreateParticipant.tfCpf.getText();
        TF_TELEFONE = view.participant.CreateParticipant.tfTelefone.getText();
    }

    private void getFieldsEdit() {
        TF_ID = view.participant.EditParticipant.lblCodigoParticipant.getText();
        TF_NOME = view.participant.EditParticipant.tfNome.getText();
        TF_EMAIL = view.participant.EditParticipant.tfEmail.getText();
        TF_CPF = view.participant.EditParticipant.tfCpf.getText();
        TF_TELEFONE = view.participant.EditParticipant.tfTelefone.getText();
    }

    public void loadComboCategory() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(nomeDasCategorias);
        CreateParticipant.cbCategoria.setModel(model);
    }

    public void insertCategoryParticipantAction() {
        String NomeCategoria = (String) CreateParticipant.cbCategoria.getSelectedItem();
        Category catAdicionada = new Category();
        catAdicionada.setId(Integer.MAX_VALUE);
        catAdicionada.setNome(NomeCategoria);
        CATEGORY_TABLE.addObject(catAdicionada);
        CATEGORY_LIST.add(catAdicionada);
    }

    public void deleteCategoryParticipantAction() {
        CATEGORY_TABLE.removeObject(getSelectedIndexCategoryParticipant());
        CATEGORY_LIST.remove(getSelectedIndexCategoryParticipant());
    }

    public void createParticipantAction() {
        getFieldsInsert();
        PARTICIPANT = new Participant();
        PARTICIPANT.setId(Integer.MAX_VALUE);
        PARTICIPANT.setNome(TF_NOME);
        PARTICIPANT.setCpf(TF_CPF);
        PARTICIPANT.setEmail(TF_EMAIL);
        PARTICIPANT.setTelefone(TF_TELEFONE);
        PARTICIPANT.setCategorias(CATEGORY_LIST);
        int idInserido = PARTICIPANT_DAO.cadastrar(PARTICIPANT);
        if (idInserido != 0) {
            PARTICIPANT.setId(idInserido);
            PARTICIPANT_TABLE.addObject(PARTICIPANT);
        }
        CATEGORY_LIST = null;
    }

    public void loadFieldsEditParticipantAction() {
        PARTICIPANT_TABLE = new ParticipantTableModel();
        updateJTableParticipant();
        PARTICIPANT = PARTICIPANT_TABLE.getObject(ManageParticipant.tblParticipante.getSelectedRow());
        System.out.println("Participante pegado da Tabela" + PARTICIPANT);
        INDEX_SELECTED = getSelectedIndexParticipant();
    }

    public void changeFieldsOnEdit() {
        CATEGORY_TABLE = new CategoryTableModel();
        EditParticipant.tblCategoriaParticipante.setModel(CATEGORY_TABLE);
        view.participant.EditParticipant.lblCodigoParticipant.setText(String.valueOf(PARTICIPANT.getId()));
        view.participant.EditParticipant.tfNome.setText(PARTICIPANT.getNome());
        view.participant.EditParticipant.tfEmail.setText(PARTICIPANT.getEmail());
        view.participant.EditParticipant.tfCpf.setText(PARTICIPANT.getCpf());
        view.participant.EditParticipant.tfTelefone.setText(PARTICIPANT.getTelefone());
        CATEGORY_TABLE.addListOfObject(CATEGORY_DAO.listarCatDoParticipante(PARTICIPANT.getId()));
    }

    public void deleteParticipantAction() {
        PARTICIPANT = PARTICIPANT_TABLE.getObject(ManageParticipant.tblParticipante.getSelectedRow());
        if (PARTICIPANT_DAO.deletar(PARTICIPANT)) {
            PARTICIPANT_TABLE.removeObject(INDEX_SELECTED);
            JOptionPane.showMessageDialog(null, "Deletado com Sucesso!");
        }

    }

    public int getSelectedIndexParticipant() {
        return ManageParticipant.tblParticipante.getSelectedRow();
    }

    public int getSelectedIndexCategoryParticipant() {
        return CreateParticipant.tblCategoriaParticipante.getSelectedRow();
    }
}
