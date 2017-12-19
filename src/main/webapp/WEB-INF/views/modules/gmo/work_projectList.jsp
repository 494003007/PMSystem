<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>项目管理</title>
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
		<li class="active"><a href="${ctx}/gmo/workConfirm/projectList">项目列表</a></li>
		 
	</ul>
	<form:form id="searchForm" modelAttribute="project" action="${ctx}/gmo/workConfirm/projectList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>项目名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<%--  <li><label>项目编号：</label>
				<form:input path="projectCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li> --%>
			<li><label>项目类型：</label>
			 <form:select path="projectTypeId" class="input-medium">
				<form:option value="" label="" />
				<form:options items="${fns:getPro_Selected('projectType')}" itemLabel="name" itemValue="id" htmlEscape="false" />
			</form:select>
			</li>
			<%-- <li><label>大区经理：</label>
				<form:input path="bpmemployee.name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			
			
			<li><label>项目经理：</label>
				<form:input path="employee.name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li> --%>
			 
			<li><label>所属区域：</label> <sys:treeselect id="sysArea" name="sysAreaId" value=""
			labelName="" labelValue="" title="区域" url="/sys/sysArea/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false" />
			</li>
		 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				 
				<th>项目名称</th>
				<th>项目类型</th>				  
				<th>所属区域</th>			 			
				<th>总金额(元)</th>		 
				<th>已验收金额(元)</th>
				 <c:forEach items="${dateList}" var="dateList">
					<th>${dateList}</th>		
				</c:forEach>				
			<!-- <th>成本是否分合同</th>
				<th>是否框架合同</th>
				<th>是否纯成本支出类项目</th>
			 -->
				<shiro:hasPermission name="gmo:workConfirm:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="project">
			<tr>
				<td><a href="${ctx}/gmo/workConfirm/detailList?projectId=${project.id}">
					${project.name}
				</a></td>
				 
				 <td>
					${project.projectType.name}
				</td>
				 	 
	 			 <td>
					${project.sysArea.name}
				</td>
	 		 				
				 <td style="text-align: right;">
			    	 <fmt:formatNumber value="${not empty project.totalamount?project.totalamount:'0.0'}" pattern="###,##0.00"></fmt:formatNumber>			      
			    </td>
				 
			    <td style="text-align: right;">
			    	 <fmt:formatNumber value="${not empty project.totalconfirm?project.totalconfirm:'0.0'}" pattern="###,##0.00"></fmt:formatNumber>			      
			    </td>
			    
			     <c:forEach items="${project.projectConfirmList}"  var = "projectConfirmList">
			      <td style="text-align: right;">
			        <a style="color: gray;" href="${ctx}/gmo/workConfirm/detailList?projectId=${project.id}&month=${projectConfirmList.date}">
			       <fmt:formatNumber value="${projectConfirmList.totalconfirm}" pattern="###,##0.00"></fmt:formatNumber>	
			       </a>
			    			      
			     </td>
			     
			     </c:forEach>
 				 <!--   
	 			 <td>
					${project.iscostcontract==2?"是":"否"}
				</td>
	 			 <td>
					${project.isframecontract==2?"是":"否"}
				</td>
				 <td>
					${project.ispaycontract==2?"是":"否"}
				</td>
				 -->
				<shiro:hasPermission name="gmo:workConfirm:*"><td>
    				<a href="${ctx}/gmo/workConfirm?projectId=${project.id}">${project.isExamine==0?"已审核":"审核"}</a>
 
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