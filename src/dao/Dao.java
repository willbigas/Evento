package dao;

import fabrica.Conexao;
import java.sql.Connection;


/**
 *
 * @author Alunos
 */
public class Dao {
    protected Connection conexao;

    public Dao() {
        conexao = Conexao.pegaConexao();
    }
    
}
