package Entidade;
import java.util.ArrayList;

public class Turma {
   ArrayList <Aluno> alunos = new ArrayList <Aluno>();
   ArrayList <Professor> professores = new ArrayList <Professor>();

public ArrayList<Professor> getProfessores() {
	return professores;
}

public void setProfessores(ArrayList<Professor> professores) {
	this.professores = professores;
}

public ArrayList<Aluno> getAlunos() {
	return alunos;
}

public void setAlunos(ArrayList<Aluno> alunos) {
	this.alunos = alunos;
}

@Override
public String toString() {
	return "Turma [alunos=" + alunos + "]";
}






   
}
