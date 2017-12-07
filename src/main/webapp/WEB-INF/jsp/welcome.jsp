<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ include file="/include/easyuilibs.jsp" %>
<style>

    text {
        font: 16px sans-serif;
    }

    body {
	background-color: #ffffff;
	background-image:;
	background-repeat: repeat;
	background-position: 0% 0%;
	margin:20px;
	height: 100%;
    width: 1000px;
    height: auto;
    margin: 0px auto;
    }
    
</style>


<script type="text/javascript">
    $(document).ready(function () {
        setInterval("document.getElementById('webjx').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());", 1000);
    });
</script>
<body>
<div style="overflow-y: auto;">
    <table>
        <tr height='30px'>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;欢迎使用，钦智防伪管理系统</td>
        </tr>
    </table>
</div>
<span style="position: absolute; z-index: 1; height: 30px; right: 20px; top: 10px; color: black;">
   	&nbsp;&nbsp;今天是：<span id="webjx"></span>
    </span>
<div style="overflow-y: auto;">

</div>
<div>
    <style>
        span {
            font-size: 17px;
            color: black;
            white-space: pre-wrap;
        }

        li {
            margin-left: 20px;
            margin-top: 10px;
            padding-left: 10px;
            margin-right: 10px;
            line-height: 26px;
            height: 26px;
            overflow: hidden;
            font-size: 17px;
            background: url(/img/square_icon.png) no-repeat left;
            background-size: 8px;
            _zoom: 1;
        }

        a {
            color: black;
            font-size: 17px;

        }
    </style>
    <ul>
        

    </ul>
</div>


</body>

