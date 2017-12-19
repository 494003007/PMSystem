<%--
  Created by IntelliJ IDEA.
  User: KingsWong
  Date: 17/3/11
  Time: 上午11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>打印计提费用增加申请单</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(
            function () {
                //$("#name").focus();
                $("#inputForm").validate(
                    {
                        submitHandler: function (form) {
                            loading('正在提交，请稍等...');
                            form.submit();
                        },
                        errorContainer: "#messageBox",
                        errorPlacement: function (error, element) {
                            $("#messageBox").text("输入有误，请先更正。");
                            if (element.is(":checkbox")
                                || element.is(":radio")
                                || element.parent().is(
                                    ".input-append")) {
                                error.appendTo(element.parent()
                                    .parent());
                            } else {
                                error.insertAfter(element);
                            }
                        }
                    });
            });
    </script>
    <style type="text/css">
        .labletd {
            width: 10%;
            background: #F4F4F4;
        }

        #table tbody tr {

            height: 40px;

        }
    </style>
</head>
<body>

<div style="width: 100%;font-size: 25px;text-align: center;margin-top: 10px;margin-bottom: 10px">计提费用付款通知单</div>
<table id="table" class="table table-bordered table-condensed">

    <tbody>
    <tr>
        <td class="labletd">费用名称</td>

        <td colspan="3">${outcontractPayRegister.printName}</td>
        <td class="labletd">OA流水号</td>

        <td colspan="2"></td>
    </tr>
    <tr>
        <td class="labletd">OA项目名称</td>
        <td colspan="2" style="width: 40%">${project.name} </td>
        <td class="labletd">大区/区域</td>
        <td colspan="3">${printMap.sysName} </td>
    </tr>
    <tr>
        <td class="labletd">供应商</td>
        <td colspan="2" style="width: 40%">${printMap.companyName} </td>
        <td class="labletd">归属月份</td>
        <td colspan="3"><fmt:formatDate value='${outcontractPayRegister.startTime}' pattern='yyyy-MM'/></td>
    </tr>

    <tr>
        <td class="labletd">付款金额</td>

        <td colspan="6" style="text-align: center">
            人民币:${money} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <fmt:formatNumber value="${outcontractPayRegister.payAmount}" pattern="###,##0.00"></fmt:formatNumber>
        </td>
    </tr>
    <tr>
        <td class="labletd">计提编号</td>

        <td colspan="6">${outcontractBill1.billNumber}</td>
    </tr>
    <tr>
        <td rowspan="3" class = "labletd" >支付方式</td>
        <td rowspan="3" style="text-align: center;" width="100px">
            对公转账
        </td>
        <td class = "labletd">户名</td>

        <td colspan="2">${printMap.bankName} </td>
        <td rowspan="3" colspan="2" style="text-align: center;color:gainsboro">
            与收入和回款是否关联？
            商务确认！(如关联)
        </td>
    </tr>

    <tr>
        <td class = "labletd">开户银行</td>

        <td colspan="2">${fns:getDictLabels(printMap.bankType,"bank","")} </td>
    </tr>
    <tr>
        <td class = "labletd">银行账号</td>

        <td colspan="2">${printMap.bankAccount} </td>
    </tr>
    <tr>
        <td class="labletd">大区审核</td>
        <td></td>
        <td class="labletd">财务审核</td>
        <td></td>
        <td class="labletd">总经办审核</td>
        <td></td>
    </tr>
    <tr>
        <td class="labletd">支付</td>
        <td></td>
        <td class="labletd">出纳</td>
        <td></td>
        <td class="labletd">支付日期</td>
        <td colspan="2"></td>
    </tr>
    <tr>
        <td colspan="7" style="text-align: center;">
            <input type="checkbox"/><label>增值税专用发票</label>
            &nbsp;&nbsp;&nbsp;
            <input type="checkbox"/><label> 验收单</label>
            &nbsp;&nbsp;&nbsp;
            <input type="checkbox"/><label> 内部成分分摊表</label>
        </td>
    </tr>
    </tbody>
</table>
<br>
<table class="table table-bordered table-condensed">
    <thead>
    <tr>
        <td class="labletd"></td>
        <td class="labletd" colspan="3">主合同情况</td>
        <td class="labletd" colspan="3">采购合同情况</td>
    </tr>
    </thead>

    <tbody>
    <tr>
        <td class="labletd">合同名称</td>
        <td  colspan="3">${project.name}</td>
        <td  colspan="3">${outcontractPayRegister.outcontractName}</td>
    </tr>
    <tr>
        <td class="labletd">合同金额</td>
        <td  colspan="3"><fmt:formatNumber value="${not empty project.pridictamount?project.pridictamount:'0.0'}" pattern="###,##0.00"></fmt:formatNumber></td>
        <td  colspan="3"><fmt:formatNumber value="${outcontractPayRegister.outcontractPredictPay}" pattern="###,##0.00"></fmt:formatNumber></td>
    </tr>
    <tr>
        <td class="labletd">合同编号</td>
        <td  colspan="3">${project.projectCode}</td>
        <td  colspan="3">${outcontract.outcontractCode}</td>
    </tr>
    <tr>
        <td class="labletd">税 率</td>
        <td  colspan="3">${fns:getDictLabels(printMap.invoiceRevenue,"tax_percent","")}</td>
        <td  colspan="3">${fns:getDictLabels(printMap.oInvoiceRevenue,"tax_percent","")}</td>
    </tr>
    <tr>
        <td class="labletd">付款约定</td>
        <td  colspan="3">${fns:getDictLabels(printMap.workconfirmid,"work_confirm","")}</td>
        <td  colspan="3">${outcontract.payPromise}</td>
    </tr>
    </tbody>
</table>
<br>
<table class="table table-bordered table-condensed" id="table1">
    <thead>
    <tr>
        <th colspan="7" style="text-align: center;  font-size: 18px;background: #EAEAEA;">本项目历史计提明细</th>
    </tr>
    <tr>
        <th>费用名称</th>
        <th>计提金额</th>
        <th>时间</th>
        <th>付款金额</th>

        <th>付款时间</th>
        <th>余额</th>

        <th>发票状态</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="outcontractBill" varStatus="varStatus">
        <tr>
            <td style="text-align: center;">
                ${outcontractBill.printName}
            </td>
            <td id="b_${varStatus.index+1}" style="text-align: center;">
                    ${outcontractBill.billAmount}
            </td>
            <td style="text-align: center;">
                <fmt:formatDate value='${outcontractBill.billDate}' pattern='yyyy-MM-dd'/>
            </td>
            <td id="p_${varStatus.index+1}" style="text-align: center;">
                    ${outcontractBill.payAmount}
            </td>
            <td style="text-align: center;">
                <fmt:formatDate value='${outcontractBill.payDate}' pattern='yyyy-MM-dd'/>
            </td>
            <td style="text-align: center;" id="bl_${varStatus.index+1}">

            </td>
            <td style="text-align: center;">
                <c:choose>
                    <c:when test="${outcontractBill.files == null}">
                        未开具
                    </c:when>

                    <c:otherwise>
                        已开具
                    </c:otherwise>

                </c:choose>

            </td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <th style="text-align: center">小计</th>
        <th id="billAmount" style="text-align: center"></th>
        <th style="text-align: center">——</th>
        <th id="payAmount" style="text-align: center"></th>
        <th style="text-align: center">——</th>
        <th id="balance" style="text-align: center"></th>
        <th style="text-align: center">——</th>
    </tr>
    </tfoot>
</table>
<button id="printButton" style="display: none"></button>
<script type="text/javascript">
    $(document).ready(function () {
        var _len = $("#table1 tr").length - 3;
        var b_sum = 0;
        var p_sum = 0;
        for(var i = 1;i <= _len;i++){
            b_sum += parseFloat(document.getElementById("b_"+i).innerText);
            if(document.getElementById("p_"+i).innerText == "") {
                p_sum += 0;
            }
            else{
                p_sum += parseFloat(document.getElementById("p_" + i).innerText);
            }
            if(document.getElementById("p_"+i).innerText == "") {
                document.getElementById("bl_"+i).innerText = document.getElementById("b_"+i).innerText;
            }
            else{
                document.getElementById("bl_"+i).innerText = parseFloat(document.getElementById("b_"+i).innerText) - parseFloat(document.getElementById("p_"+i).innerText) + "";
            }
        }
        document.getElementById("billAmount").innerText = b_sum + "";
        document.getElementById("payAmount").innerText = p_sum + "";
        document.getElementById("balance").innerText = b_sum - p_sum + "";
    })

    $("#printButton").click(function () {
        $("#billPrintName").html($("#billPrintName").val());
        var oldstr = document.body.innerHTML;
        var headstr = "<html><head><title></title></head><body>";
        var footstr = "</body>";

        var newstr = oldstr;


        $("body").html(headstr + newstr + footstr);
        window.print();
        $("body").empty();
        $("body").html(oldstr);

    })


</script>
</body>
</html>