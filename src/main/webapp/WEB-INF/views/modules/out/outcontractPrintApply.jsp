<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>打印费用支付申请单</title>
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

<div style="width: 100%;font-size: 25px;text-align: center;margin-top: 10px;margin-bottom: 10px">费用支付申请(内部)</div>
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
        <td colspan="2" style="width: 40%">${printMap.projectName} </td>
        <td class="labletd">大区/区域</td>
        <td colspan="3">${printMap.sysName} </td>
    </tr>
    <tr>
        <td class="labletd">供应商</td>
        <td colspan="2" style="width: 40%">${printMap.companyName} </td>
        <td class="labletd">归属月份</td>
        <td colspan="3"><fmt:formatDate value="${outcontractPayRegister.startTime}" pattern="yyyy-MM-dd"/></td>
    </tr>

    <tr>
        <td class="labletd">付款金额</td>

        <td colspan="6" style="text-align: center">
            人民币:${money} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <fmt:formatNumber value="${outcontractPayRegister.payAmount}" pattern="###,##0.00"></fmt:formatNumber>
        </td>
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
        <td class="labletd">申请人</td>
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
        <td  colspan="3">${outcontract.name}</td>
    </tr>
    <tr>
        <td class="labletd">合同金额</td>
        <td  colspan="3"><fmt:formatNumber value="${not empty project.pridictamount?project.pridictamount:'0.0'}" pattern="###,##0.00"></fmt:formatNumber></td>
        <td  colspan="3"><fmt:formatNumber value="${outcontractBill1.outcontractPredictPay}" pattern="###,##0.00"></fmt:formatNumber></td>
    </tr>
    <tr>
        <td class="labletd">合同编号</td>
        <td  colspan="3">${printMap.contractCode}</td>
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
        <th colspan="14" style="text-align: center;  font-size: 18px;background: #EAEAEA;">外包合同列账列表</th>
    </tr>
    <tr>
        <th>付款名称(依据合同)</th>
        <th>计划金额</th>
        <th>支付列账</th>
        <th>时间</th>
        <th>计提列账</th>
        <th>时间</th>
        <th>计提付款状态</th>
    </tr>
    </thead>
    <tbody>
    <tr align='center' style="height: 50px;">
        <td style="text-align: center;">
            ${outcontractBill1.printName}
        </td>
        <td style="text-align: center;">
            ${outcontractBill1.outcontractPredictPay}
        </td>
        <td style="text-align: center;">
            ${outcontractBill1.totalpayamount}
        </td>
        <td style="text-align: center">
            <fmt:formatDate value="${outcontractBill1.outcontractPayDate}" pattern="yyyy-MM"/>
        </td>
        <td style="text-align: center;">
            ${outcontractBill1.billAmount}
        </td>
        <td style="text-align: center">
            <fmt:formatDate value="${outcontractBill1.billDate}" pattern="yyyy-MM"/>
        </td>
        <td id="isPay">

        </td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
        <td>小计</td>
        <td id="cm_sum"> ${outcontractBill1.outcontractPredictPay}</td>
        <td id="cp_sum">${outcontractBill1.totalpayamount}</td>
        <td>——</td>
        <td id="cb_sum">${outcontractBill1.billAmount}</td>
        <td>——</td>
        <td>——</td>
    </tr>
    <tr>
        <td colspan="2">累计列账金额(含本次申请)</td>
        <td colspan="2" id="sum">${outcontractBill1.billAmount + outcontractBill1.totalpayamount}</td>
        <td colspan="2">已支付比例</td>
        <td id="payPersent">${outcontractBill1.payDate}</td>
    </tr>
    </tfoot>
</table>
<button id="printButton" style="display: none"></button>
<script type="text/javascript">

    $(document).ready(function () {
        var a = new Date("${outcontractBill1.payDate}");
        var b = new Date("${outcontractBill1.firstPrint}");
        if(a.getTime()<b.getTime()){
            document.getElementById("isPay").innerText = "已付款";
        }
        else{
            document.getElementById("isPay").innerText = "未付款";
        }
        if(document.getElementById("isPay").innerText == "未付款"){
            var c = ${outcontractBill1.billAmount + outcontractBill1.totalpayamount} / ${outcontractBill1.outcontractPredictPay}
            c = (c * 100).toFixed(2) + "%"
            document.getElementById("payPersent").innerText = c;
            document.getElementById("sum").innerText = ${outcontractBill1.billAmount + outcontractBill1.totalpayamount} + "";
        }
        else{
            var c = ${outcontractBill1.totalpayamount} / ${outcontractBill1.outcontractPredictPay}
            c = (c * 100).toFixed(2) + "%"
            document.getElementById("payPersent").innerText = c;
            document.getElementById("sum").innerText = ${outcontractBill1.totalpayamount} + "";
        }
    })

    $("#printButton").click(function () {
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