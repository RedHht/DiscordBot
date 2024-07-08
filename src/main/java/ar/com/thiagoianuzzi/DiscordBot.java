package ar.com.thiagoianuzzi;

import ar.com.thiagoianuzzi.commands.Greetings;
import ar.com.thiagoianuzzi.utils.ConfigUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.Map;

public class DiscordBot {
    private Map<String, String> config;
    private final Logger logger;
    private static DiscordBot instance;

    public DiscordBot() {
        DiscordBot.instance = this;
        this.logger = LoggerFactory.getLogger(DiscordBot.class);
        setupConfig();

        JDA jda = JDABuilder.createLight(config.get("token"), EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                .addEventListeners(new Greetings())
                .build();

        jda.updateCommands().addCommands(Commands.slash("hola", "El bot te saluda!"))
                .addCommands(Commands.slash("pinga", "???"))
                .queue();
    }

    public static DiscordBot getInstance() {
        return instance;
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
