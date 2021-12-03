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
        val ChromosomesNumber = scanner.nextInt()
        println("Podaj Pk i Pm jako wartość od 0 do 1 w typie double: ")
        val Pk = scanner.nextDouble()
        val Pm = scanner.nextDouble()
        println("Podaj maksymalną wagę plecaka: ")
        val MaxWeight = scanner.nextInt()
        println("Podaj liczbę maksymalnych wystąpień wartości funkcji przystosowania: ")
        val numOfMaxFitFunOccur = scanner.nextInt()
        var numOfFitListIterations = 0
        var numOfIterations = 0

        /*for(int i = 0; i< 10; i++){
        System.out.println("Podaj Wagę i Wartość dla " + i + " bitu: ");
            int Weight = scanner.nextInt();
            int Value = scanner.nextInt();
            VWList.add(new VW(Weight, Value));
        }*/
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
        println(" | Początkowa pula chromosomów | ")
        for (i in 0 until ChromosomesNumber) {
            val randNumHelper = RandomNumberChromosome()
            ChromosomeList.add(Chromosome(randNumHelper, 0.0))
            ChromosomeList2.add(Chromosome(randNumHelper, 0.0))
            PercentageRangeList.add(PercentageRangeClass(0.0, 0.0))
            System.out.print(" |  " + ChromosomeList[i].ch.toString() + " | ")
        }
        for (i in 0 until ChromosomesNumber) {
            var binarValue: String = Integer.toBinaryString(ChromosomeList[i].ch)
            while (binarValue.length < 10) {
                binarValue = "0$binarValue"
            }

            ChromosomeList[i].chromosomeBinar = binarValue
            ChromosomeList2[i].chromosomeBinar = binarValue
        }
        var final = 0
        while (true) {
            var valueOfFitFunForAllChromosomes: Int = 0
            var percentageIterationValue: Double = 0.00
            var aValuePercent: Double = 0.0
            for (j in 0 until ChromosomesNumber) {
                //that loop calculates Weight of each chromosome based on users keyboard input, checks if weight is bigger than admissible, and if so it throws one of bits from 1 to 0 untill
                // it finds first weight which is below limit
                var weightOfFitFun = 0
                var calculatedWeight = 0
                for (i in 0..9) {
                    val Check: Char = ChromosomeList[j].chromosomeBinar!![i]
                    calculatedWeight = Integer.parseInt(Check.toString()) * VWList[i].weight
                    weightOfFitFun += calculatedWeight
                }
                ChromosomeList[j].weightOfChromosome = weightOfFitFun
                if (MaxWeight < ChromosomeList[j].weightOfChromosome) {
                    var xd = 0
                    while (true) {
                        xd++
                        val Bit = RandomNumberLokusPm()
                        val Beg: String = ChromosomeList[j].chromosomeBinar!!.substring(0, Bit - 1)
                        val End: String = ChromosomeList[j].chromosomeBinar!!.substring(Bit)
                        val Mutate: String = ChromosomeList[j].chromosomeBinar!!.substring(Bit-1, Bit)

                        if (Mutate.equals("1")) {

                            val ChromosomeBinarFinal = Beg + "0" + End
                            ChromosomeList[j].chromosomeBinar = ChromosomeBinarFinal
                            ChromosomeList2[j].chromosomeBinar = ChromosomeBinarFinal
                            val decimal: Int = ChromosomeList[j].chromosomeBinar!!.toInt(2)
                            ChromosomeList[j].ch = decimal
                            ChromosomeList2[j].ch = decimal
                            ChromosomeList[j].weightOfChromosome = ChromosomeList[j].weightOfChromosome - VWList[Bit-1].weight
                            ChromosomeList2[j].weightOfChromosome = ChromosomeList[j].weightOfChromosome - VWList[Bit-1].weight
                        }
                        if (MaxWeight > ChromosomeList[j].weightOfChromosome) {
                            break
                        }
                    }
                }
            }
            for (j in 0 until ChromosomesNumber) {
                var valueofFitFun = 0
                var calculatedValue = 0
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
            for (i in 0 until ChromosomesNumber) {
                ChromosomeList[i].percent = PercentageValue(ChromosomeList[i].valueOfChromosome, valueOfFitFunForAllChromosomes)
            }
            for (i in 0 until ChromosomesNumber) {
                aValuePercent = percentageIterationValue
                percentageIterationValue += ChromosomeList[i].percent
                PercentageRangeList[i] = PercentageRangeClass(aValuePercent, percentageIterationValue)
            }
            for (i in 0 until ChromosomesNumber) {
                val x = RandomPercentageNumber()
                for (j in 0 until ChromosomesNumber) {
                    if (x > PercentageRangeList[j].a && x < PercentageRangeList[j].b) {
                        ChromosomeList[i].ch = ChromosomeList2[j].ch
                    }
                }
            }
            for (i in 0 until ChromosomesNumber) {
                ChromosomeList2[i].ch = ChromosomeList[i].ch
                var BinarValue: String = Integer.toBinaryString(ChromosomeList[i].ch)
                while (BinarValue.length < 10) {
                    BinarValue = "0$BinarValue"
                }
                ChromosomeList[i].chromosomeBinar = BinarValue
                ChromosomeList2[i].chromosomeBinar = BinarValue
            }
            var PkhelperNumber = 0
            for (i in 0 until ChromosomesNumber / 2) {
                val x = RandomPercentageNumber()
                val random16 = Random16()
                val random162 = Random16()
                PkhelperNumber += 2
                val Lokus = RandomNumberLokusPk()
                if (x < Pk) {
                    val BegOneOf2: String = ChromosomeList2[random16-1].chromosomeBinar!!.substring(0, Lokus)
                    val BegTwoOf2: String = ChromosomeList2[random162-1].chromosomeBinar!!.substring(0, Lokus)
                    val EndOneOf2: String = ChromosomeList2[random16-1].chromosomeBinar!!.substring(Lokus, ChromosomeList[i].chromosomeBinar!!.length)
                    val EndTwoOf2: String = ChromosomeList2[random162-1].chromosomeBinar!!.substring(Lokus, ChromosomeList[i].chromosomeBinar!!.length)
                    val One = BegOneOf2 + EndTwoOf2
                    val Two = BegTwoOf2 + EndOneOf2
                    ChromosomeList[PkhelperNumber-2].chromosomeBinar = One
                    ChromosomeList[PkhelperNumber-1].chromosomeBinar = Two
                }
            }
            for (i in 0 until ChromosomesNumber) {
                val x = RandomPercentageNumber()
                val Lokus = RandomNumberLokusPm()
                if (x < Pm) {

                    val Beg: String = ChromosomeList[i].chromosomeBinar!!.substring(0, Lokus - 1)
                    val End: String = ChromosomeList[i].chromosomeBinar!!.substring(Lokus)
                    val Mutate: String = ChromosomeList[i].chromosomeBinar!!.substring(Lokus-1, Lokus)
                    if (Mutate.equals("0")) {
                        val ChromosomeBinarFinal = Beg + "1" + End
                        ChromosomeList[i].chromosomeBinar = ChromosomeBinarFinal
                    } else if (Mutate.equals("1")) {
                        val ChromosomeBinarFinal = Beg + "0" + End
                        ChromosomeList[i].chromosomeBinar = ChromosomeBinarFinal
                    }
                }
            }
            for (i in 0 until ChromosomesNumber) {
                val decimal: Int = ChromosomeList[i].chromosomeBinar!!.toInt(2)
                ChromosomeList[i].ch = decimal
                ChromosomeList2[i].ch = decimal
            }
            numOfIterations++
        }
        println("\n | Liczba iteracji: $numOfIterations | ")
        //System.out.println("Największy fenotyp: "+FinalValue);
        println(" | Największa funkcja przystosowania: " + final / 6 + " | ")
        for (i in 0 until ChromosomesNumber) {
            val j = i + 1
            print(" | CH" + j + " = " + ChromosomeList[i].chromosomeBinar + " | ")
            System.out.print(" | Fenotyp: " + ChromosomeList[i].ch + " | ")
            System.out.print(" | Wartość plecaka: " + ChromosomeList[i].valueOfChromosome + " | ")
            System.out.println(" | Waga plecaka: " + ChromosomeList[i].weightOfChromosome + " | ")
        }
    }

    fun RandomNumberChromosome(): Int {
        val r = Random()
        return r.nextInt(1023 - 1 + 1)
    }

    fun RandomPercentageNumber(): Double {
        return Math.random() * 1
    }

    fun RandomNumberLokusPm(): Int {
        val r = Random()
        return r.nextInt(10 - 1 + 1) + 1
    }

    fun RandomNumberLokusPk(): Int {
        val r = Random()
        return r.nextInt(9 - 1 + 1) + 1
    }

    fun Random16(): Int {
        val r = Random()
        return r.nextInt(6 - 1 + 1) + 1
    }

    fun PercentageValue(a: Int, b: Int): Double {
        val c = a.toDouble()
        val d = b.toDouble()
        return c / d
    }
