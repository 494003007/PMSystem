<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工作量确认</title>
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
		<li  ><a href="${ctx}/gmo/workConfirm/projectList">项目列表</a></li>
		<li class="active"><a href="javascript:void(0);">工作量确认列表</a></li>
	 
	</ul>
	<form:form id="searchForm" modelAttribute="contract" action="${ctx}/gmo/workConfirm?projectId=${projectId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>合同编号：</label>
				<form:input path="contractCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>合同名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<%-- <li><label>项目名称：</label>
				<form:input path="project.name" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li> --%>
		<!-- <li><label>客户姓名：</label>
				<form:input path="customer.name" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
		 -->	
			<%-- <li><label>所属区域：</label>
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
			 <th colspan="15" style="text-align: center;  font-size: 18px;background: #EAEAEA;">工作量确认列表</th>
		   </tr>   
			<tr>
			 	<th>合同(订单)编号</th>			
			 	<th>合同(订单)名称</th>
			 	 
			<!-- 	<th>合同编号</th>			 -->
				<th>工作量确认方法</th>	
				<c:forEach items="${dateList}" var="dateList">
					<th>${dateList}</th>		
				</c:forEach>		
				
			<!-- 	 <th>操作</th>  -->
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contract">
			<tr>
			   <td ${contract.workconfirmid==2?"rowspan='2'":""} >
			   
			       <a  href = "${ctx}/gmo/workConfirm/allconfirmpannel?contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}">	
			    	${contract.contractCode}
			    </a> 
			    </td >
			     
			    <td ${contract.workconfirmid==2?"rowspan='2'":""} >
			     <a href="${ctx}/pro/contract/form?id=${contract.id}">  
			       ${contract.name}
			  	</a>
			    </td>
			   
			    
			<%-- 	<td ${contract.workconfirmid==2?"rowspan='2'":""} >
					${contract.contractCode}
				</td> --%>
				
				<td ${contract.workconfirmid==2?"rowspan='2'":""} >
			  
			       ${contract.workconfirmid==1?"按次确认":contract.workconfirmid==2?"按月确认":contract.workconfirmid==3?"按量确认":""} 
			  
			    </td>
			   	 <c:if test="${contract.workconfirmid==1}">
			   		  <c:forEach items="${contract.contractConfirmList}" var="contractConfirm" varStatus="varStatus">			
						<td style="text-align: right;">
<%-- 							<c:if test="${!varStatus.last}">  --%>
								<a style="${contractConfirm.examineStatus == 2?'color:black':contractConfirm.examineStatus == 3?'color:red':''}"  href = "${ctx}/gmo/workConfirm/outconfirmpannel?confirmdate=${dateList[varStatus.index]}&contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}&contractpayid=${dateList2.id}">	 	 									 
									<c:if test="${not empty contractConfirm.confirmAmount}">
							 			 <fmt:formatNumber value="${contractConfirm.confirmAmount}" pattern="###,##0.00"/>	
							 		</c:if>					 
					 			</a>
<%-- 						
						 	</c:if> 	
							 <c:if test="${varStatus.last}"> 							 		
					 			<a href = "${ctx}/pro/workConfirm/outconfirmpannel?confirmdate=${dateList[varStatus.index]}&contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}&contractpayid=${dateList2.id}">	 	 									 
									<c:if test="${not empty contractConfirm.confirmAmount}">
							 			 <fmt:formatNumber value="${contractConfirm.confirmAmount}" pattern="###,##0.00"/>	
							 		</c:if>
									<c:if test="${empty contractConfirm.confirmAmount}">
									       外部确认 	
									</c:if>							 
					 			</a>
					 		</c:if>	 --%>	
						</td>	
						
					<%--  <c:if test="${varStatus.last}"> 	
					   <td>
						 
						    <a href = "${ctx}/pro/workConfirm/allconfirmpannel?contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}">	 	 									 
						  	详细
						  </a>  
						   <a href = "${ctx}/pro/workConfirm/outconfirmpannel?confirmdate=${dateList[varStatus.index]}&contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}&contractpayid=${dateList2.id}">	 	 									 
						  	${contractConfirm.examineStatus == 2?"已审核":contractConfirm.examineStatus == 1?"审核":""}  
						 </a>
					   </td>
					 </c:if> --%>
					 </c:forEach>
					
				
				</c:if>
				
				
				
				 <c:if test="${contract.workconfirmid==2}">
			   		  <c:forEach items="${contract.contractPayList}" var="dateList2" varStatus="varStatus">			
						<td style="text-align: right;">	
 		<%-- 			 		<c:if test="${!varStatus.last}"> --%>
								<a style="color:green;"  href = "${ctx}/pro/workConfirm/confirmpannel?confirmdate=<fmt:formatDate value="${dateList2.payDate}" pattern="yyyy-MM"/>&contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}&contractpayid=${dateList2.id}">	 														 
									 <c:if test="${dateList2.inConfirmStatus == 2}">
									 
										<fmt:formatNumber value="${dateList2.inConfirmAmount}" pattern="###,##0.00"/>						 
									</c:if> 
								</a>
		<%-- 				</c:if>
					 		<c:if test="${varStatus.last}">
					 			<a style="color:red" href = "${ctx}/pro/workConfirm/confirmpannel?confirmdate=<fmt:formatDate value="${dateList2.payDate}" pattern="yyyy-MM"/>&contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}&contractpayid=${dateList2.id}">	 														 
					 				<c:if test="${dateList2.inConfirmStatus == 2}">
										<fmt:formatNumber value="${dateList2.inConfirmAmount}" pattern="###,##0.00"/>				 
									</c:if>
									<c:if test="${dateList2.inConfirmStatus != 2}">
										<c:if test="${not empty dateList2.payAmount}">内部确认
										</c:if>
									</c:if>
					 			</a>
					 		</c:if>		 --%>		 					 
						</td>	
						
					
				</c:forEach>
				
				</c:if>
				
				
 			
				 <c:if test="${contract.workconfirmid==3}">
			   		  <c:forEach items="${contract.contractConfirmList}" var="contractConfirm" varStatus="varStatus">			
						<td style="text-align: right;">
	<%-- 						<c:if test="${!varStatus.last}">  --%>
								<a style="${contractConfirm.examineStatus == 2?'color:black':contractConfirm.examineStatus == 3?'color:red':''}"  href = "${ctx}/gmo/workConfirm/outconfirmpannel?confirmdate=${dateList[varStatus.index]}&contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}&contractpayid=${dateList2.id}">	 	 									 
									<c:if test="${not empty contractConfirm.confirmAmount}">
							 			 <fmt:formatNumber value="${contractConfirm.confirmAmount}" pattern="###,##0.00"/>	
							 		</c:if>					 
					 			</a>
						
		<%-- 				 	</c:if> 	
							 <c:if test="${varStatus.last}"> 							 		
					 			<a href = "${ctx}/pro/workConfirm/outconfirmpannel?confirmdate=${dateList[varStatus.index]}&contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}&contractpayid=${dateList2.id}">	 	 									 
									<c:if test="${not empty contractConfirm.confirmAmount}">
							 			 <fmt:formatNumber value="${contractConfirm.confirmAmount}" pattern="###,##0.00"/>	
							 		</c:if>
									<c:if test="${empty contractConfirm.confirmAmount}">
									       外部确认 	
									</c:if>							 
					 			</a>
					 		</c:if>		 --%>
						</td>	
					<%-- 	
					  <c:if test="${varStatus.last}"> 	
					   <td>
						   <a href = "${ctx}/pro/workConfirm/allconfirmpannel?contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}">	 	 									 
						  	详细
						  </a>  
						   <a href = "${ctx}/pro/workConfirm/outconfirmpannel?confirmdate=${dateList[varStatus.index]}&contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}&contractpayid=${dateList2.id}">	 	 									 
						  ${contractConfirm.examineStatus == 2?"已审核":contractConfirm.examineStatus == 1?"审核":""}  
						 </a>
					   </td>
					 </c:if> --%>
					 </c:forEach>
				
				</c:if>
		 
				<!-- 
				<shiro:hasPermission name="pro:contract:edit"><td>
    				<a href="${ctx}/pro/contract/form?id=${contract.id}">修改</a>
					<a href="${ctx}/pro/contract/delete?id=${contract.id}" onclick="return confirmx('确认要删除该合同吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
				 -->	
			</tr>
			<tr>
			
			 <c:if test="${contract.workconfirmid==2}">
			   		  <c:forEach items="${contract.contractConfirmList}" var="contractConfirm" varStatus="varStatus">			
						<td style="text-align: right;">						 	
		 
							<a  style="${contractConfirm.examineStatus == 2?'color:black':contractConfirm.examineStatus == 3?'color:red':''}" href = "${ctx}/gmo/workConfirm/outconfirmpannel?confirmdate=${dateList[varStatus.index]}&contractid=${contract.id}&projectid=${contract.project.id}&workconfirmid=${contract.workconfirmid}&contractpayid=${dateList2.id}">	 	 									 
								<c:if test="${not empty contractConfirm.confirmAmount}">
						 			 <fmt:formatNumber value="${contractConfirm.confirmAmount}" pattern="###,##0.00"/>	
						 		</c:if>					 
				 			</a>
						
					</td>
					 </c:forEach>
				
				</c:if>
			
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>