package org.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class Main {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Inserisci stringa di ricerca: ");
		String search = sc.nextLine();
		
		sc.close();
		
		final String url = "jdbc:mysql://localhost:3306/db-nations";
		final String user = "root";
		final String password = "";
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			
			System.out.println("\n-----------------------------------------\n");
			System.out.println("Connessione al database stabilita corretamente");
			
			final String sql = " SELECT c.name AS nome, c.country_id AS id, r.name AS nome_regione, c2.name AS nome_continente " +
					" FROM countries c " +
					" JOIN regions r " +
					" ON r.region_id = c.region_id " +
					" JOIN continents c2 " +
					" ON c2.continent_id = r.continent_id " +
					"WHERE c.name LIKE ? " +
					" ORDER BY nome ";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + search + "%");
			
			ResultSet results = ps.executeQuery();
			
			Boolean trovato = false;
			int numeroRisultati = 0;
			
			System.out.println("\n-----------------------------------------\n");
			
			while (results.next()) {
				
				String nome = results.getString("nome");
				String id = results.getString("id");
				String nomeRegione = results.getString("nome_regione");
				String nomeContinente = results.getString("nome_continente");
				
				System.out.println("Nome: " + nome + " | " + "Id: " + id + " | " + "Regione: " + nomeRegione
									+ " | " + "Continente: " + nomeContinente);
				System.out.println("\n-----------------------------------------\n");
				
				trovato = true;
				numeroRisultati++;
				
			}
			
			System.out.println("Numero risultati: " + numeroRisultati);
			
			if (!trovato) {
				System.out.println("Nessun risultato trovato");
			}
		} catch (Exception e) {
			
			System.out.println("Errore di connessione" + e.getMessage());
		}
	}
}
