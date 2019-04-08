package model.tablemodel;

import interfaces.ActionsTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Usuario;

public class UsuarioTableModel extends AbstractTableModel implements ActionsTableModel<Usuario> {

    // Constantes representando o índice das colunas
    private static final int ID = 0;
    private static final int LOGIN = 1;
    private static final int SENHA = 2;

    // Lista de Objetos<Produtos> a serem exibidos na tela.
    private List<Usuario> linhas;

    // Colunas da Tabela (View)
    private String[] colunas = {"ID", "LOGIN"};

    // Cria um ProdutoTableModel sem nenhuma linha
    public UsuarioTableModel() {
        linhas = new ArrayList<>();
    }

    // Cria um ProdutoTableModel contendo a lista recebida por parâmetro 
    public UsuarioTableModel(List<Usuario> listUsuarios) {
        linhas = listUsuarios;
    }

    @Override
    public int getRowCount() {
        return linhas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case ID:
                return Integer.class;
            case LOGIN:
                return String.class;
            case SENHA:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Usuario usuario = linhas.get(linha);
        switch (coluna) {
            case ID:
                return usuario.getId();
            case LOGIN:
                return usuario.getLogin();
            case SENHA:
                return usuario.getSenha();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        Usuario usuario = linhas.get(linha);
        switch (coluna) {
            case ID:
                usuario.setId(Integer.valueOf((String) valor));
                break;
            case LOGIN:
                usuario.setLogin((String) valor);
                break;
            case SENHA:
                usuario.setSenha((String) valor);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");

        }
        this.fireTableCellUpdated(linha, coluna); // Atualiza Celula da tabela

    }

    @Override
    public Usuario getObject(int indiceLinha) {
        return linhas.get(indiceLinha);
    }

    @Override
    public void addObject(Usuario usuario) {
        linhas.add(usuario);
        int ultimoIndice = getRowCount() - 1; // linhas -1
        fireTableRowsInserted(ultimoIndice, ultimoIndice); // atualiza insert
    }

    @Override
    public void removeObject(int indiceLinha) {
        linhas.remove(indiceLinha);
        fireTableRowsDeleted(indiceLinha, indiceLinha); // atualiza delete
    }

    @Override
    public void updateObject(int indiceLinha, Usuario u) {
        linhas.set(indiceLinha, u);
        fireTableRowsUpdated(indiceLinha, indiceLinha); // atualiza delete
    }

    @Override
    public void addListOfObject(List<Usuario> usuarios) {
        int indice = getRowCount();
        linhas.addAll(usuarios);
        fireTableRowsInserted(indice, indice + usuarios.size());
        fireTableDataChanged();
    }

    @Override
    public void clear() {
        linhas.clear();
        fireTableDataChanged(); // atualiza toda tabela.
    }

}
