package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DataBaseOperation {
	private static String fetchData="select * from expenses_management;";
	private static String add_value="insert into expenses_management values(?,?,?,?,?,?,?,?);";
	private static String monthlyfetch="select * from expenses_management where date_ between ? and ?;";
	private static Connection connection;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet","root","pawshekar");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int addIncome(DTOModelSchema ds) {
		try {
			PreparedStatement ps=connection.prepareStatement(add_value);
			ps.setInt(1, 0);
			ps.setString(2, ds.getCategory());
			ps.setString(3, ds.getPayment());
			ps.setString(4, ds.getNote());
			ps.setDate(5, Date.valueOf(ds.getDate_()));
			ps.setTime(6, Time.valueOf(ds.getTime_()+":00"));
			ps.setInt(7, ds.getIncome());
			ps.setInt(8, 0);
			return ps.executeUpdate();
		} catch (SQLException  e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int addExpenses(DTOModelSchema ds) {
		try {
			PreparedStatement ps=connection.prepareStatement(add_value);
			ps.setInt(1, 0);
			ps.setString(2, ds.getCategory());
			ps.setString(3, ds.getPayment());
			ps.setString(4, ds.getNote());
			ps.setDate(5, Date.valueOf(ds.getDate_()));
			ps.setTime(6, Time.valueOf(ds.getTime_()+":00"));
			ps.setInt(7, 0);
			ps.setInt(8, ds.getExpense());
			return ps.executeUpdate();
		} catch (SQLException  e) {
			e.printStackTrace();
		}
		return 0;
	}
	public ArrayList<DTOModelSchema> getData(){
		ArrayList< DTOModelSchema> al=new ArrayList<DTOModelSchema>();
		DTOModelSchema dm=null;
		try {
			PreparedStatement ps=connection.prepareStatement(fetchData);
			ResultSet s=ps.executeQuery();
			while (s.next()) {
				dm=new DTOModelSchema();
				dm.setCategory(s.getString(2));
				dm.setDate_(String.valueOf(s.getDate(5)));
				dm.setIncome(s.getInt(7));
				dm.setExpense(s.getInt(8));
				al.add(dm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}
	
	
	public ArrayList<DTOModelSchema> getmonthlyData(String startdate,String enddate){
		ArrayList< DTOModelSchema> al=new ArrayList<DTOModelSchema>();
		DTOModelSchema dm=null;
		try {
			PreparedStatement ps=connection.prepareStatement(monthlyfetch);
			ps.setDate(1, Date.valueOf(startdate));
			ps.setDate(2, Date.valueOf(enddate));
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				dm=new DTOModelSchema();
				dm.setCategory(rs.getString(2));
				dm.setIncome(rs.getInt(7));
				dm.setExpense(rs.getInt(8));
				al.add(dm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return al;
	}
}
