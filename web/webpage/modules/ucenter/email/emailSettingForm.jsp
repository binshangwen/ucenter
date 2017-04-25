<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>邮箱信息管理</title>
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
		<form:form id="inputForm" modelAttribute="emailSetting" action="${ctx}/ucenter/email/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>邮箱名称：</label></td>
					<td class="width-35">
						<form:input path="emailName" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>邮箱帐号：</label></td>
					<td class="width-35">
						<form:input path="emailAccount" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>邮箱密码：</label></td>
					<td class="width-35">
						<form:input path="emailSecret" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>邮箱host：</label></td>
					<td class="width-35">
						<form:input path="stmpHost" htmlEscape="false" maxlength="100" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>邮箱端口：</label></td>
					<td class="width-35">
						<form:input path="stmpPort" htmlEscape="false" maxlength="11" class="form-control required digits"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>发送邮件模版：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="registerModel"   class="form-control required" rows="6" cols="56"></form:textarea>
					</td>

				</tr><tr>

					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>重置密码模版：</label></td>
					<td class="width-35" colspan="3">
						<form:textarea path="resetModel"   class="form-control required" rows="6" cols="56"></form:textarea>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>