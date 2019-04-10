package control;

import dao.CategoryDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Category;
import model.tablemodel.CategoryTableModel;
import util.OptionPane;
import util.Text;
import view.category.ManageCategory;

/**
 *
 * @author William
 */
public class CategoryControl {

    Category categoria;
    List<Category> listCategorias;
    CategoryDao categoriaDao;
    CategoryTableModel categoriaTable;
    Integer linhaSelecionada = 0;

    public CategoryControl() {
        categoriaDao = new CategoryDao();
        listCategorias = new ArrayList<>();
        categoriaTable = new CategoryTableModel();
        atualizarJTableCategoria();
        mudaModeloDaTable();

    }

    public void atualizarJTableCategoria() {
        listCategorias = categoriaDao.listar();
        categoriaTable.clear();
        categoriaTable.addListOfObject(listCategorias);
    }

    public void mudaModeloDaTable() {
        ManageCategory.tblCategoria.setModel(categoriaTable);
    }

    public int pegaIndexSelecionada() {
        return ManageCategory.tblCategoria.getSelectedRow();
    }

    public void pesquisarCategoriaAction() {
        String pesquisa = view.category.ManageCategory.tfPesquisar.getText();
        List<Category> categoriasPesquisadas = categoriaDao.pesquisarPorTermo(pesquisa);
        categoriaTable.clear();
        categoriaTable.addListOfObject(categoriasPesquisadas);
    }

    public void deletarCategoriaAction() {
        if (pegaIndexSelecionada() == -1) {
            OptionPane.msgInfo(Text.NOT_SELECTED_INPUT);
            return;
        }
        categoria = categoriaTable.getObject(pegaIndexSelecionada());
        if (categoria == null) {
            OptionPane.msgInfo(Text.NOT_SELECTED_INPUT);
            return;
        }
        int result = OptionPane.msgConfirm(Text.ACTION_IRREVERSIBLE);
        if (result == JOptionPane.YES_OPTION) {
            if (categoriaDao.deletar(categoria)) {
                categoriaTable.removeObject(pegaIndexSelecionada());
                OptionPane.msgInfo(Text.SUCESS_DELETE);
            } else {
                OptionPane.msgError(Text.ERROR_DELETE);
            }
        }

    }

}
