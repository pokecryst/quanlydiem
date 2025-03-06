package dao;

import java.util.ArrayList;
import java.util.List;

import entity.ClassStatus;
import entity.Course;
import service.ConnectDB;

public class ClassStatusDao {
	
	public List<ClassStatus> selectAll() {
		List<ClassStatus> list = new ArrayList<>();
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectClStatus()}");) {
			var rs = cs.executeQuery();
			while (rs.next()) {
				var cl = new ClassStatus();
				cl.setClStatID(rs.getInt("clStatID"));
				cl.setClStatName(rs.getString("clStatName"));
				
				list.add(cl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	
	public ClassStatus selectClassStatusByID(int id) {
	    ClassStatus classStatus = null;
	    try (var conn = ConnectDB.getCon();
	         var cs = conn.prepareCall("{call selectClassStatusByID(?)}")) {

	        cs.setInt(1, id);
	        try (var rs = cs.executeQuery()) {
	            if (rs.next()) {
	                classStatus = new ClassStatus();
	                classStatus.setClStatID(rs.getInt("clStatID"));
	                classStatus.setClStatName(rs.getString("clStatName"));
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return classStatus;  // Will be null if no record is found
	}


}
