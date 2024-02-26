package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.DTOModelSchema;
import Model.DataBaseOperation;

@WebServlet(urlPatterns = {"/addIncome","/addExpenses","/seeTransaction","/income_details","/expenses_details","/monthly_transaction"})
public class ControllerServletClass extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd=null;
		switch(req.getServletPath()){
		case "/addIncome":
			rd=req.getRequestDispatcher("Add_Income.html");
			rd.forward(req, resp);
			break;
		case "/addExpenses":
			rd=req.getRequestDispatcher("Add_Expenses.html");
			rd.forward(req, resp);
			break;
		case "/seeTransaction":
			rd=req.getRequestDispatcher("Transaction.jsp");
			req.setAttribute("data", new DataBaseOperation().getData());
			rd.forward(req, resp);
			break;
		case "/monthly_transaction":
			String startdate=req.getParameter("startdate");
			String enddate=req.getParameter("enddate");
			PrintWriter pw=resp.getWriter();
			rd=req.getRequestDispatcher("ExpenseManager.html");
			rd.include(req, resp);
			displayMonthlyRecord(req,resp,new DataBaseOperation().getmonthlyData(startdate,enddate),pw);
			break;
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		switch(req.getServletPath()){
		case "/income_details":
			addIncomeDetails(req,resp);
			break;
		case "/expenses_details":
			addExpensesDetails(req,resp);
			break;
		}
	}
	
private void displayMonthlyRecord(HttpServletRequest req, HttpServletResponse resp, ArrayList<DTOModelSchema> arrayList, PrintWriter pw) {
	pw.print("<table>\r\n"
			+ "                            <tr>\r\n"
			+ "                            <th>Category</th>\r\n"
			+ "                              <th>Income</th>\r\n"
			+ "                              <th>Expence</th>\r\n"
			+ "                              <th>Balance</th>\r\n"
			+ "                            </tr> \r\n"
			);
	long bal=0;
	for(DTOModelSchema d:arrayList) {
		bal+=d.getIncome();
		bal-=d.getExpense();
	pw.print("                            <tr>\r\n"
			+ "                            <td>"+d.getCategory()+"</td>\r\n"
			+ "                              <td>"+d.getIncome()+"</td>\r\n"
			+ "                              <td>"+d.getExpense()+"</td>\r\n"
			+ "                              <td>"+bal+"</td>\r\n"
			+ "                            </tr> \r\n"
			);}
	pw.print("                            <tr>\r\n"
			+ "                              <td colspan=\"4\"><span>Balance:"+bal+"</span></td>\r\n"
			+ "                            </tr>\r\n"
			+ "                    </table>");
	
		
	}

	private void addExpensesDetails(HttpServletRequest req, HttpServletResponse resp) {
		DTOModelSchema dt=new DTOModelSchema();
		dt.setCategory(req.getParameter("category"));
		dt.setExpense(Integer.parseInt(req.getParameter("income")));
		dt.setPayment(req.getParameter("payment"));
		dt.setNote(req.getParameter("note"));
		dt.setDate_(req.getParameter("date"));
		dt.setTime_(req.getParameter("time"));
		try {
			PrintWriter pw=resp.getWriter();
			if(new DataBaseOperation().addExpenses(dt)>0)
			{RequestDispatcher rd=req.getRequestDispatcher("Add_Expenses.html");
				rd.include(req, resp);
				pw.print("<h2>Data added..</h2>");
			}
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
		
	}
	private void addIncomeDetails(HttpServletRequest req, HttpServletResponse resp) {
		DTOModelSchema dt=new DTOModelSchema();
		dt.setCategory(req.getParameter("category"));
		dt.setIncome(Integer.parseInt(req.getParameter("income")));
		dt.setPayment(req.getParameter("payment"));
		dt.setNote(req.getParameter("note"));
		dt.setDate_(req.getParameter("date"));
		dt.setTime_(req.getParameter("time"));
		try {
			PrintWriter pw=resp.getWriter();
			if(new DataBaseOperation().addIncome(dt)>0)
			{RequestDispatcher rd=req.getRequestDispatcher("Add_Income.html");
				rd.include(req, resp);
				pw.print("<h2>Data added..</h2>");
			}
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
		
	}

}
