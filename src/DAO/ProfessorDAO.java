package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import Entidade.Aluno;
import Entidade.Professor;

import ufc.com.jdbc.ConexaoFactory;


public class ProfessorDAO implements IGenericoDAO<Professor>{
  private Connection conexao;
private DataSource dataSource;
  public ProfessorDAO(DataSource dataSource) {
	  this.conexao = (Connection) new ConexaoFactory().conectarBanco();
	  this.dataSource = dataSource;
  }
  public ProfessorDAO()  {
	  this.conexao = (Connection) new ConexaoFactory().conectarBanco();
  }
  @Override
  public boolean adiciona (Professor professor) {
	  String sql = "INSERT INTO professor (nomeprofessor,matriculaprofessor,id_turma,id_professor) values(?,?,1,?)";
	 
	  this.conexao = new ConexaoFactory().conectarBanco();
	  try {

		PreparedStatement stm = conexao.prepareStatement(sql);
		
		stm.setString(1, professor.getNome());
		stm.setInt(2, professor.getmatriculaProfessor());
		stm.setInt(3, professor.getmatriculaProfessor());
		
		System.out.println("Dados adicionados com sucesso");
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
  public boolean adicionaDS (Professor professor) {
	  String sql = "INSERT INTO professor (nomeprofessor,matriculaprofessor,id_turma,id_professor) values(?,?,1,?)";
	 
	
	  try {
		  this.conexao = dataSource.getConnection();
		PreparedStatement stm = conexao.prepareStatement(sql);
		
		stm.setString(1, professor.getNome());
		stm.setInt(2, professor.getmatriculaProfessor());
		stm.setInt(3, professor.getmatriculaProfessor());
		
		System.out.println("Dados adicionados com sucesso");
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
  public boolean adicionaProfTurma (int profId) {
	  String sql = "INSERT INTO professor_ensina_turma (id_professor,id_turma)values(?,1)";
	  this.conexao = new ConexaoFactory().conectarBanco();
	  try {

		
		PreparedStatement stm = conexao.prepareStatement(sql);
	
		stm.setInt(1,profId);
		System.out.println("Dados adicionados com sucesso");
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
  
	public ArrayList<Aluno> getAlunoTurma(int idTurma,int idProfessor ) {
		String sql =  "Select a.matricula ,a.nome from aluno a ,professor_ensina_turma pet \r\n" + 
				"where a.id_turma = pet.id_turma ;";
		ArrayList<Aluno> listageneroGosta = new ArrayList<Aluno>();
		 this.conexao = new ConexaoFactory().conectarBanco();
		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("matricula");
				String nome = rs.getString("nome");
				Aluno a = new Aluno(nome, id);
				
				listageneroGosta.add(a);
			}
			stm.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listageneroGosta;
	}
	public Professor getProfessorbynameandMatricula(String nome,Integer matricula) {
		String sql = "Select * From professor p where ? = p.nomeprofessor and ? = p.matriculaprofessor;";
		this.conexao = new ConexaoFactory().conectarBanco();
		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setString(1, nome);
			stm.setInt(2, matricula);
			ResultSet rs = stm.executeQuery();
			rs.next();
			Professor professor = new Professor(rs.getString("nomeprofessor"),rs.getInt("matriculaprofessor"));
			
			stm.close();
			return professor;
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
	public Aluno getAlunoByMatricula(int Matricula){
		String sql = "SELECT * FROM aluno WHERE matricula = ?;";
		this.conexao = new ConexaoFactory().conectarBanco();
		try {
	
			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setInt(1, Matricula);

			stmt.execute();
			
			ResultSet rs = stmt.executeQuery();
			rs.next();

			Aluno aluno = new Aluno(rs.getString("nome"),rs.getInt("matricula"));
			
			stmt.close();
			return aluno;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Usuario nao encontrado");
		return null;
	}
	
	public Professor cadastrarNota(Integer matriculaAluno,double nota1,double nota2) {
		String sql = "UPDATE aluno SET nota1 = ? WHERE matricula = ?;";
		String sql1 = "UPDATE aluno SET nota2 = ? WHERE matricula = ?;";
      if(nota1 >=0 && nota2<=10) {
		this.conexao = new ConexaoFactory().conectarBanco();
		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			PreparedStatement stm1 = conexao.prepareStatement(sql1);
			stm.setDouble(1, nota1);
			stm.setInt(2, matriculaAluno);
			stm1.setDouble(1, nota2);
			stm1.setInt(2, matriculaAluno);
			stm.execute();
			stm1.execute();
		
			stm.close();
			stm1.close();
		}catch (SQLException e) {
		}finally {
			try {
				this.conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
      }
		return null;
	}
	public boolean deleteUser(int matricula) {
		String sql = "DELETE FROM professor WHERE matriculaprofessor = ?";	
		this.conexao = new ConexaoFactory().conectarBanco();
		try {
			PreparedStatement stmt = conexao.prepareStatement(sql);

			// seta os valores
			stmt.setInt(1, matricula);

			int qtdRowsAffected = stmt.executeUpdate();
			stmt.close();
			if (qtdRowsAffected > 0)
				return true;
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
	@Override
	public Professor le(String nome) {
		String sql = "SELECT nome FROM professor WHERE nome = ?";
		

	    try {
	   	 this.conexao = dataSource.getConnection();
	        // prepared statement para inserção
	        PreparedStatement stmt = conexao.prepareStatement(sql);
	        // seta os valores
	        stmt.setString(1, nome);

	        // executa
	        ResultSet rs = stmt.executeQuery();
	        Professor prof = null;
	        if(rs.first()){
	       	prof = new Professor();
	       prof.setNome(rs.getString(1));
	        }
	        stmt.close();
	        return prof;
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	} 
}
