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
                                <th>Product ID</th>
                                <th>Name</th>
                                <th>Pic</th>
                                <th>Price</th>
                                <th>Stock</th>
                                <th>Info</th>
                                <th>Category</th>
                                <th>Create Time</th>
                                <th>Modified Time</th>
                                <th colspan="2">Action</th>
                            </tr>
                            </thead>
                            <tbody>

                            <#list productInfoPage.content as product>
                                <tr>
                                    <td>${product.productId}</td>
                                    <td>${product.productName}</td>
                                    <td><img height="100" width="100" src="${product.productIcon}"></td>
                                    <td>${product.productPrice}</td>
                                    <td>${product.productStock}</td>
                                    <td>${product.productDescription}</td>
                                    <td>${product.categoryType}</td>
                                    <td>${product.createTime}</td>
                                    <td>${product.updateTime}</td>
                                    <td>
                                        <a href="/sell/seller/product/index?productId=${product.productId}">Modify</a>
                                    </td>
                                    <td>
                                        <#if product.getProductStatusEnum().getCode()==0>
                                            <a href="/sell/seller/product/off_sale?productId=${product.productId}">Off Sale</a>
                                            <#else>
                                            <a href="/sell/seller/product/on_sale?productId=${product.productId}">On Sale</a>
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
                                <li><a href="/sell/seller/product/list?page=${currentPage - 1}&size=${currentSize}">Prev</a></li>

                            </#if>
                            <#list 1..productInfoPage.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class = "disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li ><a href="/sell/seller/product/list?page=${index}&size=${currentSize}">${index}</a></li>
                                </#if>
                            </#list>
                            <#if currentPage gte productInfoPage.getTotalPages()>
                                <li class="disabled"><a href="#">Next</a></li>
                            <#else>
                                <li><a href="/sell/seller/product/list?page=${currentPage + 1}&size=${currentSize}">Next</a></li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    </div>
    </body>

</html>