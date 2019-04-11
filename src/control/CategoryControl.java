package control;

import dao.CategoriaDao;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Category;
import model.tablemodel.CategoryTableModel;
import util.Mensagem;
import util.Texto;
import view.category.JanelaGerenciarCategoria;

/**
 *
 * @author William
 */
public class CategoryControl {

    Category categoria;
    List<Category> listCategorias;
    CategoriaDao categoriaDao;
    CategoryTableModel categoriaTable;
    Integer linhaSelecionada = 0;

    public CategoryControl() {
        categoriaDao = new CategoriaDao();
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
        JanelaGerenciarCategoria.tblCategoria.setModel(categoriaTable);
    }

    public int pegaIndexSelecionada() {
        return JanelaGerenciarCategoria.tblCategoria.getSelectedRow();
    }

    public void pesquisarCategoriaAction() {
        String pesquisa = view.category.JanelaGerenciarCategoria.tfPesquisar.getText();
        List<Category> categoriasPesquisadas = categoriaDao.pesquisarPorTermo(pesquisa);
        categoriaTable.clear();
        categoriaTable.addListOfObject(categoriasPesquisadas);
    }

    public void deletarCategoriaAction() {
        if (pegaIndexSelecionada() == -1) {
            Mensagem.msgInfo(Texto.NOT_SELECTED_INPUT);
            return;
        }
        categoria = categoriaTable.getObject(pegaIndexSelecionada());
        if (categoria == null) {
            Mensagem.msgInfo(Texto.NOT_SELECTED_INPUT);
            return;
        }
        int result = Mensagem.msgConfirm(Texto.ACTION_IRREVERSIBLE);
        if (result == JOptionPane.YES_OPTION) {
            if (categoriaDao.deletar(categoria)) {
                categoriaTable.removeObject(pegaIndexSelecionada());
                Mensagem.msgInfo(Texto.SUCESS_DELETE);
            } else {
                Mensagem.msgError(Texto.ERROR_DELETE);
            }
        }

    }

}
