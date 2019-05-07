<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Agendamentos</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="../style/bootstrap.min.css">
    <script src="../script/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>

</head>
<body class="bg-light align-content-center" >

<form action="agendamento" id="formAgendamento" onsubmit=" return validaCampos()" method="post"
      accept-charset="ISO-8859-1" class="align-content-center">
    <h1 class="h3 mb-3 font-weight-normal" style="text-align: center">Agendamento</h1>
    <br/>
    <br/>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="nomeCliente">Nome do Cliente</label>
            <input type="text" class="form-control" id="nomeCliente" name="nomeCliente" placeholder="Nome Cliente">
            <div id="erro-nome"></div>
        </div>
        <div class="form-group col-md-1">
            <label for="valor">Valor</label>
            <select id="valor" name="valor" class="form-control" onclick="carregarIdades()">
                <option selected="16">16</option>
            </select>
        </div>

    </div>
    <div class="form-row">
        <div class="form-group col-sm-2">
            <label for="data">Data</label>
            <input type="text" class="form-control" id="data" name="data" placeholder="03/05/2019">
        </div>
        <div class="form-group col-md-3">
            <label for="servico">Serviço</label>
            <select id="servico" name="servico" class="form-control" onclick="carregarIdades()">
                <option selected="16">16</option>
            </select>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="observacao">Observação</label>
            <textarea class="form-control" id="observacao" name="observacao" rows="4" placeholder="Observações importantes"></textarea>
        </div>
    </div>

    <div id="divResultado">
    </div>

    <button type="submit" class="btn btn-primary"
            onclick="return document.getElementById('formAgendamento').action = 'agendamento?acao=cadastrar'">
        Cadastrar
    </button>
    <button type="submit" class="btn btn-primary"
            onclick="return document.getElementById('formAgendamento').action = 'agendamento?acao=editar'">
        Editar
    </button>
    <button type="submit" class="btn btn-primary"
            onclick="return document.getElementById('formAgendamento').action = 'agendamento?acao=excluir'">
        Excluir
    </button>    <button id="botaoCancelar" type="submit" class="btn btn-danger"
            onclick="return document.getElementById('formAgendamento').action = 'agendamento?acao=cancelar'">
        Cancelar
    </button>
</form>


<script type="application/javascript">

    var botao = document.getElementById('botaoCancelar')
    botao.addEventListener('click', statusBotao)

    /*Flag para reconhecer que o botão clicado foi o botão cancelar*/
    var flagBotaoCancelar = false

    limparCampos()
    mensagemEmailExistente()

    /*Limpar campos ao abrir a tela de cadastro*/
    function limparCampos() {
        document.getElementById('formAgendamento').reset();
        return false;
    }

    function carregarIdades() {

        var element = document.getElementById('idade')

        for (i = 17; i <= 99; i++) {
            var opcao = document.createElement('option')
            opcao.value = i
            opcao.innerHTML = i
            element.appendChild(opcao)
        }

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

    function statusBotao() {
        flagBotaoCancelar = true
    }

    function validaCampos() {

        if (flagBotaoCancelar) {
            return true
        }

        var erro = ''

        if (document.getElementById("nomeCliente").value === '') {
            erro += 'Informe o nome do cliente.\n'
        }
        if (document.getElementById("valor").value === '') {
            erro += 'Informe a valor do serviço.\n'
        }
        if (document.getElementById("data").value === '') {
            erro += 'Informe a data do serviço.\n'
        }
        if (document.getElementById("servico").value === '') {
            erro += 'Informe o serviço.\n'
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

</script>
</body>
</html>
