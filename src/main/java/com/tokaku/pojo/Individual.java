package com.tokaku.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Individual implements Comparable<Individual> {
    String[][] schedule;
    double fitness;

    @Override
    public int compareTo(Individual individual) {
        if (this.getFitness() > individual.getFitness())
            return -1;
        else
            return 1;
    }
}
