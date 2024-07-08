package ar.com.thiagoianuzzi.commands;

import ar.com.thiagoianuzzi.DiscordBot;
import ar.com.thiagoianuzzi.model.entity.LoggedMessage;
import ar.com.thiagoianuzzi.model.table.LoggedMessageRepository;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Greetings extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            logMessage(event.getMessage());
        }
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        LoggedMessage loggedMessage = new LoggedMessageRepository().getByMessageId(event.getMessageId());

        DiscordBot.getInstance()
                .getJda()
                .getChannelById(TextChannel.class, DiscordBot.getInstance().getConfig().get("logs.channel"))
                .sendMessage("Mensaje editado \n" +
                        "Mensaje original: " + loggedMessage.getMessageContent() + "\n" +
                        "Autor: " + loggedMessage.getAuthor())
                .queue();

        updateMessage(event.getMessage());
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        LoggedMessage loggedMessage = new LoggedMessageRepository().getByMessageId(event.getMessageId());

        DiscordBot.getInstance()
                .getJda()
                .getChannelById(TextChannel.class, DiscordBot.getInstance().getConfig().get("logs.channel"))
                .sendMessage("Mensaje eliminado \n" +
                        "Mensaje original: " + loggedMessage.getMessageContent() + "\n" +
                        "Autor: " + loggedMessage.getAuthor())
                .queue();
    }

    private void logMessage(Message message) {
        LoggedMessage loggedMessage = new LoggedMessage();

        loggedMessage.setMessageId(message.getId());
        loggedMessage.setAuthor(message.getAuthor().getAsMention());
        loggedMessage.setMessageContent(message.getContentDisplay());

        new LoggedMessageRepository().save(loggedMessage);
    }

    private void updateMessage(Message message) {
        LoggedMessage loggedMessage = new LoggedMessageRepository().getByMessageId(message.getId());

        loggedMessage.setMessageContent(message.getContentDisplay());

        new LoggedMessageRepository().update(loggedMessage);
    }
}
