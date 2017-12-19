<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>打印支付单</title>
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
<div style="width: 100%;font-size: 25px;text-align: center;margin-top: 10px;margin-bottom: 10px">费用计提列账申请</div>
<table id="table" class="table table-bordered table-condensed">
    <tbody>
    <tr>
        <td class="labletd">费用名称</td>

        <td colspan="6">${outcontractBill.printName}</td>
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
        <td colspan="3"><fmt:formatDate value='${outcontractBill.billDate}' pattern='yyyy-MM'/></td>
    </tr>

    <tr>
        <td class="labletd">备注</td>

        <td colspan="6"></td>
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
        <th colspan="14" style="text-align: center;  font-size: 18px;background: #EAEAEA;">费用明细</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="labletd">计提编号</td>
        <td width="200px">${outcontractBill.billNumber}</td>
        <td class="labletd">本次计提金额</td>
        <td width="100px" id="billAmount">${outcontractBill.billAmount}</td>
        <td class="labletd">税率</td>
        <td width="100px">
            <select id="invoiceSelect">
                <option value="1">无</option>
                <option value="2">3%</option>
                <option value="3">6%</option>
                <option value="4">17%</option>
            </select>
        </td>
        <td class="labletd">不含税金额</td>
        <td id="price"></td>
    </tr>
    <tr>
        <td class="labletd">制表</td>
        <td></td>
        <td class="labletd">财务审核</td>
        <td></td>
        <td class="labletd">总经办审核</td>
        <td colspan="3"></td>
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
        <td colspan="3">${project.name}</td>
        <td colspan="3">${outcontractBill.outcontractName}</td>
    </tr>
    <tr>
        <td class="labletd">合同金额</td>
        <td colspan="3"><fmt:formatNumber value="${not empty project.pridictamount?project.pridictamount:'0.0'}"
                                          pattern="###,##0.00"></fmt:formatNumber></td>
        <td colspan="3"><fmt:formatNumber value="${outcontractBill.outcontractPredictPay}"
                                          pattern="###,##0.00"></fmt:formatNumber></td>
    </tr>
    <tr>
        <td class="labletd">合同编号</td>
        <td colspan="3">${printMap.contractCode}</td>
        <td colspan="3">${outcontract.outcontractCode}</td>
    </tr>
    <tr>
        <td class="labletd">税 率</td>
        <td colspan="3">${fns:getDictLabels(printMap.invoiceRevenue,"tax_percent","")}</td>
        <td colspan="3">${fns:getDictLabels(printMap.oInvoiceRevenue,"tax_percent","")}</td>
    </tr>
    <tr>
        <td class="labletd">付款约定</td>
        <td colspan="3">${fns:getDictLabels(printMap.workconfirmid,"work_confirm","")}</td>
        <td colspan="3">${outcontract.payPromise}</td>
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
            ${outcontractBill.printName}
        </td>
        <td style="text-align: center;">
            ${outcontractBill.outcontractPredictPay}
        </td>
        <td style="text-align: center;">
            ${outcontractBill.totalpayamount}
        </td>
        <td style="text-align: center">
            <fmt:formatDate value="${outcontractBill.outcontractPayDate}" pattern="yyyy-MM"/>
        </td>
        <td style="text-align: center;">
            ${outcontractBill.billAmount}
        </td>
        <td style="text-align: center">
            <fmt:formatDate value="${outcontractBill.billDate}" pattern="yyyy-MM"/>
        </td>
        <td id="isPay">

        </td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
        <td>小计</td>
        <td id="cm_sum"> ${outcontractBill.outcontractPredictPay}</td>
        <td id="cp_sum">${outcontractBill.totalpayamount}</td>
        <td>——</td>
        <td id="cb_sum">${outcontractBill.billAmount}</td>
        <td>——</td>
        <td>——</td>
    </tr>
    <tr>
        <td colspan="2">累计列账金额(含本次申请)</td>
        <td colspan="2" id="sum">${outcontractBill.billAmount + outcontractBill.totalpayamount}</td>
        <td colspan="2">已支付比例</td>
        <td id="payPersent"></td>
    </tr>
    </tfoot>
</table>

<button id="printButton" style="display: none"></button>
<script type="text/javascript">

    $(document).ready(function () {
        var a = new Date("${outcontractBill.payDate}");
        var b = new Date("${outcontractBill.firstPrint}");
        if(a.getTime()<b.getTime()){
            document.getElementById("isPay").innerText = "已付款"
        }
        else{
            document.getElementById("isPay").innerText = "未付款"
        }
        if(document.getElementById("isPay").innerText == "未付款"){
            var c = ${outcontractBill.billAmount + outcontractBill.totalpayamount} / ${outcontractBill.outcontractPredictPay}
            c = (c * 100).toFixed(2) + "%"
            document.getElementById("payPersent").innerText = c;
            document.getElementById("sum").innerText = ${outcontractBill.billAmount + outcontractBill.totalpayamount} + "";
        }
        else{
            var c = ${outcontractBill.totalpayamount} / ${outcontractBill.outcontractPredictPay}
            c = (c * 100).toFixed(2) + "%"
            document.getElementById("payPersent").innerText = c;
            document.getElementById("sum").innerText = ${outcontractBill.totalpayamount} + "";
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