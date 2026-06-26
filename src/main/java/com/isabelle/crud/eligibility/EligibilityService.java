package com.isabelle.crud.eligibility;

import com.isabelle.crud.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EligibilityService {
    //Segunda tentativa
//    @Autowired
//    private MccValidationService mccValidationService;

    //Primeira tentativa
//    private static final Set<String> ALLOWED_MCCS = Set.of(
//            "742",
//            "1799",
//            "4121",
//            "5499",
//            "5697",
//            "5963",
//            "7230",
//            "7538",
//            "8011",
//            "8021",
//            "8099",
//            "8111",
//            "8999"
//    );


    public boolean isEligible(Customer customer) {

        if (!"PF".equals(customer.getIndicationDocumentType())) {
            return false;
        }

        if (customer.getCustomerCompanyFlag()) {
            return false;
        }

//        if (!mccValidationService.mccIsValid(customer.getMcc())) {
//            return false;
//        }
        

        if (customer.getAnnualTpv().doubleValue() > 30000) {
            return false;
        }

        System.out.println("Cliente elegível.");
        return true;
    }
}