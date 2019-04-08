package control;

import dao.CategoriaDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Categoria;
import model.tablemodel.CategoriaTableModel;
import view.categoria.GerenciarCategoria;

/**
 *
 * @author William
 */
public class CategoriaControl {

    Categoria CATEGORY;
    List<Categoria> CATEGORY_LIST;
    CategoriaDao CATEGORY_DAO;
    CategoriaTableModel CATEGORIA_TABLE;
    Integer INDEX_SELECTED = 0;

    public CategoriaControl() {
        CATEGORY_DAO = new CategoriaDao();
        CATEGORY_LIST = new ArrayList<>();
        CATEGORIA_TABLE = new CategoriaTableModel();
        getUserOfDatabase();
        setModelOfTable();

    }

    private String TF_NOME = "";
    private String LBL_ID = "";


    private void getFieldsEdit() {
        TF_NOME = null;
        LBL_ID = null;
        TF_NOME = view.categoria.EditarCategoria.tfNome.getText();
        LBL_ID = view.categoria.EditarCategoria.lblCodigoCategoria.getText();
    }

    private void getFieldsInsert() {
        TF_NOME = null;
        TF_NOME = view.categoria.CadastrarCategoria.tfNome.getText();
    }

    public void getUserOfDatabase() {
        CATEGORY_LIST = CATEGORY_DAO.listar();
        CATEGORIA_TABLE.clear();
        CATEGORIA_TABLE.addListOfObject(CATEGORY_LIST);
    }

    public void setModelOfTable() {
        GerenciarCategoria.tblCategoria.setModel(CATEGORIA_TABLE);
    }

    public void InsertUserAction() {
        getFieldsInsert();
        CATEGORY = new Categoria();
        CATEGORY.setNome(TF_NOME); // mudar campos
        int idCategoria = CATEGORY_DAO.cadastrar(CATEGORY);
        if (idCategoria != 0) {
            CATEGORY.setId(idCategoria);
            CATEGORIA_TABLE.addObject(CATEGORY);
            JOptionPane.showMessageDialog(null, "Inserido com Sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não conseguir Inserir!");
        }
        CATEGORY = null;
    }

    public int getSelectedIndex() {
        return GerenciarCategoria.tblCategoria.getSelectedRow();
    }

    public void loadFieldsEditUserAction() {
        CATEGORY = CATEGORIA_TABLE.getObject(GerenciarCategoria.tblCategoria.getSelectedRow());
        System.out.println("Categoria pegada da Tabela" + CATEGORY);
        INDEX_SELECTED = getSelectedIndex();
    }

    public void changeFieldsOnEdit() {
        view.categoria.EditarCategoria.tfNome.setText(CATEGORY.getNome());
        view.categoria.EditarCategoria.lblCodigoCategoria.setText(String.valueOf(CATEGORY.getId()));
    }

    public void updateUserAction() {
        getFieldsEdit();
        CATEGORY = new Categoria();
        System.out.println("id do edit :  " + LBL_ID);
        CATEGORY.setId(Integer.valueOf(LBL_ID));
        CATEGORY.setNome(TF_NOME);
        boolean inserido = CATEGORY_DAO.alterar(CATEGORY);
        if (inserido) {
            CATEGORIA_TABLE.updateObject(INDEX_SELECTED, CATEGORY);
            JOptionPane.showMessageDialog(null, "Alterado com Sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não conseguir Alterar!");
        }
        CATEGORY = null;
    }

    public void deleteUserAction() {
        CATEGORY = CATEGORIA_TABLE.getObject(GerenciarCategoria.tblCategoria.getSelectedRow());
        if (CATEGORY_DAO.deletar(CATEGORY)) {
            CATEGORIA_TABLE.removeObject(getSelectedIndex());
            JOptionPane.showMessageDialog(null, "Deletado com Sucesso!");
        }

    }

}
