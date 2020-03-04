package org.telegram.winena_bot.scenario.drink_today.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor
public class DrinkTodayAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Positive
    private long questionId;

    @NotEmpty
    @Size(max = 255)
    private String text;

    @Min(-1) @Max(100)
    private int points;
}
