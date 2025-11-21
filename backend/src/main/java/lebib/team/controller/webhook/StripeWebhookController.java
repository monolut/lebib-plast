package lebib.team.controller.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lebib.team.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/stripe")
public class StripeWebhookController {

    private final PaymentService paymentService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    public StripeWebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader(value = "Stripe-Signature", required = false) String sigHeader) {
        System.out.println("Stripe webhook hit!");
        System.out.println("Header: " + sigHeader);
        System.out.println("Payload: " + payload);

        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            System.out.println("Webhook type=" + event.getType());

            switch (event.getType()) {
                case "payment_intent.succeeded" -> {
                    System.out.println("case payment_intent.succeeded");
                    PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                    System.out.println(intent);
                    if (intent != null) {
                        System.out.println("PaymentIntent succeeded for: " + intent.getId());
                        paymentService.markPaymentAsSucceeded(intent.getId());
                    }
                }

                case "charge.succeeded" -> {
                    JsonNode root = objectMapper.readTree(payload);
                    JsonNode chargeObject = root.path("data").path("object");
                    String paymentIntentId = chargeObject.path("payment_intent").asText();

                    if (paymentIntentId != null && !paymentIntentId.isEmpty()) {
                        System.out.println("Charge succeeded for intent: " + paymentIntentId);
                        paymentService.markPaymentAsSucceeded(paymentIntentId);
                    } else {
                        System.out.println("Charge succeeded, but payment_intent is missing!");
                    }
                }

                default -> System.out.println("Ignored event type: " + event.getType());
            }

            return ResponseEntity.ok("Received");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Webhook error: " + e.getMessage());
        }
    }

}