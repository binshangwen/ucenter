<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>微博用户信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body>
		<form:form id="inputForm" modelAttribute="qqUser" action="${ctx}/ucenter/qquser/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   <tr>
			   <td class="width-15 active"><label class="pull-right">用户昵称：</label></td>
			   <td class="width-35">
				   <form:input path="nickName" htmlEscape="false" maxlength="200" class="form-control "/>
			   </td>
			   <td class="width-15 active"><label class="pull-right">性别：</label></td>
			   <td class="width-35">
				   <c:if test="${qqUser.gender==1}">男</c:if>
				   <c:if test="${qqUser.gender==2}">女</c:if>
				   <c:if test="${qqUser.gender==3}">其他</c:if>
			   </td>
		   </tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">年龄：</label></td>
					<td class="width-35">
						<form:input path="age" htmlEscape="false" maxlength="11" class="form-control  digits"/>
					</td>
					<td class="width-15 active"><label class="pull-right">code值：</label></td>
					<td class="width-35">
						<form:input path="code" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">授权码：</label></td>
					<td class="width-35">
						<form:input path="openId" htmlEscape="false" maxlength="300" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">AccessToken：</label></td>
					<td class="width-35">
						<form:input path="accessToken" htmlEscape="false" maxlength="300" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">refreshToken：</label></td>
					<td class="width-35">
						<form:input path="refreshToken" htmlEscape="false" maxlength="300" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">注册平台：</label></td>
					<td class="width-35">
						<form:input path="signPlatform" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>


				<tr>
					<td class="width-15 active"><label class="pull-right">用户大头像：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="avatarLarge" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">用户中头像：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="avatarMid" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>

				</tr>
				<tr>

					<td class="width-15 active"><label class="pull-right">用户小头像：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="avatarSmall" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>
				<tr>

					<td class="width-15 active"><label class="pull-right">个性签名：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="signature"   class="form-control required" rows="6" cols="56"></form:textarea>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>