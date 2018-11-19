<!DOCTYPE html>
<html lang="en">
    <#include "../common/header.ftl">
    <body>
    <div id="wrapper" class="toggled">
        <#--边栏sidebar-->
        <#include "../common/nav.ftl">
        <#--主要内容content-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>订单id</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                    <#list orderDTOList as orderDTO>
                    <tr class="success">
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.orderAmount}</td>
                        <td>${orderDTO.getOrderStatusEnum().getMessage()}</td>
                        <td>${orderDTO.getPayStatusEnum().getMessage()}</td>
                        <td>${orderDTO.viewCreateTime}</td>
                        <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                        <td>
                                <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                </#if>
                        </td>
                    </tr>
                    <#--${orderDTO.orderId}<br>-->
                    </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>
                    <#list 1..sumPage as i>
                        <#if currentPage == i>
                            <li class="disabled">
                                <a href="#">${i}</a>
                            </li>
                        <#else>
                            <li>
                                <a href="/sell/seller/order/list?page=${i}&${size}">${i}</a>
                            </li>
                        </#if>
                    </#list>
                    <#if currentPage gte sumPage>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <div class="modal fade" id="MyModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        提醒
                    </h4>
                </div>
                <div class="modal-body">
                    你有新的订单
                </div>
                <div class="modal-footer">
                    <button type="button" onclick="javascript:document.getElementById('notice').pause()"  class="btn btn-default" data-dismiss="modal">关闭</button> <button type="button" onclick="location.reload()" class="btn btn-primary">查看新的订单</button>
                </div>
            </div>

        </div>

    </div>

    <#--播放音乐-->
    <audio id="notice" loop="loop">
        <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
    </audio>

    <#--<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>-->
    <#--<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->

    <script>
        var websocket = null;
        if ("WebSocket" in window){
            websocket = new WebSocket("ws://www.bnkeji.top/sell/webSocket")
        } else {
            alert("该浏览器不支持");
        }

        websocket.onopen = function (ev) {
            console.log("建立连接");
        }

        websocket.onclose = function (ev) {
            console.log("连接关闭");
        }

        websocket.onmessage = function (ev) {
            console.log("收到消息：" + ev.data);
            // alert("收到新订单，编号为：" + ev.data);
            $("#MyModal").modal("show");

            //弹窗提醒，播放音乐
            document.getElementById('notice').play();
        }

        websocket.onerror = function (ev) {
            alert("websocket通信发生错误")
        }

        window.onbeforeunload = function (ev) {
            websocket.close()
        }
    </script>

    </body>

</html>

<#--${orderDTOList[0]}-->
