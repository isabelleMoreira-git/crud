package com.isabelle.crud.eligibility;

import com.isabelle.crud.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.isabelle.crud.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class EligibilityService {

    private static final Logger logger = LoggerFactory.getLogger(EligibilityService.class);

    public boolean isEligible(Customer customer) {
        logger.info("Iniciando verificação de elegibilidade do cliente {}",
                customer.getId());

        if (!"PF".equals(customer.getIndicationDocumentType())) {
            logger.warn("O cliente {} não é elegível: tipo de documento não é PF.",
                    customer.getId());
            return false;
        }

        if (customer.getCustomerCompanyFlag()) {
            logger.warn("O cliente {} não é elegível: cliente possui vínculo com uma empresa.",
                    customer.getId());
            return false;
        }

        if (customer.getAnnualTpv().doubleValue() > 30000) {
            logger.warn("O cliente {} não é elegível: TPV anual de {} excede o limite de 30000.",
                    customer.getId(),
                    customer.getAnnualTpv()
            );
            return false;
        }

        logger.info("O cliente {} é elegível.", customer.getId());
        return true;
    }
}

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

//Segunda tentativa
//    @Autowired
//    private MccValidationService mccValidationService;

//mcc
//        if (!mccValidationService.mccIsValid(customer.getMcc())) {
//            return false;
//        }