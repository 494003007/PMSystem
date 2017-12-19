<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工薪资基准管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/gmo/salary/">员工薪资基准列表</a></li>
		<li class="active"><a href="javascript:void(0);">员工薪资基准<shiro:hasPermission name="att:salary:edit">${not empty salary.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="att:salary:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="salary" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="offset3 control-label">员工姓名：</label>
			<div class="controls">
				<input name="employeeId" type="text" style="display: none" value="${salary.employeeId}"  />
				<input  type="text" disabled="disabled"   value="${salary.employee.name}"   class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="offset3 control-label">项目名称：</label>
			<div class="controls">
				<input name="projectId" type="text" style="display: none"  value="${salary.projectId}"  class="input-xlarge"/>
				<input type="text" disabled="disabled" value="${salary.employee.project.name}"  class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="offset3 control-label">工资基准(元)：</label>
			<div class="controls">
				<input type="text"  value="${salary.salarySubsidy}" id="salarySubsidy" disabled="disabled"  class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="offset3 control-label">岗位补贴(元)：</label>
			<div class="controls">
				<input type="text"  value="${salary.positionSubsidy}" id="positionSubsidy" disabled="disabled" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="offset3 control-label">项目补贴(元)：</label>
			<div class="controls">
				<input type="text"  value="${salary.projectSubsidy}" id="projectSubsidy" disabled="disabled" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="offset3 control-label">生活补贴(元)：</label>
			<div class="controls">
				<input type="text"  value="${salary.lifeSubsidy}" id="lifeSubsidy" disabled="disabled" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="offset3 control-label">理论收入(元)：</label>
			<div class="controls">
				<input type="text"   disabled="true" value="${salary.totalSalary}"  id="totalSalary" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="offset3 control-label">审核备注：</label>
			<div class="controls">
				<form:textarea rows="4"  path="examineRemarks"  class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="gmo:salary:edit">
				<c:choose>
				  <c:when test="${salary.examineStatus != 1}">
				      <input  class="btn btn-primary  offset3"  type="button"  disabled="disabled"   value="通过"/>&nbsp;	  
					  <input  class="btn btn-danger"  type="button"  disabled="disabled"   value="不通过"/>&nbsp;
				  </c:when>
				  <c:otherwise>
				  	 <input id="btnSubmit_yes" class="btn btn-primary offset3" type="button"   value="通过"/>&nbsp;
				  	 <input id="btnSubmit_no" class="btn btn-danger"   type="button"   value="不通过"/>&nbsp;
				  </c:otherwise>
				</c:choose>
			
			</shiro:hasPermission>
			 
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
		$("#btnSubmit_yes").click(function(){
			$("#inputForm").attr("action","${ctx}/gmo/salary/save?type=2");
			$("#inputForm").submit();
		})
	
		$("#btnSubmit_no").click(function(){
			$("#inputForm").attr("action","${ctx}/gmo/salary/save?type=3");
			$("#inputForm").submit();
		})
	</script>
	 
</body>
</html>