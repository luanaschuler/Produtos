package br.senai.sc.projeto01.modelo;

import java.io.Serializable;

public class Produto implements Serializable {

    private String nome;
    private Float valor;
    private int id;

    public Produto(String nome, Float valor, int id) {
        this.nome = nome;
        this.valor = valor;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return nome;
    }
}
