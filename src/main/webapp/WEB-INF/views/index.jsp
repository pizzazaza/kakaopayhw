<%--
  Created by IntelliJ IDEA.
  User: sejun
  Date: 2017. 9. 16.
  Time: 오후 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>KakaoPay HW</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/latest/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        * { font-family: NanumGothic, 'Malgun Gothic'; }
        /*
         * 스타일 변경
         * --------------------------------------------------
         */
        html, body { overflow-x: hidden; /* 좁은 화면에서 스크롤 방지 */ }
        body { padding-top: 70px; }
        footer { padding: 30px 0; }

        /*
         * 오프캔버스
         * --------------------------------------------------
         */
        @media screen and (max-width: 767px) {
            .row-offcanvas {
                position: relative;
                -webkit-transition: all .25s ease-out;
                -o-transition: all .25s ease-out;
                transition: all .25s ease-out;
            }
            .row-offcanvas-right { right: 0; }
            .row-offcanvas-left { left: 0; }
            .row-offcanvas-right
            .sidebar-offcanvas { right: -50%; /* 컬럼 6개 */ }
            .row-offcanvas-left
            .sidebar-offcanvas { left: -50%; /* 컬럼 6개 */ }
            .row-offcanvas-right.active { right: 50%; /* 컬럼 6개 */ }
            .row-offcanvas-left.active { left: 50%; /* 컬럼 6개 */ }
            .sidebar-offcanvas {
                position: absolute;
                top: 0;
                width: 50%; /* 컬럼 6개 */
            }
        }
    </style>
    <!--
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/latest/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function () {
            $('[data-toggle="offcanvas"]').click(function () {
                $('.row-offcanvas').toggleClass('active')
            });
        });
    </script>
    -->
</head>
<body>


<div class="container">
    <div class="row row-offcanvas row-offcanvas-right">
        <div class="col-xs-12 col-sm-9">
            <p class="pull-right visible-xs">
                <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">네비게이션 토글 버튼</button>
            </p>
            <div class="jumbotron">
                <h2>파일 서비스를 제공하는<br>
                    REST Api 구현하기</h2>
                <p>파일 조회/추가/수정/삭제 기능</p>
                <form class="upload-file" method="post" action="/files" enctype="multipart/form-data" accept-charset="UTF-8">

                    Comment   : <input id="comment" type="text" name="comment"/><br>
                    <input id="post_file" type="file" name="file"/><br>
                    <input id="submit_but" type="submit" value="등록"/>
                </form>

            </div>
            <div class="row">
                <c:forEach var="file" items="${fileList}">
                    <div class="col-6 col-sm-6 col-lg-4" data-file-info=${file.fileId}>
                        <h3>${file.fileName}</h3>
                        <p>${file.comment}</p>
                        <p><a class="btn btn-default btn-download" href="#" role="button">다운로드</a></p>
                        <p><a class="btn btn-default btn-modify" href="#" role="button">삭제</a></p>

                        <form class="modify-file" method="post" action="/files/${file.fileId}" enctype="multipart/form-data" accept-charset="UTF-8">
                            <input type="hidden" name="_method" value="PUT"/>
                            Comment   : <input id="comment-m" type="text" name="comment"/><br>
                            <input id="put-file-m" type="file" name="file"/><br>
                            <input id="submit_but-m" type="submit" value="수정"/>
                        </form>
                    </div>
                </c:forEach>
            </div><!--/row-->
        </div>

    </div><!--/row-->

    <hr>
    <footer>
        <p>Kakao Pay 면접과제 2017</p>
    </footer>
</div><!--/.container-->

</body>
<script src="/resources/js/node_modules/jquery/dist/jquery.min.js"></script>
<script src="/resources/js/node_modules/handlebars/dist/handlebars.min.js"></script>
<script src="/resources/js/node_modules/egjs/dist//pkgd/eg.pkgd.min.js"></script>
<script src="/resources/js/app.js"></script>

</html>