package io.product.rnd.sample.greeting;

import org.springframework.data.annotation.Id;

public class Greeting {

    @Id
    private Long id;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Greeting(String message) {
        this.message = message;
    }

    public Greeting() {
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "message='" + message + '\'' +
                '}';
    }
}
