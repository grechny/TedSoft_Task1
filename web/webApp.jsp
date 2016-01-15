<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello World!</title>
</head>
<body>
    <script>
        $(document).ready(function(){
            // click on button submit
            $("#submit").on('click', function(){

                $("div#result").html("");
                var keyword = { 'keyword': $('#keyword').val() };
                // send ajax
                $.ajax({
                    url: 'jsonData.action', // url where to submit the request
                    type : "POST", // type of action POST || GET
                    dataType : 'json', // data type
                    contentType:"application/json; charset=utf-8",
                    data : JSON.stringify(keyword),
                    success : function(data) {
                        $("div#result").html('<p>' + data.status + '</p>').fadeIn("slow");
                    },
                    error: function(xhr, resp, text) {
                        console.log(xhr, resp, text);
                    }
                })
            });
        });
    </script>


    <form id="form" method="post" class="form-horizontal">
        Keyword: <input id="keyword" type=text size=20 name=keyword >
        <input id="submit" type="button" name="submit" value="submit">
    </form>
    <div id="result" style="display:none"></div>
</body>
</html>
