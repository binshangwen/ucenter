<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>生成方案管理</title>
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
			$("#name").focus();
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

<form:form id="inputForm" modelAttribute="menu"  class="form-horizontal" action="${ctx}/gen/genScheme/createMenu" method="post">
	<form:hidden path="id"/>
	<!-- 0:隐藏tip, 1隐藏box,不设置显示全部 -->

	<script type="text/javascript">top.$.jBox.closeTip();</script>

	<input type="hidden" name="gen_table_id" value="${gen_table_id}"/>
	<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		<tbody>
		<tr>
			<td  class="width-15 active"><label class="pull-right">上级菜单:</label></td>
			<td class="width-35" >
				<sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
								title="菜单" url="/sys/menu/treeData" extId="${menu.id}" cssClass="form-control required"/>
			</td>
			<td  class="width-15 active"><label class="pull-right"><font color="red">*</font> 名称:</label></td>
			<td  class="width-35" ><form:input path="name" class="required form-control " type="text"  maxlength="50"/></td>
		</tr>

		<tr>
			<td  class="width-15 active"><label class="pull-right">图标:</label></td>
			<td class="width-35" >
				<sys:iconselect id="icon" name="icon" value="${menu.icon}"/>
			</td>
			<td  class="width-15 active"><label class="pull-right">排序:</label></td>
			<td  class="width-35" >
				<form:input path="sort"  class="required digits form-control " type="text" value="2030" maxlength="50"/>
				<span class="help-inline">排列顺序，升序。</span></td>
		</tr>
		</tbody>
	</table>
</form:form>
</body>
</html>
