package connect;

public class TestConnectNew {

	public static void main(String[] args) {

		//mở database, đóng database thì ghi thế này để tự đóng, ko cần finally để close

		try (var conn = service.ConnectDB.getCon()) {
			System.out.println("Connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
