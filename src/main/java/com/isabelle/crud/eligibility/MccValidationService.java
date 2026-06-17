package com.isabelle.crud.eligibility;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MccValidationService {
    private final List<String> allowedMccs = new ArrayList<>();

    public MccValidationService(){
        allowedMccs.add("742");
        allowedMccs.add("1799");
        allowedMccs.add("4121");
        allowedMccs.add("5499");
        allowedMccs.add("5697");
        allowedMccs.add("5963");
        allowedMccs.add("7230");
        allowedMccs.add("7538");
        allowedMccs.add("8011");
        allowedMccs.add("8021");
        allowedMccs.add("8099");
        allowedMccs.add("8111");
        allowedMccs.add("8999");
        System.out.println(allowedMccs);
    }


    public boolean MccIsValid(String mcc) {
        System.out.println("Iniciando validação do MCC: " + mcc);

        for (int i = 0; i< allowedMccs.size(); i++){
            System.out.println("Comparando com: " + allowedMccs.get(i));

            if (mcc.equals(allowedMccs.get(i))){
                System.out.println("MCC válido encontrado.");
                return true;
            }
        }
        System.out.println("MCC inválido.");
        return false; // se passar pelo loop sem retornar true
    }

}

// Transformar a lista pra receber enum. 742 = taxista mcc.taxista

