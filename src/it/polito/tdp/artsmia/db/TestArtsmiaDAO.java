package it.polito.tdp.artsmia.db;

import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;

public class TestArtsmiaDAO {

	public static void main(String[] args) {

		ArtsmiaDAO dao = new ArtsmiaDAO();
		Map<Integer,ArtObject> map = null;
		
		List<ArtObject> objects = dao.listObjects(map);
		System.out.println(objects.get(0));
		System.out.println(objects.size());
	}

}
