package ru.vetoshkin.store.product.dao;
import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.product.Product;
import ru.vetoshkin.store.product.Products;
import ru.vetoshkin.store.util.HikariPool;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class ProductService {
    private static final BlockingQueue<Holder> queue = new LinkedBlockingQueue<>();
    private static final ExecutorService executors = Executors.newFixedThreadPool(10);


    public static void init() {
        for (int i = 0; i < 10; i++) {
            executors.execute(() -> {
                try {
                    while (true) {
                        Holder holder = queue.take();
                        URL image = new URL(holder.getUrl());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        try (InputStream stream = image.openStream()) {
                            byte[] byteChunk = new byte[4096];
                            int n;

                            while ((n = stream.read(byteChunk)) > 0) {
                                baos.write(byteChunk, 0, n);
                            }
                        }

                        ImageStorage.save(baos.toByteArray());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }



    public static void add(List<Product> products) {
        for (Product product : products) {
            for (String url : product.getImages()) {
                queue.offer(new Holder(product.getId(), url));
            }
        }
    }


    public static void main(String[] args) throws Exception {
        HikariPool.init();
        init();

        JAXBContext jaxbContext = JAXBContext.newInstance(Products.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Products from = (Products) unmarshaller.unmarshal(Files.newBufferedReader(Paths.get("C:\\Users\\Александр\\Desktop\\4 семестр\\shop\\shop\\src\\main\\resources\\o.xml")));
        add(from.getProducts());

        Thread.sleep(1000000);
    }




    @Getter
    private static class Holder {
        private final String code;
        private final String url;


        private Holder(String code, String url) {
            this.code = code;
            this.url = url;
        }


        @Override
        public String toString() {
            return code;
        }
    }

}
