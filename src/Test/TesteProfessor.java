package Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import DAO.AlunoDAO;
import DAO.ProfessorDAO;
import Entidade.Aluno;
import Entidade.Professor;

public class TesteProfessor {
	
	@Mock
	private DataSource ds = Mockito.mock(DataSource.class);
	
	@Mock
	private Connection c = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private PreparedStatement leituraStmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	@Mock
	private ResultSet nullRs = Mockito.mock(ResultSet.class);
	
	private Professor p;
	private Aluno a;
	private Aluno teste1;
	private Professor teste;
	private Professor testeNome;
	private String professorSolicitado = new String();
	
	@Before
	public void setUp() throws Exception {
		Assert.assertNotNull(ds);
		Mockito.when(ds.getConnection()).thenReturn(c);
		
		Mockito.when(c.prepareStatement(Mockito.any(String.class))).thenReturn(stmt);
		Mockito.when(c.prepareStatement(Mockito.startsWith("SELECT"))).thenReturn(leituraStmt);
		
		Mockito.when(stmt.execute()).thenReturn(true);
		Mockito.doNothing().when(stmt).close();
		
		p = new Professor();
		teste = new Professor();
		testeNome = new Professor();
		teste.setNome("teste");
		testeNome.setmatriculaProfessor(2121);
		p.setNome("Luis");
        p.setmatriculaProfessor(11111111);
		Mockito.when(rs.first()).thenReturn(true);
		Mockito.when(rs.getString(1)).thenReturn(p.getNome());

		Mockito.when(nullRs.first()).thenReturn(false);

		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				professorSolicitado = (String)invocation.getArguments()[1];
				return null;
			}
			
		}).when(leituraStmt).setString(Mockito.eq(1), Mockito.any(String.class));
		Mockito.when(leituraStmt.executeQuery()).thenAnswer(new Answer<ResultSet>() {
			@Override
			public ResultSet answer(InvocationOnMock invocation) throws Throwable {
				return professorSolicitado == "Luis" ? rs : nullRs;
			}
		});
		
	}
	@Test
	public void adicionaProfessor() throws Exception {
		ProfessorDAO dao = new ProfessorDAO(ds);
		dao.adicionaDS(p);
		Mockito.verify(stmt,Mockito.times(1)).execute();
	}
	@Test
	public void adicionaProfessorDuplicado() throws Exception {
		ProfessorDAO dao = new ProfessorDAO(ds);
		Professor b = p;
		dao.adiciona(b);
		Assert.assertFalse(dao.adiciona(b));
	}
	@Test
	public void leProfessorExistente() throws Exception {
		ProfessorDAO dao = new ProfessorDAO(ds);
		Professor pessoaLida = dao.le("Luis");
		Assert.assertEquals(pessoaLida.getNome(), p.getNome());
	}
	@Test
	public void professorNaoEncontradoRetornaNulo() throws Exception {
		ProfessorDAO dao = new ProfessorDAO(ds);
		Professor pessoaLida = dao.le("OutraPessoa");
		Assert.assertNull(pessoaLida);
	}
	@Test
	public void fazerMatriculaProfessorMatriculaVazio() throws Exception {	
		ProfessorDAO dao = new ProfessorDAO(ds);
		dao.adiciona(teste);
		Assert.assertFalse(dao.adiciona(teste));
	}
	@Test
	public void fazerMatriculaProfessorNomeVazio() throws Exception {
		ProfessorDAO dao = new ProfessorDAO(ds);
		dao.adiciona(testeNome);
		Assert.assertFalse( dao.adiciona(testeNome));
	}
	@Test
	public void RemoverMatriculaProfessor() throws Exception {
		ProfessorDAO dao = new ProfessorDAO(ds);
		dao.adiciona(p);
	    dao.deleteUser(p.getmatriculaProfessor());
		Assert.assertFalse(dao.deleteUser(p.getmatriculaProfessor()));
	}
	@Test
	public void cadastrarNotaEcalcularMedia() throws Exception  {
		ProfessorDAO dao = new ProfessorDAO(ds);
		AlunoDAO AlunoDAO  = new AlunoDAO(ds);
		Aluno teste1 = new Aluno();
		teste1.setmatricula(12345);
		teste1.setNome("teste123");
		AlunoDAO.adiciona(teste1);
         dao.cadastrarNota( 12345, 4, 10);
        double  resultadoEsperado;
     resultadoEsperado = AlunoDAO.getMediabyMatricula(12345);
     Assert.assertEquals(7,0, resultadoEsperado);
}	@Test
	public void cadastrarNotaForadoIntervalo() throws Exception  {
	ProfessorDAO dao = new ProfessorDAO(ds);
    dao.cadastrarNota( 12345, -1, 11);
    Assert.assertNull(  dao.cadastrarNota(12345, -1, 11));
}	
}


