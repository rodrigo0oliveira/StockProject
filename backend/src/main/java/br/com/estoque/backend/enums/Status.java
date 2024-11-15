package br.com.estoque.backend.enums;

import lombok.Getter;

@Getter
public enum Status {

    AVAILABLE("DISPONIVEL"),
    NOTAVAILABLE("NAODISPONIVEL");

    private String name;

    Status(String name){
        this.name = name;
    }
}
