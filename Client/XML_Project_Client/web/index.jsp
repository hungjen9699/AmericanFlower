<%-- 
    Document   : index
    Created on : Nov 5, 2020, 10:26:56 PM
    Author     : hungj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>body {
                font-family: "Lato", sans-serif;
            }



            .main-head{
                height: 150px;
                background: #FFF;

            }

            .sidenav {
                height: 100%;
                background-image: url("images/flowerback.jpg");
                overflow-x: hidden;
                padding-top: 20px;
            }


            .main {
                padding: 0px 10px;
            }

            @media screen and (max-height: 450px) {
                .sidenav {padding-top: 15px;}
            }

            @media screen and (max-width: 450px) {
                .login-form{
                    margin-top: 10%;
                }

                .register-form{
                    margin-top: 10%;
                }
            }

            @media screen and (min-width: 768px){
                .main{
                    margin-left: 40%; 
                }

                .sidenav{
                    width: 40%;
                    position: fixed;
                    z-index: 1;
                    top: 0;
                    left: 0;
                }

                .login-form{
                    margin-top: 80%;
                }

                .register-form{
                    margin-top: 20%;
                }
            }


            .login-main-text{
                margin-top: 20%;
                padding: 60px;
                color: #fff;
            }

            .login-main-text h2{
                font-weight: 300;
            }

            .btn-black{
                background-color: #000 !important;
                color: #fff;
            }</style>
    </head>
    <body>
        <div class="sidenav">
            <div class="login-main-text">
                <h2>Application<br> Login Page</h2>
                <p>Login and explore us.</p>
            </div>
        </div>

        <div class="main">
            <div class="col-md-6 col-sm-12">
                <div class="login-form">
                    <form action="LoginServlet" method="POST">
                        <div class="form-group">
                            <label>User Name</label>
                            <input type="text" class="form-control" name="txtUsername" placeholder="User Name">
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password"  class="form-control" name="txtPassword"placeholder="Password">
                        </div>
                        <button type="submit" class="btn btn-black">Login</button>
                        <%
                            if (request.getAttribute("error") != null) {
                                String error = (String) request.getAttribute("error");
                        %>
                        <h6 style="color: red"><%=error%></h6>
                        <%
                            }
                        %>
                        </br></br> <h7>Don't have an account ? <a href="create.jsp" >Register now</a></h7>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
