<%@ page contentType="text/html;charset=utf-8" errorPage="error.jsp"%>

<table width="540" border="1" align="center" bgcolor="#dddddd">
<tr>
<td align = "center">
<FORM id="loginForm"  METHOD=POST ACTION="login.do">
<font color="red">
${requestScope.error}
</font>
<hr>
<TABLE>
<TR>
	<TD colspan="2" align="center">
		请输入用户名和密码登陆
	</TD>
</TR>
<TR height="10">
	<TD>
	</TD>
</TR>
<TR>
	<TD>用户名：</TD>
	<TD><INPUT id="name" TYPE="text" NAME="name"></TD>
</TR>
<TR>
	<TD>密&nbsp;&nbsp;码：</TD>
	<TD><INPUT id="pass" TYPE="text" NAME="pass"></TD>
</TR>
<TR>
	<TD colspan="2" align="center">
		<INPUT TYPE="submit" value="提交">
		<INPUT TYPE="reset" value="重设">
	</TD>
</TR>
</TABLE>
<br>
<div align="center">
<a href="reg.jsp">注册新用户</a>
</div>
</FORM>
</td>
</tr>
</table>
<script>
	function check()
	{
		var name = document.getElementById("name");
		var pass = document.getElementById("pass");
		var errStr = "";
		if (name.value == "" || name.value == null)
		{
			errStr += "用户名不能为空\n";
		}
		if (pass.value == "" || pass.value == null)
		{
			errStr += "密码不能为空\n";
		}
		if (errStr == "" || errStr == null)
		{
			return true;
		}
		alert(errStr);
		return false;
	}
	document.getElementById("loginForm").onsubmit = check;

</script>