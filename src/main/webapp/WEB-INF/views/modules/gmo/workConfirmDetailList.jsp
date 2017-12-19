<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预算信息管理</title>
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
		<style type="text/css">
.labletd{
  width:7%;
  background:#F4F4F4;
 
}
.projectsearch{
	width: 270px;

}
.employeeseach{ 
	width: 50px;
}
#contenttable2 tbody tr{
 	height: 40px;
 
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/gmo/workConfirm/projectList">项目列表</a></li>
		<li class="active"><a href="javascript:void(0);">项目确认详细</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="contractConfirm" action="${ctx}/gmo/workConfirm/detailList?projectId=${project.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>合同编号：</label>
				<form:input path="contract.contractCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>合同名称：</label>
				<form:input path="contract.name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			
			
			<li><label>确认月份：</label>
				<input name="month" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${contractConfirm.month}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
<%-- 			<li><label>项目名称：</label>
				<form:input path="project.name" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<!-- <li><label>客户姓名：</label>
				<form:input path="customer.name" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>-->

			<li><label>所属区域：</label>
				<sys:treeselect id="sysArea" name="project.sysArea.id" value="${project.sysArea.id}"  labelName="sysArea.name" labelValue="${project.sysArea.name}"
								title="区域" url="/sys/sysArea/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>

			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	
	
	<table id= "contenttable2" class="table table-bordered table-condensed" >
		<thead>
			<tr>
				<th colspan="14" style="text-align: center;  font-size: 18px;background: #EAEAEA;">项目信息</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="labletd">项目名称</td>
				<td colspan="3"  >
			    ${project.name}
				</td>
				<td class="labletd">项目类型</td>
				<td colspan="3"  >
				${project.projectType.name}
				</td>
				<td class="labletd">甲方单位名称</td>
				<td colspan="5"  >
				${project.customer.companyName}
				</td>
			</tr>
			<tr>
				<td class="labletd">大区经理</td>
				<td >
				 ${project.bpmemployee.name}					
				</td>
				<td class="labletd">所属区域</td>
				<td >
			 	${project.sysArea.name}			
				</td>
				<td class="labletd">项目负责人</td>
				<td >
				  ${project.employee.name}		
				</td>
				<td class="labletd">联系方式</td>
				<td >
			 	 ${project.employee.mobile}		
				</td>
				<td  class="labletd">成本是否分合同</td>
				<td>
				  ${project.iscostcontract == 2?"是":project.iscostcontract == 1?"否":""}
				</td>
				<td class="labletd" >是否框架合同</td>
				<td >
				  ${project.isframecontract == 2?"是":project.isframecontract == 1?"否":""}
				</td>
				<td class="labletd">是否纯成本支出类项目</td>
				<td>	
				  ${project.ispaycontract == 2?"是":project.ispaycontract == 1?"否":""}
				</td>
			
			</tr>
			
			 <tr>
				<td class="labletd">项目编号</td>
				<td >
				 ${project.projectCode}					
				</td>
				<td class="labletd">总金额(元)</td>
				<td >
				<fmt:formatNumber pattern="###,##0.00" value="${not empty project.totalamount?project.totalamount:0}"></fmt:formatNumber>
			 	 				
				</td>
				<td class="labletd">总成本(元)</td>
				<td >
				<fmt:formatNumber pattern="###,##0.00" value="${not empty project.totalcost?project.totalcost:0}"></fmt:formatNumber>
				  	
				</td>
				<td class="labletd">总预算(元)</td>
				<td >
				<fmt:formatNumber pattern="###,##0.00" value="${not empty project.totalbudget?project.totalbudget:0}"></fmt:formatNumber>
			 	 		
				</td>
				<td  class="labletd">已验收金额(元)</td>
				<td>
				<fmt:formatNumber pattern="###,##0.00" value="${not empty project.totalconfirm?project.totalconfirm:0}"></fmt:formatNumber>
				   	
				</td>
				<td class="labletd" >已开票金额(元)</td>
				<td >
				<fmt:formatNumber pattern="###,##0.00" value="${not empty project.totalinvoice?project.totalinvoice:0}"></fmt:formatNumber>
				  	 
				</td>
				<td class="labletd">已回款金额(元)</td>
				<td>
				<fmt:formatNumber pattern="###,##0.00" value="${not empty project.totalreturn?project.totalreturn:0}"></fmt:formatNumber>	
				  	
				</td>
			
			</tr>
		</tbody>
	</table>
	
	

	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			 <th colspan="11" style="text-align: center;  font-size: 18px;background: #EAEAEA;">合同预算列表</th>
		 </tr>
		<tr>
			<th>合同编号</th>
			<th>合同(订单)名称</th>
			<th>工作量确认方法</th>
			<th>确认归属月份</th>
			<th>外部确认总额</th>
			<th>审核状态</th>
			<shiro:hasPermission name="gmo:workConfirm:edit"><th>操作</th></shiro:hasPermission>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="confirm">
			<tr>
				<td>
				  <a  href ="${ctx}/gmo/workConfirm/allconfirmpannel?contractid=${confirm.contract.id}&projectid=${confirm.projectId}&workconfirmid=${confirm.contract.workconfirmid}">	
						${confirm.contract.contractCode}
				</a>
				
				</td>
				<td>
				 <a href="${ctx}/pro/contract/form?id=${confirm.contract.id}">
						${confirm.contract.name}
				</a>
				</td>
				<td>
				
					${fns:getDictLabels(confirm.contract.workconfirmid,"work_confirm","")}
				</td>
				
				 
				<td>
					<fmt:formatDate value="${confirm.month}" pattern="yyyy-MM"/>
				</td>
				 <td style="text-align: right;">
			      <fmt:formatNumber value="${not empty confirm.confirmAmount?confirm.confirmAmount:0}" pattern="###,###.00"></fmt:formatNumber>
			      
			    </td>
			    <td style="${confirm.examineStatus != 3?'':'color:red'}">${confirm.examineStatus == 2?"审核通过":confirm.examineStatus == 3?"审核不通过":"未审核"}</td>
				<shiro:hasPermission name="gmo:workConfirm:edit"><td>
					<a href = "${ctx}/gmo/workConfirm/outconfirmpannel?projectid=${confirm.projectId}&contractid=${confirm.contract.id}&confirmdate=<fmt:formatDate value="${confirm.month}" pattern="yyyy-MM"/>&workconfirmid=${confirm.contract.workconfirmid}">	 	 									 
					 		详细
					 </a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	
	
</body>
</html>