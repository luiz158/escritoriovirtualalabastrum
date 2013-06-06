<%@ include file="/base.jsp" %> 

<style>
    body {
      -webkit-font-smoothing: antialiased;
      font: normal 15px/1.5 "Helvetica Neue", Helvetica, Arial, sans-serif;
      color: #232525;
    }

    #slides {
      display: none
    }

    #slides .slidesjs-navigation {
      margin-top:5px;
    }

    a.slidesjs-next,
    a.slidesjs-previous,
    a.slidesjs-play,
    a.slidesjs-stop {
      background-image: url(../css/img/btns-next-prev.png);
      background-repeat: no-repeat;
      display:block;
      width:12px;
      height:18px;
      overflow: hidden;
      text-indent: -9999px;
      float: left;
      margin-right:5px;
    }

    a.slidesjs-next {
      margin-right:10px;
      background-position: -12px 0;
    }

    a:hover.slidesjs-next {
      background-position: -12px -18px;
    }

    a.slidesjs-previous {
      background-position: 0 0;
    }

    a:hover.slidesjs-previous {
      background-position: 0 -18px;
    }

    a.slidesjs-play {
      width:15px;
      background-position: -25px 0;
    }

    a:hover.slidesjs-play {
      background-position: -25px -18px;
    }

    a.slidesjs-stop {
      width:18px;
      background-position: -41px 0;
    }

    a:hover.slidesjs-stop {
      background-position: -41px -18px;
    }

    .slidesjs-pagination {
      margin: 7px 0 0;
      float: right;
      list-style: none;
    }

    .slidesjs-pagination li {
      float: left;
      margin: 0 1px;
    }

    .slidesjs-pagination li a {
      display: block;
      width: 13px;
      height: 0;
      padding-top: 13px;
      background-image: url(../css/img/pagination.png);
      background-position: 0 0;
      float: left;
      overflow: hidden;
    }

    .slidesjs-pagination li a.active,
    .slidesjs-pagination li a:hover.active {
      background-position: 0 -13px
    }

    .slidesjs-pagination li a:hover {
      background-position: 0 -26px
    }

    #slides a:link,
    #slides a:visited {
      color: #333
    }

    #slides a:hover,
    #slides a:active {
      color: #9e2020
    }

    .navbar {
      overflow: hidden
    }

    #slides {
      display: none
    }

    .container {
      margin: 0 auto
    }

    @media (max-width: 767px) {
      body {
        padding-left: 20px;
        padding-right: 20px;
      }
      .container {
        width: auto
      }
    }

    .container {
       width: 800px;
       height: 300px;
     }

</style>

  <div class="container">
    <div id="slides">
      <img src="http://www.alabastrum.com.br/escritoriovirtual/imagens/slide1.jpg" >
      <img src="http://www.alabastrum.com.br/escritoriovirtual/imagens/slide2.jpg" >
      <img src="http://www.alabastrum.com.br/escritoriovirtual/imagens/slide3.jpg" >
      <img src="http://www.alabastrum.com.br/escritoriovirtual/imagens/slide4.jpg" >
      <img src="http://www.alabastrum.com.br/escritoriovirtual/imagens/slide5.jpg" >
    </div>
  </div>
  
  <div class="imagensLink" style="margin-top: 300px;" >
	<a href="<c:url value="/atualizacaoDados/acessarTelaAtualizacaoDados"/>"> <img class="imagemLink" src="../css/images/imagem-link-atualize-seus-dados.jpg"  name="" /> </a>
	<a href="<c:url value="/home/emails"/>"> <img class="imagemLink" src="../css/images/imagem-link-emails.jpg"  name="" /> </a>
	<a href="<c:url value="/malaDireta/acessarTelaMalaDireta"/>"> <img class="imagemLink" src="../css/images/imagem-link-mala-direta.jpg"  name="" /> </a>
  </div>

  <script type="text/javascript" src="<c:url value="/js/jquery.slides.js"/>"></script>

  <script>
    $(function() {
      $('#slides').slidesjs({
        width: 940,
        height: 528,
        play: {
          active: true,
          auto: true,
          interval: 4000,
          swap: true
        }
      });
    });
  </script>

