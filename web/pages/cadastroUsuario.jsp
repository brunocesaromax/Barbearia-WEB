<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cadastro de Usuário</title>
    <link rel="stylesheet" href="style/bootstrap.min.css">
    <script src="script/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>

</head>
<body class="bg-light">

<form action="cadastrarUsuario" id="formCadastroUsuario" method="post">
    <h1 class="h3 mb-3 font-weight-normal" style="text-align: center">Cadastro de usuário</h1>
    <br/>
    <br/>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="nome">Nome</label>
            <input type="text" class="form-control" id="nome" placeholder="Nome">
        </div>
        <div class="form-group col-md-1">
            <label for="idade">Idade</label>
            <select id="idade" class="form-control" onclick="carregarIdades()">
                <option></option>
            </select>
        </div>

    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="email">Email</label>
            <input type="text" class="form-control" id="email" placeholder="Email">
        </div>
        <div class="form-group col-md-6">
            <label for="senha">Password</label>
            <input type="password" class="form-control" id="senha" placeholder="Senha">
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="rua">Rua</label>
            <input type="text" class="form-control" id="rua" placeholder="Rua X">
        </div>
        <div class="form-group col-md-6">
            <label for="bairro">Bairro</label>
            <input type="text" class="form-control" id="bairro" placeholder="Setor XYZ">
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="cidade">Cidade</label>
            <input type="text" class="form-control" id="cidade">
        </div>
        <div class="form-group col-md-4">
            <label for="estado">Estado</label>
            <input type="text" class="form-control" id="estado">

        </div>
        <div class="form-group col-md-2">
            <label for="cep">CEP</label>
            <input type="text" class="form-control" id="cep" onblur="consultaCep()">
        </div>
    </div>

    <button type="submit" class="btn btn-primary"
            onclick="return document.getElementById('formCadastroUsuario').action = 'cadastrarUsuario?acao=cadastrar'">
        Cadastrar
    </button>
    <button type="submit" class="btn btn-danger"
            onclick="return document.getElementById('formCadastroUsuario').action = 'cadastrarUsuario?acao=cancelar'">
        Cancelar
    </button>
</form>


<script type="application/javascript">

    function carregarIdades() {

        var element = document.getElementById('idade')

        for (i = 16; i <= 99; i++) {
            var opcao = document.createElement('option')
            opcao.value = i
            opcao.innerHTML = i
            element.appendChild(opcao)
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


</script>
</body>
</html>
