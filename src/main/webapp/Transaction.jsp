<%@page import="Model.DTOModelSchema"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
 <link rel="stylesheet" href="./Transaction_CSS.css">
</head>
<body>
   <nav>
          <h1 class="title">Transaction</h1>
</nav>

<%ArrayList<DTOModelSchema> al=(ArrayList<DTOModelSchema>)request.getAttribute("data");
int income=0,expense=0;
%>

<main class="main_section">

        <table border="1">
            <tr><td colspan="4"><h1 style="color: darkorange;">Your Total Transaction</h1></td></tr>
            <tr>
                <th>DATE</th>
                <th>CATEGORY</th>
                <th>INCOME</th>
                <th>EXPENSES</th>
            </tr>
            <%for(DTOModelSchema dm:al){ 
	            income+=dm.getIncome();
	            expense+=dm.getExpense();
            %>
            <tr>
                <td><%= dm.getDate_() %></td>
                <td><%= dm.getCategory()%></td>
                <td><%= dm.getIncome() %></td>
                <td><%= dm.getExpense() %></td>
            </tr>
			<%} %>
            <tr>
                <td colspan="4">
                    <div class="footer_head">
                     <h2>Total Income</h2>
                     <h4><%= income %></h4>
                    </div>
                    <div class="footer_head">
                     <h2>Total Expenses</h2>
                     <h4><%= expense %></h4>
                    </div> <div class="footer_head">
                     <h2>Balance</h2>
                     <h4><%= income-expense %></h4>
                    </div>
            </td>
            </tr>
        </table>
</main>
</body>
</html>