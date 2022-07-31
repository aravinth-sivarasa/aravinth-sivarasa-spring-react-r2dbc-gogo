package io.product.rnd.sample.barcode;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.google.zxing.WriterException;

import reactor.core.publisher.Flux;

@Configuration(proxyBeanMethods = false)
public class BarcodeRouter {

    private static Logger log = LoggerFactory.getLogger(BarcodeRouter.class);

    @Bean
    public RouterFunction<ServerResponse> generate(BarcodeHandler barcodeHandler) {
        return route(GET(BarcodeEndpoints.GET_BARCODE_V1).and(accept(MediaType.APPLICATION_JSON)),
                request -> {
                    try {
                        BufferedImage bi = barcodeHandler.generateQRCodeImage(request.pathVariable("code"));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bi, "png", baos);
                        byte[] bytes = baos.toByteArray();
                        DataBuffer buffer = new DefaultDataBufferFactory().wrap(bytes);
                        return ServerResponse.ok().contentType(MediaType.IMAGE_PNG)
                                .body(BodyInserters.fromDataBuffers(Flux.just(buffer)));
                    } catch (WriterException | IOException e) {
                        log.error("Unable to write buffer {}", e.getMessage());
                        return ServerResponse.badRequest().body(e.getMessage(), String.class);
                    }
                });
    }

    @Bean
    public BarcodeHandler barcodeHandler() {
        return new BarcodeHandler();
    }
}

class BarcodeEndpoints {
    private static final String PREFIX = "";
    public static final String GET_BARCODE_V1 = PREFIX + "/v1/barcode/{code}";

    private BarcodeEndpoints() {
    }
}