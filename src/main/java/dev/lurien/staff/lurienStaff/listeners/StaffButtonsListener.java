package dev.lurien.staff.lurienStaff.listeners;

import dev.lurien.bot.LurienBot;
import dev.lurien.staff.lurienStaff.managers.WarnsManager;
import dev.lurien.staff.lurienStaff.model.Warn;
import dev.lurien.staff.lurienStaff.model.WarnReason;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
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
        }else if(event.getButton().getId().startsWith("crw;")){
            UUID id = UUID.fromString(event.getButton().getId().split(";")[1]);
            Warn warn = WarnsManager.getWarnById(id);
            if(!Objects.requireNonNull(event.getMember()).getRoles().contains(LurienBot.getModeratorRole())){
                event.reply(LurienBot.getCrossEmoji()+" Solo moderadores en adelante pueden elimina advertencias.").setEphemeral(true).queue();
                return;
            }

            if(warn == null){
                event.getHook().sendMessage(LurienBot.getCrossEmoji()+" La advertencia no existe.").setEphemeral(true).queue();
                return;
            }

            TextInput ti = TextInput.create("crwt1", "Nueva Razón", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setPlaceholder("Mal Comportamiento, Toxicidad, Spam, Flood").build();
            Modal modal = Modal.create("crwm;"+id, "Cambio de Razón")
                    .addComponents(ActionRow.of(ti)).build();

            event.replyModal(modal).queue();
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if(event.getMember() == null) return;
        if(event.getModalId().startsWith("crwm;")){
            UUID id = UUID.fromString(event.getModalId().split(";")[1]);
            Warn warn = WarnsManager.getWarnById(id);

            if(warn == null){
                event.getHook().sendMessage(LurienBot.getCrossEmoji()+" La advertencia no existe.").setEphemeral(true).queue();
                return;
            }

            String fs = Objects.requireNonNull(event.getValue("crwt1")).getAsString().trim();

            if(!WarnReason.isReason(fs) && !Objects.requireNonNull(event.getMember()).getRoles().contains(LurienBot.getHeadStaffRole())){
                event.getHook().sendMessage(LurienBot.getCrossEmoji()+" Esa no es una razón válida para una advertencia.").queue();
                return;
            }

            WarnsManager.changeReason(warn, event.getMember(), fs);
        }
    }
}
