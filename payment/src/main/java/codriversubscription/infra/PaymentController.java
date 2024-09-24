package codriversubscription.infra;

import codriversubscription.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/payments")
@Transactional
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @RequestMapping(
        value = "/payments/pay",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Payment pay(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody PayCommand payCommand
    ) throws Exception {
        System.out.println("##### /payment/pay  called #####");
        Payment payment = new Payment();
        payment.pay(payCommand);
        paymentRepository.save(payment);
        return payment;
    }
}
//>>> Clean Arch / Inbound Adaptor
