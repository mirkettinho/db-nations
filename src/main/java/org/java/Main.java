package org.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Main {
	
	public static void main(String[] args) {
		
		final String url = "jdbc:mysql://localhost:3306/db-nations";
		final String user = "root";
		final String password = "";
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			
			System.out.println("Connessione stabilita corretamente");
			
			final String sql = " SELECT c.name AS nome, c.country_id AS id, r.name AS nome_regione, c2.name AS nome_continente " +
					" FROM countries c " +
					" JOIN regions r " +
					" ON r.region_id = c.region_id " +
					" JOIN continents c2 " +
					" ON c2.continent_id = r.continent_id " +
					" ORDER BY nome ";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet results = ps.executeQuery();
			
			System.out.println("\n-----------------------------------------\n");
			
			while (results.next()) {
				
				String nome = results.getString("nome");
				String id = results.getString("id");
				String nomeRegione = results.getString("nome_regione");
				String nomeContinente = results.getString("nome_continente");
				
				System.out.println("Nome: " + nome + " | " + "Id: " + id + " | " + "Regione: " + nomeRegione
									+ " | " + "Continente: " + nomeContinente);
				System.out.println("\n-----------------------------------------\n");
			}
		} catch (Exception e) {
			
			System.out.println("Errore di connessione" + e.getMessage());
		}
	}
}
