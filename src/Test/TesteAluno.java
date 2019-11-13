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
import Entidade.Aluno;

public class TesteAluno {
	
	@Mock
	private DataSource ds = Mockito.mock(DataSource.class);
	
	@Mock
	private Connection c = Mockito.mock(Connection.class);
	
	@Mock
	private PreparedStatement stm = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private PreparedStatement leituraStmt = Mockito.mock(PreparedStatement.class);
	
	@Mock
	private ResultSet rs = Mockito.mock(ResultSet.class);
	
	@Mock
	private ResultSet nullRs = Mockito.mock(ResultSet.class);
	
	AlunoDAO dao;
	private Aluno a;
	private Aluno teste;
	private Aluno testeNome;
	private String alunoSolicitado = new String();
	
	@Before
	public void setUp() throws Exception {
		Assert.assertNotNull(ds);
		Mockito.when(ds.getConnection()).thenReturn(c);
		
		Mockito.when(c.prepareStatement(Mockito.any(String.class))).thenReturn(stm);
		Mockito.when(c.prepareStatement(Mockito.startsWith("SELECT"))).thenReturn(leituraStmt);
		
		Mockito.when(stm.execute()).thenReturn(true);
		Mockito.doNothing().when(stm).close();
		
		a = new Aluno();
		teste = new Aluno();
		testeNome = new Aluno();
		teste.setNome("teste");
		testeNome.setmatricula(2121);
		a.setNome("Luis");
		a.setmatricula(999999999);

		Mockito.when(rs.first()).thenReturn(true);
		Mockito.when(rs.getString(1)).thenReturn(a.getNome());

		Mockito.when(nullRs.first()).thenReturn(false);
		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				alunoSolicitado = (String)invocation.getArguments()[1];
				return null;
			}
		}).when(leituraStmt).setString(Mockito.eq(1), Mockito.any(String.class));
		Mockito.when(leituraStmt.executeQuery()).thenAnswer(new Answer<ResultSet>() {
			@Override
			public ResultSet answer(InvocationOnMock invocation) throws Throwable {
				return alunoSolicitado == "Luis" ? rs : nullRs;
			}
		});	
	}
	@Test
	public void adicionaAluno() throws Exception {
		AlunoDAO dao = new AlunoDAO(ds);
		a = new Aluno();
		a.setNome("Luis");
		a.setmatricula(999999999);
		dao.adicionaDS(a);	
		Mockito.verify(stm,Mockito.times(1)).execute();
	}
	@Test
	public void adicionaAlunoDuplicado() throws Exception {
		AlunoDAO dao = new AlunoDAO(ds);
		Aluno b = a;
		dao.adiciona(b);
		Assert.assertFalse(dao.adiciona(b));
	}
	@Test
	public void fazerMatriculaAlunoMatriculaVazio() throws Exception {	
		AlunoDAO dao = new AlunoDAO(ds);
		dao.adiciona(teste);
		Assert.assertFalse(dao.adiciona(teste));
	}
	@Test
	public void fazerMatriculaAlunoNomeVazio() throws Exception {
		AlunoDAO dao = new AlunoDAO(ds);
		dao.adiciona(testeNome);
		Assert.assertFalse( dao.adiciona(testeNome));
	}
	@Test
	public void lePessoaExistente() throws Exception {
		AlunoDAO dao = new AlunoDAO(ds);
		Aluno pessoaLida = dao.le(a.getNome());
		System.out.println(pessoaLida.getNome());
		Assert.assertEquals(pessoaLida.getNome(), a.getNome());	
	}
	@Test
	public void pessoaNaoEncontradaRetornaNulo() throws Exception {
		AlunoDAO dao = new AlunoDAO(ds);
		Aluno pessoaLida  = dao.le("OutraPessoa");
		Assert.assertNull(pessoaLida);
	}
	@Test
	public void RemoverMatriculaAluno() throws Exception {
		AlunoDAO dao = new AlunoDAO(ds);
		dao.adiciona(a);
	    dao.deleteUser(a.getmatricula());
	Assert.assertFalse(dao.deleteUser(a.getmatricula()));
	}
	

}



