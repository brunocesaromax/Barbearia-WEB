<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Edição de Usuário</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../style/bootstrap.min.css">
    <script src="../script/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>


</head>
<body class="bg-light">

<form action="/pages/editarUsuario" id="formEdicaoUsuario" onsubmit=" return validaCampos()" method="post"
      accept-charset="ISO-8859-1">
    <h1 class="h3 mb-3 font-weight-normal" style="text-align: center">Edição de usuário</h1>
    <br/>
    <br/>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="nome">Nome</label>
            <input type="text" class="form-control" id="nome" name="nome" value="${usuarioSessao.nome}" placeholder="Nome">
            <div id="erro-nome"></div>
        </div>
        <div class="form-group col-md-1">
            <label for="idade">Idade</label>
            <select id="idade" name="idade" value="${usuarioSessao.idade}" class="form-control" onclick="carregarIdades()">
                <option selected="${usuarioSessao.idade}">${usuarioSessao.idade}</option>
            </select>
        </div>

    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="email">Email</label>
            <input type="text" class="form-control" id="email" name="email" value="${usuarioSessao.email}" placeholder="Email">
        </div>
        <div class="form-group col-md-6">
            <label for="senha">Senha</label>
            <input type="password" class="form-control" id="senha" name="senha" value="${usuarioSessao.senha}" placeholder="Senha">
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="rua">Rua</label>
            <input type="text" class="form-control" id="rua" name="rua" value="${usuarioSessao.rua}" placeholder="Rua X">
        </div>
        <div class="form-group col-md-6">
            <label for="bairro">Bairro</label>
            <input type="text" class="form-control" id="bairro" name="bairro" value="${usuarioSessao.bairro}" placeholder="Setor XYZ">
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="cidade">Cidade</label>
            <input type="text" class="form-control" id="cidade" name="cidade" value="${usuarioSessao.cidade}">
        </div>
        <div class="form-group col-md-4">
            <label for="estado">Estado</label>
            <input type="text" class="form-control" id="estado" name="estado" value="${usuarioSessao.estado}">

        </div>
        <div class="form-group col-md-2">
            <label for="cep">CEP</label>
            <input type="text" class="form-control" id="cep" name="cep" value="${usuarioSessao.cep}" onblur="consultaCep()">
        </div>
    </div>

    <div id="divResultado">
    </div>

    <button type="submit" class="btn btn-primary"
            onclick="return document.getElementById('formEdicaoUsuario').action = 'editarUsuario?acao=editar'">
        Editar
    </button>
    <button id="botaoVoltar" type="button" class="btn btn-secondary"
            onclick="redirecionarPaginaInicial();">
        Voltar
    </button>
</form>


<script type="application/javascript">

    /*Flag para reconhecer que o botão clicado foi o botão cancelar*/
    var flagBotaoCancelar = false

    limparCampos()
    mensagemEmailExistente()
    mensagemSucessoEdicao()

    /*Limpar campos ao abrir a tela de cadastro*/
    function limparCampos() {
        document.getElementById('formEdicaoUsuario').reset();
        return false;
    }

    function mensagemEmailExistente() {

        var texto = ''
        texto+='${msgCadastro}'

        if (texto != '') {
            var divDangerLogin = document.getElementById('divResultado')
            divDangerLogin.setAttribute('class', "alert alert-danger")
            divDangerLogin.setAttribute('role', "alert")
            divDangerLogin.innerText =texto
        }
    }

    function mensagemSucessoEdicao() {

        var texto = ''
        texto+='${msgSucessoEdicao}'

        if (texto != '') {
            var divDangerLogin = document.getElementById('divResultado')
            divDangerLogin.setAttribute('class', "alert alert-success")
            divDangerLogin.setAttribute('role', "alert")
            divDangerLogin.innerText =texto
        }
    }

    function carregarIdades() {

        var element = document.getElementById('idade')

        for (i = 16; i <= 99; i++) {
            var opcao = document.createElement('option')
            opcao.value = i
            opcao.innerHTML = i
            element.appendChild(opcao)
        }

    }

    function validaCampos() {

        if (flagBotaoCancelar) {
            return true
        }

        var erro = ''

        if (document.getElementById("nome").value === '') {
            erro += 'Informe o nome.\n'
        }
        if (document.getElementById("idade").value === '') {
            erro += 'Informe a idade.\n'
        }
        if (document.getElementById("email").value === '') {
            erro += 'Informe o email.\n'
        }
        if (document.getElementById("senha").value === '') {
            erro += 'Informe a senha.\n'
        }
        if (document.getElementById("rua").value === '') {
            erro += 'Informe a rua.\n'
        }
        if (document.getElementById("bairro").value === '') {
            erro += 'Informe o bairro.\n'
        }
        if (document.getElementById("cidade").value === '') {
            erro += 'Informe a cidade.\n'
        }
        if (document.getElementById("estado").value === '') {
            erro += 'Informe o estado.\n'
        }
        if (document.getElementById("cep").value === '') {
            erro += 'Informe o cep.\n'
        }

        if (erro == '') {
            return true
        } else {
            var divDangerLogin = document.getElementById('divResultado')
            divDangerLogin.setAttribute('class', "alert alert-danger")
            divDangerLogin.setAttribute('role', "alert")
            divDangerLogin.innerText = erro

            return false
        }

    }

    function consultaCep() {

        var cep = $("#cep").val();

        //Consulta o webservice viacep.com.br/
        $.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function (dados) {


            if (!("erro" in dados)) {

                $("#rua").val(dados.logradouro);
                $("#bairro").val(dados.bairro);
                $("#cidade").val(dados.localidade);
                $("#estado").val(dados.uf);

            } else {

                $("#rua").val('');
                $("#bairro").val('');
                $("#cidade").val('');
                $("#estado").val('');

                //CEP pesquisado não encontrado
                alert("CEP não encontrado.")
            }

        });
    }

    function redirecionarPaginaInicial() {
        // Faz um redirecionamento sem adicionar a página original ao histórico de navegação do browser.
        window.location.replace("/gerenciador_barbearia/pages/paginaInicialUsuario.jsp");
    }

</script>
</body>
</html>
