package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Accoppiamenti;
import it.polito.tdp.artsmia.model.ArtObject;
import sun.java2d.SunGraphics2D;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects(Map<Integer, ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				idMap.put(artObj.getId(), artObj);
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Accoppiamenti> creoArchi() {
		
		//Join della stessa tabella in modo da avere i una tabella con 3 colonne: id_esibizione, id_oggetto1, id_oggetto2 e il conteggio ( condizione con > per togliere duplicati!)
		final String sql = "SELECT eo1.object_id AS id1, eo2.object_id AS id2, COUNT(*) AS cnt FROM exhibition_objects eo1, exhibition_objects eo2 WHERE eo1.exhibition_id = eo2.exhibition_id AND eo2.object_id > eo1.object_id GROUP BY eo1.object_id, eo2.object_id";
	
		List<Accoppiamenti> accoppiamenti = new ArrayList<>();
		
		Connection conn = DBConnect.getConnection();

		try {
			
			PreparedStatement st = conn.prepareStatement(sql);
		    //Eseguo query
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				accoppiamenti.add(new Accoppiamenti(res.getInt("id1"),res.getInt("id2"),res.getInt("cnt")));
			}
			
			//Chiudo connessione
			conn.close();
			
			return accoppiamenti;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
