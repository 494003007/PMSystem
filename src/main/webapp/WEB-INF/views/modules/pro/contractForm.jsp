<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同管理</title>
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
            
            
           if($("#projectselectId").val()==""){
	            var date = new FormData();
				date.append("projectid","${projectId}");						 
				  $.ajax({
	                  url: "${ctx}//pro/project/selectProject",  
	                  type: 'POST',
	                  dataType: 'json',
	                  data: date,
	                  cache: false,
	                  contentType: false,
	                  processData: false,
	    		      success : function(map) {
	    		    	setprojectdate(map);                   		    
					 		
	    			},
	    			error : function(data) {  
	    				alertx("数据加载失败，请重试");
	    			}
	    		});			   
           }
            
            
            
            
            
        });

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
#contenttable tbody tr{
 	height: 50px;
 
}
</style>
</head>
<body>
	<ul class="nav nav-tabs"  >
		<shiro:hasPermission name="pro:contract:edit">
    	<li ><a href="${ctx}/pro/contract/projectList">项目列表</a></li>
		<li><a href="${ctx}/pro/contract/">合同列表</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/pro/contract/form?id=${contract.id}">合同<shiro:hasPermission name="pro:contract:edit">${not empty contract.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pro:contract:edit">查看</shiro:lacksPermission></a></li>
	</ul> 
	
	<form:form  id="inputForm" modelAttribute="contract" action="${ctx}/pro/contract/save" method="post"  >
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<table class="table table-bordered table-condensed" >
		<thead>
			<tr>
				<th colspan="14" style="text-align: center;  font-size: 18px;background: #EAEAEA;">项目信息</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="labletd">项目名称</td>
				<td colspan="5" style="padding-top: 10px;padding-bottom: 0px"  id = "project_name">
			    <sys:projectselect  id="projectselect" name="projectId" value="${contract.project.id}" labelName="project.name" labelValue="${contract.project.name}"
			     selecttype="radioselect" allowClear="true" notAllowSelectParent="true"  getinformation="true" cssClass="required" getcontract="true" getcustomer="true" customerName="contractselect777" /> 
			     <span class="help-inline"><font color="red">*</font> </span> 
			   
				</td>
				<td class="labletd">项目类型</td>
				<td colspan="7" id = "project_type">
				${contract.project.projectType.name}
				</td>
			</tr>
			<tr>
				<td class="labletd">大区经理</td>
				<td  style="width: 9%; "id = "bpm_employee">
				 ${contract.project.bpmemployee.name}					
				</td>
				<td class="labletd">所属区域</td>
				<td  style="width: 9%" id = "sys_area">
			 	${contract.project.sysArea.name}			
				</td>
				<td class="labletd">项目负责人</td>
				<td  style="width: 9% ;"id = "pm_employee">
				  ${contract.project.employee.name}		
				</td>
				<td class="labletd">联系方式</td>
				<td  style="width: 9%" id = "pm_employee_mobile">
			 	 ${contract.project.employee.mobile}		
				</td>
				<td  class="labletd">成本是否分合同</td>
				<td id = "iscostcontract"  style="width: 5%">
				  ${contract.project.iscostcontract == 2?"是":contract.project.iscostcontract == 1?"否":""}
				</td>
				<td class="labletd" >是否框架合同</td>
				<td id = "isframecontract"  style="width: 5%">
				  ${contract.project.isframecontract == 2?"是":contract.project.isframecontract == 1?"否":""}
				</td>
				<td class="labletd">是否纯成本支出类项目</td>
				<td id ="ispaycontract"  style="width: 5%">	
				  ${contract.project.ispaycontract == 2?"是":contract.project.ispaycontract == 1?"否":""}
				</td>
			
			</tr>
		</tbody>
	</table>
	
	<ul class="nav nav-tabs">
		<li></li>
	</ul>
	<table class="table table-bordered table-condensed" id = "contenttable" >
		<thead>
			<tr>
				<th colspan="14" style="text-align: center;  font-size: 18px;background: #EAEAEA;">合同信息</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="labletd">合同编号
				</td>
				<td colspan="3" style="width:35%">
				 <div id = "contractCodediv">${contract.contractCode}</div>
				 <input name ="contractCode"  id = "contractCode" type="text"  value="${contract.contractCode}"  style="display: none"/>
				 
				</td>
				<td class="labletd">甲方合同编号
				</td>
			    <td colspan="3"  style="padding-top: 10px;padding-bottom: 0px">
				<form:input path="acontractCode" htmlEscape="false"   class="input-xlarge required"/>
				</td>
			</tr>
			<tr>
				<td  class="labletd">合同名称
				</td>
				<td colspan="3"  style="padding-top: 10px;padding-bottom: 0px">
				<form:input path="name" htmlEscape="false"   class="required" cssStyle="width:98%"/>
				</td>
				<td  class="labletd">合同金额（元）
				</td>
				<td  style="padding-top: 10px;padding-bottom: 0px">
				<form:input path="amount" htmlEscape="false"  class="input-xlarge checkTenTwoNumber"/>
			
				</td>
			</tr>
			<tr>
				<td  class="labletd">甲方单位
				</td>
				<td id= "customercompanyName">${contract.customer.companyName}
				</td>
			
				<td  class="labletd" >甲方联系人
				</td>
				<td style="padding-top:10px;padding-bottom: 0px;margin: 0px;">			 			
				<sys:customeruserselect id="contractselect777" name="customer.id" value="${contract.customer.id}"
					labelName="customerName" labelValue="${contract.customerName}" allowInput="true" selecttype="radioselect" allowClear="true"
					notAllowSelectParent="true" getinformation="true" setproject="true" notshow="true"/> 		
				</td>
				
				<td  class="labletd">联系人电话
				</td>
				<td  style="padding-top: 10px;padding-bottom: 0px">
				 <form:input id = "customermobile"  path="customerMobile" htmlEscape="false"   class="input-xlarge"/>
	 
				</td>
			</tr>
			<tr>
				<td  class="labletd">合同约定开工时间
				</td>
				<td  style="padding-top: 10px;padding-bottom: 0px">
				<input name="startTime" type="text" readonly="readonly"   class="input-xlarge Wdate "
					value="<fmt:formatDate value="${contract.startTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				
				</td >
				<td  class="labletd">合同约定结束时间
				</td>
				<td  style="padding-top: 10px;padding-bottom: 0px">
				<input name="endTime" type="text" readonly="readonly"   class="input-xlarge Wdate "
					value="<fmt:formatDate value="${contract.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				
				</td>
				<td  class="labletd">实际开工时间
				</td>
				<td  style="padding-top: 10px;padding-bottom: 0px">
				 <input name="realStartTime" type="text" readonly="readonly"   class="input-xlarge Wdate "
					value="<fmt:formatDate value="${contract.realStartTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
		
				</td>
			</tr>
			
			
		 
			<tr name = "selectisframecontract">
				<td  class="labletd">工作量确认方法
				</td>
				<td  style="padding-top: 10px;padding-bottom: 0px">
				<c:if test="${contract.project.isframecontract != 2}"> 
				<form:select id = "workconfirmid" path="workconfirmid" class="addselectclass" htmlEscape = "true">
					<form:option value="0" label=""/>
					<c:choose>
						<c:when test="${workconfirmid == 2 or workconfirmid == 1}">
							<form:option value="1" label="按次确认"/>
							<form:option value="2" label="按月确认"/>
						</c:when>
						<c:when test="${workconfirmid == 3}">
							<form:option value="3" label="按量确认"/>
						</c:when>
						<c:otherwise>
						
						
						</c:otherwise>
					</c:choose>
				</form:select>
				</c:if>
				<c:if test="${contract.project.isframecontract == 2}"> 
				 <form:select id = "workconfirmid" path="workconfirmid" class="addselectclass">
					  <form:option value="0" label=""/>
					 <form:option value="3" label="按量确认"/>
		    	</form:select>		
				</c:if>
				</td>
				<td  class="labletd">预计合同回收时间
				</td>
			    <td style="padding-top: 10px;padding-bottom: 0px">
				<input name="predictRecoverDate" type="text" readonly="readonly"  class="input-xlarge Wdate "
					value="<fmt:formatDate value="${contract.predictRecoverDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			
				</td>
				<td  class="labletd">合同签订时间
				</td>
			    <td style="padding-top: 10px;padding-bottom: 0px">
				<input name="signTime" type="text" readonly="readonly"   class="input-xlarge Wdate "
					value="<fmt:formatDate value="${contract.signTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			
				</td>
			</tr>
	 
			
			
			<tr>
				<td  class="labletd">开票内容
				</td>
				<td>
			<form:select path="invoiceContentId" class="addselectclass required">
				<form:option value="" label="" />
				<form:options items="${fns:getSys_Selected('invoice_content')}" itemLabel="name" itemValue="id" htmlEscape="false" />
			</form:select>		
				</td>
				<td  class="labletd">发票类型
				</td>
				<td>
				 <form:select path="invoiceTypeId" class="addselectclass required">
				<form:option value="" label="" />
				<form:options items="${fns:getSys_Selected('invoice_type')}" itemLabel="name" itemValue="id" htmlEscape="false" />
			   </form:select>
	
				</td>
				<td  class="labletd" >发票税率
				</td>
				<td>
			 
				<form:select path="invoiceRevenue" class="addselectclass required">
				<form:option value="" label="" />
			 
	        	<form:options items="${fns:getDictList('tax_percent')}" itemLabel="label" itemValue="value" htmlEscape="false" />
		
			</form:select>	
				
				
				</td>
			</tr>
			<tr>
			 	<td  class="labletd">合同工作量描述
				</td>
				<td colspan="5" style="padding-top: 10px;padding-bottom: 0px">
		    	<form:textarea  rows="4" path="workRemarks" htmlEscape="false"   cssStyle="width:99%"/>		 
				</td>
				 
			</tr>
			
			<tr>			
				 <td class="labletd">约定付款列表
				 </td>
				 <td  colspan="5">
				 
				 
				 
				 
				<c:if test = "${workconfirmid == 2}">
				<table  class="table table-bordered" id="paytable"  >
				<thead>
				<tr style="text-align: center; height:20px;background: #EAEAEA">
					 <th style="width: 4%">序号</th>
					 <th style="width: 18%">确认月份</th>
				 	 <th style="width: 18%">比例（%）</th>
					 <th style="width: 18%">金额（元）</th>
					 <th style="width: 18%">约定付款时间</th>
					 <th style="width: 18%">备注</th>
					  <th style="width: 5%">操作</th></tr>
					</thead>
		        <tbody>
		        <c:forEach items="${contractPayList}" var="contractPayList" varStatus="status">
     				  <tr id="${status.index + 1}" align='center'>
	                     
	                       	<td>${status.index + 1}
	                       	</td>
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  name="desc2${status.index + 1}" id="desc2${status.index + 1}" type="text" readonly="readonly"  class="Wdate reuqired"  value="<fmt:formatDate value="${contractPayList.payDate}" pattern='yyyy-MM' />" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
	                       </td> 
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%" class = "reuqired" value="${contractPayList.payPercent}"  type="text" name="desc3${status.index + 1}" id="desc3${status.index + 1}" />
	                       </td>
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%" class = "reuqired checkTenTwoNumber"  value="${contractPayList.payAmount}"  type="text" name="desc4${status.index + 1}" id="desc4${status.index + 1}" />
	                       </td>
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  name="desc5${status.index + 1}" id="desc5${status.index + 1}" type="text" readonly="readonly"  class="Wdate required"  value="<fmt:formatDate value='${contractPayList.appointePayDate}' pattern='yyyy-MM-dd' />" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	                       </td>	        
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"   value="${contractPayList.remarks}"  type="text" name="desc6${status.index + 1}" id="desc6${status.index + 1}" />
	                       </td>  
	                         <td name = "isinput"><a href="javascript:void(0)" onclick="deltr('${status.index + 1}')">
	                       <input type="hidden"  name = "contractPayId" value="${contractPayList.id}" />
	                       <input type="hidden"  name = "inConfirmStatus" value="${contractPayList.inConfirmStatus}" />
	                       <input type="hidden"  name = "outConfirmStatus" value="${contractPayList.outConfirmStatus}" />
	                       		删除</a>
	                       	</td>                                      
	                  </tr>
	            </c:forEach>
		        </tbody>
			    </table>
			    	
			  	<div style="text-align: center;"  id = "addul">
					<input class="btn btn-danger" value="添加" type="button" id = "addrow"">    				 	
		    	</div>   
			  	
			    </c:if>
			    
			    	 
				<c:if test = "${workconfirmid == 1}">
				<table  class="table table-bordered" id="paytable"  >
				<thead>
				<tr style="text-align: center; height:20px;background: #EAEAEA">
					 <th style="width: 4%">序号</th>
					
				 	 <th style="width: 18%">比例（%）</th>
					 <th style="width: 18%">金额（元）</th>
					 <th style="width: 18%">约定付款时间</th>
					 <th style="width: 18%">备注</th>
					 <th style="width: 4%">操作</th></tr>
					</thead>
		        <tbody>
		        <c:forEach items="${contractPayList}" var="contractPayList" varStatus="status">
     				  <tr id="${status.index + 1}" align='center'>
	                       <td>${status.index + 1}
	                       	</td>
	                     
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  value="${contractPayList.payPercent}"  type="text" name="desc3${status.index + 1}" id="desc3${status.index + 1}"  class = "required"/>
	                       </td>
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  value="${contractPayList.payAmount}"  type="text" name="desc4${status.index + 1}" id="desc4${status.index + 1}" class = "required checkTenTwoNumber"/>
	                       </td>
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  name="desc5${status.index + 1}" id="desc5${status.index + 1}" type="text" readonly="readonly"  class="Wdate required"  value="<fmt:formatDate value='${contractPayList.appointePayDate}' pattern='yyyy-MM-dd' />" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	                       </td>	        
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  value="${contractPayList.remarks}"  type="text" name="desc6${status.index + 1}" id="desc6${status.index + 1}" />
	                       </td> 
	                       <td name = "isinput"><a href="javascript:void(0)" onclick="deltr('${status.index + 1}')">
	                        <input type="hidden"  name = "contractPayId" value="${contractPayList.id}" />
	                        <input type="hidden"  name = "inConfirmStatus" value="${contractPayList.inConfirmStatus}" />
	                        <input type="hidden"  name = "outConfirmStatus" value="${contractPayList.outConfirmStatus}" />
	                       		删除</a>
	                       	</td>                                      
	                  </tr>
	            </c:forEach>
		        </tbody>
			    </table>
			    	  	
			  	<div style="text-align: center;"  id = "addul">
					<input class="btn btn-danger" value="添加" type="button" id = "addrow"">    				 	
		    	</div>   
			    
			    </c:if>
			    	 
				<c:if test = "${workconfirmid == 3}">
				<table  class="table table-bordered" id="paytable"  >
				<thead>
				<tr style="text-align: center; height:20px;background: #EAEAEA">
					 <th style="width: 4%">序号</th>
					 <th style="width: 15%">单位</th>
				 	 <th style="width: 15%">单价</th>
			         <th style="width: 15%">数量</th>
					 <th style="width: 15%">约定付款时间</th>
					 <th style="width: 15%">备注</th>
					  <th style="width: 5%">操作</th></tr>
					</thead>
		        <tbody>
		        <c:forEach items="${contractPayList}" var="contractPayList" varStatus="status">
     				  <tr id="${status.index + 1}" align='center'>
	                       <td>${status.index + 1}
	                       	</td>
	                       
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  value="${contractPayList.payUnit}"  type="text" name="desc3${status.index + 1}" id="desc3${status.index + 1}" class = "required"/>
	                       </td>
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  value="${contractPayList.payAmount}"  type="text" name="desc4${status.index + 1}" id="desc4${status.index + 1}" class = "required checkTenTwoNumber"/>
	                       </td>
	                       <td style="padding-top: 10px;padding-bottom: 0px"> 
	                       		<input style="width:93%"  type="text" name="desc1${status.index + 1}" id="desc1${status.index + 1}" class = "required digits" value='${contractPayList.count}'/>
	                       </td>
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  name="desc5${status.index + 1}" id="desc5${status.index + 1}" type="text" readonly="readonly"  class="Wdate required"  value="<fmt:formatDate value='${contractPayList.appointePayDate}' pattern='yyyy-MM-dd' />" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	                       </td>	        
	                       <td style="padding-top: 10px;padding-bottom: 0px">
	                       		<input style="width:93%"  value="${contractPayList.remarks}"  type="text" name="desc6${status.index + 1}" id="desc6${status.index + 1}" />
	                       </td>  
	                       <td name = "isinput"><a href="javascript:void(0)" onclick="deltr('${status.index + 1}')">
	                       		 <input type="hidden"  name = "contractPayId" value="${contractPayList.id}" />
	                             <input type="hidden"  name = "inConfirmStatus" value="${contractPayList.inConfirmStatus}" />
	                             <input type="hidden"  name = "outConfirmStatus" value="${contractPayList.outConfirmStatus}" />
	                       		删除</a> 
	                       	</td>                                      
	                  </tr>
	            </c:forEach>
		        </tbody>
			    </table>
			     
			    	 <div style="text-align: center;"  id = "addul">
				   
					<input class="btn btn-danger" value="添加" type="button" id = "addrow">
		    		</div>    
				    			    
			    </c:if>
			    
			  
			
			<c:if test = "${workconfirmid != 3 and workconfirmid != 1 and workconfirmid != 2}">
				<table  class="table table-bordered" id="paytable"  >
				<thead> </thead>
		        <tbody> </tbody>
			    </table>
			    		<div style="text-align: center;display: none"  id = "addul">
					<input class="btn btn-danger" value="添加" type="button" id = "addrow">
		    	</div>
				    			    
			    </c:if>
	
			   </td>
			</tr>
		</tbody>
		</table>	
		<div class="form-actions" style="text-align: center;">
			<shiro:hasPermission name="pro:contract:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
		<input type="hidden" id = "paytype" name = "paytype" value = "" />
		<input type="hidden" id = "paydate" name = "paydate" value = "" />
	</form:form>
 
	<script type="text/javascript">
	getProjectId = "${contract.project.id}";
	var paylistthead = "";
	var paylisttbody = "";
	$("#workconfirmid").bind("change", function(){  
		  if($(this).val() == 0){
			$("#paytable thead").empty();
			$("#paytable tbody").empty();
			$("#addul").css("display","none");
 
		  }
		  else if($(this).val() == 2){
			$("#paytable thead").empty();
			$("#paytable tbody").empty();
			paylistthead = "<tr style=\"text-align: center; height:20px;background: #EAEAEA\">"
				+"<th style=\"width: 4%\">序号</th>"
				+"<th style=\"width: 18%\">确认月份</th>"
				+"<th style=\"width: 18%\">比例（%）</th>"
				+"<th style=\"width: 18%\">金额（元）</th>"
				+"<th style=\"width: 18%\">约定付款时间</th>"
				+"<th style=\"width: 18%\">备注</th>"
				+"<th style=\"width: 5%\">操作</th></tr>";				
			  $("#paytable thead").append(paylistthead);			 
			  $("#addul").css("display","block");
		  }
		  else if($(this).val() == 1){
				$("#paytable thead").empty();
				$("#paytable tbody").empty();
				paylistthead = "<tr style=\"text-align: center; height:20px;background: #EAEAEA\">"
						+"<th style=\"width: 4%\">序号</th>"
						 
						+"<th style=\"width: 18%\">比例（%）</th>"
						+"<th style=\"width: 18%\">金额（元）</th>"
						+"<th style=\"width: 18%\">约定付款时间</th>"
						+"<th style=\"width: 18%\">备注</th>"
						+"<th style=\"width: 4%\">操作</th></tr>";				
				 $("#paytable thead").append(paylistthead);			 
				 $("#addul").css("display","block");
		  }
		  else if($(this).val() == 3){
				$("#paytable thead").empty();
				$("#paytable tbody").empty();
				paylistthead = "<tr style=\"text-align: center; height:20px;background: #EAEAEA\">"
						+"<th style=\"width: 4%\">序号</th>"
						+"<th style=\"width: 15%\">单位</th>"
						+"<th style=\"width: 15%\">单价</th>"
						+"<th style=\"width: 15%\">数量</th>"
						+"<th style=\"width: 15%\">约定付款时间</th>"
						+"<th style=\"width: 15%\">备注</th>"
						+"<th style=\"width: 5%\">操作</th></tr>";				
				  $("#paytable thead").append(paylistthead);			 
				  $("#addul").css("display","block");
		  }
 
	})
	
 
  $("#addrow").click(function(){
	  var addcontent = "";
	  getProjectId = "${contract.project.id}";
	  if(getProjectId=="")
		  getProjectId = -1;
	  var _len = $("#paytable tr").length; 
	 
  		if($("#workconfirmid").val() == 2){
  			addcontent = "<tr id="+_len+" align='center'>"
            +"<td>"+_len+"</td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc2"+_len+"' id='desc2"+_len+"' readonly=\"readonly\"  class=\"Wdate required\"  onclick=\"WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});\"/></td>"	 
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc3"+_len+"' id='desc3"+_len+"' class = 'required '/></td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc4"+_len+"' id='desc4"+_len+"' class = 'required checkTenTwoNumber'/></td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc5"+_len+"' id='desc5"+_len+"'  readonly=\"readonly\"  class=\"Wdate required\"   onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});\"/></td>"	        
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc6"+_len+"' id='desc6"+_len+"'/></td>"	                                        
            +"<td><a href=\'javascript:void(0)\' onclick=\'deltr("+_len+")\'>删除</a></td>"
            +"</tr>";
  		}else if($("#workconfirmid").val() == 1){
  			addcontent = "<tr id="+_len+" align='center'>"
  			+"<td>"+_len+"</td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc3"+_len+"' id='desc3"+_len+"' class = 'required'/></td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc4"+_len+"' id='desc4"+_len+"' class = 'required checkTenTwoNumber'/></td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc5"+_len+"' id='desc5"+_len+"'  readonly=\"readonly\"  class=\"Wdate required\"   onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});\"/></td>"	        
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc6"+_len+"' id='desc6"+_len+"'/></td>"	                                        
            +"<td><a href=\'javascript:void(0)\' onclick=\'deltr("+_len+")\'>删除</a></td>"
            +"</tr>";
  		}else if($("#workconfirmid").val() == 3){
  			addcontent = "<tr id="+_len+" align='center'>"
  			+"<td>"+_len+"</td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  class = 'required' type='text' name='desc3"+_len+"' id='desc3"+_len+"' /></td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  class = 'required checkTenTwoNumber' type='text' name='desc4"+_len+"' id='desc4"+_len+"' /></td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  class = 'required digits' type='text' name='desc1"+_len+"' id='desc1"+_len+"' /></td>"
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  name='desc5"+_len+"' id='desc5"+_len+"' type=\"text\" readonly=\"readonly\"  class=\"Wdate required\"  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});\"/></td>"	        
            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%'  type='text' name='desc6"+_len+"' id='desc6"+_len+"' /></td>"	                                        
            +"<td><a href=\'javascript:void(0)\' onclick=\'deltr("+_len+")\'>删除</a></td>"
            +"</tr>";
           
        //    if(_len >= 1)
        //    	 $("#addul").css("display","none");
         
  		} 
	  
	     $("#paytable").append(addcontent);            
	   })    
	 function deltr(index)
     {        //contractPayId  inConfirmStatus  outConfirmStatus  
		var inConfirmStatus = $("tr[id='"+index+"']").children("td:last").find("input[name='inConfirmStatus']").val();
		var outConfirmStatus = $("tr[id='"+index+"']").children("td:last").find("input[name='outConfirmStatus']").val();
		if((inConfirmStatus == 2 || outConfirmStatus == 2) && $("tr[id='"+index+"']").children("td:last").find("input[name='contractPayId']").val() !=""){		
			
			alertx("该条付款信息已经已经被确认，不能删除该信息");
			
		}else{
		var submit = function (v, h, f){
		    if (v == 'ok'){
		    	 var _len = $("#paytable tbody tr").length + 1;
		    	
		      	 var deletecontent = "";
		          $("tr[id='"+index+"']").remove();//删除当前行        
		         
		          for(var i=parseInt(index) + 1,j=_len;i<j;i++)
		          {
		         	 var nextTxtVal2 = $("#desc2"+i).val()==null?"":$("#desc2"+i).val();
		         	 var nextTxtVal3 = $("#desc3"+i).val()==null?"":$("#desc3"+i).val();
		         	 var nextTxtVal4 = $("#desc4"+i).val()==null?"":$("#desc4"+i).val();
		         	 var nextTxtVal5 = $("#desc5"+i).val()==null?"":$("#desc5"+i).val();
		             var nextTxtVal6 = $("#desc6"+i).val()==null?"":$("#desc6"+i).val();
		             var nextTxtVal1 = $("#desc1"+i).val()==null?"":$("#desc1"+i).val();
		           	if($("#workconfirmid").val() == 2){
		          		deletecontent = "<tr id="+(i-1)+" align='center'>"
		              			 +"<td>"+(i-1)+"</td>"
		               			 +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' name='desc2"+(i-1)+"'  value='"+nextTxtVal2+"'  id='desc2"+(i-1)+"' type=\"text\" readonly=\"readonly\"  class=\"Wdate required\"  onclick=\"WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});\"/></td>"	 
		     		             +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc3"+(i-1)+"' id='desc3"+(i-1)+"' value='"+nextTxtVal3+"'  class = 'required '/></td>"
		     		             +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc4"+(i-1)+"' id='desc4"+(i-1)+"' value='"+nextTxtVal4+"'  class = 'required checkTenTwoNumber'/></td>"
		     		             +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' name='desc5"+(i-1)+"'  value='"+nextTxtVal5+"' id='desc5"+(i-1)+"'  type=\"text\" readonly=\"readonly\"  class=\"Wdate required\"  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});\"/></td>"	        
		     		             +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc6"+(i-1)+"' value='"+nextTxtVal6+"' id='desc6"+(i-1)+"'/></td>"                             
		     		             +"<td><a href=\'javascript:void(0)\' onclick=\'deltr("+(i-1)+")\'>删除</a></td>"
		     		             +"</tr>";    		
		          	}else if($("#workconfirmid").val() == 1){
		          		deletecontent = "<tr id="+(i-1)+" align='center'>"
		          		 +"<td>"+(i-1)+"</td>"
    		             +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc3"+(i-1)+"' id='desc3"+(i-1)+"' value='"+nextTxtVal3+"'  class = 'required'/></td>"
    		             +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc4"+(i-1)+"' id='desc4"+(i-1)+"' value='"+nextTxtVal4+"'  class = 'required checkTenTwoNumber'/></td>"
    		             +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' name='desc5"+(i-1)+"'  value='"+nextTxtVal5+"' id='desc5"+(i-1)+"'  type=\"text\" readonly=\"readonly\"  class=\"Wdate required\"  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});\"/></td>"	        
    		             +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc6"+(i-1)+"' value='"+nextTxtVal6+"' id='desc6"+(i-1)+"'/></td>"                             
    		             +"<td><a href=\'javascript:void(0)\' onclick=\'deltr("+(i-1)+")\'>删除</a></td>"
    		             +"</tr>";    		
         			}else if($("#workconfirmid").val() == 3){
		          		deletecontent = "<tr id="+(i-1)+" align='center'>"
		          		 +"<td>"+(i-1)+"</td>"
		     		            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc3"+(i-1)+"' id='desc3"+(i-1)+"' value='"+nextTxtVal3+"'  class = 'required'/></td>"
		     		            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc4"+(i-1)+"' id='desc4"+(i-1)+"' value='"+nextTxtVal4+"'  class = 'required checkTenTwoNumber'/></td>"
		       		            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc1"+(i-1)+"' id='desc1"+(i-1)+"' value='"+nextTxtVal1+"'  class = 'required digits'/></td>"
		     		            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' name='desc5"+(i-1)+"'  value='"+nextTxtVal5+"' id='desc5"+(i-1)+"'  type=\"text\" readonly=\"readonly\"  class=\"Wdate required\"  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});\"/></td>"	        
		     		            +"<td style='padding-top: 10px;padding-bottom: 0px'><input style='width:93%' type='text' name='desc6"+(i-1)+"' value='"+nextTxtVal6+"' id='desc6"+(i-1)+"'/></td>"                             
		     		           +"<td><a href=\'javascript:void(0)\' onclick=\'deltr("+(i-1)+")\'>删除</a></td>"
		     		            +"</tr>";  
		    		
		          	} 
		            $("tr[id="+i+"]").replaceWith(deletecontent); 
		          }    
		//	if($("#workconfirmid").val() == 3 )
		 //        	if(_len == 2)
		 //        		 $("#addul").css("display","block");
		//          
		          jBox.tip('删除成功');
		    }
		    else if (v == 'cancel')
		    	  jBox.tip('操作已取消');

		    return true; //close
		};
		 
		$.jBox.confirm("确定要删除该条付款记录吗？", "提示", submit);
		
		}
     }
	
	function setprojectdate(map){
		$("#contractCodediv").html(map.contractCode);
		$("#customercompanyName").text(map.companyName);
		$("#contractCode").val(map.contractCode);
		
		if($("#projectselectId").val()==""){
			$("#projectselectName").val(map.project_name);
			$("#projectselectId").val(map.project_id);
		}
		
		
		$("#project_type").text(map.project_type);
    	$("#bpm_employee").text(map.bpm_employee);
    	$("#pm_employee").text(map.pm_employee);
    	$("#pm_employee_mobile").text(map.pm_employee_mobile);
    	$("#iscostcontract").text(map.iscostcontract==2?"是":"否");
    	$("#isframecontract").text(map.isframecontract==2?"是":"否");
    	$("#ispaycontract").text(map.ispaycontract==2?"是":"否");
    	$("#sys_area").text(map.sys_area);
    	if(map.isframecontract==2){    		 
    	//	var iscostcontract2 = "<td  class='labletd'>工作量确认方法 </td> <td style='padding-top: 10px;padding-bottom: 0px'></td> <td  class='labletd'>约定付款时间  </td>  <td> </td> <td  class='labletd'>备注  </td><td>  </td>";
    	//	$("tr[name='selectisframecontract']").html("");
    	//	$("tr[name='selectisframecontract']").html(iscostcontract2);
    		$("#workconfirmid").empty();
    		$("#workconfirmid").append("<option value='0'  selected = 'selected'></option>");
    	 
    		$("#workconfirmid").append("<option value='3'>按量确认</option>");
    		$("#workconfirmid").val(3).trigger("change");
    	}
    	else if(map.isframecontract==1){
    		$("#workconfirmid").empty();
    		
    		$("#workconfirmid").append("<option value='0' selected = 'selected'</option>");
    		$("#workconfirmid").append("<option value='1'>按次确认</option>");
    		$("#workconfirmid").append("<option value='2'>按月确认</option>");
    		
    		$("#workconfirmid").val(0).trigger("change");
     	    	//	$("#workconfirmid").val("0");
    	//	var iscostcontract1 = "<td  class='labletd'>工作量确认方法 </td> <td> </td> <td  class='labletd'>约定付款时间 </td> <td colspan='3'>  </td>";
    	//	$("tr[name='selectisframecontract']").html("");
    	//	$("tr[name='selectisframecontract']").html(iscostcontract1);
    	}
    		
    	
	}
	function clearprojectdate(){
		$("#contractCodediv").html("");
		$("#project_type").text("");
		$("#bpm_employee").text("");
		$("#pm_employee").text("");
		$("#pm_employee_mobile").text("");
		$("#iscostcontract").text("");
		$("#isframecontract").text("");
		$("#ispaycontract").text("");
		$("#sys_area").text("");
		$("#contractCode").val("");
		$("#customercompanyName").text("");
	}
	
	function setcustomerdate(map){
	 
		$("#customermobile").val(map.customerUser_Moblie);
    

	}
	
	function clearcustomer(){
		$("#customermobile").val("");
     
	}
 
	$("#btnSubmit").click(function(){	
	 	var paydate = "";
		 $("#paytable tbody").find("tr").each(function(){
			 
			var inConfirmStatus = $(this).children("td:last").find("input[name='inConfirmStatus']").val();
			var outConfirmStatus = $(this).children("td:last").find("input[name='outConfirmStatus']").val();
			if((inConfirmStatus == 2 || outConfirmStatus == 2) && $(this).children("td:last").find("input[name='contractPayId']").val() !=""){
        	 }else{
        	    $(this).find("td").each(function(){	 
        			paydate += $(this).children("input").val()==null?"":$(this).children("input").val() +",";
                 })    
        	 }
	
	  })
	  paydate = paydate.substring(0,paydate.length - 1);
	  $("#paydate").val(paydate);
	  $("#paytype").val($("#workconfirmid").val());
 
	 
	  $("#inputForm").submit();
	   
	  })
	  
	  
 
 	$("td[name='isinput']").each(function(){
 	
 		var inConfirmStatus = $(this).find("input[name='inConfirmStatus']").val();
		var outConfirmStatus = $(this).find("input[name='outConfirmStatus']").val();
		 
		if((inConfirmStatus == 2 || outConfirmStatus == 2) && $(this).find("input[name='contractPayId']").val() !=""){		
			 
			$(this).parent().find("input").attr("disabled",true);
		  }
		
 	})
	</script>	
</body>
</html>