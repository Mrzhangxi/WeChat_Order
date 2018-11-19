<html>
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
                    <form role="form" class="form-inline" action="/sell/seller/login">
                        <h1>模拟登录</h1><br>
                        <b>数据库目前用户openId为：123456</b><br>
                        <b>实际项目中，openId为微信接入后获取的</b>
                        <br><br>
                        <div class="form-group">
                            <label for="exampleInputEmail1">用户名</label>
                            <input type="text" class="form-control" name="openid"/>
                        </div>
                        <button type="submit" class="btn btn-default">登录</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>