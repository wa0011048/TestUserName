<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Username Check Form</title>
<script type="text/javascript">
function validate(){
	var f=document.getElementById("userName").value;
	if(	f.length >=6){
		return true;
	}else{	
		document.getElementById("userNameError").innerHTML="please enter 6 characters long";
		document.getElementById("results").innerHTML="";
		return false;
	}
}
</script>
</head>

<body>
	<form:form name="submitForm" method="POST">

		<div align="center">
			<table>
				<tr>
					<td colspan="2"><b>${msg}</b></td>
				</tr>
				<tr>
					<td>User Name</td>
					<td><input type="text" name="userName" id="userName" 
						value="<c:out value="${userName}"/>"/></td>
				</tr>
				<tr>
					<td colspan="2"><div style="color: red" id="userNameError"></div></td>
				</tr>
				<tr>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="CheckUserName" onClick="return validate()"/></td>
				</tr>
			</table>

			<div id ="results">
				<table>
					<tr>
						<c:choose>
							<c:when test="${userNameExist==true}">
							<td colspan="2">
								<br/><br/><b><c:out value="User Name already exist !"> <br/></c:out></b>
							</td>
							</c:when>
							<c:otherwise>
							<td colspan="2">
								<c:if test="${userNameExist==false}">
									<b> <c:out value="Please use one of the following suggested username"></c:out> </b> <br/>
								</c:if>
							</td>
							<tr>
							<td>
								<c:forEach var="alternateUserName" items="${alternateUserNameSet}">									
									<c:out value="${alternateUserName}"></c:out><br/>
								</c:forEach>
							</td>
							</tr>
							</c:otherwise>
						</c:choose>					
				</tr>
			</table>
			</div>
			<div style="color: red">${error}</div>

		</div>
	</form:form>
</body>
</html>