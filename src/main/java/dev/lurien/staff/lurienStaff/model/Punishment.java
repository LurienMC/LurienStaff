package dev.lurien.staff.lurienStaff.model;

import lombok.Data;

public record Punishment(PunishmentType type, long secondDuration) {
}
