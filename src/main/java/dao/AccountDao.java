
package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import entity.Account;
import entity.Classes;
import entity.GradeStu;
import service.ConnectDB;

public class AccountDao {

	public boolean validateAccount(String accEmail, String accPass) {
        try (
            var conn = ConnectDB.getCon();
				    var cs = conn.prepareCall("{CALL ValidateAccount(?, ?)}")
        ) {
					cs.setString(1, accEmail);
					cs.setString(2, accPass);
            try (var rs = cs.executeQuery()) {
                if (rs.next()) {
									return rs.getInt("IsValid") == 1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

		public List<Account> selectAccount() {
			List<Account> list = new ArrayList<>();

			try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectAcc()}"); var rs = cs.executeQuery()) {

				while (rs.next()) {
					var acc = new Account();
					acc.setAccId(rs.getInt("accId"));
					acc.setAccEmail(rs.getString("accEmail"));
					acc.setAccPass(rs.getString("accPass"));
					acc.setAccCreateDate(rs.getDate("CreatedDate").toLocalDate());
					acc.setAccStatus(rs.getBoolean("accStatus"));
					acc.setEmpId(rs.getInt("empId"));
					acc.setRoleId(rs.getInt("roleId"));

					// only add non-admin account
					if (acc.getRoleId() != 1) {
						list.add(acc);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return list;
		}
		
		public List<Account> pagingAccount(int currentPage, int numberOfRows){
			List<Account> list = new ArrayList<>();

			try(
					var conn = ConnectDB.getCon();
					var cs = conn.prepareCall("{call pagingAccount(?,?)}")
					){
				cs.setInt(1, currentPage);
				cs.setInt(2, numberOfRows);
				var rs = cs.executeQuery();
				while (rs.next()) {
					var acc = new Account();
					acc.setAccId(rs.getInt("accId"));
					acc.setAccEmail(rs.getString("accEmail"));
					acc.setAccPass(rs.getString("accPass"));
					acc.setAccCreateDate(rs.getDate("CreatedDate").toLocalDate());
					acc.setAccStatus(rs.getBoolean("accStatus"));
					acc.setEmpId(rs.getInt("empId"));
					acc.setRoleId(rs.getInt("roleId"));
				}

			}catch(Exception e) {
				e.printStackTrace();
			}


			return list;


		}
		
		public Integer countAcc() {
			var count = 0;

			try (var conn = ConnectDB.getCon();
					var cs = conn.prepareCall("{call countAccount()}");
					var rs = cs.executeQuery();
					) {
			
				if (rs.next()) {
					count = rs.getInt("total");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return count;
		}
		
		public void insertAccount(Account acc) {
			try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call insertAcc(?, ?, ?, ?, ?)}")) {
				cs.setString(1, acc.getAccEmail());
				cs.setString(2, acc.getAccPass());
				cs.setInt(3, acc.getRoleId());
				cs.setBoolean(4, acc.getAccStatus());
				cs.setInt(5, acc.getEmpId());
				cs.execute();
				JOptionPane.showMessageDialog(null, "Add Success");
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Add Fail");
			}
		}

		public void updateAccount(Account acc) {
			try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call updateAcc(?, ?, ?, ?, ?, ?, ?)}")) {
				cs.setInt(1, acc.getAccId());
				cs.setString(2, acc.getAccEmail());
				cs.setString(3, acc.getAccPass());
				cs.setInt(4, acc.getRoleId());
				cs.setDate(5, java.sql.Date.valueOf(acc.getAccCreateDate()));
				cs.setBoolean(6, acc.getAccStatus());
				cs.setInt(7, acc.getEmpId());
				cs.execute();
				JOptionPane.showMessageDialog(null, "Update successfully!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
//		Phuong part
		public Account selectOneAccount(String accEmail) {
			var acc = new Account();

			try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call selectOneAcc(?)}")) {
				
				cs.setString(1, accEmail);
				var rs = cs.executeQuery();
				
				while (rs.next()) {
					acc.setAccId(rs.getInt("accId"));
					acc.setAccEmail(rs.getString("accEmail"));
					acc.setAccPass(rs.getString("accPass"));
					acc.setAccCreateDate(rs.getDate("CreatedDate").toLocalDate());
					acc.setAccStatus(rs.getBoolean("accStatus"));
					acc.setEmpId(rs.getInt("empId"));
					acc.setRoleId(rs.getInt("roleId"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return acc;
		}
}
