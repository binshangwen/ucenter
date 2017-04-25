<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
	<title>生成代码</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="JEE-Framework"/>
    <%@include file="/webpage/include/head.jsp"%>
	<script type="text/javascript">var ctx = '/a', ctxStatic='/static';</script>
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

<form:form id="inputForm" modelAttribute="genScheme" class="form-horizontal" action="${ctx}/gen/genScheme/genCode" method="post">
	<form:hidden path="id"/>

	<!-- 0:隐藏tip, 1隐藏box,不设置显示全部 -->
	<script type="text/javascript">top.$.jBox.closeTip();</script>

	<div class="control-group">
	</div>
	<div class="control-group">
		<label class="control-label"><font color="red">*</font>代码风格:</label>
		<div class="controls">
			<form:select path="category" name="category" class="required form-control">
				<form:option value="curd">增删改查（单表）</form:option>
				<form:option value="curd_many">增删改查（一对多）</form:option>
				<form:option value="treeTable">树结构表（一体）</form:option>
			</form:select>
				<span class="help-inline">
					生成结构：{包名}/{模块名}/{分层(dao,entity,service,web)}/{子模块名}/{java类}
				</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><font color="red">*</font>生成包路径:</label>
		<div class="controls">
			<form:input path="packageName" class="required form-control" type="text" maxlength="500"/>
			<span class="help-inline">建议模块包：com.framework.modules</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><font color="red">*</font>生成模块名:</label>
		<div class="controls">
			<form:input path="moduleName" class="required form-control" type="text"  maxlength="500"/>
			<span class="help-inline">可理解为子系统名，例如 sys</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">生成子模块名:</label>
		<div class="controls">
			<form:input path="subModuleName" class="form-control" type="text" maxlength="500"/>
			<span class="help-inline">可选，分层下的文件夹，例如 </span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><font color="red">*</font>生成功能描述:</label>
		<div class="controls">
			<form:input path="functionName" class="required form-control" type="text"  maxlength="500"/>
			<span class="help-inline">将设置到类描述</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><font color="red">*</font>生成功能名:</label>
		<div class="controls">
			<form:input path="functionNameSimple" class="required form-control" type="text"  maxlength="500"/>
			<span class="help-inline">用作功能提示，如：保存“某某”成功</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><font color="red">*</font>生成功能作者:</label>
		<div class="controls">
			<form:input path="functionAuthor" class="required form-control" type="text"  maxlength="500"/>
			<span class="help-inline">功能开发者</span>
		</div>
	</div>

	<form:input type="hidden" path="genTable.id" class="required form-control"/>
</form:form>
</body>
</html>
