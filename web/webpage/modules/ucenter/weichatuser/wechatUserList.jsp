<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>微信用户信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>微信用户信息列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="wechatUser" action="${ctx}/ucenter/weichatuser/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>用户昵称：</span>
				<form:input path="nickName" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="weichatuser:wechatUser:add">
				<table:addRow url="${ctx}/ucenter/weichatuser/form" title="微信用户信息"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="weichatuser:wechatUser:edit">
			    <table:editRow url="${ctx}/ucenter/weichatuser/form" title="微信用户信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="weichatuser:wechatUser:del">
				<table:delRow url="${ctx}/ucenter/weichatuser/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="weichatuser:wechatUser:import">--%>
				<%--<table:importExcel url="${ctx}/ucenter/weichatuser/import"></table:importExcel><!-- 导入按钮 -->--%>
			<%--</shiro:hasPermission>--%>
			<%--<shiro:hasPermission name="weichatuser:wechatUser:export">--%>
	       		<%--<table:exportExcel url="${ctx}/ucenter/weichatuser/export"></table:exportExcel><!-- 导出按钮 -->--%>
	       	<%--</shiro:hasPermission>--%>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column code">code值</th>
				<th  class="sort-column signPlatform">注册平台</th>
				<th  class="sort-column nickName">用户昵称</th>
				<th  class="sort-column gender">性别</th>
				<th  class="sort-column createDate">创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wechatUser">
			<tr>
				<td> <input type="checkbox" id="${wechatUser.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看微信用户信息', '${ctx}/ucenter/weichatuser/form?id=${wechatUser.id}','800px', '500px')">
					${wechatUser.id}
				</a></td>
				<td>
					${wechatUser.code}
				</td>
				<td>
					${wechatUser.signPlatform}
				</td>
				<td>
					${wechatUser.nickName}
				</td>
				<td>
					<c:if test="${wechatUser.gender==1}">男</c:if>
					<c:if test="${wechatUser.gender==2}">女</c:if>
					<c:if test="${wechatUser.gender==3}">其他</c:if>
				</td>
				<td>
					<fmt:formatDate value="${wechatUser.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="weichatuser:wechatUser:view">
						<a href="#" onclick="openDialogView('查看微信用户信息', '${ctx}/ucenter/weichatuser/form?id=${wechatUser.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="weichatuser:wechatUser:edit">
    					<a href="#" onclick="openDialog('修改微信用户信息', '${ctx}/ucenter/weichatuser/form?id=${wechatUser.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="weichatuser:wechatUser:del">
						<a href="${ctx}/ucenter/weichatuser/delete?id=${wechatUser.id}" onclick="return confirmx('确认要删除该微信用户信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>