package modelo;

import Utilitarios.Util;

import java.util.Date;

public class Agendamento {

    private Long id;
    private String nomeCliente;
    private float valor;
    private Date dataServico;
    private EnumServico servico;
    private String horario;
    private String observacao;
    private Usuario usuario;//Barbeiro
    private String imagem;
    private String contenttype;
    private String imagemTemporaria;


    public Agendamento(String nomeCliente, float valor, String dataServico, EnumServico servico, String observacao, Usuario usuario) {
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.dataServico = Util.getDataFormatada(dataServico);
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

    public Date getDataServico() {
        return dataServico;
    }

    public void setDataServico(Date dataServico) {
        this.dataServico = dataServico;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getImagemTemporaria() {
        return ("data:" + contenttype + ";base64," + imagem).split("value")[0];
    }

    public void setImagemTemporaria(String imagemTemporaria) {
        this.imagemTemporaria = imagemTemporaria;
    }
}