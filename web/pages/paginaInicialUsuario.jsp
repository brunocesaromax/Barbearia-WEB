<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Accordion Menu</title>


    <link rel='stylesheet prefetch'
          href='http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/themes/smoothness/jquery-ui.css'>

    <style>
        /* NOTE: The styles were added inline because Prefixfree needs access to your styles and they must be inlined if they are on local disk! */
        @import url('http://fonts.googleapis.com/css?family=Open+Sans:300,400,700');
        @import url('//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css');

        body {
            color: #5D5F63;
            background: #293949;
            font-family: 'Open Sans', sans-serif;
            padding: 0;
            margin: 0;
            text-rendering: optimizeLegibility;
            -webkit-font-smoothing: antialiased;
        }

        .sidebar-toggle {
            margin-left: -240px;
        }

        .sidebar {
            width: 240px;
            height: 100%;
            background: #293949;
            position: absolute;
            -webkit-transition: all 0.3s ease-in-out;
            -moz-transition: all 0.3s ease-in-out;
            -o-transition: all 0.3s ease-in-out;
            -ms-transition: all 0.3s ease-in-out;
            transition: all 0.3s ease-in-out;
            z-index: 100;
        }

        .sidebar #leftside-navigation ul,
        .sidebar #leftside-navigation ul ul {
            margin: -2px 0 0;
            padding: 0;
        }

        .sidebar #leftside-navigation ul li {
            list-style-type: none;
            border-bottom: 1px solid rgba(255, 255, 255, 0.05);
        }

        .sidebar #leftside-navigation ul li.active > a {
            color: #1abc9c;
        }

        .sidebar #leftside-navigation ul li.active ul {
            display: block;
        }

        .sidebar #leftside-navigation ul li a {
            color: #aeb2b7;
            text-decoration: none;
            display: block;
            padding: 18px 0 18px 25px;
            font-size: 12px;
            outline: 0;
            -webkit-transition: all 200ms ease-in;
            -moz-transition: all 200ms ease-in;
            -o-transition: all 200ms ease-in;
            -ms-transition: all 200ms ease-in;
            transition: all 200ms ease-in;
        }

        .sidebar #leftside-navigation ul li a:hover {
            color: #1abc9c;
        }

        .sidebar #leftside-navigation ul li a span {
            display: inline-block;
        }

        .sidebar #leftside-navigation ul li a i {
            width: 20px;
        }

        .sidebar #leftside-navigation ul li a i .fa-angle-left,
        .sidebar #leftside-navigation ul li a i .fa-angle-right {
            padding-top: 3px;
        }

        .sidebar #leftside-navigation ul ul {
            display: none;
        }

        .sidebar #leftside-navigation ul ul li {
            background: #23313f;
            margin-bottom: 0;
            margin-left: 0;
            margin-right: 0;
            border-bottom: none;
        }

        .sidebar #leftside-navigation ul ul li a {
            font-size: 12px;
            padding-top: 13px;
            padding-bottom: 13px;
            color: #aeb2b7;
        }

        h1 {
            color: white;
        }

    </style>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>

</head>

<body>
<h1 style="">Bem vindo: ${usuarioSessao.nome}</h1>

<aside class="sidebar">

    <div id="leftside-navigation" class="nano">
        <ul class="nano-content">
            <li>
                <a href="/gerenciador_barbearia/pages/paginaInicialUsuario.jsp"><i class="fa fa-dashboard"></i><span>Dashboard</span></a>
            </li>
            <li class="sub-menu">
                <a href="/gerenciador_barbearia/pages/editarUsuario.jsp"><i
                        class="fa fa-cogs"></i><span>Editar Perfil</span><i></i></a>

            </li>
            <li class="sub-menu">
                <a href="/gerenciador_barbearia/pages/agendamento.jsp"><i
                        class="fa fa-table"></i><span>Agendamentos</span><i
                ></i></a>
            </li>
            <li class="sub-menu">
                <a href="javascript:void(0);"><i class="fa fa fa-tasks"></i><span>Relatórios</span><i
                        class="arrow fa fa-angle-right pull-right"></i></a>
                <ul>
                    <li><a href="forms-components.html">Últimos 7 dias</a>
                    </li>
                    <li><a href="forms-validation.html">Últimos 15 dias</a>
                    </li>
                    <li><a href="forms-mask.html">Último mês</a>
                    </li>
                    <li><a href="forms-wizard.html">Último ano</a>
                    </li>
                </ul>
            </li>
            <li class="sub-menu active">
                <a href="javascript:void(0);"><i class="fa fa-envelope"></i><span>Mail</span><i
                        class="arrow fa fa-angle-right pull-right"></i></a>
                <ul>
                    <li class="active"><a href="mail-inbox.html">Inbox</a>
                    </li>
                    <li><a href="mail-compose.html">Compose Mail</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</aside>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js'></script>

<script src="script/paginaInicial.js"></script>

</body>
</html>