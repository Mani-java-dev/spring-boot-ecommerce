package com.project.hammer.config;

import com.project.hammer.exceptions.BadRequestCustomException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RateLimitConfiguration {

    private static final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public static void performRateLimiting(HttpServletRequest request, String[] whiteList) {
        if (Arrays.asList(whiteList).contains(request.getRequestURI())) {
            return;
        }

        String clientIP = request.getRemoteAddr();

        StringBuilder requestPathWithParams = new StringBuilder(request.getRequestURI());

        Map<String, String[]> params = request.getParameterMap();
        if (!params.isEmpty()) {
            requestPathWithParams.append("?");
            params.keySet().stream().sorted().forEach(key -> {
                String[] values = params.get(key);
                for (String value : values) {
                    requestPathWithParams.append(key)
                            .append("=")
                            .append(value)
                            .append("&");
                }
            });
            requestPathWithParams.setLength(requestPathWithParams.length() - 1);
        }

        if(!allowRequest(clientIP,requestPathWithParams.toString())){
            log.error("rate limit exceeded");
            throw new BadRequestCustomException("access limit exceeded");
        }


    }



    //10 requests per minute
    private static Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))))
                .build();
    }

    public static boolean allowRequest(String clientId, String requestPath) {
        String key = clientId + ":" + requestPath;
        Bucket bucket = buckets.computeIfAbsent(key, k -> createNewBucket());
        return bucket.tryConsume(1); 
    }
}
