package com.company

class Chromosome {
    var ch = 0
    var chromosomeBinar: String? = null
    var percent = 0.0
    var weightOfChromosome = 0
    var valueOfChromosome = 0

    constructor(Ch: Int, Percent: Double) {
        ch = Ch
        percent = Percent
    }

    constructor(ch: Int, chromosomeBinar: String?, percent: Double, weightOfChromosome: Int, valueOfChromosome: Int) {
        this.ch = ch
        this.chromosomeBinar = chromosomeBinar
        this.percent = percent
        this.weightOfChromosome = weightOfChromosome
        this.valueOfChromosome = valueOfChromosome
    }
}