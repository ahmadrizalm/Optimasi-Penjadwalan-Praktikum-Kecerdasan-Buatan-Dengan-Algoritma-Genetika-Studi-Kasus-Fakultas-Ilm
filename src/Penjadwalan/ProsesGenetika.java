package Penjadwalan;

/**
 *
 * @author Diva
 */
public class ProsesGenetika {

    private int popSize, generasi, iterasi, hasilCr[][], hasilMr[][];
    private double pc, pm, maxFitness;
    private int[] gen = new int[20];
    private int[] cekTabrakan;
    private int[] cekAsistenKosong;
    private int[] cekDosenSama;
    private int[] hasilIndex;
    private int[] cekJadwalBerlebih;
    private double[] fitness;
    private int[][] kromosom;
    private int[][] hasilKromosom;
    private int[] dDosen = {1, 3, 0, 1, 2, 0, 6, 1, 4, 5};
    private String[] dataDosen = new String[7];
    private String[] dataAsisten = new String[13];
    private double[] x_absis;
    private double[] y_ordinat;

    public ProsesGenetika() {
        dataDosen();
        dataAsisten();
    }

    public void dataDosen() {
        dataDosen[0] = "Satrio";
        dataDosen[1] = "Lailil";
        dataDosen[2] = "Ika";
        dataDosen[3] = "Hannats";
        dataDosen[4] = "Ali";
        dataDosen[5] = "Imam";
        dataDosen[6] = "Nurizal";
    }

    public void dataAsisten() {
        dataAsisten[0] = "Syafiq";
        dataAsisten[1] = "Kadafi";
        dataAsisten[2] = "Sabrina";
        dataAsisten[3] = "Diva";
        dataAsisten[4] = "Nanda";
        dataAsisten[5] = "Radita";
        dataAsisten[6] = "Danes";
        dataAsisten[7] = "Andri";
        dataAsisten[8] = "Karina";
        dataAsisten[9] = "Anan";
        dataAsisten[10] = "Agung";
        dataAsisten[11] = "Ardi";
        dataAsisten[12] = "Fathor";
    }

    public void parameter(double cr, double mr, int popSize, int iterasi) {
        this.pc = cr;
        this.pm = mr;
        this.generasi = iterasi;
        this.popSize = popSize;
        this.x_absis = new double[iterasi];
        this.y_ordinat = new double[iterasi];
    }

    public void populasiKromosom() {
        int temp = 0;
        kromosom = new int[this.popSize][gen.length];
        for (int i = 0; i < this.popSize; i++) {
            for (int j = 0; j < gen.length; j++) {
                temp = (int) (Math.random() * 13);
                if (j > 0) {
                    while (temp == kromosom[i][j - 1]) {
                        temp = (int) (Math.random() * 13);
                    }
                }
                kromosom[i][j] = temp;
            }
        }
        System.out.println("HASIL POPULASI AWAL----------");
        // int inisialisasi=0;
        for (int i = 0; i < this.popSize; i++) {
            System.out.print("==Individu [" + i + "]");
            for (int j = 0; j < gen.length; j++) {
                if (j == 0) {
                    System.out.println("");
                    System.out.print("R.1 : ");
                }

                if (j == 10) {
                    System.out.println("");
                    System.out.print("R.2 : ");
                }
                if (j % 2 == 0) {
                    if (j > 0 && j < 10 || j > 10) {
                        System.out.print(" | ");
                    }
                    System.out.print("[[" + dataDosen[dDosen[j / 2]] + "]]->");

                }
                System.out.print("[" + dataAsisten[kromosom[i][j]] + "]");
            }
            System.out.println("");
        }
        this.kromosom = kromosom;

        prosesIterasi(kromosom);
    }

    public void crossoverKromosom(int[][] kromosom) {
        //---inisialisasi variabel
        int posisi[] = new int[2];
        int offspringCr = (int) Math.ceil(this.pc * popSize);
        hasilCr = new int[offspringCr][gen.length];
        int individu1, individu2, cutCr, tempIndividu = 0, iterasiCut = 0,
                jumChild = 0, pilihIndividu = 0, child = 0, iterasi = 0, stop;

        for (int i = 0; i < Math.ceil(offspringCr / 2); i++) {
            individu1 = (int) (Math.random() * kromosom.length);
            individu2 = (int) (Math.random() * kromosom.length);

            while (individu1 == individu2) {
                individu2 = (int) (Math.random() * kromosom.length);
            }

            cutCr = (int) (Math.random() * kromosom[0].length - 1);
            System.out.print("~Individu Terpilih : " + individu1 + " dan " + individu2);
            System.out.println(", Cut-point Pada Gen " + cutCr);
            if (jumChild % 2 == 0) {

                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < gen.length; j++) {
                        if (k == 0) {
                            if (j > cutCr) {
                                hasilCr[child][j] = kromosom[individu2][iterasiCut];
                            } else {
                                hasilCr[child][j] = kromosom[individu1][iterasiCut];
                            }

                        } else {
                            if (j > cutCr) {
                                hasilCr[child][j] = kromosom[individu1][iterasiCut];
                            } else {
                                hasilCr[child][j] = kromosom[individu2][iterasiCut];
                            }
                        }
                        iterasiCut++;
                    }
                    iterasiCut = 0;
                    child++;
                }
            } else {
                pilihIndividu = (int) (Math.random() * 2);
                if (pilihIndividu == 1) {
                    individu1 = tempIndividu;
                    individu1 = individu2;
                    individu2 = tempIndividu;
                }

                for (int j = 0; j < gen.length; j++) {
                    if (j > cutCr) {
                        hasilCr[child][j] = kromosom[individu1][iterasiCut];
                    } else {
                        hasilCr[child][j] = kromosom[individu2][iterasiCut];
                    }
                    iterasiCut++;
                }
                iterasiCut = 0;
            }
            jumChild = offspringCr - child;
        }

        this.hasilCr = hasilCr;

        System.out.println("HASIL CROSSOVER----------");
       
            for (int i = 0; i < hasilCr.length; i++) {
                System.out.print("==Individu [" + i + "]");
                for (int j = 0; j < hasilCr[0].length; j++) {
                    if (j == 0) {
                        System.out.println("");
                        System.out.print("R.1 : ");
                    }

                    if (j == 10) {
                        System.out.println("");
                        System.out.print("R.2 : ");
                    }
                    if (j % 2 == 0) {
                        if (j > 0 && j < 10 || j > 10) {
                            System.out.print(" | ");
                        }
                        System.out.print("[[" + dataDosen[dDosen[j / 2]] + "]]->");

                    }
                    System.out.print("[" + dataAsisten[hasilCr[i][j]] + "]");
                }
                System.out.println("");
            }
        
    }

    public void mutasiKromosom(int[][] kromosom) {
        System.out.println("-------------------------------------------");
        //---inisialisasi variabel
        int posisi[] = new int[2];
        int range[] = new int[2];
        int temp = 0;
        int[] simpan;
        int offspringMr = (int) Math.ceil(this.pm * popSize);
        hasilMr = new int[offspringMr][gen.length];

        //---acak gen yg dimutasi
        for (int i = 0; i < offspringMr; i++) {
            int pilihPosisi = 0;
            int acakPopsize = (int) (Math.random() * kromosom.length);

            for (int k = 0; k < gen.length; k++) {
                hasilMr[i][k] = kromosom[acakPopsize][k];
            }

            System.out.print("~Individu Terpilih : " + acakPopsize);
            //---proses mutasi
            while (pilihPosisi < 2) {
                posisi[pilihPosisi] = (int) (Math.random() * kromosom[0].length);
                while (pilihPosisi > 0 && posisi[pilihPosisi] == posisi[pilihPosisi - 1]) {
                    posisi[pilihPosisi] = (int) (Math.random() * kromosom[0].length);
                }

                if (pilihPosisi % 2 == 1) {
                    System.out.println(", Mutasi Pada Gen " + posisi[0] + " dan " + posisi[1]);
                    temp = hasilMr[i][posisi[pilihPosisi]];
                    hasilMr[i][posisi[pilihPosisi]] = hasilMr[i][posisi[pilihPosisi - 1]];
                    hasilMr[i][posisi[pilihPosisi - 1]] = temp;
                }
                pilihPosisi++;
            }
        }
        this.hasilMr = hasilMr;

        System.out.println("HASIL MUTASI----------");
            for (int i = 0; i < hasilMr.length; i++) {
                System.out.print("==Individu [" + i + "]");
                for (int j = 0; j < hasilMr[0].length; j++) {
                    if (j == 0) {
                        System.out.println("");
                        System.out.print("R.1 : ");
                    }

                    if (j == 10) {
                        System.out.println("");
                        System.out.print("R.2 : ");
                    }
                    if (j % 2 == 0) {
                        if (j > 0 && j < 10 || j > 10) {
                            System.out.print(" | ");
                        }
                        System.out.print("[[" + dataDosen[dDosen[j / 2]] + "]]->");

                    }
                    System.out.print("[" + dataAsisten[hasilMr[i][j]] + "]");
                }
                System.out.println("");
            }
        
    }

    public void evaluasiKromosom() {
        int jumKromosom = (kromosom.length + hasilCr.length + hasilMr.length);
        hasilKromosom = new int[jumKromosom][kromosom[0].length];
        cekJadwalBerlebih = new int[jumKromosom];
        cekTabrakan = new int[jumKromosom];
        cekAsistenKosong = new int[jumKromosom];
        cekDosenSama = new int[jumKromosom];
        fitness = new double[jumKromosom];
        double hasilFitness = 0;
        System.out.println("HASIL KROMOSOM ----------");

        for (int i = 0; i < this.popSize; i++) {
            for (int j = 0; j < this.gen.length; j++) {
                hasilKromosom[i][j] = this.kromosom[i][j];
            }
        }

        //data cr        
        int jumKromosom2 = (this.kromosom.length + this.hasilCr.length);
        int individu = 0;
        for (int i = this.kromosom.length; i < jumKromosom2; i++) {
            for (int j = 0; j < hasilCr[0].length; j++) {
                hasilKromosom[i][j] = hasilCr[individu][j];
            }
            individu++;
        }

        //data mr
        individu = 0;
        for (int i = jumKromosom2; i < jumKromosom; i++) {
            for (int j = 0; j < kromosom[0].length; j++) {
                hasilKromosom[i][j] = hasilMr[individu][j];
            }
            individu++;
        }

//        for (int m = 0; m < hasilKromosom.length; m++) {
//            for (int i = 0; i < hasilKromosom[0].length; i++) {
//                System.out.print(hasilKromosom[m][i] + " ");
//            }
//            System.out.println("");
//        }
        //cek jadwal
        int jadwal, jadwal2, loop = 0;
        int hitungJadwal = 0;
        for (int i = 0; i < hasilKromosom.length; i++) {
            int jumJadwalBerlebih = 0;
            for (int j = 0; j < hasilKromosom[0].length; j++) {
                while (loop < hasilKromosom[0].length) {
                    jadwal = hasilKromosom[i][j];
                    jadwal2 = hasilKromosom[i][loop];
                    // System.out.println("data1 " + data1 + " data2 " + data2);
                    if (jadwal == jadwal2) {
                        // System.out.println(i + " Hitung " + hitung + " jadwal " + jumJadwal);
                        hitungJadwal++;
                    }
                    loop++;
                }
                if (hitungJadwal > 3) {
                    jumJadwalBerlebih++;
                }
                loop = 0;
                hitungJadwal = 0;
            }
            cekJadwalBerlebih[i] = jumJadwalBerlebih;
//            System.out.println("hasil " + cekJadwal[i]);
//            System.out.println("-------------------------------");
        }

        //cek kelas
        int jumKelas = 0, ruang = 0;
        int tempRuang = 0;
        int ruang2 = (hasilKromosom[0].length / 2);
        for (int i = 0; i < hasilKromosom.length; i++) {
            while (ruang < (hasilKromosom[0].length / 2) && ruang2 < hasilKromosom[0].length) {
                //System.out.println("Ruang1 = " + j + " , , Ruang2 = " + loop);
                //System.out.println(hasilKromosom[i][j] + " ," + hasilKromosom[i][loop]);
                if (hasilKromosom[i][ruang] == hasilKromosom[i][ruang2]) {
                    //  System.out.println("SAMA ");
                    tempRuang++;
                }

                if (ruang % 2 == 0) {
                    if (ruang2 % 2 == 1) {
                        ruang2--;
                        ruang++;
                        //System.out.println("PING");
                    } else {
                        ruang2++;
                        //System.out.println("PONG");
                    }
                } else {
                    if (ruang2 % 2 == 0) {
                        ruang2++;
                        //System.out.println("CUY");
                    } else {
                        if (tempRuang > 0) {
                            jumKelas++;
                            //System.out.println("JUM " + jumKelas);
                        }
                        ruang++;
                        ruang2++;
                        //    System.out.println("IHIR");
                    }
                }
            }
            //     System.out.println("hasil " + cekAsisten[i]);
            //System.out.println("-------------------------------");
            cekTabrakan[i] = jumKelas;
            ruang = 0;
            ruang2 = 0;
            jumKelas = 0;
            tempRuang = 0;
        }
//
//        for (int i = 0; i < hasilKromosom.length; i++) {
//            System.out.println("Asisten Tabrakan " + cekTabrakan[i]);
//
//        }

        //cekKosong
        int jumAda = 0, jumKosong = 0;
        for (int i = 0; i < hasilKromosom.length; i++) {
            for (int k = 0; k < 13; k++) {
                for (int l = 0; l < hasilKromosom[0].length; l++) {
                    //System.out.println("i = " + i + ", k = " + k + " , l = " + l);
                    if (k == hasilKromosom[i][l]) {
                        jumAda = 1;
                        //System.out.println(hasilKromosom[i][l] + "ADA");
                        continue;
                    }
                    if (jumAda == 0 && l == (hasilKromosom[0].length - 1)) {
                        jumKosong++;
                        //  System.out.println("JUMLAH " + jumKosong);
                    }
                }
                jumAda = 0;
            }
            cekAsistenKosong[i] = jumKosong;
            jumKosong = 0;
        }
//        for (int i = 0; i < hasilKromosom.length; i++) {
//            System.out.println("Asisten Kosong " + cekKosong[i]);
//
//        }

        //cekDosenSama
        int jumDosenSama = 0;
        int tempAsisten = 0, tempAsisten2 = 0;
        int jum = 0;
        for (int i = 0; i < hasilKromosom.length; i++) {
            for (int k = 0; k < 13; k++) {
                for (int l = 0; l < hasilKromosom[0].length; l++) {
                    //System.out.println("i = " + i + ", k = " + k + " , l = " + l);
                    if (k == hasilKromosom[i][l]) {
                        tempAsisten = (int) Math.floor(l / 2);
                        //System.out.println("Dosen " + dDosen[tempDosen]);
                        if (jumDosenSama > 0) {
                            if (dDosen[tempAsisten] == dDosen[tempAsisten2]) {
                                // System.out.println("Dosen 1" + dDosen[tempDosen] + "Dosen 2" + dDosen[tempDosen2]);
                                jumDosenSama++;
                            }
                        }
                        tempAsisten2 = tempAsisten;
                        jum++;
                    }
                    if (tempAsisten == 2) {
                        continue;
                    }
                }
                jum = 0;
            }
            cekDosenSama[i] = tempAsisten;
            tempAsisten = 0;
        }
//        for (int i = 0; i < hasilKromosom.length; i++) {
//            System.out.println("Dosen sama " + cekDosen[i]);
//
//        }

        //hitung fitness
        for (int i = 0; i < jumKromosom; i++) {
            hasilFitness = (double) (1) / (double) (1 + cekAsistenKosong[i] + cekDosenSama[i] + cekJadwalBerlebih[i] + cekTabrakan[i]);
            fitness[i] = hasilFitness;
            //  System.out.println("Fitness " +fitness[i]);
        }

       
            for (int i = 0; i < hasilKromosom.length; i++) {
                System.out.print("==Individu [" + i + "]");
                for (int j = 0; j < hasilKromosom[0].length; j++) {
                    if (j == 0) {
                        System.out.println("");
                        System.out.print("R.1 : ");
                    }

                    if (j == 10) {
                        System.out.println("");
                        System.out.print("R.2 : ");
                    }
                    if (j % 2 == 0) {
                        if (j > 0 && j < 10 || j > 10) {
                            System.out.print(" | ");
                        }
                        System.out.print("[[" + dataDosen[dDosen[j / 2]] + "]]->");

                    }
                    System.out.print("[" + dataAsisten[hasilKromosom[i][j]] + "]");
                }
                System.out.println("");
                System.out.println("Fitness " + fitness[i]);
            }
        
        this.fitness = fitness;
    }

    public void seleksiKromosom(double[] hasilFitness) {
        int jumKromosom = (kromosom.length + hasilCr.length + hasilMr.length), tempIndex;
        double tempFitness;
        this.hasilIndex = new int[jumKromosom];

        for (int i = 0; i < jumKromosom; i++) {
            hasilIndex[i] = i;
        }

        //----sorting data
        for (int n = 0; n < jumKromosom; n++) {
            for (int p = 1; p < jumKromosom - n; p++) {
                if (fitness[p - 1] < fitness[p]) {
                    tempFitness = hasilFitness[p - 1];
                    hasilFitness[p - 1] = hasilFitness[p];
                    hasilFitness[p] = tempFitness;
                    tempIndex = hasilIndex[p - 1];
                    hasilIndex[p - 1] = hasilIndex[p];
                    hasilIndex[p] = tempIndex;
                }
            }
        }

        System.out.println("KROMOSOM BARU ----------");

        for (int i = 0; i < this.popSize; i++) {
            System.out.print("==Individu [" + i + "]");
            for (int j = 0; j < gen.length; j++) {
                int nilai = hasilIndex[i];
                kromosom[i][j] = hasilKromosom[nilai][j];
                this.kromosom = kromosom;
                
                if (j == 0) {
                    System.out.println("");
                    System.out.print("R.1 : ");
                }

                if (j == 10) {
                    System.out.println("");
                    System.out.print("R.2 : ");
                }
                if (j % 2 == 0) {
                    if (j > 0 && j < 10 || j > 10) {
                        System.out.print(" | ");
                    }
                    System.out.print("[[" + dataDosen[dDosen[j / 2]] + "]]->");

                }
                System.out.print("[" + dataAsisten[kromosom[i][j]] + "]");
            }
            System.out.println("");
            System.out.println("Fitness " +hasilFitness[i]);
        }

        maxFitness(fitness[0]);
        this.x_absis[(this.iterasi - 1)] = this.iterasi - 1;
        this.y_ordinat[(this.iterasi - 1)] = this.maxFitness * 100;
        System.out.println(y_ordinat[(this.iterasi - 1)]);
    }

    public void maxFitness(double fitness) {
        if (fitness > maxFitness) {
            this.maxFitness = fitness;
        }
    }

    public void prosesIterasi(int[][] kromosom) {
        iterasi++;
        while (iterasi < generasi + 1) {
            System.out.println("");
            System.out.println("GENERASI [" + iterasi + "]");
            crossoverKromosom(kromosom);
            mutasiKromosom(kromosom);
            evaluasiKromosom();
            seleksiKromosom(this.fitness);
            prosesIterasi(this.kromosom);
        }

    }

    public static void main(String[] args) {
        ProsesGenetika pg = new ProsesGenetika();
        pg.parameter(0.8, 0.05, 5, 50);
        pg.populasiKromosom();

        Grafik ccd = new Grafik();
        ccd.PlotAllDataFitness(pg.x_absis, pg.y_ordinat);
    }
}
