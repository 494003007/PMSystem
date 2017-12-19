<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>员工管理</title>
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
<style type="text/css">
	.labletd{
	  width:7%;
	  height:50px;
	  background:#F4F4F4;
 
	 
	}
	.projectsearch{
		width: 270px;
	
	}
	.employeeseach{ 
		width: 50px;
	}
	#contenttable tbody tr{
	 	height: 50px;
	 
	}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/gmo/examineemployee/examinequitlist">员工列表</a></li>
		<li class="active"><a href="${ctx}/gmo/examineemployee/examinequitForm?id=${employee.id}">员工<shiro:hasPermission name="per:employee:edit">${not empty employee.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="per:employee:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="employee" action="" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		
		 
	  <table class="table table-bordered table-condensed" >
		<thead>
			<tr>
				<th colspan="6" style="text-align: center;  font-size: 18px;background: #EAEAEA;">员工基本信息</th>
			</tr>
		</thead>
		<tbody>
		 <tr>
		 	<td class="labletd"  style="text-align: center;">所属区域
			</td>
		 	<td>
		 	<sys:treeselect id="sysArea" disabled="true" name="sysArea.id" value="${employee.sysArea.id}" labelName="sysArea.name" labelValue="${employee.sysArea.name}"
					title="区域" url="/sys/sysArea/treeData" cssClass="addtreeselectclass" allowClear="true" notAllowSelectParent="false"/>			 	
		 	</td>
		 	<td class="labletd"  style="text-align: center;">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名
			</td>
			<td>
				<form:input path="name"  disabled="true" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				 <span class="help-inline"><font color="red">*</font> </span>	
			</td>
			<td class="labletd"  style="text-align: center;">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别
			</td>
			<td>
			  <form:select disabled="true" path="sex" class="addselectclass">
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</td>
		 </tr>
		 
		 <tr>
		   <td class="labletd"  style="text-align: center;"> 手机号码 </td>
		   <td>
		   <form:input disabled="true" path="mobile" htmlEscape="false" maxlength="20" class="input-xlarge "/>
		   </td>
		    <td class="labletd"  style="text-align: center;"> 身&nbsp;&nbsp;份&nbsp;&nbsp;证 </td>
		   <td>
		   <form:input  disabled="true" path="identityCard" htmlEscape="false" maxlength="30" class="input-xlarge "/>
		   </td>
		    <td class="labletd"  style="text-align: center;">所属部门  </td>
		   <td>
		   <form:select   disabled="true" path="departmentId" class="addselectclass">
					<form:option value="" label=""/>
					<form:options items="${fns:getSys_Selected('department_type')}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
		   </td>		 
		 </tr>
		 
		  <tr>
		   <td class="labletd"  style="text-align: center;"> 开&nbsp;&nbsp;户&nbsp;&nbsp;行 </td>
		   <td>
		   <form:select disabled="true" path="bankType" class="addselectclass">
					<form:options items="${fns:getDictList('bank')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
		   </td>
		   
		    <td class="labletd"  style="text-align: center;"> 银行账号 </td>
		   <td>
		   <form:input disabled="true" path="bankAccount" htmlEscape="false" maxlength="30" class="input-xlarge "/>
		   </td>
		    <td class="labletd"  style="text-align: center;"> 员工类型 </td>
		   <td>
		   <form:select  disabled="true" path="employeeTypeId" class="addselectclass required">
					<form:option value="" label=""/>
					<form:options items="${fns:getSys_Selected('employee_type')}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				 <span class="help-inline"><font color="red">*</font> </span>
		   </td>
		  
		   
		 
		 </tr>
		  <tr>
		   <td class="labletd"  style="text-align: center;"> 出生日期 </td>
		   <td>
		   <input name="bornDate" disabled="disabled" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate "
					value="<fmt:formatDate value="${employee.bornDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		   </td>
		     <td class="labletd"  style="text-align: center;"> 邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱 </td>
		   <td>
		   <form:input path="email" disabled="true" htmlEscape="false" maxlength="30" class="input-xlarge email"/>
		   </td>
		   
		    <td class="labletd"  style="text-align: center;">岗&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位  </td>
		   <td>
		   <form:select path="quarters"  disabled="true" class="addselectclass required">
					<form:option value="" label=""/>
					<form:options items="${fns:getPer_Selected('quarters_type')}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
		   </td>
		   
		 
		 </tr>
		 
		  <tr>
		   <td class="labletd"  style="text-align: center;"> O&nbsp;A&nbsp;编&nbsp;号 </td>
		   <td>
		   <form:input path="oaId" disabled="true" htmlEscape="false" maxlength="20" class="input-xlarge "/>
		   </td>
		    <td class="labletd"  style="text-align: center;"> 转正时间 </td>
		   <td>
		   <input name="formalDate" disabled="disabled" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate "
					value="<fmt:formatDate value="${employee.formalDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		   </td>
		 <td class="labletd"  style="text-align: center;"> 籍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贯 </td>
		   <td>
		   	<form:input path="address" disabled="true" htmlEscape="false" maxlength="50" class="input-xlarge "/>
		   </td>
		   
		 
		 </tr>
		 
		  <tr>
		  <td class="labletd"  style="text-align: center;">档案编号  </td>
		   <td>
		   <form:input path="archivesId" disabled="true" htmlEscape="false" maxlength="20" class="input-xlarge "/>
		   </td>
		    <td class="labletd"  style="text-align: center;"> 学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历 </td>
		   <td>
		   <form:select disabled="true" path="educationType" class="addselectclass">
					<form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
		   </td>
		    <td class="labletd"  style="text-align: center;"> 毕业时间 </td>
		   <td>
		   
		 <input disabled="disabled" name="graduationDate" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate "
					value="<fmt:formatDate value="${employee.graduationDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		   </td>
		   
		 
		 </tr>
		 
		  <tr>
		   <td class="labletd"  style="text-align: center;"> 毕业学校 </td>
		   <td>
		   <form:input disabled="true" path="graduationSchool" htmlEscape="false" maxlength="50" class="input-xlarge "/>
 			
		   </td>
		   
		  <td class="labletd"  style="text-align: center;">专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业  </td>
		   <td>
		 <form:input path="major" disabled="true"  htmlEscape="false" maxlength="20" class="input-xlarge "/>
		   </td>
		    <td class="labletd"  style="text-align: center;"> 员工状态</td>
		    <td>
		       <input type="text" disabled="disabled" value="${employee.status == 2?'在职':employee.status == 3?'正在离职':employee.status == 4?'已离职':'正在入职'}" class="input-xlarge "/>
		   </td>
		 
		 </tr>
		 
		  <tr>
		  
		   <td class="labletd"  style="text-align: center;">入职时间  </td>
		   <td>
		   	<input disabled="disabled" name="entryDate" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate "
					value="<fmt:formatDate value="${employee.entryDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		   </td>
		   <td class="labletd"  style="text-align: center;"> 入职原因 </td>
		   <td>
		   <textarea disabled="disabled" name="enteyReson"  rows="4" style="width: 96%">${employee.enteyReson} </textarea>			 
		   
		   </td>
		    <td class="labletd"  style="text-align: center;"> 入职证件上传 </td>
		   <td>
			<form:hidden id="files" path="files" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:ckfinder input="files" type="pdf"  uploadPath="/入职登记附件上传" selectMultiple="true" readonly="true"/>

		   </td>
 
		 </tr>
		 
		  <tr>
		  
		   <td class="labletd"  style="text-align: center;">离职时间  </td>
		   <td>
		   	<input disabled="disabled" name="entryDate" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate "
					value="<fmt:formatDate value="${employee.quitDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		   </td>
		   <td class="labletd"  style="text-align: center;"> 离职原因 </td>
		   <td>
		   <textarea name="quitReson" disabled="disabled" rows="4" style="width: 96%">${employee.quitReson} </textarea>			 
		   
		   </td>
		    <td class="labletd"  style="text-align: center;"> 离职证件上传 </td>
		   <td>
			<form:hidden id="files2" path="quitFiles" htmlEscape="false" maxlength="255" class="input-xlarge"/>
			<sys:ckfinder input="files2" type="pdf"  uploadPath="/离职登记附件上传" selectMultiple="true" readonly="true"/>

		   </td>
 
		 </tr>
		 <tr>
		    <td class="labletd"  style="text-align: center;">审核回执</td>
		 	<td colspan="5">
		 	
		 	  <textarea name="quitExamineRemarks"  rows="3" style="width: 99%">${employee.quitExamineRemarks}</textarea>			 
		 		 	
		 	</td>
		 </tr>
		</tbody>
	</table>
	 
		<div class="form-actions">
		 <shiro:hasPermission name="gmo:quitemployee:edit">
		<c:choose>
			<c:when test="${employee.quitExamineStatus == 3}">
				<input disabled="disabled" id="btnSubmit_no" class="btn btn-danger offset5"  type="button" value="不通过"/>&nbsp;
			</c:when>
			
			<c:when test="${employee.quitExamineStatus == 2}">
				<input id="btnSubmit_ok" disabled="disabled" class="btn btn-primary offset5 " type="button" value="通过"/>&nbsp;		
			</c:when>
			<c:otherwise>
				<input id="btnSubmit_ok" class="btn btn-primary offset5 " type="button" value="通过"/>&nbsp;
				<input id="btnSubmit_no" class="btn btn-danger"  type="button" value="不通过"/>&nbsp;
		    </c:otherwise>
		</c:choose>
		
		
	   </shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
 
	</form:form>
<script type="text/javascript">
 $("#btnSubmit_ok").click(function(){
	 $("#inputForm").attr("action","${ctx}/gmo/examineemployee/examinequitSave?quitexaminestatus=2");
	 $("#inputForm").submit();
 })
 

$("#btnSubmit_no").click(function(){
	 $("#inputForm").attr("action","${ctx}/gmo/examineemployee/examinequitSave?quitexaminestatus=3");
	 $("#inputForm").submit();
 })
 
</script>
</body>
</html>