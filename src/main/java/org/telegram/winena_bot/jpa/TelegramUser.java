package org.telegram.winena_bot.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.telegram.winena_bot.BotStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor
public class TelegramUser {
    @Id
    @Positive
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BotStatus status;
}

