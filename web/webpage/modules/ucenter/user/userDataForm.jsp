<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户注册登录管理</title>
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
		<form:form id="inputForm" modelAttribute="userData" action="${ctx}/ucenter/user/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>

				<tr>
					<td class="width-15 active"><label class="pull-right">登录名：</label></td>
					<td class="width-35">
						<form:input path="userName" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">登录密码：</label></td>
					<td class="width-35">
						<form:input path="passwd" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">姓：</label></td>
					<td class="width-35">
						<form:input path="firstName" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">名：</label></td>
					<td class="width-35">
						<form:input path="lastName" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">用户昵称：</label></td>
					<td class="width-35">
						<form:input path="nickName" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">所在地：</label></td>
					<td class="width-35">
						<form:input path="locale" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>


				<tr>
					<td class="width-15 active"><label class="pull-right">年龄：</label></td>
					<td class="width-35">
						<form:input path="age" htmlEscape="false" maxlength="11" class="form-control  digits"/>
					</td>
					<td class="width-15 active"><label class="pull-right">性别：</label></td>
					<td class="width-35">
						<c:if test="${userData.gender==1}">男</c:if>
						<c:if test="${userData.gender==2}">女</c:if>
						<c:if test="${userData.gender==3}">其他</c:if>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">博客：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="blog" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>

				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">注册类型：</label></td>
					<td class="width-35">
						<form:input path="registerBy" htmlEscape="false" maxlength="45" class="form-control " readonly="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right">注册平台：</label></td>
					<td class="width-35">
						<form:input path="registerPlatform" htmlEscape="false" maxlength="100" class="form-control " readonly="true"/>
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
						<form:textarea path="signature" class="form-control "  cols="56" rows="8"></form:textarea>
					</td>

				</tr>
				<tr>

					<td class="width-15 active"><label class="pull-right">注册IP：</label></td>
					<td class="width-35" colspan="3">
						<form:input path="registerIp" htmlEscape="false" maxlength="200" class="form-control " readonly="true"/>
					</td>
				</tr>

				<tr>
					<td class="width-15 active"><label class="pull-right">用户认证状态：</label></td>
					<td class="width-35">
						<c:if test="${userData.verified==1}">已认证</c:if>
						<c:if test="${userData.verified==2}">未认证</c:if>
					</td>
					<td class="width-15 active"><label class="pull-right">微信关联：</label></td>
					<td class="width-35">
						<c:if test="${userData.linkedWechat==1}">是</c:if>
						<c:if test="${userData.linkedWechat==2}">否</c:if>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">微信OPENID：</label></td>
					<td class="width-35">
						<form:input path="wechatOpenid" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">微博关联：</label></td>
					<td class="width-35">

						<c:if test="${userData.linkedWeibo==1}">是</c:if>
						<c:if test="${userData.linkedWeibo==2}">否</c:if>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">微博OPENID：</label></td>
					<td class="width-35">
						<form:input path="weiboOpenid" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">邮箱关联：</label></td>
					<td class="width-35">
						<c:if test="${userData.linkedEmail==1}">是</c:if>
						<c:if test="${userData.linkedEmail==2}">否</c:if>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">邮箱认证：</label></td>
					<td class="width-35">
						<c:if test="${userData.verifiedEmail==1}">已认证</c:if>
						<c:if test="${userData.verifiedEmail==2}">未认证</c:if>
					</td>
					<td class="width-15 active"><label class="pull-right">QQ关联：</label></td>
					<td class="width-35">
						<c:if test="${userData.linkedQq==1}">是</c:if>
						<c:if test="${userData.linkedQq==2}">否</c:if>

					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">QQ认证平台：</label></td>
					<td class="width-35">
						<form:input path="linkedQqPlatform" htmlEscape="false" maxlength="200" class="form-control " />
					</td>
					<td class="width-15 active"><label class="pull-right">QQ关联：</label></td>
					<td class="width-35">
						<form:input path="qqOpenid" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>