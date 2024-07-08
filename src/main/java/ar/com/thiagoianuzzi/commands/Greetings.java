package ar.com.thiagoianuzzi.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Greetings extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("hola")) {
            event.reply("¡Holi " + event.getUser().getEffectiveName() + "!").queue();
        }

        if (event.getName().equals("pinga")) {
            event.reply("¡¡¡PINGA!!!").queue();
        }
    }

}
