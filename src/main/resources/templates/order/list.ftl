<html>
    <#include "../common/header.ftl">
    <body>

    <div id="wrapper" class="toggled">
        <#--sidebar-->
        <#include "../common/nav.ftl">

        <#--main content-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-condensed">
                            <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Name</th>
                                <th>Phone</th>
                                <th>Address</th>
                                <th>Total Amount</th>
                                <th>Order Status</th>
                                <th>Payment</th>
                                <th>Payment Status</th>
                                <th>Create Time</th>
                                <th colspan="2">Action</th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list orderDTOPage.content as orderDTO>
                                <tr>
                                    <td>${orderDTO.orderId}</td>
                                    <td>${orderDTO.buyerName}</td>
                                    <td>${orderDTO.buyerPhone}</td>
                                    <td>${orderDTO.buyerAddress}</td>
                                    <td>${orderDTO.buyerAmount}</td>
                                    <td>${orderDTO.orderStatusEnum}</td>
                                    <td>WeChat</td>
                                    <td>${orderDTO.payStatusEnum}</td>
                                    <td>${orderDTO.createTime}</td>
                                    <td>
                                        <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">Detail</a>
                                    </td>
                                    <td>
                                        <#if orderDTO.orderStatusEnum.getCode()==0>
                                            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">Cancel</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                            <#if currentPage lte 1>
                                <li class="disabled"><a href="#">Prev</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${currentSize}">Prev</a></li>

                            </#if>
                            <#list 1..orderDTOPage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class = "disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li ><a href="/sell/seller/order/list?page=${index}&size=${currentSize}">${index}</a></li>
                                </#if>
                            </#list>
                            <#if currentPage gte orderDTOPage.getTotalPages()>
                                <li class="disabled"><a href="#">Next</a></li>
                            <#else>
                                <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${currentSize}">Next</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </div>
    </body>
</html>