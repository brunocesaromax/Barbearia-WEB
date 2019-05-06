<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gerenciador de Universidade</title>
    <meta http-equiv= "Content-Type" content= "text/html; charset=UTF-8" >
    <link rel="stylesheet" href="style/bootstrap.min.css">
    <script src="script/bootstrap.min.js"></script>
</head>
<body class="text-center">
<form action="logarOuCadastrar" class="form-control" id="formLoginCadastro" method="post" acceptcharset="ISO-8859-1">
    <h1 class="h3 mb-3 font-weight-normal">Login</h1>

    <label for="email" class="sr-only">Email address</label>
    <input type="text" id="email" name="email" class="form-control" placeholder="Email address" required autofocus>

    <label for="senha" class="sr-only">Password</label>
    <input type="password" id="senha" name="senha" class="form-control" placeholder="Password" required>

    <%--<div class="checkbox mb-3">
        <label>
            <input type="checkbox" value="remember-me"> Remember me
        </label>
    </div>
--%>
    <button class="btn btn-lg btn-primary btn-block" type="submit"
            onclick="return document.getElementById('formLoginCadastro').action = 'logarOuCadastrar?acao=logar'">Sign in
    </button>

    <br/>
    Não possui conta? Registre-se agora.
    <button class="btn btn-lg btn-primary btn-block" type="submit"
            onclick="return document.getElementById('formLoginCadastro').action = 'logarOuCadastrar?acao=cadastrar'">
        Sign up
    </button>

    <br/>

    <div id="divResultado">
    </div>
</form>


<script type="application/javascript">

    function erroLogin() {

        var texto = '${msgLogin}'

        if (texto != 'Email ou senha inválidos! Tente novamente.'){
            return
        }else {
            var divDangerLogin = document.getElementById('divResultado')
            divDangerLogin.setAttribute('class', "alert alert-danger")
            divDangerLogin.setAttribute('role', "alert")
            divDangerLogin.innerText = texto

            return
        }
    }

    erroLogin()

</script>

</body>
</html>
