package org.telegram.winena_bot.scenario.drink_today.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor
public class DrinkTodayQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Positive
    private int authorId;

    @NotEmpty
    @Size(max = 255)
    private String text;

    private boolean completed;
}
