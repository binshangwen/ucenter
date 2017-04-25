<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>QQ信息管理</title>
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
		<form:form id="inputForm" modelAttribute="qqSetting" action="${ctx}/ucenter/qq/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-25" colspan="3">
						<form:input path="name" htmlEscape="false" maxlength="45" class="form-control required"/>
					</td>

				</tr><tr>

					<td class="width-15 active"><label class="pull-right">android平台APPID：</label></td>
					<td class="width-25" colspan="3">
						<form:input path="androidAppid" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">android平台密钥：</label></td>
					<td class="width-25" colspan="3">
						<form:input path="androidSecret" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>

				</tr>
				<tr>

					<td class="width-15 active"><label class="pull-right">ios平台APPID：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="iosAppid" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">ios平台密钥：</label></td>
					<td class="width-25" colspan="3">
						<form:input path="iosSecret" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>

				</tr>
				<tr>

					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>获取AccessToken的url：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="getTokenUrl" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>获取用户信息的url：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="getInfoUrl" htmlEscape="false" maxlength="200" class="form-control required"/>
					</td>

		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>