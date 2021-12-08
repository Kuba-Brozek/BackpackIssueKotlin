package com.company
import java.util.*
import kotlin.collections.ArrayList

private val ChromosomeList = ArrayList<Chromosome>()
    private val ChromosomeList2 = ArrayList<Chromosome>()
    private val VWList = ArrayList<VW>()
    private val PercentageRangeList = ArrayList<PercentageRangeClass>()
    fun main() {
        val scanner = Scanner(System.`in`)
        println("Podaj liczbe chromosomów: ")
        val chromosomesNumber = scanner.nextInt()
        println("Podaj Pk i Pm jako wartość od 0 do 1 w typie double: ")
        val pk = scanner.nextDouble()
        val pm = scanner.nextDouble()
        println("Podaj maksymalną wagę plecaka: ")
        val maxWeight = scanner.nextInt()
        println("Podaj liczbę maksymalnych wystąpień wartości funkcji przystosowania: ")
        val numOfMaxFitFunOccur = scanner.nextInt()
        var numOfFitListIterations = 0
        var numOfIterations = 0
        for(i in 0..9){
            System.out.println("Podaj Wagę i Wartość dla " + i + " bitu: ")
                val Weight = scanner.nextInt()
                val Value = scanner.nextInt()
                VWList.add( VW(Weight, Value))
        }
        /*
        VWList.add(VW(4, 12))
        VWList.add(VW(9, 6))
        VWList.add(VW(12, 10))
        VWList.add(VW(4, 3))
        VWList.add(VW(6, 3))
        VWList.add(VW(11, 18))
        VWList.add(VW(8, 12))
        VWList.add(VW(12, 4))
        VWList.add(VW(6, 8))
        VWList.add(VW(8, 6))
        */

        println(" | Początkowa pula chromosomów | ")
        for (i in 0 until chromosomesNumber) {
            val randNumHelper = randomNumberChromosome()
            ChromosomeList.add(Chromosome(randNumHelper, 0.0))
            ChromosomeList2.add(Chromosome(randNumHelper, 0.0))
            PercentageRangeList.add(PercentageRangeClass(0.0, 0.0))
            print(" |  " + ChromosomeList[i].ch.toString() + " | ")
        }
        for (i in 0 until chromosomesNumber) {
            var binarValue: String = Integer.toBinaryString(ChromosomeList[i].ch)
            while (binarValue.length < 10) {
                binarValue = "0$binarValue"
            }
            ChromosomeList[i].chromosomeBinar = binarValue
            ChromosomeList2[i].chromosomeBinar = binarValue
        }
        var final = 0
        while (true) {
            var valueOfFitFunForAllChromosomes = 0
            var percentageIterationValue = 0.00
            var aValuePercent: Double
            for (j in 0 until chromosomesNumber) {
                //that loop calculates Weight of each chromosome based on users keyboard input, checks if weight is bigger than admissible, and if so it throws one of bits from 1 to 0 until
                // it finds first weight which is below limit
                var weightOfFitFun = 0
                var calculatedWeight: Int
                for (i in 0..9) {
                    val check: Char = ChromosomeList[j].chromosomeBinar!![i]
                    calculatedWeight = Integer.parseInt(check.toString()) * VWList[i].weight
                    weightOfFitFun += calculatedWeight
                }
                ChromosomeList[j].weightOfChromosome = weightOfFitFun
                if (maxWeight < ChromosomeList[j].weightOfChromosome) {
                    var xd = 0
                    while (true) {
                        xd++
                        val bit = randomNumberLokusPm()
                        val beg: String = ChromosomeList[j].chromosomeBinar!!.substring(0, bit - 1)
                        val end: String = ChromosomeList[j].chromosomeBinar!!.substring(bit)
                        val mutate: String = ChromosomeList[j].chromosomeBinar!!.substring(bit-1, bit)
                            if (mutate == "1") {
                                val chromosomeBinarFinal = beg + "0" + end
                                ChromosomeList[j].chromosomeBinar = chromosomeBinarFinal
                                ChromosomeList2[j].chromosomeBinar = chromosomeBinarFinal
                                val decimal: Int = ChromosomeList[j].chromosomeBinar!!.toInt(2)
                                ChromosomeList[j].ch = decimal
                                ChromosomeList2[j].ch = decimal
                                ChromosomeList[j].weightOfChromosome = ChromosomeList[j].weightOfChromosome - VWList[bit-1].weight
                                ChromosomeList2[j].weightOfChromosome = ChromosomeList[j].weightOfChromosome - VWList[bit-1].weight
                            }
                        if (maxWeight > ChromosomeList[j].weightOfChromosome) {
                            break
                        }
                    }
                }
            }
            for (j in 0 until chromosomesNumber) {
                var valueofFitFun = 0
                var calculatedValue: Int
                for (i in 0..9) {
                    val checker: Char = ChromosomeList[j].chromosomeBinar!![i]
                    calculatedValue = Integer.parseInt(checker.toString()) * VWList[i].value
                    valueofFitFun += calculatedValue
                }
                ChromosomeList[j].valueOfChromosome = valueofFitFun
                valueOfFitFunForAllChromosomes += ChromosomeList[j].valueOfChromosome
            }
            if (valueOfFitFunForAllChromosomes > final) {
                final = valueOfFitFunForAllChromosomes
                numOfFitListIterations = 1
            } else if (valueOfFitFunForAllChromosomes == final) {
                numOfFitListIterations++
            }
            if (numOfFitListIterations == numOfMaxFitFunOccur) {
                break
            }
            for (i in 0 until chromosomesNumber) {
                ChromosomeList[i].percent = percentageValue(ChromosomeList[i].valueOfChromosome, valueOfFitFunForAllChromosomes)
            }
            for (i in 0 until chromosomesNumber) {
                aValuePercent = percentageIterationValue
                percentageIterationValue += ChromosomeList[i].percent
                PercentageRangeList[i] = PercentageRangeClass(aValuePercent, percentageIterationValue)
            }
            for (i in 0 until chromosomesNumber) {
                val x = randomPercentageNumber()
                for (j in 0 until chromosomesNumber) {
                    if (x > PercentageRangeList[j].a && x < PercentageRangeList[j].b) {
                        ChromosomeList[i].ch = ChromosomeList2[j].ch
                    }
                }
            }
            for (i in 0 until chromosomesNumber) {
                ChromosomeList2[i].ch = ChromosomeList[i].ch
                var binarValue: String = Integer.toBinaryString(ChromosomeList[i].ch)
                while (binarValue.length < 10) {
                    binarValue = "0$binarValue"
                }
                ChromosomeList[i].chromosomeBinar = binarValue
                ChromosomeList2[i].chromosomeBinar = binarValue
            }
            var pkhelperNumber = 0
            for (i in 0 until chromosomesNumber / 2) {
                val x = randomPercentageNumber()
                val random16 = random16()
                val random162 = random16()
                pkhelperNumber += 2
                val lokus = randomNumberLokusPk()
                if (x < pk) {
                    val begOneOf2: String = ChromosomeList2[random16-1].chromosomeBinar!!.substring(0, lokus)
                    val begTwoOf2: String = ChromosomeList2[random162-1].chromosomeBinar!!.substring(0, lokus)
                    val endOneOf2: String = ChromosomeList2[random16-1].chromosomeBinar!!.substring(lokus, ChromosomeList[i].chromosomeBinar!!.length)
                    val endTwoOf2: String = ChromosomeList2[random162-1].chromosomeBinar!!.substring(lokus, ChromosomeList[i].chromosomeBinar!!.length)
                    val one = begOneOf2 + endTwoOf2
                    val two = begTwoOf2 + endOneOf2
                    ChromosomeList[pkhelperNumber-2].chromosomeBinar = one
                    ChromosomeList[pkhelperNumber-1].chromosomeBinar = two
                }
            }
            for (i in 0 until chromosomesNumber) {
                val x = randomPercentageNumber()
                val lokus = randomNumberLokusPm()
                if (x < pm) {
                    val beg: String = ChromosomeList[i].chromosomeBinar!!.substring(0, lokus - 1)
                    val end: String = ChromosomeList[i].chromosomeBinar!!.substring(lokus)
                    val mutate: String = ChromosomeList[i].chromosomeBinar!!.substring(lokus-1, lokus)
                    if (mutate == "0") {
                        val chromosomeBinarFinal = beg + "1" + end
                        ChromosomeList[i].chromosomeBinar = chromosomeBinarFinal
                    } else if (mutate == "1") {
                        val chromosomeBinarFinal = beg + "0" + end
                        ChromosomeList[i].chromosomeBinar = chromosomeBinarFinal
                    }
                }
            }
            for (i in 0 until chromosomesNumber) {
                val decimal: Int = ChromosomeList[i].chromosomeBinar!!.toInt(2)
                ChromosomeList[i].ch = decimal
                ChromosomeList2[i].ch = decimal
            }
            numOfIterations++
        }
        println("\n | Liczba iteracji: $numOfIterations | ")
        println(" | Największa funkcja przystosowania: " + final / 6 + " | ")
        for (i in 0 until chromosomesNumber) {
            val j = i + 1
            print(" | CH" + j + " = " + ChromosomeList[i].chromosomeBinar + " | ")
            print(" | Fenotyp: " + ChromosomeList[i].ch + " | ")
            print(" | Wartość plecaka: " + ChromosomeList[i].valueOfChromosome + " | ")
            println(" | Waga plecaka: " + ChromosomeList[i].weightOfChromosome + " | ")
        }
    }
    fun randomNumberChromosome(): Int {
        val r = Random()
        return r.nextInt(1023 - 1 + 1)
    }
    fun randomPercentageNumber(): Double {
        return Math.random() * 1
    }
    fun randomNumberLokusPm(): Int {
        val r = Random()
        return r.nextInt(10 - 1 + 1) + 1
    }
    fun randomNumberLokusPk(): Int {
        val r = Random()
        return r.nextInt(9 - 1 + 1) + 1
    }
    fun random16(): Int {
        val r = Random()
        return r.nextInt(6 - 1 + 1) + 1
    }
    fun percentageValue(a: Int, b: Int): Double {
        val c = a.toDouble()
        val d = b.toDouble()
        return c / d
    }