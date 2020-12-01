<%@page import="hunglnv.object.TempaState"%>
<%@page import="com.sun.javafx.util.TempState"%>
<%@page import="hunglnv.object.Flower"%>
<%@page import="hunglnv.object.FlowerCategory"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
    <!-- Basic -->

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <!-- Mobile Metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Site Metas -->
        <title>Freshshop - Ecommerce Bootstrap 4 HTML Template</title>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Site Icons -->
        <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <!-- Site CSS -->
        <link rel="stylesheet" href="css/style.css">
        <!-- Responsive CSS -->
        <link rel="stylesheet" href="css/responsive.css">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="css/custom.css">

        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <script type="text/javascript">
            var count = 0;
            var xmlDOM = null;
            var xmlHttp;
            var cells = [];

            function GetXmlHttpObject() {
                var xmlHttp = null;
                try {
                    //Firefox, Opera, Safari
                    xmlHttp = new XMLHttpRequest();
                } catch (e) { //IE
                    try {
                        xmlHttp = new ActiveXObject("Msxml12.XMLHTTP");
                    } catch (e) {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                }
                return xmlHttp;
            }
            
            function traversalDOMTree(tableName) {
                var tableElem = document.getElementById(tableName);
                var i = 1;
                while (i < tableElem.rows.length) {
                    deleteRow(tableName, i);
                }
                count = 0;
                tmpTableName = tableName;
                update();
            }
            function deleteRow(tableId, rowNumber) {
                var tableElem = document.getElementById(tableId);
                if (rowNumber > 0 && rowNumber < tableElem.rows.length) {
                    tableElem.deleteRow(rowNumber);
                } else {
                    alert("Failed");
                }
            }
            function update() {
                xmlHttp = GetXmlHttpObject();
                if (xmlHttp == null) {
                    alert("Your browser does not support AJAX");
                    return;
                }
                var msg = document.getElementById('search');
                var season = document.getElementById('season');
                var state = document.getElementById('stateName');
                var matuDay = document.getElementById('matuDay');
                var url = "EvaluateStateServlet?txtSearch=" + msg.value + "&txtState=" + state.value + "&txtSeason=" + season.value + "&txtDay=" + matuDay.value; 
                xmlHttp.onreadystatechange = handleStateChange;
                xmlHttp.open("POST", url, true);
                xmlHttp.send(null);
            }
            function handleStateChange() {
                if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                    var tmp = xmlHttp.responseText;
                    var parser = new DOMParser();
                    xmlDOM = parser.parseFromString(tmp, "application/xml");
                    getAll(xmlDOM, tmpTableName);
                }
            }
            
            function getAll(node, tableName) {
                if (node == null) {
                    return;
                }
                if (node.tagName == "flowerID") {
                    count++;
                    cells[0] = count;
                    var flowerName = node.nextSibling;
                    cells[1] = flowerName.firstChild.nodeValue;
                    var imgLink = node.nextSibling.nextSibling;
                    var img = "<img class=\"flowerImage\" src=\"" + imgLink.firstChild.nodeValue + "\" \/>";
                    cells[2] = img;
                    var temparature = node.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling;
                    cells[3] = temparature.firstChild.nodeValue;
                    var maturity = node.nextSibling.nextSibling.nextSibling.nextSibling.nextSibling;
                    var germination = node.nextSibling.nextSibling.nextSibling.nextSibling;
                    var day = parseInt(maturity.firstChild.nodeValue) + parseInt(germination.firstChild.nodeValue);
                    cells[4] = day;
                    addRow(tableName, cells)
                }
                var childs = node.childNodes;
                for (var i = 0; i < childs.length; i++) {
                    getAll(childs[i], tableName);
                }
                
            }
            function addRow(tableID, cells) {
                var tableElem = document.getElementById(tableID);
                var newRow = tableElem.insertRow(tableElem.rows.length);
                var newCell;
                for (var i = 0; i < cells.length; i++) {
                    newCell = newRow.insertCell(newRow.cells.length);
                    newCell.innerHTML = cells[i];
                }
                return newRow;
            }

                        
            
            
        </script>
        <style>
            #getAllBtn{
                width: 150px;
                height: 50px;
                background-color: orange;
                border-color: yellow;
                margin-left: 650px;
            }
            .flowerImage{
                width: 200px;
                height: 200px;
            }
            #topNav {
                max-height:400px;
                overflow-x:hidden;
                overflow-y:auto;
            }
            .dropBox_zen select {
                background-color: #0563af;
                color: white;
                padding: 12px;
                width: 200px;
                border: none;
                font-size: 20px;
                box-shadow: 0 5px 25px rgba(0, 0, 0, 0.2);
                -webkit-appearance: button;
                appearance: button;
                outline: none;
            }

            .dropBox_zen::before {
                content: "\f13a";
                font-family: FontAwesome;
                position: absolute;
                top: 0;
                right: 0;
                width: 20%;
                height: 100%;
                text-align: center;
                font-size: 28px;
                line-height: 45px;
                color: rgba(255, 255, 255, 0.5);
                background-color: rgba(255, 255, 255, 0.1);
                pointer-events: none;
            }

            .dropBox_zen:hover::before {
                color: rgba(255, 255, 255, 0.6);
                background-color: rgba(255, 255, 255, 0.2);
            }

            .dropBox_zen select option {
                padding: 30px;

            }
            .input_zen {
                border-color: #0563af;
                width: 200px;
                height: 52px;
                margin-left: 10px;
            }
        </style>

    </head>

    <body>


        <!-- Start Main Top -->
        <header class="main-header">
            <!-- Start Navigation -->
            <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-default bootsnav">
                <div class="container">
                    <!-- Start Header Navigation -->
                    <div class="navbar-header">
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-menu" aria-controls="navbars-rs-food" aria-expanded="false" aria-label="Toggle navigation">
                            <i class="fa fa-bars"></i>
                        </button>
                        <a class="navbar-brand" href="index.html"><img src="" class="logo" alt=""></a>
                    </div>
                    <!-- End Header Navigation -->

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="navbar-menu">
                        <ul class="nav navbar-nav ml-auto" data-in="fadeInDown" data-out="fadeOutUp">
                            <li class="nav-item active"><a class="nav-link" href="ForwardHomeServlet">Seeds</a></li>
                            <li class="nav-item active"><a class="nav-link" href="ForwardStateServlet">States</a></li>
                        </ul>
                    </div>
                    <!-- /.navbar-collapse -->

                    <!-- Start Atribute Navigation -->
                    <div class="attr-nav">
                        <ul>
                           <li class="nav-item active"><a class="nav-link">Welcome, ${sessionScope.loginUsername}</a></li>
                            <li class="search"><a href="LogoutServlet">Log out</a></li>
                        </ul>
                    </div>
                    <!-- End Atribute Navigation -->
                </div>
             
            </nav>
            <!-- End Navigation -->
        </header>
        <!-- End Main Top -->

        <!-- Start Top Search -->
        <div class="top-search">
            <div class="container">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-search"></i></span>
                    <input type="text" class="form-control" placeholder="Search">
                    <span class="input-group-addon close-search"><i class="fa fa-times"></i></span>
                </div>
            </div>
        </div>
        <!-- End Top Search -->

        <!-- Start All Title Box -->
        <div class="all-title-box">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h2>States</h2>
                        <ul class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">Home</a></li>
                            <li class="breadcrumb-item active">States</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- End All Title Box -->

        <!-- Start Gallery  -->
        <form name="myForm" method="POST">
            <div class="products-box">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="title-all text-center">
                                <h1></h1>
                            </div>
                        </div>
                    </div>
                    <%List<TempaState> listState = (List<TempaState>) request.getAttribute("listState"); %>
                    <div class="row special-list">
                        <!--Type code here-->
                        <h1 style="margin-top: 5px;margin-right :10px;margin-left: 10px;"> State: </h1>
                        <div class="dropBox_zen">
                            <select id="stateName">
                                <%for (TempaState state : listState) {
                                %>
                                <option><%=state.getStateName()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <h1 style="margin-top: 5px;margin-right :10px;margin-left: 10px;"> Season: </h1>
                        <div class="dropBox_zen">
                            <select id="season">
                                <option>Spring</option>
                                <option>Summer</option>
                                <option>Fall</option>
                                <option>Winter</option>
                            </select>
                        </div>
                        <h1 style="margin-top: 5px;margin-right :10px;margin-left: 10px;"> Days: </h1>
                        <div class="dropBox_zen">
                            <select id="matuDay">
                                                                <option>300</option>
                                <option>250</option>
                                <option>200</option>
                                <option>150</option>
                                <option>100</option>
                                <option>50</option>
                            </select>
                        </div>
                        <h1 style="margin-top: 5px;margin-right :10px;margin-left: 10px;"> Search: </h1>
                        <input type="text" class="input_zen" id="search"/>
                    </div>

                </div>

            </div>
            <!-- End Gallery  -->
            <div>
                <div style="margin-bottom: 30px;">
                    <input type="button" value="GetAll" class="btn btn-info" onclick="traversalDOMTree('dataTable')" id="getAllBtn"/>
                </div>

                <table class="table" border="1" id="dataTable">
                    <thead id="headerTable" class="thead-dark">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Flower Name</th>
                            <th scope="col">Image</th>
                            <th scope="col">Temperature</th>
                            <th scope="col">Harvest days</th>
                        </tr>
                    </thead>
                </table>
            </div>
        </form>
        <!-- Start Instagram Feed  -->
        <div class="instagram-box">
            <div class="main-instagram owl-carousel owl-theme">
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-01.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-02.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-03.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-04.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-05.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-06.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-07.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-08.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-09.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
                <div class="item">
                    <div class="ins-inner-box">
                        <img src="images/instagram-img-05.jpg" alt="" />
                        <div class="hov-in">
                            <a href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Instagram Feed  -->


        <!-- Start Footer  -->
        <footer>
            <div class="footer-main">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-4 col-md-12 col-sm-12">
                            <div class="footer-top-box">
                                <h3>Business Time</h3>
                                <ul class="list-time">
                                    <li>Monday - Friday: 08.00am to 05.00pm</li> <li>Saturday: 10.00am to 08.00pm</li> <li>Sunday: <span>Closed</span></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-12 col-sm-12">
                            <div class="footer-top-box">
                                <h3>Newsletter</h3>
                                <form class="newsletter-box">
                                    <div class="form-group">
                                        <input class="" type="email" name="Email" placeholder="Email Address*" />
                                        <i class="fa fa-envelope"></i>
                                    </div>
                                    <button class="btn hvr-hover" type="submit">Submit</button>
                                </form>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-12 col-sm-12">
                            <div class="footer-top-box">
                                <h3>Social Media</h3>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                                <ul>
                                    <li><a href="#"><i class="fab fa-facebook" aria-hidden="true"></i></a></li>
                                    <li><a href="#"><i class="fab fa-twitter" aria-hidden="true"></i></a></li>
                                    <li><a href="#"><i class="fab fa-linkedin" aria-hidden="true"></i></a></li>
                                    <li><a href="#"><i class="fab fa-google-plus" aria-hidden="true"></i></a></li>
                                    <li><a href="#"><i class="fa fa-rss" aria-hidden="true"></i></a></li>
                                    <li><a href="#"><i class="fab fa-pinterest-p" aria-hidden="true"></i></a></li>
                                    <li><a href="#"><i class="fab fa-whatsapp" aria-hidden="true"></i></a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-lg-4 col-md-12 col-sm-12">
                            <div class="footer-widget">
                                <h4>About Freshshop</h4>
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p> 
                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p> 							
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-12 col-sm-12">
                            <div class="footer-link">
                                <h4>Information</h4>
                                <ul>
                                    <li><a href="#">About Us</a></li>
                                    <li><a href="#">Customer Service</a></li>
                                    <li><a href="#">Our Sitemap</a></li>
                                    <li><a href="#">Terms &amp; Conditions</a></li>
                                    <li><a href="#">Privacy Policy</a></li>
                                    <li><a href="#">Delivery Information</a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-12 col-sm-12">
                            <div class="footer-link-contact">
                                <h4>Contact Us</h4>
                                <ul>
                                    <li>
                                        <p><i class="fas fa-map-marker-alt"></i>Address: Michael I. Days 3756 <br>Preston Street Wichita,<br> KS 67213 </p>
                                    </li>
                                    <li>
                                        <p><i class="fas fa-phone-square"></i>Phone: <a href="tel:+1-888705770">+1-888 705 770</a></p>
                                    </li>
                                    <li>
                                        <p><i class="fas fa-envelope"></i>Email: <a href="mailto:contactinfo@gmail.com">contactinfo@gmail.com</a></p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
        <!-- End Footer  -->

        <!-- Start copyright  -->
        <div class="footer-copyright">
            <p class="footer-company">All Rights Reserved. &copy; 2018 <a href="#">ThewayShop</a> Design By :
                <a href="https://html.design/">html design</a></p>
        </div>
        <!-- End copyright  -->

        <a href="#" id="back-to-top" title="Back to top" style="display: none;">&uarr;</a>

        <!-- ALL JS FILES -->
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <!-- ALL PLUGINS -->
        <script src="js/jquery.superslides.min.js"></script>
        <script src="js/bootstrap-select.js"></script>
        <script src="js/inewsticker.js"></script>
        <script src="js/bootsnav.js."></script>
        <script src="js/images-loded.min.js"></script>
        <script src="js/isotope.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/baguetteBox.min.js"></script>
        <script src="js/form-validator.min.js"></script>
        <script src="js/contact-form-script.js"></script>
        <script src="js/custom.js"></script>
    </body>

</html>