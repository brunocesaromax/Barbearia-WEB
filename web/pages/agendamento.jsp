<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Agendamentos</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="../style/bootstrap.min.css">

    <script src="../script/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrit,y="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
    <script src="https://cdn.rawgit.com/plentz/jquery-maskmoney/master/dist/jquery.maskMoney.min.js"></script>

</head>
<body class="bg-light align-content-center">

<form action="AgendamentoServlet" id="formAgendamento" onsubmit=" return validaCampos()" method="post"
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
        <div class="form-group col-md-3">
            <label for="servico">Serviço</label>
            <select id="servico" name="servico" class="form-control" onfocus="carregarServicos()">
                <option selected="Corte">Corte</option>
            </select>
        </div>

    </div>
    <div class="form-row">
        <div class="form-group col-sm-2">
            <label for="data">Data</label>
            <input type="text" class="form-control" id="data" name="data" placeholder="03/05/2019">
        </div>

        <div class="form-group col-md-2">
            <label for="valor">Valor R$</label>
            <input type="text" id="valor" name="valor" class="form-control" placeholder="R$00,00">
        </div>

        <div class="form-group col-md-2">
            <label for="horario">Horário</label>
            <input type="text" id="horario" name="horario" class="form-control" placeholder="00:00">
        </div>

    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="observacao">Observação</label>
            <textarea class="form-control" id="observacao" name="observacao" rows="4"
                      placeholder="Observações importantes"></textarea>
        </div>
    </div>

    <div id="divResultado">
    </div>

    <button type="submit" class="btn btn-primary"
            onclick="return document.getElementById('formAgendamento').action = 'agendamento?acao=cadastrar'">
        Cadastrar
    </button>
    <button type="submit" class="btn btn-secondary"
            onclick="return document.getElementById('formAgendamento').action = 'agendamento?acao=editar'">
        Editar
    </button>
    <button type="submit" class="btn btn-danger"
            onclick="return document.getElementById('formAgendamento').action = 'agendamento?acao=excluir'">
        Excluir
    </button>
    <button id="botaoCancelar" type="submit" class="btn btn-warning"
            onclick="return document.getElementById('formAgendamento').action = 'agendamento?acao=cancelar'">
        Cancelar
    </button>
</form>


<script type="application/javascript">

    var botao = document.getElementById('botaoCancelar')
    botao.addEventListener('click', statusBotao)

    /*Flag para reconhecer que o botão clicado foi o botão cancelar*/
    var flagBotaoCancelar = false

    /*Flag para reconhecer que o select de serviços já foi clicado*/
    var flagServico = false

    limparCampos()

    /*Limpar campos ao abrir a tela de cadastro*/
    function limparCampos() {
        document.getElementById('formAgendamento').reset();
        return false;
    }

    function carregarServicos() {

        if (!flagServico) {

            var element = document.getElementById('servico')
            var servicos = ['Barba', 'Química', 'Pigmentação', 'Barba + Corte',
                'Sobrancelha', 'Descoloração', 'Depilação', 'Unha', 'Outros']

            for (i = 0; i < servicos.length; i++) {

                var opcao = document.createElement('option')
                opcao.value = servicos[i]
                opcao.innerHTML = servicos[i]
                element.appendChild(opcao)
            }

            flagServico = true
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

    $(document).ready(function () {
        $('#data').mask('99/99/9999');
        return false;
    });

    $(document).ready(function () {
        $("#valor").maskMoney({
            prefix: "R$",
            decimal: ",",
            thousands: "."
        });
    });

    //todo: revisar a máscara de horario
    /*$(document).ready(function(){
        $("#horario").inputmask("h:s",{ "placeholder": "hh/mm" });
    });*/


</script>
</body>
</html>
