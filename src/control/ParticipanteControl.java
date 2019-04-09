package control;

import dao.CategoriaDao;
import dao.ParticipanteDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.Categoria;
import model.Participante;
import model.tablemodel.CategoriaTableModel;
import model.tablemodel.ParticipanteTableModel;
import view.participante.CadastrarParticipante;
import view.participante.EditarParticipante;
import view.participante.GerenciarParticipante;

/**
 *
 * @author William
 */
public class ParticipanteControl {

    Participante PARTICIPANT;
    ParticipanteDao PARTICIPANT_DAO;
    CategoriaDao CATEGORIA_DAO;
    CategoriaTableModel CATEGORY_TABLE;
    ParticipanteTableModel PARTICIPANT_TABLE;
    List<Participante> PARTICIPANT_LIST;
    public static final String[] nomeDasCategorias = {"Exatas", "Programação", "Letras", "Ciencias"};
    Integer INDEX_SELECTED = 0;

    private String TF_NOME = "";
    private String TF_EMAIL = "";
    private String TF_CPF = "";
    private String TF_TELEFONE = "";

    public ParticipanteControl() {
        CATEGORIA_DAO = new CategoriaDao();
        PARTICIPANT_DAO = new ParticipanteDao();
        CATEGORY_TABLE = new CategoriaTableModel();
        PARTICIPANT_TABLE = new ParticipanteTableModel();
        PARTICIPANT_LIST = new ArrayList<>();
        setModelOfTableCategory();
        setModelOfTableParticipant();
        getParticipantOfDatabase();
    }

    public void setModelOfTableCategory() {
        CadastrarParticipante.tblCategoriaParticipante.setModel(CATEGORY_TABLE);
        EditarParticipante.tblCategoriaParticipante.setModel(CATEGORY_TABLE);
    }

    public void setModelOfTableParticipant() {
        GerenciarParticipante.tblParticipante.setModel(PARTICIPANT_TABLE);
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
        TF_NOME = view.participante.CadastrarParticipante.tfNome.getText();
        TF_EMAIL = view.participante.CadastrarParticipante.tfEmail.getText();
        TF_CPF = view.participante.CadastrarParticipante.tfCpf.getText();
        TF_TELEFONE = view.participante.CadastrarParticipante.tfTelefone.getText();
    }

    public void loadComboCategory() {
        DefaultComboBoxModel model = new DefaultComboBoxModel(nomeDasCategorias);
        CadastrarParticipante.cbCategoria.setModel(model);
        EditarParticipante.cbCategoria.setModel(model);
    }

    public List<Categoria> getCategorysAdded() {
        List<Categoria> categoriasDoParticipante = new ArrayList<>();
        for (int i = 0; i < CATEGORY_TABLE.getRowCount(); i++) {
            categoriasDoParticipante.add(CATEGORY_TABLE.getObject(i));
        }
        return categoriasDoParticipante;
    }

    public void insertCategoryParticipantAction() {
        String NomeCategoria = (String) CadastrarParticipante.cbCategoria.getSelectedItem();
        Categoria catAdicionada = new Categoria();
        catAdicionada.setId(Integer.MAX_VALUE);
        catAdicionada.setNome(NomeCategoria);
        CATEGORY_TABLE.addObject(catAdicionada);
    }

    public void deleteCategoryParticipantAction() {
        CATEGORY_TABLE.removeObject(CadastrarParticipante.tblCategoriaParticipante.getSelectedRow());
    }

    public void createParticipantAction() {
        getFieldsInsert();
        PARTICIPANT = new Participante();
        PARTICIPANT.setId(Integer.MAX_VALUE);
        PARTICIPANT.setNome(TF_NOME);
        PARTICIPANT.setCpf(TF_CPF);
        PARTICIPANT.setEmail(TF_EMAIL);
        PARTICIPANT.setTelefone(TF_TELEFONE);
        PARTICIPANT.setCategorias(getCategorysAdded());
        PARTICIPANT_DAO.cadastrar(PARTICIPANT);
        PARTICIPANT_TABLE.addObject(PARTICIPANT);

    }
    
    public void loadFieldsEditParticipantAction() {
        PARTICIPANT = PARTICIPANT_TABLE.getObject(GerenciarParticipante.tblParticipante.getSelectedRow());
        System.out.println("Usuario pegado da Tabela" + PARTICIPANT);
        INDEX_SELECTED = getSelectedIndex();
    }

    public void changeFieldsOnEdit() {
        view.participante.EditarParticipante.tfNome.setText(PARTICIPANT.getNome());
        view.participante.EditarParticipante.tfEmail.setText(PARTICIPANT.getEmail());
        view.participante.EditarParticipante.tfCpf.setText(PARTICIPANT.getCpf());
        view.participante.EditarParticipante.tfTelefone.setText(PARTICIPANT.getTelefone());
        CATEGORY_TABLE.clear();
        CATEGORY_TABLE.addListOfObject(PARTICIPANT.getCategorias());
    }

    public void deleteParticipantAction() {
        PARTICIPANT = PARTICIPANT_TABLE.getObject(GerenciarParticipante.tblParticipante.getSelectedRow());
        if (PARTICIPANT_DAO.deletar(PARTICIPANT)) {
            PARTICIPANT_TABLE.removeObject(getSelectedIndex());
            JOptionPane.showMessageDialog(null, "Deletado com Sucesso!");
        }

    }

    public int getSelectedIndex() {
        return GerenciarParticipante.tblParticipante.getSelectedRow();
    }
}
