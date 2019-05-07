package modelo;

import Utilitarios.Util;

import java.util.Date;

public class Agendamento {

    private Long id;
    private String nomeCliente;
    private float valor;
    private Date data;
    private EnumServico servico;
    private String observacao;
    private Usuario usuario;//Barbeiro

    public Agendamento(String nomeCliente, float valor, String data, EnumServico servico, String observacao, Usuario usuario) {
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.data = Util.getDataFormatada(data);
        this.servico = servico;
        this.observacao = observacao;
        this.usuario = usuario;
    }

    public Agendamento(Long id) {
        this.id = id;
    }

    public Agendamento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public EnumServico getServico() {
        return servico;
    }

    public void setServico(EnumServico servico) {
        this.servico = servico;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}