
package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import entity.Account;
import entity.Classes;
import entity.GradeStu;
import service.ConnectDB;

public class AccountDao {
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//	public String validateAccount(String accEmail, String accPass) {
//        try (
//            var conn = ConnectDB.getCon();
//				    var cs = conn.prepareCall("{CALL ValidateAccount(?, ?)}")
//        ) {
//					cs.setString(1, accEmail);
//					cs.setString(2, accPass);
//					try (var rs = cs.executeQuery()) {
//						if (rs.next() && rs.getInt("IsValid") == 1) {
//							var roleId = rs.getInt("roleId");
//							// You can use roleId here if needed
//							return "Valid account with roleId: " + roleId;
//						}
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					return "Invalid account";
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return "Error occurred";
//        }
//    }

	public String validateAccount(String accEmail, String accPass) {
        try (
            var conn = ConnectDB.getCon();
				    var cs = conn.prepareCall("{CALL ValidateAccountEmail(?)}")
        ) {
					cs.setString(1, accEmail);
					try (var rs = cs.executeQuery()) {
						if (rs.next() && rs.getInt("IsValid") == 1 && this.validatePass(accPass, rs.getString("accPass")) && rs.getBoolean("accStatus")) {
							var roleId = rs.getInt("roleId");
							// You can use roleId here if needed
							return "Valid account with roleId: " + roleId;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return "Invalid account";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }
	
	public boolean validatePass(String inputtedPass, String accPass) {
		return encoder.matches(inputtedPass, accPass);
	}
	
	public boolean validatePass(String inputtedPass, Account acc) {
		var check = false;
		try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call returnPassOnly(?)}")) {
			
			cs.setInt(1, acc.getAccId());
			var rs = cs.executeQuery();
			
			while (rs.next()) {
				check = encoder.matches(inputtedPass, rs.getString("accPass"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
		
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
					list.add(acc);
					
					
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
					list.add(acc);
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
				cs.setString(2, acc.encodePass(acc.getAccPass()));
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
			try (var conn = ConnectDB.getCon(); var cs = conn.prepareCall("{call updateAcc(?, ?, ?, ?, ?, ?)}")) {
				cs.setInt(1, acc.getAccId());
				cs.setString(2, acc.getAccEmail());
				cs.setString(3, acc.getAccPass());
				cs.setInt(4, acc.getRoleId());
				cs.setBoolean(5, acc.getAccStatus());
				cs.setInt(6, acc.getEmpId());
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
