package dev.lurien.staff.lurienStaff.listeners;

import dev.lurien.bot.LurienBot;
import dev.lurien.staff.lurienStaff.managers.WarnsManager;
import dev.lurien.staff.lurienStaff.model.Warn;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class StaffButtonsListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if(event.getButton().getId() == null) return;
        if(event.getButton().getId().startsWith("dw;")){
            if(!Objects.requireNonNull(event.getMember()).getRoles().contains(LurienBot.getModeratorRole())){
                event.reply(LurienBot.getCrossEmoji()+" Solo moderadores en adelante pueden elimina advertencias.").setEphemeral(true).queue();
                return;
            }

            event.deferReply(true).queue();

            UUID id = UUID.fromString(event.getButton().getId().split(";")[1]);
            Warn warn = WarnsManager.getWarnById(id);
            if(warn == null){
                event.getHook().sendMessage(LurienBot.getCrossEmoji()+" La advertencia ya fue removida.").setEphemeral(true).queue();
                return;
            }

            WarnsManager.removeWarn(warn, event.getMember());
            event.getHook().sendMessage(LurienBot.getTickEmoji()+" Removiste la advertencia.").setEphemeral(true).queue();
        }
    }
}
