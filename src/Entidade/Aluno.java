package Entidade;

public class Aluno {
	String nome;
	int matricula;
	double nota1;
	double nota2;
	public Aluno(String nome, int matricula, double nota1, double nota2) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.nota1 = nota1;
		this.nota2 = nota2;
	}
	@Override
	public String toString() {
		return "Aluno [nome=" + nome + ", matricula=" + matricula + ", nota1=" + nota1 + ", nota2=" + nota2 + "]";
	}
	public Aluno(String nome, int matricula) {
		super();
		this.nome = nome;
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getmatricula() {
		return matricula;
	}
	public Aluno() {
		super();
	}
	public void setmatricula(int matricula) {
		this.matricula = matricula;
	}
	

	public double getNota1() {
		return nota1;
	}
	public void setNota1(double nota1) {
		this.nota1 = nota1;
	}
	public double getNota2() {
		return nota2;
	}
	public void setNota2(double nota2) {
		this.nota2 = nota2;
	}

	

}
