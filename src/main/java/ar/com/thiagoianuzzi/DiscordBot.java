package ar.com.thiagoianuzzi;

import ar.com.thiagoianuzzi.commands.Greetings;
import ar.com.thiagoianuzzi.utils.ConfigUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class DiscordBot {
    private Map<String, String> config;
    private final Logger logger;
    private static DiscordBot instance;
    private final JDA jda;
    private EntityManager entityManager;

    public DiscordBot() {
        DiscordBot.instance = this;
        this.logger = LoggerFactory.getLogger(DiscordBot.class);
        setupConfig();

        this.jda = JDABuilder.createLight(config.get("token"), EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                .addEventListeners(new Greetings())
                .build();

        setupEntityManager();
    }

    private void setupEntityManager() {
        String url = "jdbc:mysql://" + getConfig().get("mysql.host") + ":3306/" + getConfig().get("mysql.database");

        HashMap<String, String> map = new HashMap<>();
        map.put("jakarta.persistence.jdbc.url", url);
        map.put("jakarta.persistence.jdbc.user", getConfig().get("mysql.user"));
        map.put("jakarta.persistence.jdbc.password", getConfig().get("mysql.password"));

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("jpa-hibernate-mysql", map);
        this.entityManager = entityManagerFactory.createEntityManager();

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public static DiscordBot getInstance() {
        return instance;
    }

    public JDA getJda() {
        return jda;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    private void setupConfig() {
        this.config = ConfigUtils.getConfig();
    }

    public Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {
        DiscordBot bot = new DiscordBot();
    }

}
