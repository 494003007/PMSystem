<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工薪资基准管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/gmo/salary/list?projectId=${projectId}">员工薪资基准列表</a></li>
	 
	</ul>
	<form:form id="searchForm" modelAttribute="salary" action="${ctx}/gmo/salary/list?projectId=${projectId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			
			<li><label>姓名：</label>
				<form:input path="employee.name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>项目名称：</label>
				<form:input path="employee.project.name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			 <li>
			<label>所属区域：</label>
				<sys:treeselect id="sysArea" name="employee.sysArea.id" value="${salary.employee.sysArea.id}"  labelName="employee.sysArea.name" labelValue="${salary.employee.sysArea.name}"
					title="区域" url="/sys/sysArea/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>员工编号</th>
				<th>所属项目名称</th>
				<th>姓名</th>
				<th>所属区域</th>
				<th>员工类型</th>
				<th>岗位</th>			 						 
				<th>工资基准</th>
				<th>岗位补贴</th>
				<th>项目补贴</th>
				<th>生活补贴</th>			 
				<th>理论收入</th>
				<th>审核状态</th>
				<shiro:hasPermission name="gmo:salary:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="salary">
			<tr ${empty salary.totalSalary or salary.totalSalary == 0?"style='color:red'":""}>
				<td>
					${salary.employee.id}
				 </td>
				<td><a href="${ctx}/pro/project/form?id=${salary.employee.project.id}">
					${salary.employee.project.name}
					</a>
				</td>
				<td><a href="${ctx}/per/employee/form?id=${salary.employee.id}">
					${salary.employee.name}
				</a>
				</td>
				<td>
					${salary.employee.sysArea.name}
				</td>
				<td>
					${salary.employee.employeeType.name}
				</td>
					<td>
					${salary.employee.quartersType.name}
				</td>
					<td style="text-align: right;">
					
					<fmt:formatNumber value="${not empty salary.salarySubsidy?salary.salarySubsidy:'0'}"  pattern="###,##0.00"></fmt:formatNumber>
					
				</td>
					<td  style="text-align: right;">
					 <fmt:formatNumber value="${not empty salary.positionSubsidy?salary.positionSubsidy:'0'}"  pattern="###,##0.00"></fmt:formatNumber>				 
				</td>
				<td  style="text-align: right;">
				 <fmt:formatNumber value="${not empty salary.projectSubsidy?salary.projectSubsidy:'0'}"  pattern="###,##0.00"></fmt:formatNumber>				 
					
				</td>
				<td  style="text-align: right;">
				 <fmt:formatNumber value="${not empty salary.lifeSubsidy?salary.lifeSubsidy:'0'}"  pattern="###,##0.00"></fmt:formatNumber>				 
					
				</td>
				 
				<td  style="text-align: right;">
				 <fmt:formatNumber value="${not empty salary.totalSalary?salary.totalSalary:'0'}"  pattern="###,##0.00"></fmt:formatNumber>				 
					
				</td >
				<td>
				 ${salary.examineStatus == 3?"审核不通过":salary.examineStatus == 2?"审核通过":"未审核"}
				</td>
				<shiro:hasPermission name="gmo:salary:edit"><td>
    				<a href="${ctx}/gmo/salary/form?id=${salary.id}">${salary.examineStatus==1?"审核":"已审核"}</a>
					 
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
	 $.ajax({
	     url: "${ctx}//sys/notify",  
	     type: 'POST',
	     dataType: 'json',
	     cache: false,
	     contentType: false,
	     processData: false,
	     success : function(list) {
	   	var notifySum = 0;	 	     
	       for(var i = 0;i<list.length;i++){
	      		 notifySum += list[i].count;	         
	       }	 
	        if(notifySum > 0)
		          $(window.parent.document).find("#notifySum").html(notifySum);
		        else
		          $(window.parent.document).find("#notifySum").html("");

		},
			error : function(data) {
		 
		}
	});	
	</script>
</body>
</html>