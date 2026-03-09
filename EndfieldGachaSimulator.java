package com.bgigov.endfield;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;

public class EndfieldGachaSimulator {

    public static void rollWithoutUrgRecAndDoss(int rolls) {
        long start = System.currentTimeMillis();
        int index = 0, pity10 = 0, pity65 = 0, pity120 = 0;
        int counterBanner6 = 0, counter6 = 0, counter5 = 0, counter4 = 0;
        while (index < rolls) {
            index++;
            // pity being 65 means we have failed 65 rolls already and are on the 66th and should get +5% pity
            BigDecimal pity65And80Bonus = pity65 == 80 ? new BigDecimal(1) : pity65 < 65 ? null : new BigDecimal(0.05).multiply(new BigDecimal(pity65+1-65));
            double chance6 = new BigDecimal(0.008).add(pity65 >= 65 ? pity65And80Bonus : new BigDecimal(0)).doubleValue();
            double random = Math.random();
            if (pity120 == 120) {
                chance6 = 1;
            }
            if (random < chance6) {
                counter6++;
                pity10 = 0;
                pity65 = 0;
                if (pity120 == 120 || Math.random() < 0.5) {
                    counterBanner6++;
                    pity120 = 0;
                } else {
                    pity120++;
                }
            } else if (random > 0.008 && random < 0.08) {
                pity10 = 0;
                counter5++;
                pity65++;
                pity120++;
            } else if (pity10 == 10 || random > 0.008 && random < 0.08) {
                counter5++;
                pity10 = 0;
                pity65++;
                pity120++;
            } else {
                counter4++;
                pity10++;
                pity65++;
                pity120++;
            }
        }
        System.out.println("total rolls: " + index);
        System.out.println("6* banner " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* " + counter6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter5), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter4), 5, RoundingMode.HALF_UP));
        System.out.println("Simulation took " + ((double)(System.currentTimeMillis() - start))/1000 + "s");
    }

    public static void rollUntilBannerOperator(int rolls) {
        long start = System.currentTimeMillis();
        int index = 0, pity10 = 0, pity65 = 0, pity120 = 0;
        int counterBanner6 = 0, counter6 = 0, counter5 = 0, counter4 = 0, counterDossier = 0, counterUrgentRecruitment = 0;
        while (index < rolls) {
            index++;
            // pity being 65 means we have failed 65 rolls already and are on the 66th and should get +5% pity
            BigDecimal pity65And80Bonus = pity65 == 80 ? new BigDecimal(1) : pity65 < 65 ? null : new BigDecimal(0.05).multiply(new BigDecimal(pity65+1-65));
            double chance6 = new BigDecimal(0.008).add(pity65 >= 65 ? pity65And80Bonus : new BigDecimal(0)).doubleValue();
            double random = Math.random();
            if (pity120 == 120) {
                chance6 = 1;
            }

            if (random < chance6) {
                counter6++;
                pity10 = 0;
                pity65 = 0;
                if (pity120 == 120 || Math.random() < 0.5) {
                    counterBanner6++;
                    pity120 = 0;
                } else {
                    pity120++;
                }
            } else if (pity10 == 10 || random > 0.008 && random < 0.08) {
                counter5++;
                pity10 = 0;
                pity65++;
                pity120++;
            } else {
                counter4++;
                pity10++;
                pity65++;
                pity120++;
            }
            if (pity120 == 30) {
                counterUrgentRecruitment++;
                int recruitUrgently = 0;
                while (recruitUrgently < 10) {
                    recruitUrgently++;
                    double randomUrgentRecruitment = Math.random();
                    if (randomUrgentRecruitment < 0.008) {
                        counter6++;
                        pity10 = 0;
                        pity65 = 0;
                        if (Math.random() < 0.5) {
                            counterBanner6++;
                            pity120 = 0;
                        }
                    } else if (random > 0.008 && random < 0.08) {
                        counter5++;
                        pity10 = 0;
                    } else {
                        counter4++;
                    }
                }
            } else if (pity120 == 60) {
                counterDossier++;
            }
        }
        System.out.println("total rolls (without urgent recruitment or dossier): " + index);
        System.out.println("6* banner " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* any " + counter6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter5), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter4), 5, RoundingMode.HALF_UP));
        System.out.println("\n\n" + "after accounting for\n" + counterUrgentRecruitment + " urgent recruitments\n" + counterDossier + " dossiers\n\n");
        System.out.println("6* banner  " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* any " + counter6 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counter5), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counter4), 5, RoundingMode.HALF_UP));
        System.out.println("Simulation took " + ((double)(System.currentTimeMillis() - start))/1000 + "s");
    }

    public static void roll60Always(int rolls) {
        long start = System.currentTimeMillis();
        int index = 0, pity10 = 0, pity65 = 0, rollsInBanner = 0, counterUrgentRecruitment = 0;
        int counterBanner6 = 0, counter6 = 0, counter5 = 0, counter4 = 0, counterDossier = 0;
        while (index < rolls) {
            index++;
            // pity being 65 means we have failed 65 rolls already and are on the 66th and should get +5% pity
            BigDecimal pity65And80Bonus = pity65 == 80 ? new BigDecimal(1) : pity65 < 65 ? null : new BigDecimal(0.05).multiply(new BigDecimal(pity65+1-65));
            double chance6 = new BigDecimal(0.008).add(pity65 >= 65 ? pity65And80Bonus : new BigDecimal(0)).doubleValue();
            double random = Math.random();

            if (random < chance6) {
                counter6++;
                pity10 = 0;
                pity65 = 0;
                if (Math.random() < 0.5) {
                    counterBanner6++;
                }
            } else if (pity10 == 10 || random > 0.008 && random < 0.08) {
                counter5++;
                pity10 = 0;
                pity65++;
            } else {
                counter4++;
                pity10++;
                pity65++;
            }
            rollsInBanner++;

            if (rollsInBanner == 30) {
                counterUrgentRecruitment++;
                int recruitUrgently = 0;
                while (recruitUrgently < 10) {
                    recruitUrgently++;
                    double randomUrgentRecruitment = Math.random();
                    if (randomUrgentRecruitment < 0.008) {
                        counter6++;
                        pity10 = 0;
                        pity65 = 0;
                        if (Math.random() < 0.5) {
                            counterBanner6++;
                        }
                    } else if (random > 0.008 && random < 0.08) {
                        counter5++;
                        pity10 = 0;
                    } else {
                        counter4++;
                    }
                }
            }
            if (rollsInBanner == 60) {
                counterDossier++;
                rollsInBanner = 0;
            }
        }
        System.out.println("total rolls (without urgent recruitment): " + index);
        System.out.println("6* banner " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* " + counter6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter5), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter4), 5, RoundingMode.HALF_UP));
        System.out.println("\n\n" + "after accounting for\n" + counterUrgentRecruitment + " urgent recruitments\n" + counterDossier + " dossiers\n\n");
        System.out.println("6* banner " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* " + counter6 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counter5), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counter4), 5, RoundingMode.HALF_UP));
        System.out.println("Simulation took " + ((double)(System.currentTimeMillis() - start))/1000 + "s");
    }

    public static void rollWeapons(int rolls) {
        long start = System.currentTimeMillis();
        int index = 0, counter6 = 0, counterBanner6 = 0;
        int pity4 = 0, pity8 = 0;
        while (index < rolls) {
            index++;
            int roll10 = 0;
            boolean increasePity4 = true, increasePity8 = true;
            while (roll10 < 10) {
                roll10++;
                if (pity4 == 4 || pity8 == 8 || Math.random() < new BigDecimal(0.04).doubleValue()) {
                    counter6++;
                    pity4 = 0;
                    increasePity4 = false;
                    if (pity8 == 8 || Math.random() < 0.25) {
                        counterBanner6++;
                        pity8 = 0;
                        increasePity8 = false;
                    }
                }
            }
            if (increasePity4) {
                pity4++;
            }
            if (increasePity8) {
                pity8++;
            }
        }

        System.out.println("total weapon rolls: " + index);
        System.out.println("6* banner " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* " + counter6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("Simulation took " + ((double)(System.currentTimeMillis() - start))/1000 + "s");

    }

    public static void rollUntilBannerOperatorSkipDossierBanner(int rolls) {
        long start = System.currentTimeMillis();
        int index = 0, pity10 = 0, pity65 = 0, pity120 = 0;
        int counterBanner6 = 0, counter6 = 0, counter5 = 0, counter4 = 0, counterDossier = 0, counterDossier2 = 0, counterUrgentRecruitment = 0;
        while (index < rolls) {
            index++;
            // pity being 65 means we have failed 65 rolls already and are on the 66th and should get +5% pity
            BigDecimal pity65And80Bonus = pity65 == 80 ? new BigDecimal(1) : pity65 < 65 ? null : new BigDecimal(0.05).multiply(new BigDecimal(pity65+1-65));
            double chance6 = new BigDecimal(0.008).add(pity65 >= 65 ? pity65And80Bonus : new BigDecimal(0)).doubleValue();
            double random = Math.random();
            if (pity120 == 120) {
                chance6 = 1;
            } else if (pity120 == 0 && counterDossier2 != 0) {
                counterDossier2--;
                double randomDossier = Math.random();
                if (randomDossier < chance6) {
                    counter6++;
                    pity10 = 0;
                    pity65 = 0;
                    if (pity120 == 120 || Math.random() < 0.5) {
                        counterBanner6++;
                        pity120 = 0;
                    } else {
                        pity120++;
                    }
                } else if (pity10 == 10 || randomDossier > 0.008 && randomDossier < 0.08) {
                    counter5++;
                    pity10 = 0;
                    pity65++;
                    pity120++;
                } else {
                    counter4++;
                    pity10++;
                    pity65++;
                    pity120++;
                }
            }

            if (random < chance6) {
                counter6++;
                pity10 = 0;
                pity65 = 0;
                if (pity120 == 120 || Math.random() < 0.5) {
                    counterBanner6++;
                    pity120 = 0;
                } else {
                    pity120++;
                }
            } else if (pity10 == 10 || random > 0.008 && random < 0.08) {
                counter5++;
                pity10 = 0;
                pity65++;
                pity120++;
            } else {
                counter4++;
                pity10++;
                pity65++;
                pity120++;
            }

            if (pity120 == 30) {
                counterUrgentRecruitment++;
                int recruitUrgently = 0;
                while (recruitUrgently < 10) {
                    recruitUrgently++;
                    double randomUrgentRecruitment = Math.random();
                    if (randomUrgentRecruitment < 0.008) {
                        counter6++;
                        pity10 = 0;
                        pity65 = 0;
                        if (Math.random() < 0.5) {
                            counterBanner6++;
                            pity120 = 0;
                        }
                    } else if (random > 0.008 && random < 0.08) {
                        counter5++;
                        pity10 = 0;
                    } else {
                        counter4++;
                    }
                }
            } else if (pity120 == 60) {
                counterDossier++;
                counterDossier2++;
            }
        }
        System.out.println("total rolls (without urgent recruitment or dossier): " + index);
        System.out.println("6* banner " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* any " + counter6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter5), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter4), 5, RoundingMode.HALF_UP));
        System.out.println("\n\n" + "after accounting for\n" + counterUrgentRecruitment + " urgent recruitments\n" + counterDossier + " dossiers\n\n");
        System.out.println("6* banner  " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* any " + counter6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter5), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter4), 5, RoundingMode.HALF_UP));
        System.out.println("Simulation took " + ((double)(System.currentTimeMillis() - start))/1000 + "s");
    }

    public static void rollUrgentRecruitment(int rolls) {
        long start = System.currentTimeMillis();
        int index = 0;
        int counterBanner6 = 0, counter6 = 0, counter5 = 0, counter4 = 0;
        while (index < rolls) {
            index++;
            double random = Math.random();
            double randomUrgentRecruitment = Math.random();
            if (randomUrgentRecruitment < 0.008) {
                counter6++;
                if (Math.random() < 0.5) {
                    counterBanner6++;
                }
            } else if (random > 0.008 && random < 0.08) {
                counter5++;
            } else {
                counter4++;
            }
        }
        System.out.println("total rolls (without urgent recruitment or dossier): " + index);
        System.out.println("6* banner " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* any " + counter6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter5), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter4), 5, RoundingMode.HALF_UP));
        System.out.println("Simulation took " + ((double)(System.currentTimeMillis() - start))/1000 + "s");
    }

    public static void rollUntilBannerOperatorHuntBonuses(int rolls) {
        boolean huntUrgentRecruitment = false, huntDossier = false;
        long start = System.currentTimeMillis();
        int index = 0;
        AtomicInteger pity10 = new AtomicInteger();
        AtomicInteger pity65 = new AtomicInteger();
        AtomicInteger pity120 = new AtomicInteger();
        AtomicInteger counterBanner6 = new AtomicInteger();
        AtomicInteger counter6 = new AtomicInteger();
        AtomicInteger counter5 = new AtomicInteger();
        AtomicInteger counter4 = new AtomicInteger();
        int counterDossier = 0;
        AtomicInteger counterUrgentRecruitment = new AtomicInteger();
        while (index < rolls) {
            index++;
            // pity being 65 means we have failed 65 rolls already and are on the 66th and should get +5% pity
            BigDecimal pity65And80Bonus = pity65.get() == 80 ? new BigDecimal(1) : pity65.get() < 65 ? null : new BigDecimal(0.05).multiply(new BigDecimal(pity65.get() +1-65));
            double chance6 = new BigDecimal(0.008).add(pity65.get() >= 65 ? pity65And80Bonus : new BigDecimal(0)).doubleValue();
            double random = Math.random();
            if (pity120.get() == 120) {
                chance6 = 1;
            }

            if (random < chance6) {
                counter6.getAndIncrement();
                pity10.set(0);
                pity65.set(0);
                if (pity120.get() == 120 || Math.random() < 0.5) {
                    counterBanner6.getAndIncrement();
                    if (pity120.get() < 29 && pity120.get() >= 27) {
                        huntUrgentRecruitment = true;
                    } else if (pity120.get() < 59 && pity120.get() >= 53) {
                        huntDossier = true;
                    } else {
                        pity120.set(0); // because this is also counter for current banner, only reset after we stop hunting bonus
                    }
                } else {
                    pity120.getAndIncrement();
                }
            } else if (pity10.get() == 10 || random > 0.008 && random < 0.08) {
                counter5.getAndIncrement();
                pity10.set(0);
                pity65.getAndIncrement();
                pity120.getAndIncrement();
            } else {
                counter4.getAndIncrement();
                pity10.getAndIncrement();
                pity65.getAndIncrement();
                pity120.getAndIncrement();
            }
            if (pity120.get() == 30) {
                if (huntUrgentRecruitment) {
                    huntUrgentRecruitment = false;
                    pity120.set(0);
                }
                doUrgentRecruitment(random, counterUrgentRecruitment, pity120, pity10, pity65, counterBanner6, counter6, counter5, counter4);
            } else if (pity120.get() == 60) {
                if (huntDossier) {
                    huntDossier = false;
                    pity120.set(0);
                }
                counterDossier++;
            }
        }
        System.out.println("total rolls (without urgent recruitment or dossier): " + index);
//        System.out.println("6* banner " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counterBanner6.get()), 5, RoundingMode.HALF_UP));
//        System.out.println("6* any " + counter6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter6.get()), 5, RoundingMode.HALF_UP));
//        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter5.get()), 5, RoundingMode.HALF_UP));
//        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter4.get()), 5, RoundingMode.HALF_UP));
        System.out.println("\n\n" + "after accounting for\n" + counterUrgentRecruitment + " urgent recruitments\n" + counterDossier + " dossiers\n\n");
        System.out.println("6* banner  " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counterBanner6.get()), 5, RoundingMode.HALF_UP));
        System.out.println("6* any " + counter6 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counter6.get()), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counter5.get()), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index-counterDossier*10).divide(new BigDecimal(counter4.get()), 5, RoundingMode.HALF_UP));
        System.out.println("Simulation took " + ((double)(System.currentTimeMillis() - start))/1000 + "s");
    }

    private static void doUrgentRecruitment(Double random,
                                            AtomicInteger counterUrgentRecruitment,
                                            AtomicInteger pity120,
                                            AtomicInteger pity10,
                                            AtomicInteger pity65,
                                            AtomicInteger counterBanner6,
                                            AtomicInteger counter6,
                                            AtomicInteger counter5,
                                            AtomicInteger counter4) {
        counterUrgentRecruitment.incrementAndGet();
        int recruitUrgently = 0;
        while (recruitUrgently < 10) {
            recruitUrgently++;
            double randomUrgentRecruitment = Math.random();
            if (randomUrgentRecruitment < 0.008) {
                counter6.incrementAndGet();
                pity10.set(0);
                pity65.set(0);
                if (Math.random() < 0.5) {
                    counterBanner6.incrementAndGet();
                    pity120.set(0);
                }
            } else if (random > 0.008 && random < 0.08) {
                counter5.incrementAndGet();
                pity10.set(0);
            } else {
                counter4.incrementAndGet();
            }
        }
    }

    public static void rollAlwaysLessThan30(int rolls) {
        long start = System.currentTimeMillis();
        int index = 0, pity10 = 0, pity65 = 0;
        int counterBanner6 = 0, counter6 = 0, counter5 = 0, counter4 = 0;
        while (index < rolls) {
            index++;
            // pity being 65 means we have failed 65 rolls already and are on the 66th and should get +5% pity
            BigDecimal pity65And80Bonus = pity65 == 80 ? new BigDecimal(1) : pity65 < 65 ? null : new BigDecimal(0.05).multiply(new BigDecimal(pity65+1-65));
            double chance6 = new BigDecimal(0.008).add(pity65 >= 65 ? pity65And80Bonus : new BigDecimal(0)).doubleValue();
            double random = Math.random();
            if (random < chance6) {
                counter6++;
                pity10 = 0;
                pity65 = 0;
                if (Math.random() < 0.5) {
                    counterBanner6++;
                }
            } else if (random > 0.008 && random < 0.08) {
                pity10 = 0;
                counter5++;
                pity65++;
            } else if (pity10 == 10 || random > 0.008 && random < 0.08) {
                counter5++;
                pity10 = 0;
                pity65++;
            } else {
                counter4++;
                pity10++;
                pity65++;
            }
        }
        System.out.println("total rolls: " + index);
        System.out.println("6* banner " + counterBanner6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counterBanner6), 5, RoundingMode.HALF_UP));
        System.out.println("6* " + counter6 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter6), 5, RoundingMode.HALF_UP));
        System.out.println("5* " + counter5 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter5), 5, RoundingMode.HALF_UP));
        System.out.println("4* " + counter4 + " total; avg rolls for one are " + new BigDecimal(index).divide(new BigDecimal(counter4), 5, RoundingMode.HALF_UP));
        System.out.println("Simulation took " + ((double)(System.currentTimeMillis() - start))/1000 + "s");
    }

    public static void main(String[] args) {
        int rolls = 1_000_000_0;
//        int index = 0;
//        while (index < rolls) {
//            index++;
//        }
//        double random = Math.random();
        rollWithoutUrgRecAndDoss(rolls);
        System.out.println("\n\n=====rollUntilBannerOperator======\n\n");
        rollUntilBannerOperator(rolls);
        System.out.println("\n\n=====rollUntilBannerOperatorHuntBonuses======\n\n");
        rollUntilBannerOperatorHuntBonuses(rolls);
        System.out.println("\n\n=====roll60Always======\n\n");
        roll60Always(rolls);
        System.out.println("\n\n=====rollAlwaysLessThan30======\n\n");
        rollAlwaysLessThan30(rolls);
        System.out.println("\n\n=====rollWeapons======\n\n");
        rollWeapons(rolls/10);
        System.out.println("\n\n=====rollUntilBannerOperatorSkipDossierBanner======\n\n");
        rollUntilBannerOperatorSkipDossierBanner(rolls);
        System.out.println("\n\n=====rollUrgentRecruitment======\n\n");
        rollUrgentRecruitment(rolls); // this is more of a sanity test, really
    }
}

