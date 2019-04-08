package dao;

import factory.Conexao;
import java.sql.Connection;


/**
 *
 * @author Alunos
 */
public class Dao {
    protected Connection conexao;

    public Dao() {
        conexao = Conexao.getConexao();
    }
    
}
