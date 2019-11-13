package Menu;

import java.util.Locale;
import java.util.Scanner;

import javax.sound.sampled.BooleanControl.Type;


import DAO.AlunoDAO;
import DAO.ProfessorDAO;
import Entidade.Aluno;
import Entidade.Professor;
import Entidade.Turma;



public class Menu {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AlunoDAO alunoDAO = new AlunoDAO();
		ProfessorDAO professorDAO = new ProfessorDAO();

		 
		
	    int option;
		Scanner scanner = new Scanner(System.in);
		scanner.useLocale(Locale.ENGLISH);
		boolean end = false;

		while(!end) {
			System.out.println("   Menu   ");
			System.out.println("| 1 | Menu Aluno");
			System.out.println("| 2 | Menu Professor");
			System.out.println("| 0 | Sair");


			option = scanner.nextInt();
			scanner.nextLine();

			switch (option){
			case 1:{
				while(!end) {
					System.out.println("   Menu  Aluno ");
					System.out.println("| 1 | Cadastrar Aluno");
					System.out.println("| 2 | Fazer Login");
					System.out.println("| 0 | Sair");


					option = scanner.nextInt();
					scanner.nextLine();

					switch (option){
					case 1:{
						
				String nome;
				int matricula;
				System.out.println("Digite o nome do usuario:");
				nome = scanner.nextLine();
				System.out.println("Digite a matricula do usuario");
				matricula = scanner.nextInt();			
				if(alunoDAO.getAlunobynameandMatricula(nome, matricula)==null) {
				Aluno aluno = new Aluno(nome,matricula);
				if(alunoDAO.adiciona(aluno)) {
					System.out.println("Inserido com sucesso!");
					break;
				}else {
					System.err.println("Erro ao inserir o usuario.");	
				}
					
				}else {
					System.err.println("Erro ao inserir o usuario.");
				}
		
				}
					case 2:{
						System.out.println("Digite o nome do usuario:");
						String nome = scanner.nextLine();
						System.out.println("Digete a senha do usuario");
						int matricula = scanner.nextInt();
						Aluno aluno = new Aluno(nome,matricula);
						if(alunoDAO.getAlunobynameandMatricula(nome, matricula)!=null) {
							while(!end) {
								System.out.println("♩Menu Aluno ");
								System.out.println("| 1 | Ver Nota");
								System.out.println("| 2 | Apagar Conta");
								System.out.println("| 0 | Sair");

								option = scanner.nextInt();
								scanner.nextLine();

								switch (option){ 		
								case 1:
			
								System.out.println(alunoDAO.getNotaByMatricula(matricula));
								alunoDAO.getMediabyMatricula(matricula);
							break;

							case 2:{
								System.out.println("Tem certeza que quer deletar a sua conta?");
								System.out.println("1-Sim 2-Nao");
								int res = scanner.nextInt();
								if (res == 1) {
							    alunoDAO.deleteUser(matricula);
								}
								else {
									break;
								}
							
							}
			
								case 0:{
									end = true;
									break;
								}
						}
					   
						
					}
			    
				}
						else {
							System.out.println("Usuario nao encontrado");
						}
			}
					case 0:{
						end = true;
						break;
					}
				}
				}
				break;
			}
			case 2:{
				while(!end) {
					System.out.println("   Menu  Professor ");
					System.out.println("| 1 | Cadastrar Professor");
					System.out.println("| 2 | Fazer Login");
					System.out.println("| 0 | Sair");
					option = scanner.nextInt();
					scanner.nextLine();
					switch (option){
					case 1:{
						String nome;
						int matricula;
						System.out.println("Digite o nome do usuario:");
						nome = scanner.nextLine();
						System.out.println("Digete a matricula do usuario");
						matricula = scanner.nextInt();			
						if(professorDAO.getProfessorbynameandMatricula(nome, matricula)==null) {
						Professor prof= new Professor(nome,matricula);
						if(professorDAO.adiciona(prof)) {
							professorDAO.adicionaProfTurma(matricula);
							System.out.println("Inserido com sucesso!");
							break;
						}else {
							System.err.println("Erro ao inserir o usuÃ¡rio.");	
						}
							
						}else {
							System.err.println("Erro ao inserir o usuÃ¡rio.");
						}
						break;
					}
					case 2:{
						System.out.println("Digite o nome do usuario:");
						String nome = scanner.nextLine();
						System.out.println("Digite a matricula do usuario");
						int matricula = scanner.nextInt();
						Professor prof = new Professor(nome,matricula);
						if(professorDAO.getProfessorbynameandMatricula(nome, matricula)!=null) {
							while(!end) {
								System.out.println("♩Menu Professor");
								System.out.println("| 1 | Ver Turma");
								System.out.println("| 2 | Cadastrar nota");
								System.out.println("| 3 | Apagar Conta");
								System.out.println("| 0 | Sair");
								option = scanner.nextInt();
								scanner.nextLine();
								switch (option){ 		
								case 1:{
								System.out.println(professorDAO.getAlunoTurma(1,matricula));
							break;
								}
								case 2:{
									System.out.println(professorDAO.getAlunoTurma(1,matricula));
									System.out.println("Digite a matricula do aluno");
									int matriculaAluno = scanner.nextInt();
									System.out.println(professorDAO.getAlunoByMatricula(matriculaAluno));
									if(professorDAO.getAlunoByMatricula(matriculaAluno)!=null) {
										System.out.println("Digite a nota 1 do aluno");
										Double nota1;
										nota1 = scanner.nextDouble();
										System.out.println("Digite a nota 2 do aluno");
										Double nota2;
										nota2 = scanner.nextDouble();
								 professorDAO.cadastrarNota(matriculaAluno, nota1, nota2);			
									}	
								}break;
							case 3:{
								System.out.println("Tem certeza que quer deletar a sua conta?");
								System.out.println("1-Sim 2-Nao");
								int res = scanner.nextInt();
								if (res == 1) {
									professorDAO.deleteUser(matricula);
								}
								else {
									break;
								}
							}
								case 0:{
									end = true;
									break;
								}
						}
		
					}
			    
				}
						else {
							System.out.println("Usuario nao encontrado");
						}
						break;
					
					}
					case 0:{
						end = true;
						break;
					}
					}
					
					}
					
				break;
			}
	
			}
		}
	}
}
