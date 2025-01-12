package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import service.ConnectDB;

public class GeneralDao {

	public interface ResultSetMapper<T> {
		T map(ResultSet rs) throws Exception;
	}

	public int countEntity(String column, String table) {
		var count = 0;

		try(
			var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call countSth(?,?)}")
			){
			cs.setString(1, column);
			cs.setString(2, table);
			var rs = cs.executeQuery();
			if(rs.next()){
        count = rs.getInt("total");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return count;

	}

	public <T> List<T> selectPaging(String table, String column, int currentpage, int numberofrows, ResultSetMapper<T> mapper) {
		List<T> list = new ArrayList<>();

		try (var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call paging(?,?,?,?)}");
		) {
			cs.setString(1, table);
			cs.setString(2, column);
			cs.setInt(3, currentpage);
			cs.setInt(4, numberofrows);

			var rs = cs.executeQuery();
			while (rs.next()) {
				list.add(mapper.map(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public <T> List<T> selectPagingWithIds(String table, String column, String ids, int currentpage, int numberofrows, ResultSetMapper<T> mapper) {
		List<T> list = new ArrayList<>();

		try (var conn = ConnectDB.getCon();
				var cs = conn.prepareCall("{call paging(?,?,?,?)}");
		) {
			cs.setString(1, table);
			cs.setString(2, column);
			cs.setInt(3, currentpage);
			cs.setInt(4, numberofrows);
			var rs = cs.executeQuery();
			while (rs.next()) {
				list.add(mapper.map(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}



}
