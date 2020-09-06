package machine;
import java.util.Scanner;

public class CoffeeMachine {
    Scanner sc = new Scanner(System.in);
    Storage sS = new Storage(400, 540, 120, 9, 550);

    //MAIN FUNCTION
    public static void main(String[] args) {
        var coffeeMachine = new CoffeeMachine();
        coffeeMachine.beroperasi();
    }

    enum Minuman {
        ESPRESSO("1",250, 0, 16, 4),
        LATTE("2",350, 75, 20, 7),
        CAPPUCCINO("3",200, 100, 12, 6);

        String kKode;
        int aAir;
        int sSusu;
        int kKopi;
        int uUang;

        //constructor
        Minuman(String kKode, int aAir, int sSusu, int kKopi, int uUang) {
            this.kKode = kKode;
            this.aAir = aAir;
            this.sSusu = sSusu;
            this.kKopi = kKopi;
            this.uUang = uUang;
        }
        //getters
        public int getaAir() {
            return aAir;
        }
        public int getsSusu() {
            return sSusu;
        }
        public int getkKopi() {
            return kKopi;
        }
        public int getuUang() {
            return uUang;
        }
        //searcher by kKode
        public static Minuman findByKKode(String kKode) {
            for (Minuman value : values()) {
                if (kKode.equals(value.kKode)) {
                    return value;
                }
            }
            return null;
        }
    }

    static class BikinKopi {
        private String kodeKopi;

        //constructor
        public BikinKopi(String kodeKopi) {
            this.kodeKopi = kodeKopi;
        }

        public void buat(Storage sS) {
            Minuman num = Minuman.findByKKode(kodeKopi);

            if (cekKecukupan(num, sS)) {
                eksekusiKopi(sS, num.getaAir(), num.getsSusu(), num.getkKopi(), num.getuUang());
            } else {
                gaCukup(sS, num.getaAir(), num.getsSusu(), num.getkKopi());
            }
        }

        private boolean cekKecukupan(Minuman num, Storage sS) {
            boolean cukup = sS.getAir() >= num.getaAir() && sS.getSusu() >= num.getsSusu()
                    && sS.getKopi() >= num.getkKopi() && sS.getCup() >= 1;

            if(cukup) {
                System.out.println("I have enough resources, making you a coffee!");
            }
            return cukup;
        }

        private void gaCukup(Storage sS, int cAir, int cSusu, int cKopi) {
            String ygGaCukup = "cup";

            if (sS.getAir() - cAir < 0) {
                ygGaCukup = "water";
            } else if (sS.getSusu() - cSusu < 0) {
                ygGaCukup = "milk";
            } else  if (sS.getKopi() - cKopi < 0) {
                ygGaCukup = "coffee bean";
            }

            System.out.println("Sorry, not enough " + ygGaCukup + " !");
        }

        private void eksekusiKopi(Storage sS, int cAir, int cSusu, int cKopi, int cUang) {
            sS.setAir(sS.getAir() - cAir);
            sS.setSusu(sS.getSusu() - cSusu);
            sS.setKopi(sS.getKopi() - cKopi);
            sS.setUang(sS.getUang() + cUang);
            sS.setCup(sS.getCup() - 1);
        }
    }

    static class Storage {
        private int air;
        private int susu;
        private int kopi;
        private int cup;
        private int uang;

        public Storage(int air, int susu, int kopi, int cup, int uang) {
            this.air = air;
            this.susu = susu;
            this.kopi = kopi;
            this.cup = cup;
            this.uang = uang;
        }

        public int getAir() {
            return air;
        }
        public int getSusu() {
            return susu;
        }
        public int getKopi() {
            return kopi;
        }
        public int getCup() {
            return cup;
        }
        public int getUang() {
            return uang;
        }

        public void setAir(int air) {
            this.air = air;
        }
        public void setSusu(int susu) {
            this.susu = susu;
        }
        public void setKopi(int kopi) {
            this.kopi = kopi;
        }
        public void setCup(int cup) {
            this.cup = cup;
        }
        public void setUang(int uang) {
            this.uang = uang;
        }


    }

    private void beroperasi() {
        String pilihan = "init";

        while (pilihan != "exit") {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            pilihan = sc.next();
            System.out.println();

            switch (pilihan) {
                case "buy":
                    beliKopi();
                    break;
                case "fill":
                    isiMesin();
                    break;
                case "take":
                    ambilUang();
                    break;
                case "remaining":
                    cetak();
                    break;
                case "exit":
                    pilihan = "exit";
                    break;
                default:
                    break;
            }
        }
    }

    private void beliKopi() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String minuman = sc.next();

        switch (minuman) {
            case "1": //buy espresso
            case "2": //buy latte
            case "3": //buy cappuccino
                new BikinKopi(minuman).buat(sS);
                break;
            case "back": //back to main menu
                break;
            default:
                break;
        }
    }

    private void isiMesin() {
        System.out.println("Write how many ml of water do you want to add");
        sS.setAir(sS.getAir() + sc.nextInt());
        System.out.println("Write how many ml of milk do you want to add:");
        sS.setSusu(sS.getSusu() + sc.nextInt());
        System.out.println("Write how many grams of coffee beans do you want to add:");
        sS.setKopi(sS.getKopi() + sc.nextInt());
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        sS.setCup(sS.getCup() + sc.nextInt());
    }

    private void ambilUang() {
        System.out.println("I gave you $" + sS.getUang());
        sS.setUang(0);
    }

    private void cetak() {
        System.out.println("The coffee machine has:");
        System.out.println(sS.getAir() + " of water");
        System.out.println(sS.getSusu() + " of milk");
        System.out.println(sS.getKopi() + " of coffee beans");
        System.out.println(sS.getCup() + " of disposable cups");
        System.out.println(sS.getUang() + " of money");
    }
}
