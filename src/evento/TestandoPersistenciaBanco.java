package evento;

import dao.CategoriaDao;
import dao.ParticipanteDao;

/**
 *
 * @author William
 */
public class TestandoPersistenciaBanco {

    public static void main(String[] args) {
        ParticipanteDao participanteDao = new ParticipanteDao();
        CategoriaDao categoriaDao = new CategoriaDao();
//        Categoria c = new Categoria();
//        c.setId(1);
//        c.setNome("Teste de Categoria");
//        Categoria c1 = new Categoria();
//        c1.setId(2);
//        c1.setNome("Teste de Categoria 2");
//        List<Categoria> categorias = new ArrayList<>();
//        categorias.add(c);
//        categorias.add(c1);
//        Participante p = new Participante();
//        p.setId(1);
//        p.setNome("bbbbbbbb");
//        p.setEmail("aaaaaaaaaaaaa@hotmail.com");
//        p.setCpf("4541547");
//        p.setTelefone("asdasda");
//        p.setCategorias(categorias);
//        participanteDao.cadastrar(p);
        try {
            categoriaDao.excluirCategoriaParticipante(5);
        } catch (Exception exception) {
        }
    }

}
