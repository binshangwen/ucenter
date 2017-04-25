<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户注册登录管理</title>
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
		<h5>用户注册登录列表 </h5>
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
	<form:form id="searchForm" modelAttribute="userData" action="${ctx}/ucenter/user/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>登录名：</span>
				<form:input path="userName" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
			<span>用户昵称：</span>
				<form:input path="nickName" htmlEscape="false" maxlength="200"  class=" form-control input-sm"/>
			<span>注册类型：</span>
				<form:input path="registerBy" htmlEscape="false" maxlength="45"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<%--<shiro:hasPermission name="user:userData:add">--%>
				<%--<table:addRow url="${ctx}/ucenter/user/form" title="用户注册登录"></table:addRow><!-- 增加按钮 -->--%>
			<%--</shiro:hasPermission>--%>
			<%--<shiro:hasPermission name="user:userData:edit">--%>
			    <%--<table:editRow url="${ctx}/ucenter/user/form" title="用户注册登录" id="contentTable"></table:editRow><!-- 编辑按钮 -->--%>
			<%--</shiro:hasPermission>--%>
			<shiro:hasPermission name="user:userData:del">
				<table:delRow url="${ctx}/ucenter/user/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<%--<shiro:hasPermission name="user:userData:import">--%>
				<%--<table:importExcel url="${ctx}/ucenter/user/import"></table:importExcel><!-- 导入按钮 -->--%>
			<%--</shiro:hasPermission>--%>
			<%--<shiro:hasPermission name="user:userData:export">--%>
	       		<%--<table:exportExcel url="${ctx}/ucenter/user/export"></table:exportExcel><!-- 导出按钮 -->--%>
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
				<th  class="sort-column userName">登录名</th>
				<th  class="sort-column nickName">用户昵称</th>
				<th  class="sort-column registerBy">注册类型</th>
				<th  class="sort-column gender">性别</th>
				<th  class="sort-column createDate">创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userData">
			<tr>
				<td> <input type="checkbox" id="${userData.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看用户注册登录', '${ctx}/ucenter/user/form?id=${userData.id}','800px', '500px')">
						${userData.userName}
				</a></td>
				<td>
					${userData.nickName}
				</td>
				<td>
					${userData.registerBy}
				</td>
				<td>
					<c:if test="${userData.gender==1}">男</c:if>
					<c:if test="${userData.gender==2}">女</c:if>
					<c:if test="${userData.gender==3}">其他</c:if>
				</td>
				<td>
					<fmt:formatDate value="${userData.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="user:userData:view">
						<a href="#" onclick="openDialogView('查看用户注册登录', '${ctx}/ucenter/user/form?id=${userData.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
    				<shiro:hasPermission name="user:userData:del">
						<a href="${ctx}/ucenter/user/delete?id=${userData.id}" onclick="return confirmx('确认要删除该用户注册登录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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