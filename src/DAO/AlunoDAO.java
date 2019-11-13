package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import Entidade.Aluno;

import ufc.com.jdbc.ConexaoFactory;


public class AlunoDAO implements IGenericoDAO<Aluno> {
  private Connection conexao;

private List<Float> notas;
private DataSource dataSource;
  
  public AlunoDAO(DataSource dataSource) {
	  this.conexao = (Connection) new ConexaoFactory().conectarBanco();
	  this.dataSource = dataSource;
  }
  
  public AlunoDAO() {
	  this.conexao = new ConexaoFactory().conectarBanco();

}
  
@Override
public boolean adiciona (Aluno aluno) {
	  String sql = "INSERT INTO aluno (nome,matricula,id_turma) VALUES(?,?,1)";
	  this.conexao = new ConexaoFactory().conectarBanco();
	  try {
		PreparedStatement stm = conexao.prepareStatement(sql);
		stm.setString(1, aluno.getNome());
		stm.setInt(2, aluno.getmatricula());	
		int qtdRowsAffected = stm.executeUpdate();
		stm.close();
		if (qtdRowsAffected > 0)
			return true;
		return false;
	} catch (SQLException e) {
		System.err.println(e.getMessage());
		
	}
	  finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	return false;

   }

public boolean adicionaDS (Aluno aluno) {
	  String sql = "INSERT INTO aluno (nome,matricula) VALUES(?,?)";
	  
	  try {
		  this.conexao = dataSource.getConnection();
		PreparedStatement stm = conexao.prepareStatement(sql);
		stm.setString(1, aluno.getNome());
		stm.setInt(2, aluno.getmatricula());	
		int qtdRowsAffected = stm.executeUpdate();
		stm.execute();
		stm.close();
		if (qtdRowsAffected > 0)
			return true;
		return false;
	} catch (SQLException e) {
		System.err.println(e.getMessage());
		
	}
	  finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	return false;

   }
	public Aluno getAlunobynameandMatricula(String nome,Integer matricula) {
		String sql = "Select * From aluno a where ? = a.nome and ? = a.matricula;";
		this.conexao = new ConexaoFactory().conectarBanco();
		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setString(1, nome);
			stm.setInt(2, matricula);
			ResultSet rs = stm.executeQuery();
			rs.next();
			Aluno aluno = new Aluno(rs.getString("nome"),rs.getInt("matricula"));
			stm.close();
			return aluno;
			
		}catch (SQLException e) {
			
		}finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
		return null;
		
	}

	public Float getMediabyMatricula(Integer matricula) {
		
		
		  float nota, nota2 = 0 ;
		String sql = "Select nota1,nota2 From aluno a where  ? = a.matricula;";
		this.conexao = new ConexaoFactory().conectarBanco();
		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, matricula);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				nota = rs.getFloat("nota1");
				nota2 = rs.getFloat("nota2");
	  System.out.println("Sua media foi: "+(nota+nota2)/2);
			return 	(nota+nota2)/2;
			
				 
			
			}
				
		
			
		}catch (SQLException e) {
			
		}finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
		return null;
		
	}
	public Float getMediabynameandMatriculaDs(Integer matricula) {
		
		
		  float nota, nota2 = 0 ;
		String sql = "Select nota1,nota2 From aluno a where  ? = a.matricula;";
		
		try {
			  this.conexao = dataSource.getConnection();
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, matricula);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				nota = rs.getFloat("nota1");
				nota2 = rs.getFloat("nota2");
	  System.out.println("Sua media foi: "+(nota+nota2)/2);
			return 	(nota+nota2)/2;
			
				 
			
			}
				
		
			
		}catch (SQLException e) {
			
		}finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
		return null;
		
	}
	public boolean deleteUser(Integer matricula) {
		String sql = "DELETE FROM aluno WHERE matricula = ?";
		  this.conexao = new ConexaoFactory().conectarBanco();

		
		try {
			
			PreparedStatement stm = conexao.prepareStatement(sql);

		
			stm.setInt(1, matricula);

			stm.execute();
			
			stm.close();
		
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
  public List<Float> getNotaByMatricula (Integer matricula) {
	  List<Float> notas = new ArrayList<Float>();
	  float nota, nota2 = 0 ;
	  String sql = "Select nota1,nota2 From aluno a where  ? = a.matricula;";
		this.conexao = new ConexaoFactory().conectarBanco();
		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, matricula);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
			
				nota = rs.getFloat("nota1");
				nota2 = rs.getFloat("nota2");
				  notas.add(nota);
				  notas.add(nota2);
				
				
				
			}
			stm.close();
			
		
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	  
  }
		return notas;
  
}



@Override
public String toString() {
	return "AlunoDAO [notas=" + notas + "]";
}

@Override
public Aluno le(String nome) {
	String sql = "SELECT nome FROM aluno WHERE nome = ?";
	

    try {
   	 this.conexao = dataSource.getConnection();
        // prepared statement para inserção
        PreparedStatement stmt = conexao.prepareStatement(sql);
        // seta os valores
        stmt.setString(1, nome);

        // executa
        ResultSet rs = stmt.executeQuery();
        Aluno aluno = null;
        if(rs.first()){
       	aluno = new Aluno();
       	aluno.setNome(rs.getString(1));
        }
        stmt.close();
        return aluno;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

}
