package ru.innoseti.demo.config;

import org.apache.commons.io.FileUtils;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyPostgreSQLContainer extends PostgreSQLContainer<MyPostgreSQLContainer> {

    private static final int EXPOSED_PORT = 5432;
    private static final String INIT_SCRIPT = "containers/postgres/configs/init.sql";

    @SuppressWarnings("resource")
    public MyPostgreSQLContainer() {
        super("postgres:latest");
        withDatabaseName("test_db");
        withUsername("admin");
        withPassword("test");
        withExposedPorts(EXPOSED_PORT);
        withCommand("postgres", "-c", "listen_addresses=*");
    }

    @Override
    public void start() {
        super.start();
        try (Connection conn = DriverManager.getConnection(getJdbcUrl(), getUsername(), getPassword());
             Statement stmt = conn.createStatement()) {
            stmt.execute(FileUtils.readFileToString(new File(INIT_SCRIPT), "UTF-8"));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create schema", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}