package com.lyacoin.api.seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SeedGenerator {

    @Autowired
    private Random random;

    @Value("${seed.size}")
    private Integer seedSize;
    @Value("${seed.range}")
    private Integer seedRange;

    public List<String> generate() {
        return IntStream.range(0, seedSize)
                .boxed()
                .collect(Collectors.toList())
                .stream()
                .map(i -> {
                    return EnglishWordList
                            .INSTANCE.getWord(random.ints(0, seedRange).findFirst().getAsInt());
                })
                .collect(Collectors.toList());
    }
}
