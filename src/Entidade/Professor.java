package Entidade;

public class Professor {
  private String nome;
  private int matriculaProfessor;

public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public int getmatriculaProfessor() {
	return matriculaProfessor;
}
public void setmatriculaProfessor(int matriculaProfessor) {
	this.matriculaProfessor = matriculaProfessor;
}
@Override
public String toString() {
	return "Professor [nome=" + nome + ", matriculaProfessor=" + matriculaProfessor + "]";
}

public Professor(String nome, int matriculaProfessor) {
	super();
	this.nome = nome;
	this.matriculaProfessor = matriculaProfessor;
}
public Professor() {
	super();
}

 
}
