package ru.innoseti.demo.dependencies;

import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapStructTest {

    @Test
    public void test() {
        UUID uuid = UUID.randomUUID();
        TestInput testInput = new TestInput(uuid);
        TestOutput testOutput = TestMapper.INSTANCE.map(testInput);
        assertEquals(uuid, testOutput.id);
    }

    record TestInput(UUID id) {
    }

    record TestOutput(UUID id) {
    }

    @Mapper(componentModel = "default") //not redundant
    public interface TestMapper {
        TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

        TestOutput map(TestInput testInput);

    }
}
