<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工管理</title>
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
		<li class="active"><a href="${ctx}/gmo/examineemployee/examinequitlist">员工列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="employee" action="${ctx}/gmo/examineemployee/examinequitlist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	
				
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false"  maxlength="20" class="input-medium"/>
			</li>
	
			<label>所属区域：</label>
				<sys:treeselect id="sysArea" name="sysArea.id" value="${employee.sysArea.id}"  labelName="sysArea.name" labelValue="${employee.sysArea.name}"
					title="区域" url="/sys/sysArea/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>


			
			<li><label>所属部门：</label> 
			<form:select path="department.id" class="input-medium">
				<form:option value="" label="" />
				<form:options items="${fns:getSys_Selected('department_type')}" itemLabel="name" itemValue="id" htmlEscape="false" />
		   	</form:select>
			</li>
			<li>
			<label>员工类型：</label> 
			<form:select path="employeeType.id" class="input-medium">
			<form:option value="" label="" />
			<form:options items="${fns:getSys_Selected('employee_type')}" itemLabel="name" itemValue="id" htmlEscape="false" />
			</form:select>
			</li>
		 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
			

	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>员工编号</th>
			
				<th>姓名</th>
				<th>所属区域</th>
				<th>所属部门</th>
				<th>员工类型</th>
				<th>岗位</th>
				<th>性别</th>
				<th>手机号码</th>
				<th>邮箱</th>
				<th>员工状态</th>
			 	<th>审核状态</th>
				<shiro:hasPermission name="gmo:quitemployee:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="employee">
			<tr>
				<td>
					${employee.id}
				</td>
				
				<td><a  href="${ctx}/gmo/examineemployee/examinequitForm?id=${employee.id}">
					${employee.name}
					</a>
				</td>
				<td>
					${employee.sysArea.name}
				</td>
				<td>
					${employee.department.name}
				</td>
				<td>
					${employee.employeeType.name}
				</td>
				<td>
					${employee.quartersType.name}
				</td>
				<td>
					${employee.sex == 1? '男':'女'}
				</td>
				<td>
					${employee.mobile}
				</td>
				<td>
					${employee.email}
				</td>
				<td>
				${employee.status == 2?'在职':employee.status == 3?'正在离职':employee.status == 4?'已离职':''}
				
				</td>
				<td style="${employee.quitExamineStatus == 3?'color: red':''}">
				 ${employee.quitExamineStatus == 2?'审核通过':employee.quitExamineStatus == 3?'审核不通过':employee.status==2?'':'未审核'}
				</td>
				<shiro:hasPermission name="gmo:quitemployee:edit"><td>
			 
    				<a  href="${ctx}/gmo/examineemployee/examinequitForm?id=${employee.id}">${(employee.quitExamineStatus==2 or employee.quitExamineStatus==3)?'已审核':'审核'}</a>
 
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