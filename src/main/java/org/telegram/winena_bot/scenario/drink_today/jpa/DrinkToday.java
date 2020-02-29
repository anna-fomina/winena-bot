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

@Entity
@Getter @Setter @Accessors(chain = true)
@NoArgsConstructor
public class DrinkToday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Positive
    private Integer userId;

    @NotEmpty
    private String question;

    private int points;
}
