package control;

import dao.CategoriaDao;
import dao.ParticipanteDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.Category;
import model.Participant;
import model.tablemodel.CategoryTableModel;
import model.tablemodel.ParticipantTableModel;
import view.participante.CreateParticipant;
import view.participante.EditParticipant;
import view.participante.ManageParticipant;

/**
 *
 * @author William
 */
public class ParticipanteControl {

    Participant PARTICIPANT;
    ParticipanteDao PARTICIPANT_DAO;
    CategoriaDao CATEGORIA_DAO;
    CategoryTableModel CATEGORY_TABLE;
    ParticipantTableModel PARTICIPANT_TABLE;
    List<Participant> PARTICIPANT_LIST;
    public static final String[] nomeDasCategorias = {"Exatas", "Programação", "Letras", "Ciencias"};
    Integer INDEX_SELECTED = 0;

    private String TF_ID = "";
    private String TF_NOME = "";
    private String TF_EMAIL = "";
    private String TF_CPF = "";
    private String TF_TELEFONE = "";

    public ParticipanteControl() {
        CATEGORIA_DAO = new CategoriaDao();
        PARTICIPANT_DAO = new ParticipanteDao();
        CATEGORY_TABLE = new CategoryTableModel();
        PARTICIPANT_TABLE = new ParticipantTableModel();
        PARTICIPANT_LIST = new ArrayList<>();
        getParticipantOfDatabase();
        setModelOfTableParticipant();
        setModelOfTableCategory();

    }

    public void setModelOfTableCategory() {
        CreateParticipant.tblCategoriaParticipante.setModel(CATEGORY_TABLE);
    }

    public void setModelOfTableParticipant() {
        ManageParticipant.tblParticipante.setModel(PARTICIPANT_TABLE);
    }

    public void getParticipantOfDatabase() {
        PARTICIPANT_LIST = PARTICIPANT_DAO.listar();
        PARTICIPANT_TABLE.clear();
        PARTICIPANT_TABLE.addListOfObject(PARTICIPANT_LIST);
    }

    private void getFieldsInsert() {
        TF_NOME = null;
        TF_EMAIL = null;
        TF_CPF = null;
        TF_TELEFONE = null;
        TF_NOME = view.participante.CreateParticipant.tfNome.getText();
        TF_EMAIL = view.participante.CreateParticipant.tfEmail.getText();
        TF_CPF = view.participante.CreateParticipant.tfCpf.getText();
        TF_TELEFONE = view.participante.CreateParticipant.tfTelefone.getText();
    }

    private void getFieldsEdit() {
//        TF_NOME = null;
//        TF_EMAIL = null;
//        TF_CPF = null;
//        TF_TELEFONE = null;
        TF_ID = view.participante.EditParticipant.lblCodigoParticipant.getText();
        TF_NOME = view.participante.EditParticipant.tfNome.getText();
        TF_EMAIL = view.participante.EditParticipant.tfEmail.getText();
        TF_CPF = view.participante.EditParticipant.tfCpf.getText();
        TF_TELEFONE = view.participante.EditParticipant.tfTelefone.getText();
    }

    public void loadComboCategory() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(nomeDasCategorias);
        CreateParticipant.cbCategoria.setModel(model);
        EditParticipant.cbCategoria.setModel(model);
    }

    public List<Category> getCategorysAdded() {
        List<Category> categoriasDoParticipante = new ArrayList<>();
        for (int i = 0; i < CATEGORY_TABLE.getRowCount(); i++) {
            categoriasDoParticipante.add(CATEGORY_TABLE.getObject(i));
        }
        return categoriasDoParticipante;
    }

    public void insertCategoryParticipantAction() {
        String NomeCategoria = (String) CreateParticipant.cbCategoria.getSelectedItem();
        Category catAdicionada = new Category();
        catAdicionada.setId(Integer.MAX_VALUE);
        catAdicionada.setNome(NomeCategoria);
        CATEGORY_TABLE.addObject(catAdicionada);
    }

    public void deleteCategoryParticipantAction() {
        CATEGORY_TABLE.removeObject(CreateParticipant.tblCategoriaParticipante.getSelectedRow());
    }

    public void insertCategoryParticipantEditAction() {
        String NomeCategoria = (String) EditParticipant.cbCategoria.getSelectedItem();
        Category catAdicionada = new Category();
        catAdicionada.setId(Integer.MAX_VALUE);
        catAdicionada.setNome(NomeCategoria);
        CATEGORY_TABLE.addObject(catAdicionada);
    }

    public void deleteCategoryParticipantEditAction() {
        CATEGORY_TABLE.removeObject(EditParticipant.tblCategoriaParticipante.getSelectedRow());
    }

    public void createParticipantAction() {
        getFieldsInsert();
        PARTICIPANT = new Participant();
        PARTICIPANT.setId(Integer.MAX_VALUE);
        PARTICIPANT.setNome(TF_NOME);
        PARTICIPANT.setCpf(TF_CPF);
        PARTICIPANT.setEmail(TF_EMAIL);
        PARTICIPANT.setTelefone(TF_TELEFONE);
        PARTICIPANT.setCategorias(getCategorysAdded());
        PARTICIPANT_DAO.cadastrar(PARTICIPANT);
        PARTICIPANT_TABLE.addObject(PARTICIPANT);

    }

    public void updateParticipantAction() {
        getFieldsEdit();
        PARTICIPANT = new Participant();
        PARTICIPANT.setId(Integer.valueOf(TF_ID));
        PARTICIPANT.setNome(TF_NOME);
        PARTICIPANT.setCpf(TF_CPF);
        PARTICIPANT.setEmail(TF_EMAIL);
        PARTICIPANT.setTelefone(TF_TELEFONE);
        PARTICIPANT.setCategorias(getCategorysAdded());
        System.out.println("PARTICIPANTE DO UPDATE : + " + PARTICIPANT);
        PARTICIPANT_DAO.alterar(PARTICIPANT);
        PARTICIPANT_TABLE.updateObject(INDEX_SELECTED, PARTICIPANT);
    }

    public void loadFieldsEditParticipantAction() {
        PARTICIPANT = PARTICIPANT_TABLE.getObject(ManageParticipant.tblParticipante.getSelectedRow());
        System.out.println("Usuario pegado da Tabela" + PARTICIPANT);
        INDEX_SELECTED = getSelectedIndex();
    }

    public void changeFieldsOnEdit() {
        CATEGORY_TABLE = new CategoryTableModel();
        EditParticipant.tblCategoriaParticipante.setModel(CATEGORY_TABLE);
        view.participante.EditParticipant.lblCodigoParticipant.setText(String.valueOf(PARTICIPANT.getId()));
        view.participante.EditParticipant.tfNome.setText(PARTICIPANT.getNome());
        view.participante.EditParticipant.tfEmail.setText(PARTICIPANT.getEmail());
        view.participante.EditParticipant.tfCpf.setText(PARTICIPANT.getCpf());
        view.participante.EditParticipant.tfTelefone.setText(PARTICIPANT.getTelefone());
        List<Category> catDoParticipant = CATEGORIA_DAO.listarCatDoParticipante(PARTICIPANT.getId());
        System.out.println("Categorias do Participante" + catDoParticipant);
        for (Category categoria : catDoParticipant) {
            System.out.println(categoria);
            CATEGORY_TABLE.addObject(categoria);
        }
    }

    public void deleteParticipantAction() {
        PARTICIPANT = PARTICIPANT_TABLE.getObject(ManageParticipant.tblParticipante.getSelectedRow());
        if (PARTICIPANT_DAO.deletar(PARTICIPANT)) {
            PARTICIPANT_TABLE.removeObject(INDEX_SELECTED);
            JOptionPane.showMessageDialog(null, "Deletado com Sucesso!");
        }

    }

    public int getSelectedIndex() {
        return ManageParticipant.tblParticipante.getSelectedRow();
    }
}
