package com.tailan.confeitaria.web.domain.enums;

public enum StatusPagamento {
    PENDENTE(1),
    QUITADO(2),
    CANCELADO(3);
    private  int  code;
    StatusPagamento(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StatusPagamento valueOf(int code){
        for(StatusPagamento statusPagamento : StatusPagamento.values()){
            if(statusPagamento.getCode() == code){
                return statusPagamento;
            }
        }
        throw new IllegalArgumentException("Código de Status de Pagamento inválido");
    }
}
