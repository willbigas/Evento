package interfaces;

import java.util.List;

/**
 *
 * @author Alunos
 */
public interface AcoesTableModel<E> {

    public void addObject(E obj);

    public void removeObject(int indice);

    public void updateObject(int indice, E obj);

    public E getObject(int indice);

    public void addListOfObject(List<E> obj);

    public void clear();

}
