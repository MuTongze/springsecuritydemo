package com.mtz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class FuncTest {
    @Test
    public void testFunc() {
        String name = "mtz";
        Optional<String> optionalS = Optional.ofNullable(name);
        List<String> collect = Arrays.stream(optionalS.map(s -> s.split(""))
                .get()).collect(Collectors.toList());
        System.out.println(collect);

    }
}
