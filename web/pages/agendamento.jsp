<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Agendamentos</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="../style/bootstrap.min.css">
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css"/>

    <script src="../script/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrit,y="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
    <script src="https://cdn.rawgit.com/plentz/jquery-maskmoney/master/dist/jquery.maskMoney.min.js"></script>
    <script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
    <script src='../script/jquery.mask.min.js'></script>

    <style>
        #div-imagem {
            top: 32px;
            right: 56px;
        }

    </style>
</head>
<body class="bg-light align-content-center">

<form action="AgendamentoServlet" id="formAgendamento" onsubmit=" return validaCampos()" method="post"
      enctype="multipart/form-data" accept-charset="ISO-8859-1" class="align-content-center">
    <h1 class="h3 mb-3 font-weight-normal" style="text-align: center">Agendamento</h1>
    <br/>
    <br/>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="nomeCliente">Nome do Cliente</label>
            <input type="text" class="form-control" id="nomeCliente" name="nomeCliente" value="${agendamento.nomeCliente}" placeholder="Nome Cliente">
            <div id="erro-nome"></div>
        </div>
        <div class="form-group col-md-3">
            <label for="servico">Serviço</label>
            <select id="servico" name="servico" class="form-control" onfocus="carregarServicos()">
                <option selected="Corte">${agendamento.servico.descricao}</option>
            </select>
        </div>

    </div>
    <div class="form-row">
        <div class="form-group col-sm-2">
            <label for="dataServico">Data</label>
            <input type="text" class="form-control" id="dataServico" name="dataServico" value="<fmt:formatDate value="${agendamento.dataServico}" type="date" pattern="dd/MM/yyyy"/>" placeholder="03/05/2019">
        </div>

        <div class="form-group col-md-2">
            <label for="valor">Valor R$</label>
            <input type="text" id="valor" name="valor" class="form-control" value="R$${agendamento.valor}" placeholder="R$00,00">
        </div>

        <div class="form-group col-md-2">
            <label for="horario">Horário</label>
            <input type="text" id="horario" name="horario" class="form-control" value="${agendamento.horario}" placeholder="00:00">
        </div>

        <label>Imagem</label>
        <div class="form-group col-md-2" id="div-imagem">
            <img src="<c:out value="${agendamento.imagemTemporaria}"></c:out>" width="150px" height="150px">
            <input type="file" id="imagem" name="imagem"/>
        </div>

    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="observacao">Observação</label>
            <textarea class="form-control" id="observacao" name="observacao" rows="4"
                      placeholder="Observações importantes">${agendamento.observacao}</textarea>
        </div>
    </div>

    <div id="divResultado">
    </div>

    <button type="submit" class="btn btn-primary"
            onclick="return document.getElementById('formAgendamento').action = 'agendamento?acao=cadastrar&agendamento=${agendamento.id}'">
        Salvar
    </button>
    <button id="botaoVoltar" type="button" class="btn btn-secondary"
            onclick="redirecionarPaginaInicialUsuario();">
        Voltar
    </button>
</form>

<br/>

<h2 align="center">Tabela de agendamentos</h2>

<!-- Apresentando a tabela através do jstl-->
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Nome do Cliente</th>
        <th scope="col">Serviço</th>
        <th scope="col">Data</th>
        <th scope="col">Valor R$</th>
        <th scope="col">Horário</th>
        <th scope="col">Observação</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${agendamentos}" var="agendamento">
        <tr>
            <td><c:out value="${agendamento.nomeCliente}"></c:out></td>
            <td><c:out value="${agendamento.servico.descricao}"></c:out></td>
            <td><fmt:formatDate value="${agendamento.dataServico}" type="date" pattern="dd/MM/yyyy"/></td>
            <td>R$ <c:out value="${agendamento.valor}"></c:out></
            </td>
            <td><c:out value="${agendamento.horario}"></c:out></td>
            <td><c:out value="${agendamento.observacao}"></c:out></td>
            <td><a href="/gerenciador_barbearia/pages/agendamento?acao=deletar&agendamento=${agendamento.id}"
                   onclick="return confirm('Confirmar exclusão de agendamento?')"><img
                    src="../resources/img/excluir.png"
                    alt="Excluir" title="Excluir"
                    width="20px" height="20px"></a></td>
            <td><a href="/gerenciador_barbearia/pages/agendamento?acao=editar&agendamento=${agendamento.id}"><img
                    src="../resources/img/editar.png"
                    alt="Editar" title="Editar" width="20px"
                    height="20px"></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script type="application/javascript">

    /*Flag para reconhecer que o botão clicado foi o botão cancelar*/
    var flagBotaoCancelar = false;

    /*Flag para reconhecer que o select de serviços já foi clicado*/
    var flagServico = false;

    limparCampos();
    msgExclusao();

    /*Limpar campos ao abrir a tela de cadastro*/
    function limparCampos() {
        document.getElementById('formAgendamento').reset();
        return false;
    }

    function carregarServicos() {

        if (!flagServico) {

            var element = document.getElementById('servico');
            var servicos = ['Barba', 'Química', 'Pigmentação', 'Barba + Corte',
                'Sobrancelha', 'Descoloração', 'Depilação', 'Unha', 'Outros'];

            for (i = 0; i < servicos.length; i++) {

                var opcao = document.createElement('option');
                opcao.value = servicos[i];
                opcao.innerHTML = servicos[i];
                element.appendChild(opcao)
            }

            flagServico = true
        }

    }

    function msgExclusao() {

        var texto = '${msgAgendamento}';

        if (texto === 'Agendamento excluído com sucesso.'){
            var divDangerLogin = document.getElementById('divResultado');
            divDangerLogin.setAttribute('class', "alert alert-success");
            divDangerLogin.setAttribute('role', "alert");
            divDangerLogin.innerText = texto
        }
    }

    function validaCampos() {

        if (flagBotaoCancelar) {
            return true
        }

        var erro = '';

        if (document.getElementById("nomeCliente").value === '') {
            erro += 'Informe o nome do cliente.\n'
        }
        if (document.getElementById("valor").value === '') {
            erro += 'Informe a valor do serviço.\n'
        }
        if (document.getElementById("dataServico").value === '' || !ValidaData()) {
            erro += 'Informe uma dataServico correta para o serviço.\n'
        }
        if (document.getElementById("horario").value === '' || !Mascara_Hora('horario')) {
            erro += 'Informe um horário correto.\n'
        }
        if (document.getElementById("servico").value === '') {
            erro += 'Informe o serviço.\n'
        }

        if (erro == '') {
            return true
        } else {
            var divDangerLogin = document.getElementById('divResultado');
            divDangerLogin.setAttribute('class', "alert alert-danger");
            divDangerLogin.setAttribute('role', "alert");
            divDangerLogin.innerText = erro;

            return false
        }

    }

    $(document).ready(function () {
        $("#horario").mask("99:99");
    });

    function Mascara_Hora(Campo) {

        var hora01 = '';
        var Hora = document.getElementById(Campo).value;
        hora01 = hora01 + Hora;

        if (hora01.length == 2) {
            hora01 = hora01 + ':';
            Hora = hora01;
        }
        if (hora01.length == 5) {
            return Verifica_Hora(Campo);
        }
    }

    function Verifica_Hora(Campo) {

        Hora = document.getElementById(Campo);
        hrs = (Hora.value.substring(0, 2));
        min = (Hora.value.substring(3, 5));

        estado = "";
        if ((hrs < 00) || (hrs > 23) || (min < 00) || (min > 59)) {
            estado = "errada";
        }
        if (Hora == "") {
            estado = "errada";
        }
        if (estado == "errada") {
            document.getElementById(Campo).focus();
            return false
        }

        return true
    }

    $(document).ready(function () {
        $('#dataServico').mask('99/99/9999');
        return false;
    });

    $(document).ready(function () {
        $("#valor").maskMoney({
            prefix: "R$",
            decimal: ",",
            thousands: "."
        });
    });

    $(function () {
        $("#dataServico").datepicker({dateFormat: 'dd/mm/yy'});
    });

    function ValidaData() {

        var dataServico = document.getElementById('dataServico').value;

        reg = /[^\d\/\.]/gi;                  // Mascara = dd/mm/aaaa | dd.mm.aaaa
        var valida = dataServico.replace(reg, '');    // aplica mascara e valida só numeros
        if (valida && valida.length == 10) {  // é válida, então ;)
            var ano = dataServico.substr(6),
                mes = dataServico.substr(3, 2),
                dia = dataServico.substr(0, 2),
                M30 = ['04', '06', '09', '11'],
                v_mes = /(0[1-9])|(1[0-2])/.test(mes),
                v_ano = /(19[1-9]\d)|(20\d\d)|2100/.test(ano),
                rexpr = new RegExp(mes),
                fev29 = ano % 4 ? 28 : 29;

            if (v_mes && v_ano) {
                if (mes == '02') return (dia >= 1 && dia <= fev29);
                else if (rexpr.test(M30)) return /((0[1-9])|([1-2]\d)|30)/.test(dia);
                else return /((0[1-9])|([1-2]\d)|3[0-1])/.test(dia);
            }
        }
        return false                           // se inválida :(
    }

    function redirecionarPaginaInicialUsuario() {
        // Faz um redirecionamento sem adicionar a página original ao histórico de navegação do browser.
        window.location.replace("/gerenciador_barbearia/pages/paginaInicialUsuario.jsp");
    }

</script>
</body>
</html>
