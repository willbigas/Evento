package dao;

import factory.Connections;
import java.sql.Connection;


/**
 *
 * @author Alunos
 */
public class Dao {
    protected Connection conexao;

    public Dao() {
        conexao = Connections.getConnection();
    }
    
}
